package dev.austinbarnes.retailinventorymanagement.auth.service;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserRequestDto;
import dev.austinbarnes.retailinventorymanagement.auth.dto.UserResponseDto;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.mapper.UserMapper;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getUserByID(UUID id) {
        if (isManager()) {
            return ApiResponseDto.ok(repository.findById(id)
                    .map(mapper::toDetailDto)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID: %s not found".formatted(id))));
        }
        CustomUserPrincipal principal = (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getId().equals(id)) {
            return ApiResponseDto.ok(repository.findById(id)
                    .map(mapper::toDetailDto)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID: %s not found".formatted(id))));
        }
        throw new AccessDeniedException("Without elevated permissions, you can only access your own user details.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getAllUsers() {
        return ApiResponseDto.ok(repository.findAll().stream()
                .map(user -> (UserResponseDto) mapper.toDetailDto(user))
                .toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> createUser(UUID id, UserRequestDto userRequestDto) {
        User target = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID: %s not found".formatted(id)));
        mapper.updateEntityFromRequest(userRequestDto, target);

        return ApiResponseDto.ok(mapper.toDetailDto(repository.save(target)));
    }

    private boolean isManager() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("ROLE_MANAGER") || authority.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
