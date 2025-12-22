package com.example.norm;

import org.springframework.stereotype.Component;

@Component
public class Loc3x1bRequestNormalizer {
    public String normalize(String xml) {
        String ns = "http://ei/location/get_location_list_request_loc3x1b";
        String addressLine1 = extract(xml, "AddressLine1");
        String addressLine2 = extract(xml, "AddressLine2");
        String cityName = extract(xml, "CityName");
        String stateOrProvinceCode = extract(xml, "StateOrProvinceCode");
        String postalStateAbbreviation = extract(xml, "PostalStateAbbreviation");
        String postalCode = extract(xml, "PostalCode");
        String locationPlaceCode = extract(xml, "LocationPlaceCode");
        String countryCode = extract(xml, "CountryCode");
        String countryAbbreviation = extract(xml, "CountryAbbreviation");
        StringBuilder sb = new StringBuilder();
        sb.append("<GetLocationListRequest xmlns=\"").append(ns).append("\">");
        if (addressLine1 != null) sb.append(tag("AddressLine1", addressLine1));
        if (addressLine2 != null) sb.append(tag("AddressLine2", addressLine2));
        if (cityName != null) sb.append(tag("CityName", cityName));
        if (stateOrProvinceCode != null) sb.append(tag("StateOrProvinceCode", stateOrProvinceCode));
        if (postalStateAbbreviation != null) sb.append(tag("PostalStateAbbreviation", postalStateAbbreviation));
        if (postalCode != null) sb.append(tag("PostalCode", postalCode));
        if (locationPlaceCode != null) sb.append(tag("LocationPlaceCode", locationPlaceCode));
        if (countryCode != null) sb.append(tag("CountryCode", countryCode));
        if (countryAbbreviation != null) sb.append(tag("CountryAbbreviation", countryAbbreviation));
        sb.append("</GetLocationListRequest>");
        return sb.toString();
    }

    private String tag(String n, String v) { return "<"+n+">"+v+"</"+n+">"; }
    private String extract(String xml, String tag) {
        int s = xml.indexOf("<" + tag + ">");
        int e = xml.indexOf("</" + tag + ">");
        if (s == -1 || e == -1 || e < s) return null;
        int start = s + tag.length() + 2;
        return xml.substring(start, e);
    }
}
