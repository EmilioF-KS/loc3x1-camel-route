<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="ErrorCode">
      <xsl:value-of select="ErrorCode"/>
      <xsl:value-of select="ErrorDescription"/>
      <xsl:value-of select="ErrorSeverityLevel"/>
      <xsl:value-of select="ErrorSourceIdentifier"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
