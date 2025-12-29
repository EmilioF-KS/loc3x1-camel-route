<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="StandardizeUSAddressRequest">
      <xsl:element name="StandardizedAddress">
        <xsl:copy-of select="StandardizedAddress"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
