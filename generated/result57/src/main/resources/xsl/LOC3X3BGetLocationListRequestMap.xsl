<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="StandardizedAddress">
      <xsl:element name="AddressLine1">
        <xsl:value-of select="StandardizedAddress/AddressLine1"/>
      </xsl:element>
      <xsl:element name="CityName">
        <xsl:value-of select="StandardizedAddress/CityName"/>
      </xsl:element>
      <xsl:element name="PostalCode">
        <xsl:value-of select="StandardizedAddress/PostalCode"/>
      </xsl:element>
      <xsl:element name="LocationPlaceCode">
        <xsl:value-of select="StandardizedAddress/LocationPlaceCode"/>
      </xsl:element>
      <xsl:element name="StateOrProvinceCode">
        <xsl:value-of select="StandardizedAddress/StateOrProvinceCode"/>
      </xsl:element>
      <xsl:element name="StateOrProvinceName">
        <xsl:value-of select="StandardizedAddress/StateOrProvinceName"/>
      </xsl:element>
      <xsl:element name="StateOrProvinceAbbreviation">
        <xsl:value-of select="StandardizedAddress/StateOrProvinceAbbreviation"/>
      </xsl:element>
      <xsl:element name="PostalStateAbbreviation">
        <xsl:value-of select="StandardizedAddress/PostalStateAbbreviation"/>
      </xsl:element>
      <xsl:element name="CountryCode">
        <xsl:value-of select="StandardizedAddress/CountryCode"/>
      </xsl:element>
      <xsl:element name="CountryName">
        <xsl:value-of select="StandardizedAddress/CountryName"/>
      </xsl:element>
      <xsl:element name="CountryAbbreviation">
        <xsl:value-of select="StandardizedAddress/CountryAbbreviation"/>
      </xsl:element>
      <xsl:element name="ISOCountryCode">
        <xsl:value-of select="StandardizedAddress/ISOCountryCode"/>
      </xsl:element>
      <xsl:value-of select="UserId"/>
      <xsl:value-of select="SystemId"/>
      <xsl:value-of select="DataSourceOptionsText"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
