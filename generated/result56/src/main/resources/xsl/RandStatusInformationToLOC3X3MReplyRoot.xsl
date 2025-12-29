<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="context">
      <xsl:element name="failInfo">
        <xsl:element name="failureString">
          <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/StatusInformation/Error[1]/ErrorDescription"/>
        </xsl:element>
      </xsl:element>
      <xsl:element name="GetLocation3X3MResponse">
        <xsl:element name="GetLocationReply">
          <xsl:element name="StatusInformation">
            <xsl:element name="StatusCode">
              <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/StatusInformation/StatusCode"/>
            </xsl:element>
            <xsl:element name="Error">
              <xsl:copy-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/StatusInformation/Error"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
