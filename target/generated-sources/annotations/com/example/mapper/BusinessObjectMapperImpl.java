package com.example.mapper;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-03T08:56:14-0600",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
public class BusinessObjectMapperImpl implements BusinessObjectMapper {

    @Override
    public GetLocationsRequestDTO toDTO(BusinessObject bo) {
        if ( bo == null ) {
            return null;
        }

        GetLocationsRequestDTO getLocationsRequestDTO = new GetLocationsRequestDTO();

        getLocationsRequestDTO.setAddressLine1( bo.getAddressLine1() );
        getLocationsRequestDTO.setCityName( bo.getCityName() );
        getLocationsRequestDTO.setStateOrProvinceCode( bo.getStateOrProvinceCode() );
        getLocationsRequestDTO.setCountryCode( bo.getCountryCode() );
        getLocationsRequestDTO.setPostalCode( bo.getPostalCode() );
        getLocationsRequestDTO.setLocationPlaceCode( bo.getLocationPlaceCode() );

        return getLocationsRequestDTO;
    }
}
