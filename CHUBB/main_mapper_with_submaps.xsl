<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:se_1="http://service.rand.chubb.com"
    xmlns:dto="http://dto.rand.chubb.com"
    exclude-result-prefixes="se_1 dto">

  <xsl:output method="xml" indent="yes"/>

  <!-- Match the root element -->
  <xsl:param name="fireDistrictCodeValue"/>
  <xsl:param name="statusCode"/>
  <xsl:param name="errorDescription"/>
  <xsl:param name="errorSourceIdentifier"/>
  <xsl:param name="errorCode"/>
  <xsl:param name="errorSeverityLevel"/>
  <xsl:template match="/se_1:getLocationWithTaxInfoResponse">
    <LocationListReply>
      <Location>
        <LocationInformation>
          <CityCode><xsl:value-of select="location/dto:cityCode"/></CityCode>
          <CountyCode><xsl:value-of select="location/dto:countyCode"/></CountyCode>
          <LocationClassificationCode><xsl:value-of select="location/dto:locationClassCode"/></LocationClassificationCode>
          <FireDistrictCode><xsl:value-of select="fireDistrictCodeValue"/></FireDistrictCode>
        </LocationInformation>
        <StandardizedAddress>
          <CityName><xsl:value-of select="location/dto:cityName"/></CityName>
          <CountyName><xsl:value-of select="location/dto:countyName"/></CountyName>
          <StateOrProvinceCode><xsl:value-of select="location/dto:stateOrProvinceCode"/></StateOrProvinceCode>
          <StateOrProvinceName><xsl:value-of select="location/dto:stateOrProvinceName"/></StateOrProvinceName>
          <PostalStateAbbreviation><xsl:value-of select="location/dto:postalStateAbbreviation"/></PostalStateAbbreviation>
          <PostalCode><xsl:value-of select="location/dto:postalCode"/></PostalCode>
          <CountryCode><xsl:value-of select="location/dto:countryCode"/></CountryCode>
          <CountryName><xsl:value-of select="location/dto:countryName"/></CountryName>
          <LocationPlaceCode><xsl:value-of select="location/dto:locationPlaceCode"/></LocationPlaceCode>
          <POBoxIndicator><xsl:value-of select="location/dto:poBoxIndicator"/></POBoxIndicator>
        </StandardizedAddress>
        <TaxingJurisdiction>
          <TaxingJurisdictionTypeName>FireDistrict</TaxingJurisdictionTypeName>
          <TaxingJurisdictionCode><xsl:value-of select="location/dto:fireDistrictCode"/></TaxingJurisdictionCode>
        </TaxingJurisdiction>
		    <TaxingJurisdiction>
          <TaxingJurisdictionTypeName>TaxingTerritory</TaxingJurisdictionTypeName>
          <TaxingJurisdictionCode></TaxingJurisdictionCode>
          <TaxingJurisdictionName><xsl:value-of select="location/dto:stateOrProvinceName"/></TaxingJurisdictionName>
        </TaxingJurisdiction>
      </Location>
      <StatusInformation>
        <StatusCode><xsl:value-of select="$statusCode"/></StatusCode>
        <Error>
            <ErrorDescription><xsl:value-of select="$errorDescription"/></ErrorDescription>
            <ErrorSourceIdentifier><xsl:value-of select="$errorSourceIdentifier"/></ErrorSourceIdentifier>
            <ErrorCode><xsl:value-of select="$errorCode"/></ErrorCode>
            <ErrorSeverityLevel><xsl:value-of select="$errorSeverityLevel"/></ErrorSeverityLevel>
        </Error>
      </StatusInformation>
    </LocationListReply>
  </xsl:template>

</xsl:stylesheet>
