<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="HazardousAreaTypeCode">
      <xsl:value-of select="HazardousAreaTypeCode"/>
      <xsl:value-of select="HazardousAreaTypeName"/>
      <xsl:value-of select="HazardousAreaName"/>
      <xsl:value-of select="HazardousAreaCode"/>
      <xsl:value-of select="HazardousTierCode"/>
      <xsl:value-of select="HazardousAreaIndicator"/>
      <xsl:value-of select="HazardousAreaText"/>
      <xsl:value-of select="DataSourceName"/>
      <xsl:value-of select="LocationMatchTypeName"/>
      <xsl:value-of select="LocationMatchTypeValue"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
