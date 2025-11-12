<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <!-- Input root assumed <Location> with child elements matching the map -->
  <xsl:template match="/Location">
    <CimLocation>
      <StandardizedAddress>
        <CityName><xsl:value-of select="normalize-space(cityName)"/></CityName>
        <CountyName><xsl:value-of select="normalize-space(countyName)"/></CountyName>
        <StateOrProvinceCode><xsl:value-of select="normalize-space(stateOrProvinceCode)"/></StateOrProvinceCode>
        <StateOrProvinceName><xsl:value-of select="normalize-space(stateOrProvinceName)"/></StateOrProvinceName>
        <PostalCode><xsl:value-of select="normalize-space(postalCode)"/></PostalCode>
        <CountryCode><xsl:value-of select="normalize-space(countryCode)"/></CountryCode>
        <CountryName><xsl:value-of select="normalize-space(countryName)"/></CountryName>
        <POBoxIndicator>
          <xsl:variable name="po" select="normalize-space(poBoxIndicator)"/>
          <xsl:value-of select="translate($po,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ') = 'Y'"/>
        </POBoxIndicator>
      </StandardizedAddress>
      <LocationInformation>
        <FireDistrictCode><xsl:value-of select="normalize-space(fireDistrictCode)"/></FireDistrictCode>
        <LicenseCode><xsl:value-of select="normalize-space(taxInfo/licenseCode)"/></LicenseCode>
      </LocationInformation>
    </CimLocation>
  </xsl:template>

  <!-- Identity for other nodes -->
  <xsl:template match="@*|node()">
    <xsl:copy><xsl:apply-templates select="@*|node()"/></xsl:copy>
  </xsl:template>
</xsl:stylesheet>
