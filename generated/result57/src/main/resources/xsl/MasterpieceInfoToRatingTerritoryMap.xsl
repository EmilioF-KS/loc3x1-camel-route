<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="MasterpieceTerritoryCode">
      <xsl:value-of select="masterpieceTerritoryCode"/>
      <xsl:value-of select="altMasterpieceTerritoryCode"/>
      <xsl:value-of select="prvAltMasterpieceTerritoryCode"/>
      <xsl:value-of select="autoTerritoryCode"/>
      <xsl:value-of select="homeownersTerritoryCode"/>
      <xsl:value-of select="prvMasterpieceTerritoryCode"/>
      <xsl:value-of select="changeEffectiveDate"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
