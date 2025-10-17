package com.locationretrievalloc3x1process.webservice.mapper;

import java.util.Map;
import java.util.stream.Collectors;

import com.locationretrievalloc3x1process.webservice.dto.BusinessObject;
import com.locationretrievalloc3x1process.webservice.dto.GetLocationsRequestDTO;

public class BusinessObjectMapper {

    public Map<String, GetLocationsRequestDTO> mapToDTO(Map<String, BusinessObject> input) {
        if (input == null) return null;
        return input.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                    GetLocationsRequestDTO dto = new GetLocationsRequestDTO();
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
