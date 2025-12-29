<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="GetLocationResponse">
      <xsl:element name="GetLocationReply">
        <xsl:element name="Location">
          <xsl:value-of select="GetLocation3X2BResponse/GetLocationReply/Location"/>
        </xsl:element>
        <xsl:element name="StatusInformation">
          <xsl:value-of select="GetLocation3X2BResponse/GetLocationReply/StatusInformation"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
