<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="getLocations">
      <xsl:element name="address">
        <xsl:element name="addressLine1">
          <xsl:value-of select="GetLocationList3X1M/GetLocationListRequest/AddressLine1"/>
        </xsl:element>
        <xsl:element name="cityName">
          <xsl:value-of select="GetLocationList3X1M/GetLocationListRequest/CityName"/>
        </xsl:element>
        <xsl:element name="stateOrProvinceCode">
          <xsl:value-of select="GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode"/>
        </xsl:element>
        <xsl:element name="countryCode">
          <xsl:value-of select="GetLocationList3X1M/GetLocationListRequest/CountryCode"/>
        </xsl:element>
        <xsl:element name="postalCode">
          <xsl:value-of select="GetLocationList3X1M/GetLocationListRequest/PostalCode"/>
        </xsl:element>
        <xsl:element name="locationPlaceCode">
          <xsl:value-of select="GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
