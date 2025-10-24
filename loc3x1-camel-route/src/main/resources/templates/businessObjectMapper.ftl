package ${packageName};

import java.util.Map;
import java.util.stream.Collectors;

public class ${className} {

    public Map<String, ${dtoClass}> mapToDTO(Map<String, ${entityClass}> input) {
        if (input == null) return null;
        return input.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                    ${dtoClass} dto = new ${dtoClass}();
                    dto.setAddressLine1(e.getValue().getAddressLine1());
                    dto.setCityName(e.getValue().getCityName());
                    dto.setStateOrProvinceCode(e.getValue().getStateOrProvinceCode());
                    dto.setCountryCode(e.getValue().getCountryCode());
                    dto.setPostalCode(e.getValue().getPostalCode());
                    dto.setLocationPlaceCode(e.getValue().getLocationPlaceCode());
                    return dto;
                }
            ));
    }
}
