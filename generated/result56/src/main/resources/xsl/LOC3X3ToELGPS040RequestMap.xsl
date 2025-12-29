<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="STATE">
      <xsl:value-of select="Location/StandardizedAddress/PostalStateAbbreviation"/>
      <xsl:value-of select="Location/StandardizedAddress/StreetNumber"/>
      <xsl:value-of select="Location/StandardizedAddress/PreDirectionalAbbreviation"/>
      <xsl:value-of select="Location/StandardizedAddress/StreetName"/>
      <xsl:value-of select="Location/StandardizedAddress/StreetSuffixAbbreviation"/>
      <xsl:value-of select="Location/StandardizedAddress/PostDirectionalAbbreviation"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
