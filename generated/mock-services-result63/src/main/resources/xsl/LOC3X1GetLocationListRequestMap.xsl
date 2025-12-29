<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="GetLocationList3X1B">
      <xsl:element name="GetLocationListRequest">
        <xsl:element name="AddressLine1">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/AddressLine1"/>
        </xsl:element>
        <xsl:element name="AddressLine2">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/AddressLine2"/>
        </xsl:element>
        <xsl:element name="CityName">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/CityName"/>
        </xsl:element>
        <xsl:element name="StateOrProvinceCode">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/StateOrProvinceCode"/>
        </xsl:element>
        <xsl:element name="PostalStateAbbreviation">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/PostalStateAbbreviation"/>
        </xsl:element>
        <xsl:element name="PostalCode">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/PostalCode"/>
        </xsl:element>
        <xsl:element name="LocationPlaceCode">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/LocationPlaceCode"/>
        </xsl:element>
        <xsl:element name="CountryCode">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/CountryCode"/>
        </xsl:element>
        <xsl:element name="CountryAbbreviation">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/CountryAbbreviation"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
