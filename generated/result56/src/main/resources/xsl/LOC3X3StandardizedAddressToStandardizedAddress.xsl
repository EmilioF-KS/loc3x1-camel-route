<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="AddressLine1">
      <xsl:value-of select="AddressLine1"/>
      <xsl:value-of select="AddressLine2"/>
      <xsl:value-of select="AddressLine3"/>
      <xsl:value-of select="CityName"/>
      <xsl:value-of select="CountyName"/>
      <xsl:value-of select="StateOrProvinceCode"/>
      <xsl:value-of select="StateOrProvinceName"/>
      <xsl:value-of select="StateOrProvinceAbbreviation"/>
      <xsl:value-of select="PostalStateAbbreviation"/>
      <xsl:value-of select="PostalCode"/>
      <xsl:value-of select="CountryCode"/>
      <xsl:value-of select="CountryName"/>
      <xsl:value-of select="CountryAbbreviation"/>
      <xsl:value-of select="ISOCountryCode"/>
      <xsl:value-of select="LocationPlaceCode"/>
      <xsl:value-of select="POBoxIndicator"/>
      <xsl:value-of select="BuildingName"/>
      <xsl:value-of select="StreetNumber"/>
      <xsl:value-of select="PreDirectionalAbbreviation"/>
      <xsl:value-of select="StreetName"/>
      <xsl:value-of select="StreetSuffixAbbreviation"/>
      <xsl:value-of select="PostDirectionalAbbreviation"/>
      <xsl:value-of select="AddressUnitNumber"/>
      <xsl:value-of select="AddressUnitTypeAbbreviation"/>
      <xsl:value-of select="DeliveryPointNumber"/>
      <xsl:value-of select="Latitude"/>
      <xsl:value-of select="Longitude"/>
      <xsl:value-of select="FIPSCountyCode"/>
      <xsl:value-of select="FIPSStateCode"/>
      <xsl:value-of select="EnclosingPostalCode"/>
      <xsl:value-of select="GeoCodingStatusCode"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
