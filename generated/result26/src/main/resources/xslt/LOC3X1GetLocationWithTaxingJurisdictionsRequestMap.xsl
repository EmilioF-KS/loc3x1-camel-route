<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="GetLocationWithTaxingJurisdictions3X1B">
      <xsl:element name="GetLocationWithTaxingJurisdictionsRequest">
        <xsl:element name="AddressLine1">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1"/>
        </xsl:element>
        <xsl:element name="AddressLine2">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2"/>
        </xsl:element>
        <xsl:element name="CityName">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName"/>
        </xsl:element>
        <xsl:element name="StateOrProvinceCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode"/>
        </xsl:element>
        <xsl:element name="PostalStateAbbreviation">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation"/>
        </xsl:element>
        <xsl:element name="PostalCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode"/>
        </xsl:element>
        <xsl:element name="CountryCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode"/>
        </xsl:element>
        <xsl:element name="CountryAbbreviation">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation"/>
        </xsl:element>
        <xsl:element name="LocationPlaceCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode"/>
        </xsl:element>
        <xsl:element name="AsOfDate">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
