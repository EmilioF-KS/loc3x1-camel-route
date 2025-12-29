<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="context">
      <xsl:element name="ApplyMasterpieceTerritorySelectionRules">
        <xsl:element name="ApplyMasterpieceTerritorySelectionRulesRequest">
          <xsl:element name="StateOrProvinceCode">
            <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/Location/stateOrProvinceCode"/>
          </xsl:element>
          <xsl:element name="CountryCode">
            <xsl:value-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/Location/countryCode"/>
          </xsl:element>
          <xsl:element name="AsOfDate">
            <xsl:value-of select="context/correlation/AsOfDate"/>
          </xsl:element>
          <xsl:element name="MasterpieceTerritoryInformation">
            <xsl:copy-of select="body/getLocationWithLineOfBusinessInfoResponse/locationOutput/Location/masterpieceInfo"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:element name="correlation">
        <xsl:value-of select="context/correlation"/>
      </xsl:element>
      <xsl:element name="transient">
        <xsl:value-of select="context/transient"/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
