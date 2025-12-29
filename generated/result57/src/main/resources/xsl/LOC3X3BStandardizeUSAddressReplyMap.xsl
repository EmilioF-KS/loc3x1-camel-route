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
        <xsl:element name="PreDirectionalAbbreviation">
          <xsl:value-of select="StandardizedAddress/PreDirectionalAbbreviation"/>
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
        <xsl:element name="StreetSuffixAbbreviation">
          <xsl:value-of select="StandardizedAddress/StreetSuffixAbbreviation"/>
        </xsl:element>
        <xsl:element name="PostDirectionalAbbreviation">
          <xsl:value-of select="StandardizedAddress/PostDirectionalAbbreviation"/>
        </xsl:element>
        <xsl:element name="AddressUnitNumber">
          <xsl:value-of select="StandardizedAddress/AddressUnitNumber"/>
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
        <xsl:element name="AddressUnitTypeAbbreviation">
          <xsl:value-of select="StandardizedAddress/AddressUnitTypeAbbreviation"/>
        </xsl:element>
        <xsl:element name="DeliveryPointNumber">
          <xsl:value-of select="StandardizedAddress/DeliveryPointNumber"/>
        </xsl:element>
        <xsl:element name="FIPSCountyCode">
          <xsl:value-of select="StandardizedAddress/FIPSCountyCode"/>
        </xsl:element>
        <xsl:element name="FIPSStateCode">
          <xsl:value-of select="StandardizedAddress/FIPSStateCode"/>
        </xsl:element>
        <xsl:element name="CountyName">
          <xsl:value-of select="StandardizedAddress/CountyName"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
