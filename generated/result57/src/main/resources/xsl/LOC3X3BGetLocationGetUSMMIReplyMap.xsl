<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="Location">
      <xsl:element name="StandardizedAddress">
        <xsl:element name="EnclosingPostalCode">
          <xsl:value-of select="Location/StandardizedAddress/EnclosingPostalCode"/>
        </xsl:element>
        <xsl:element name="CountyName">
          <xsl:value-of select="Location/StandardizedAddress/CountyName"/>
        </xsl:element>
      </xsl:element>
      <xsl:element name="HazardousArea">
        <xsl:copy-of select="Location/HazardousArea"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
