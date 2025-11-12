<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <!-- TODO: Replace with real mapping once the request map content is available. -->
  <xsl:template match="/">
    <GetLocationListRequest>
      <AddressLine1><xsl:value-of select="normalize-space(//addressLine1)"/></AddressLine1>
      <AddressLine2><xsl:value-of select="normalize-space(//addressLine2)"/></AddressLine2>
      <CityName><xsl:value-of select="normalize-space(//cityName)"/></CityName>
      <StateOrProvinceCode><xsl:value-of select="normalize-space(//stateOrProvinceCode)"/></StateOrProvinceCode>
      <PostalStateAbbreviation><xsl:value-of select="normalize-space(//postalStateAbbreviation)"/></PostalStateAbbreviation>
      <PostalCode><xsl:value-of select="normalize-space(//postalCode)"/></PostalCode>
      <LocationPlaceCode><xsl:value-of select="normalize-space(//locationPlaceCode)"/></LocationPlaceCode>
      <CountryCode><xsl:value-of select="normalize-space(//countryCode)"/></CountryCode>
      <CountryAbbreviation><xsl:value-of select="normalize-space(//countryAbbreviation)"/></CountryAbbreviation>
    </GetLocationListRequest>
  </xsl:template>
</xsl:stylesheet>
