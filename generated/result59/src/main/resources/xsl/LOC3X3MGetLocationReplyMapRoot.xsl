<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="context">
      <xsl:element name="GetLocation3X3MResponse">
        <xsl:element name="GetLocationReply">
          <xsl:element name="StatusInformation">
            <xsl:element name="Error">
              <xsl:copy-of select="context/transient/StatusInformation/Error"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:element name="transient">
        <xsl:value-of select="context/transient"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
