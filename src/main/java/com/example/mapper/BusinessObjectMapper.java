package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessObjectMapper {

    BusinessObjectMapper INSTANCE = Mappers.getMapper(BusinessObjectMapper.class);

    @Mapping(source = "addressLine1", target = "addressLine1")
    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "stateOrProvinceCode", target = "stateOrProvinceCode")
    @Mapping(source = "countryCode", target = "countryCode")
    @Mapping(source = "postalCode", target = "postalCode")
    @Mapping(source = "locationPlaceCode", target = "locationPlaceCode")
    GetLocationsRequestDTO toDTO(BusinessObject bo);
}
