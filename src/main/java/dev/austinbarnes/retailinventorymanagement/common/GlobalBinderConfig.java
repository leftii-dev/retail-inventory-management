package dev.austinbarnes.retailinventorymanagement.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.Instant;
import java.util.UUID;

/**
 * GlobalBinderConfig is a controller advice that configures data binding for FilterDTO objects.
 * It extracts common filter parameters from the HTTP request and populates a BaseFilterDTO
 * which can be used in controllers for filtering purposes.
 */
@ControllerAdvice
public class GlobalBinderConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        if(binder.getTarget() instanceof FilterDTO) {
//            BaseFilterDTO baseFilter = new BaseFilterDTO();
            String createdAt = request.getParameter("createdAt");
            String createdBefore = request.getParameter("createdBefore");
            String createdAfter = request.getParameter("createdAfter");
            String modifiedAt = request.getParameter("modifiedAt");
            String modifiedBefore = request.getParameter("modifiedBefore");
            String modifiedAfter = request.getParameter("modifiedAfter");
            String createdBy = request.getParameter("createdBy");
            String modifiedBy = request.getParameter("modifiedBy");
            String showInactive = request.getParameter("showInactive");

            Instant createdAtVal = parseInstant(createdAt);
            Instant createdBeforeVal = parseInstant(createdBefore);
            Instant createdAfterVal = parseInstant(createdAfter);
            Instant modifiedAtVal = parseInstant(modifiedAt);
            Instant modifiedBeforeVal = parseInstant(modifiedBefore);
            Instant modifiedAfterVal = parseInstant(modifiedAfter);
            UUID createdByVal = parseUUID(createdBy);
            UUID modifiedByVal = parseUUID(modifiedBy);
            Boolean showInactiveVal = parseBoolean(showInactive);

            BaseFilterDTO baseFilter = new BaseFilterDTO(
                    createdAtVal,
                    createdBeforeVal,
                    createdAfterVal,
                    modifiedAtVal,
                    modifiedBeforeVal,
                    modifiedAfterVal,
                    createdByVal,
                    modifiedByVal,
                    showInactiveVal
            );

            binder.getBindingResult().getModel().put("baseFilter", baseFilter);
        }
    }

    private Instant parseInstant(String value) {
        if(value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Instant.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    private UUID parseUUID(String value) {
        if(value == null || value.isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(value);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    private Boolean parseBoolean(String value) {
        if(value == null || value.isEmpty()) {
            return null;
        }
        return Boolean.valueOf(value);
    }
}
