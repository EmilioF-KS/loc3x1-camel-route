package com.ei.camel.mappings;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mapstruct.factory.Mappers;

public class LOC3X1GetLocationWithTaxingJurisdictionsRequestMapMapperTest {
    @Test
    void mapsTypedFieldsDeterministically() {
        var src = new GetLocationWithTaxingJurisdictionsTypes.GetLocationWithTaxingJurisdictionsRequestMsg();
        src.addressLine1 = "123 Main";
        src.addressLine2 = "Apt 4";
        src.cityName = "Austin";
        src.stateOrProvinceCode = "TX";
        src.postalStateAbbreviation = "TX";
        src.postalCode = "78701";
        src.countryCode = "US";
        src.countryAbbreviation = "USA";
        src.locationPlaceCode = "12345";
        src.asOfDate = "2025-10-25";

        var mapper = Mappers.getMapper(LOC3X1GetLocationWithTaxingJurisdictionsRequestMapMapper.class);
        var out = mapper.map(src);

        assertNotNull(out);
        assertEquals(src.addressLine1, out.addressLine1);
        assertEquals(src.addressLine2, out.addressLine2);
        assertEquals(src.cityName, out.cityName);
        assertEquals(src.stateOrProvinceCode, out.stateOrProvinceCode);
        assertEquals(src.postalStateAbbreviation, out.postalStateAbbreviation);
        assertEquals(src.postalCode, out.postalCode);
        assertEquals(src.countryCode, out.countryCode);
        assertEquals(src.countryAbbreviation, out.countryAbbreviation);
        assertEquals(src.locationPlaceCode, out.locationPlaceCode);
        assertEquals(src.asOfDate, out.asOfDate);
    }
}