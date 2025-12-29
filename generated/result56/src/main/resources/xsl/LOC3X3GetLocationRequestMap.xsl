<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="GetLocation">
      <xsl:element name="GetLocationRequest">
        <xsl:element name="StandardizedAddress">
          <xsl:value-of select="GetLocation/GetLocationRequest/StandardizedAddress"/>
        </xsl:element>
        <xsl:element name="LineOfBusinessTypeCode">
          <xsl:value-of select="GetLocation/GetLocationRequest/LineOfBusinessTypeCode"/>
        </xsl:element>
        <xsl:element name="UserId">
          <xsl:value-of select="GetLocation/GetLocationRequest/UserId"/>
        </xsl:element>
        <xsl:element name="SystemId">
          <xsl:value-of select="GetLocation/GetLocationRequest/SystemId"/>
        </xsl:element>
        <xsl:element name="AsOfDate">
          <xsl:value-of select="GetLocation/GetLocationRequest/AsOfDate"/>
        </xsl:element>
        <xsl:element name="DataSourceOptionsText">
          <xsl:value-of select="GetLocation/GetLocationRequest/DataSourceOptionsText"/>
        </xsl:element>
        <xsl:element name="AdditionalOptionsText">
          <xsl:value-of select="GetLocation/GetLocationRequest/AdditionalOptionsText"/>
        </xsl:element>
        <xsl:element name="HazardousAreaOptionsText">
          <xsl:value-of select="GetLocation/GetLocationRequest/HazardousAreaOptionsText"/>
        </xsl:element>
        <xsl:element name="RatingTerritoryOptionsText">
          <xsl:value-of select="GetLocation/GetLocationRequest/RatingTerritoryOptionsText"/>
        </xsl:element>
        <xsl:element name="ReferralZonesOptionsText">
          <xsl:value-of select="GetLocation/GetLocationRequest/ReferralZonesOptionsText"/>
        </xsl:element>
        <xsl:element name="LocationInformationOptionsText">
          <xsl:value-of select="GetLocation/GetLocationRequest/LocationInformationOptionsText"/>
        </xsl:element>
        <xsl:element name="ProcessClassificationName">
          <xsl:value-of select="GetLocation/GetLocationRequest/ProcessClassificationName"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
