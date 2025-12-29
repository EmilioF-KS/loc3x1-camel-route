<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="StandardizedAddress">
      <xsl:element name="PostalStateAbbreviation">
        <xsl:value-of select="postalStateAbbreviation"/>
      </xsl:element>
      <xsl:element name="LocationPlaceCode">
        <xsl:value-of select="locationPlaceCode"/>
      </xsl:element>
      <xsl:element name="CityCode">
        <xsl:value-of select="cityCode"/>
      </xsl:element>
      <xsl:element name="CountyCode">
        <xsl:value-of select="countyCode"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
