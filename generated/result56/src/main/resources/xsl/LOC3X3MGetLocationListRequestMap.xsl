<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="getLocations">
      <xsl:element name="address">
        <xsl:element name="addressLine1">
          <xsl:value-of select="GetLocationList3X3M/GetLocationListRequest/StandardizedAddress/AddressLine1"/>
        </xsl:element>
        <xsl:element name="cityName">
          <xsl:value-of select="GetLocationList3X3M/GetLocationListRequest/StandardizedAddress/CityName"/>
        </xsl:element>
        <xsl:element name="stateOrProvinceCode">
          <xsl:value-of select="GetLocationList3X3M/GetLocationListRequest/StandardizedAddress/StateOrProvinceCode"/>
        </xsl:element>
        <xsl:element name="countryCode">
          <xsl:value-of select="GetLocationList3X3M/GetLocationListRequest/StandardizedAddress/CountryCode"/>
        </xsl:element>
        <xsl:element name="postalCode">
          <xsl:value-of select="GetLocationList3X3M/GetLocationListRequest/StandardizedAddress/PostalCode"/>
        </xsl:element>
        <xsl:element name="locationPlaceCode">
          <xsl:value-of select="GetLocationList3X3M/GetLocationListRequest/StandardizedAddress/LocationPlaceCode"/>
        </xsl:element>
        <xsl:element name="countyName">
          <xsl:value-of select="GetLocationList3X3M/GetLocationListRequest/StandardizedAddress/CountyName"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
