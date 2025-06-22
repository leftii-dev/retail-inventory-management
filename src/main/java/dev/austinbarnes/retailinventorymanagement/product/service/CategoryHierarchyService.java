package dev.austinbarnes.retailinventorymanagement.product.service;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyFilterDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyRequestDTO;
import dev.austinbarnes.retailinventorymanagement.product.dto.category.CategoryHierarchyResponseDTO;
import dev.austinbarnes.retailinventorymanagement.product.entity.CategoryHierarchy;
import dev.austinbarnes.retailinventorymanagement.product.mapper.CategoryHierarchyMapper;
import dev.austinbarnes.retailinventorymanagement.product.repo.CategoryHierarchyRepository;
import dev.austinbarnes.retailinventorymanagement.product.specification.CategoryHierarchySpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * CategoryHierarchyService handles all operations related to category hierarchies.
 * <p>
 * It provides methods to create, update, retrieve, and delete category hierarchies.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryHierarchyService {
    private final CategoryHierarchyRepository repository;
    private CategoryHierarchyMapper mapper;

    /**
     * Create new CategoryHierarchy.
     *
     * @param request request DTO containing the details of the category hierarchy to be created.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'MANAGER') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<CategoryHierarchyResponseDTO>> createCategoryHierarchy(CategoryHierarchyRequestDTO request) {
        log.info("Create category hierarchy {}", request);
        return ApiResponseDto.created(isManager() ?
                mapper.toDetailDTO(repository.save(mapper.toEntity(request))) :
                mapper.toBasicDTO(repository.save(mapper.toEntity(request)))
        );
    }

    /**
     * Retrieves a category hierarchy by its ID.
     *
     * @param id The ID of the category hierarchy to retrieve.
     * @return ResponseEntity with the category hierarchy details.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<CategoryHierarchyResponseDTO>> getCategoryHierarchy(UUID id) {
        log.info("Get category hierarchy {}", id);
        return ApiResponseDto.ok(repository.findById(id).map(categoryHierarchy ->
                        isManager() ?
                                mapper.toDetailDTO(categoryHierarchy) :
                                mapper.toBasicDTO(categoryHierarchy)
                ).orElseThrow(() -> new EntityNotFoundException("No CategoryHierarchy found with id %s".formatted(id)))
        );
    }

    /**
     * Retrieves all category hierarchies.
     *
     * @return ResponseEntity with a list of all category hierarchies.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseDto<List<CategoryHierarchyResponseDTO>>> getCategoryHierarchies(
            CategoryHierarchyFilterDTO filterDTO, Pageable pageable
    ) {
        log.info("Get category hierarchies");
        Specification<CategoryHierarchy> spec = CategoryHierarchySpecifications.applyFilters(filterDTO);
        return ApiResponseDto.ok(repository.findAll(spec, pageable).stream().map( categoryHierarchy ->
                isManager() ?
                        (CategoryHierarchyResponseDTO) mapper.toDetailDTO(categoryHierarchy) :
                        mapper.toBasicDTO(categoryHierarchy)
                                ).toList()
        );
    }

    /**
     * Updates an existing category hierarchy.
     *
     * @param id      The ID of the category hierarchy to update.
     * @param request The request DTO containing updated category hierarchy details.
     * @return ResponseEntity with the updated category hierarchy details.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'EMPLOYEE') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<CategoryHierarchyResponseDTO>> updateCategoryHierarchy(UUID id, CategoryHierarchyRequestDTO request) {
        log.info("Update category hierarchy {}", id);
        CategoryHierarchy target = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No CategoryHierarchy found with id %s".formatted(id)));
        mapper.updateEntityFromRequest(request, target);
        return ApiResponseDto.ok(isManager() ?
                (CategoryHierarchyResponseDTO) mapper.toDetailDTO(repository.save(target)):
                mapper.toBasicDTO(repository.save(target))
        );
    }

    /**
     * Deletes a category hierarchy by its ID.
     *
     * @param id The ID of the category hierarchy to delete.
     * @return ResponseEntity with no content status.
     */
    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN') and hasAuthority('WRITE_PRODUCT')")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategoryHierarchy(UUID id) {
        log.info("Delete category hierarchy {}", id);
        repository.deleteById(id);
        return ApiResponseDto.noContent();
    }

    /**
     * Determines is the current user has manager or admin role.
     *
     * @return true if the user has manager or admin role, false otherwise.
     */
    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
