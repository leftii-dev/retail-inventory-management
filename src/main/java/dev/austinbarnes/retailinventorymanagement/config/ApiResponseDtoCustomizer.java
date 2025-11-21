package dev.austinbarnes.retailinventorymanagement.config;

import dev.austinbarnes.retailinventorymanagement.common.ApiResponseDto;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApiResponseDtoCustomizer customizes the OpenAPI documentation to wrap response schemas
 * in the ApiResponseDto structure. It scans controller methods to determine the data types
 * used in ApiResponseDto and modifies the OpenAPI paths accordingly.
 */
@Configuration
@SuppressWarnings("unchecked")
public class ApiResponseDtoCustomizer {
    // Handler mapping to access controller methods
    private final RequestMappingHandlerMapping handlerMapping;

    public ApiResponseDtoCustomizer(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    /**
     * Customizes the OpenAPI documentation to wrap response schemas in ApiResponseDto.
     *
     * @return OpenApiCustomizer that modifies the OpenAPI paths
     */
    @Bean
    public OpenApiCustomizer customizeApiResponses() {
        Map<String, Class<?>> operationIdToDataType = extractDataTypesFromControllerMethods();

        // Return the OpenApiCustomizer
        return openApi -> {
            if (openApi.getPaths() == null || openApi.getComponents() == null) return;

            Components components = openApi.getComponents();

            openApi.getPaths().forEach((path, pathItem) -> {
                for (PathItem.HttpMethod httpMethod : PathItem.HttpMethod.values()) {
                    Operation operation = pathItem.readOperationsMap().get(httpMethod);
                    if (operation == null) continue;

                    String operationId = operation.getOperationId();
                    if (operationId == null) continue;

                    Class<?> dataType = operationIdToDataType.get(operationId);
                    if (dataType == null || dataType == Void.class) continue;

                    ApiResponses responses = operation.getResponses();
                    if (responses == null) continue;

                    for (ApiResponse response : responses.values()) {
                        Content content = response.getContent();
                        if (content == null) continue;

                        content.forEach((mediaType, media) -> {
                            Schema<?> wrapper = createApiResponseDtoSchema(dataType, components);
                            if (wrapper != null) {
                                media.setSchema(wrapper);
                            }
                        });
                    }
                }
            });
        };
    }

    /**
     * Extracts the data types used in ApiResponseDto from controller methods.
     *
     * @return Map of operation IDs to their corresponding data types
     */
    private Map<String, Class<?>> extractDataTypesFromControllerMethods() {
        Map<String, Class<?>> operationIdToType = new HashMap<>();

        handlerMapping.getHandlerMethods().forEach((info, handler) -> {
            if (!handler.getBeanType().isAnnotationPresent(RestController.class)) return;

            Type returnType = handler.getMethod().getGenericReturnType();
            ResolvableType type = ResolvableType.forType(returnType);

            if (!ResponseEntity.class.isAssignableFrom(type.toClass())) return;

            ResolvableType bodyType = type.getGeneric(0);
            if (!ApiResponseDto.class.isAssignableFrom(bodyType.toClass())) return;

            ResolvableType dataType = bodyType.getGeneric(0);
            Class<?> resolved = dataType.resolve();

            if (resolved != null) {
                operationIdToType.put(handler.getMethod().getName(), resolved);
            }
        });

        return operationIdToType;
    }

    /**
     * Creates a Schema representing the ApiResponseDto structure for the given data type.
     *
     * @param dataType   The data type to be wrapped
     * @param components The OpenAPI components
     * @return Schema representing ApiResponseDto with the specified data type
     */
    private Schema<?> createApiResponseDtoSchema(Class<?> dataType, Components components) {
        Schema<?> dataSchema;

        if (dataType.isInterface()) {
            List<Schema<?>> oneOfSchemas = new ArrayList<>();
            for (Class<?> impl : findImplementations(dataType)) {
                oneOfSchemas.add(new Schema<>().$ref("#/components/schemas/" + impl.getSimpleName()));
            }
            if (oneOfSchemas.isEmpty()) return null;

            dataSchema = new ComposedSchema().oneOf((List<Schema>) (List<?>) oneOfSchemas);

        } else {
            dataSchema = new Schema<>().$ref("#/components/schemas/" + dataType.getSimpleName());
        }

        Schema<Object> wrapper = new ObjectSchema();
        wrapper.addProperty("data", dataSchema);
        wrapper.addProperty("message", new StringSchema());
        wrapper.addProperty("success", new BooleanSchema());
        wrapper.addProperty("timestamp", new StringSchema().format("date-time"));
        wrapper.addProperty("validationErrors", new ArraySchema()
                .items(new ObjectSchema()
                        .addProperty("field", new StringSchema())
                        .addProperty("message", new StringSchema())
                ));

        return wrapper;
    }

    /**
     * Finds all concrete implementations of the given interface within specified packages.
     *
     * @param iface The interface to find implementations for
     * @return List of classes that implement the given interface
     */
    private List<Class<?>> findImplementations(Class<?> iface) {
        List<Class<?>> implementations = new ArrayList<>();
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AssignableTypeFilter(iface));

        // Scan all feature packages
        List<String> basePackages = List.of(
                "dev.austinbarnes.retailinventorymanagement.auth.dto",
                "dev.austinbarnes.retailinventorymanagement.product.dto",
                "dev.austinbarnes.retailinventorymanagement.employee.dto",
                "dev.austinbarnes.retailinventorymanagement.location.dto",
                "dev.austinbarnes.retailinventorymanagement.inventory.dto"
        );

        for (String pkg : basePackages) {
            for (BeanDefinition candidate : scanner.findCandidateComponents(pkg)) {
                try {
                    Class<?> clazz = Class.forName(candidate.getBeanClassName());
                    if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                        implementations.add(clazz);
                    }
                } catch (ClassNotFoundException ignored) {}
            }
        }

        return implementations;
    }
}
