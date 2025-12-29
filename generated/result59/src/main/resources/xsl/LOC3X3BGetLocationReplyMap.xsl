<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="Location">
      <xsl:element name="LocationInformation">
        <xsl:element name="CityCode">
          <xsl:value-of select="Location/LocationInformation/CityCode"/>
        </xsl:element>
        <xsl:element name="CountyCode">
          <xsl:value-of select="Location/LocationInformation/CountyCode"/>
        </xsl:element>
        <xsl:element name="FloridaTortCode">
          <xsl:value-of select="Location/LocationInformation/FloridaTortCode"/>
        </xsl:element>
        <xsl:element name="FireDistrictCode">
          <xsl:value-of select="Location/LocationInformation/FireDistrictCode"/>
        </xsl:element>
        <xsl:element name="IndustrialRiskInsurerCode">
          <xsl:value-of select="Location/LocationInformation/IndustrialRiskInsurerCode"/>
        </xsl:element>
        <xsl:element name="LicenseCode">
          <xsl:value-of select="Location/LocationInformation/LicenseCode"/>
        </xsl:element>
        <xsl:element name="LocationClassificationCode">
          <xsl:value-of select="Location/LocationInformation/LocationClassificationCode"/>
        </xsl:element>
        <xsl:element name="MineSubsidenceCode">
          <xsl:value-of select="Location/LocationInformation/MineSubsidenceCode"/>
        </xsl:element>
        <xsl:element name="TexasCityCode">
          <xsl:value-of select="Location/LocationInformation/TexasCityCode"/>
        </xsl:element>
        <xsl:element name="TexasCountyCode">
          <xsl:value-of select="Location/LocationInformation/TexasCountyCode"/>
        </xsl:element>
        <xsl:element name="TexasPlaceCode">
          <xsl:value-of select="Location/LocationInformation/TexasPlaceCode"/>
        </xsl:element>
        <xsl:element name="PublicProtectionInformation">
          <xsl:value-of select="Location/LocationInformation/PublicProtectionInformation"/>
        </xsl:element>
      </xsl:element>
      <xsl:element name="RatingTerritory">
        <xsl:value-of select="Location/RatingTerritory"/>
      </xsl:element>
      <xsl:element name="TaxingJurisdiction">
        <xsl:value-of select="Location/TaxingJurisdiction"/>
      </xsl:element>
      <xsl:element name="HazardousArea">
        <xsl:value-of select="Location/HazardousArea"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
