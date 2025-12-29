<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="getLocationWithLineOfBusinessInfo">
      <xsl:element name="address">
        <xsl:element name="addressLine1">
          <xsl:value-of select="GetLocation3X3M/GetLocationRequest/StandardizedAddress/AddressLine1"/>
        </xsl:element>
        <xsl:element name="cityName">
          <xsl:value-of select="GetLocation3X3M/GetLocationRequest/StandardizedAddress/CityName"/>
        </xsl:element>
        <xsl:element name="countryCode">
          <xsl:value-of select="GetLocation3X3M/GetLocationRequest/StandardizedAddress/CountryCode"/>
        </xsl:element>
        <xsl:element name="postalCode">
          <xsl:value-of select="GetLocation3X3M/GetLocationRequest/StandardizedAddress/PostalCode"/>
        </xsl:element>
        <xsl:element name="locationPlaceCode">
          <xsl:value-of select="GetLocation3X3M/GetLocationRequest/StandardizedAddress/LocationPlaceCode"/>
        </xsl:element>
        <xsl:element name="countyName">
          <xsl:value-of select="GetLocation3X3M/GetLocationRequest/StandardizedAddress/CountyName"/>
        </xsl:element>
      </xsl:element>
      <xsl:element name="asOfDate">
        <xsl:value-of select="GetLocation3X3M/GetLocationRequest/AsOfDate"/>
      </xsl:element>
      <xsl:element name="lineOfBusinessTypeCode">
        <xsl:value-of select="GetLocation3X3M/GetLocationRequest/LineOfBusinessTypeCode"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
