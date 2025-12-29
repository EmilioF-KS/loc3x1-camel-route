<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="GetLocationList">
      <xsl:element name="GetLocationListRequest">
        <xsl:element name="StandardizedAddress">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/StandardizedAddress"/>
        </xsl:element>
        <xsl:element name="LineOfBusinessTypeCode">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/LineOfBusinessTypeCode"/>
        </xsl:element>
        <xsl:element name="ProcessClassificationName">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/ProcessClassificationName"/>
        </xsl:element>
        <xsl:element name="UserId">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/UserId"/>
        </xsl:element>
        <xsl:element name="SystemId">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/SystemId"/>
        </xsl:element>
        <xsl:element name="AsOfDate">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/AsOfDate"/>
        </xsl:element>
        <xsl:element name="DataSourceOptionsText">
          <xsl:value-of select="GetLocationList/GetLocationListRequest/DataSourceOptionsText"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
