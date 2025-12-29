<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="context">
      <xsl:element name="transient">
        <xsl:element name="Location">
          <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/Location"/>
        </xsl:element>
        <xsl:element name="StatusInformation">
          <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/StatusInformation"/>
        </xsl:element>
      </xsl:element>
      <xsl:element name="correlation">
        <xsl:element name="LineOfBusinessTypeCode">
          <xsl:value-of select="context/correlation/LineOfBusinessTypeCode"/>
        </xsl:element>
        <xsl:element name="AsOfDate">
          <xsl:value-of select="context/correlation/AsOfDate"/>
        </xsl:element>
      </xsl:element>
      <xsl:element name="getLocationWithLineOfBusinessInfoResponse">
        <xsl:element name="locationOutput">
          <xsl:element name="Location">
            <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/Location"/>
          </xsl:element>
          <xsl:element name="StatusInformation">
            <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/StatusInformation"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
