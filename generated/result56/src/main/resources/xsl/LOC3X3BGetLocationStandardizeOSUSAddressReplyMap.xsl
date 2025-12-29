<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="Location">
      <xsl:element name="StandardizedAddress">
        <xsl:element name="AddressLine1">
          <xsl:value-of select="StandardizedAddress/AddressLine1"/>
        </xsl:element>
        <xsl:element name="BuildingName">
          <xsl:value-of select="StandardizedAddress/BuildingName"/>
        </xsl:element>
        <xsl:element name="StreetNumber">
          <xsl:value-of select="StandardizedAddress/StreetNumber"/>
        </xsl:element>
        <xsl:element name="StreetName">
          <xsl:value-of select="StandardizedAddress/StreetName"/>
        </xsl:element>
        <xsl:element name="CityName">
          <xsl:value-of select="StandardizedAddress/CityName"/>
        </xsl:element>
        <xsl:element name="PostalCode">
          <xsl:value-of select="StandardizedAddress/PostalCode"/>
        </xsl:element>
        <xsl:element name="StateOrProvinceName">
          <xsl:value-of select="StandardizedAddress/StateOrProvinceName"/>
        </xsl:element>
        <xsl:element name="ISOCountryCode">
          <xsl:value-of select="StandardizedAddress/ISOCountryCode"/>
        </xsl:element>
        <xsl:element name="CountryName">
          <xsl:value-of select="StandardizedAddress/CountryName"/>
        </xsl:element>
        <xsl:element name="PostalStateAbbreviation">
          <xsl:value-of select="StandardizedAddress/PostalStateAbbreviation"/>
        </xsl:element>
        <xsl:element name="Latitude">
          <xsl:value-of select="StandardizedAddress/Latitude"/>
        </xsl:element>
        <xsl:element name="Longitude">
          <xsl:value-of select="StandardizedAddress/Longitude"/>
        </xsl:element>
        <xsl:element name="GeoCodingStatusCode">
          <xsl:value-of select="StandardizedAddress/GeoCodingStatusCode"/>
        </xsl:element>
      </xsl:element>
      <xsl:copy-of select="StatusInformation"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
