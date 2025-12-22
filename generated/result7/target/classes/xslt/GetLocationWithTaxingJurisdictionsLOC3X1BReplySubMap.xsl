<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="LocationWithTaxing">
      <xsl:element name="PostalCode">
        <xsl:value-of select="StandardizedAddress/PostalCode"/>
      </xsl:element>
      <xsl:element name="CountryCode">
        <xsl:value-of select="StandardizedAddress/CountryCode"/>
      </xsl:element>
      <xsl:element name="CountryName">
        <xsl:value-of select="StandardizedAddress/CountryName"/>
      </xsl:element>
      <xsl:element name="LocationPlaceCode">
        <xsl:value-of select="StandardizedAddress/LocationPlaceCode"/>
      </xsl:element>
      <xsl:element name="POBoxIndicator">
        <xsl:value-of select="StandardizedAddress/POBoxIndicator"/>
      </xsl:element>
      <xsl:value-of select="TaxingJurisdiction"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
