package com.ei.camel.mappings;

import org.mapstruct.Mapper;
import com.ei.camel.mappings.GetLocationWithTaxingJurisdictionsTypes;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface LOC3X1GetLocationWithTaxingJurisdictionsRequestMapMapper {
    @Mappings({@Mapping(source = "addressLine1", target = "addressLine1"),
        @Mapping(source = "addressLine2", target = "addressLine2"),
        @Mapping(source = "cityName", target = "cityName"),
        @Mapping(source = "stateOrProvinceCode", target = "stateOrProvinceCode"),
        @Mapping(source = "postalStateAbbreviation", target = "postalStateAbbreviation"),
        @Mapping(source = "postalCode", target = "postalCode"),
        @Mapping(source = "countryCode", target = "countryCode"),
        @Mapping(source = "countryAbbreviation", target = "countryAbbreviation"),
        @Mapping(source = "locationPlaceCode", target = "locationPlaceCode"),
        @Mapping(source = "asOfDate", target = "asOfDate")})
    GetLocationWithTaxingJurisdictionsTypes.GetLocationWithTaxingJurisdictions3X1BRequestMsg map(GetLocationWithTaxingJurisdictionsTypes.GetLocationWithTaxingJurisdictionsRequestMsg source);
}
