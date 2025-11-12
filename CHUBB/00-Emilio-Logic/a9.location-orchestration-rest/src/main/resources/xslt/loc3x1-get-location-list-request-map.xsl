<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:req="http://ei/location/location_retrieval_loc3x1"
  xmlns:res="http://ei/location/location_retrieval_loc3x1b"
  exclude-result-prefixes="req">
  <xsl:output method="xml" indent="yes"/>

  <!-- Accept either the message root or directly the request wrapper -->
  <xsl:template match="/">
    <xsl:apply-templates select="//req:GetLocationList"/>
  </xsl:template>

  <xsl:template match="req:GetLocationList">
    <res:GetLocationList3X1B>
      <res:GetLocationListRequest>
        <res:AddressLine1><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:AddressLine1)"/></res:AddressLine1>
        <res:AddressLine2><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:AddressLine2)"/></res:AddressLine2>
        <res:CityName><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:CityName)"/></res:CityName>
        <res:StateOrProvinceCode><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:StateOrProvinceCode)"/></res:StateOrProvinceCode>
        <res:PostalStateAbbreviation><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:PostalStateAbbreviation)"/></res:PostalStateAbbreviation>
        <res:PostalCode><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:PostalCode)"/></res:PostalCode>
        <res:LocationPlaceCode><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:LocationPlaceCode)"/></res:LocationPlaceCode>
        <res:CountryCode><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:CountryCode)"/></res:CountryCode>
        <res:CountryAbbreviation><xsl:value-of select="normalize-space(req:GetLocationListRequest/req:CountryAbbreviation)"/></res:CountryAbbreviation>
      </res:GetLocationListRequest>
    </res:GetLocationList3X1B>
  </xsl:template>

</xsl:stylesheet>
