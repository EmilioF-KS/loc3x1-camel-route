<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="getLocationWithTaxInfo">
      <xsl:element name="address">
        <xsl:element name="addressLine1">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1"/>
        </xsl:element>
        <xsl:element name="cityName">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName"/>
        </xsl:element>
        <xsl:element name="stateOrProvinceCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode"/>
        </xsl:element>
        <xsl:element name="countryCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode"/>
        </xsl:element>
        <xsl:element name="postalCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode"/>
        </xsl:element>
        <xsl:element name="locationPlaceCode">
          <xsl:value-of select="GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode"/>
        </xsl:element>
      </xsl:element>
      <xsl:element name="asOfDate">
        <xsl:value-of select="GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
