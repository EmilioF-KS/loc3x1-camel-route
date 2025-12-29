<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:element name="GetLocationResponse">
      <xsl:element name="GetLocationReply">
        <xsl:element name="Location">
          <xsl:element name="StandardizedAddress">
            <xsl:element name="CityName">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/CityName"/>
            </xsl:element>
            <xsl:element name="CountyName">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/CountyName"/>
            </xsl:element>
            <xsl:element name="StateOrProvinceCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/StateOrProvinceCode"/>
            </xsl:element>
            <xsl:element name="StateOrProvinceName">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/StateOrProvinceName"/>
            </xsl:element>
            <xsl:element name="PostalStateAbbreviation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/PostalStateAbbreviation"/>
            </xsl:element>
            <xsl:element name="StateOrProvinceAbbreviation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/StateOrProvinceAbbreviation"/>
            </xsl:element>
            <xsl:element name="PostalCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/PostalCode"/>
            </xsl:element>
            <xsl:element name="CountryCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/CountryCode"/>
            </xsl:element>
            <xsl:element name="CountryName">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/CountryName"/>
            </xsl:element>
            <xsl:element name="LocationPlaceCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/LocationPlaceCode"/>
            </xsl:element>
            <xsl:element name="POBoxIndicator">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/POBoxIndicator"/>
            </xsl:element>
            <xsl:element name="AddressLine1">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/AddressLine1"/>
            </xsl:element>
            <xsl:element name="CountryAbbreviation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/CountryAbbreviation"/>
            </xsl:element>
            <xsl:element name="ISOCountryCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/ISOCountryCode"/>
            </xsl:element>
            <xsl:element name="PreDirectionalAbbreviation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/PreDirectionalAbbreviation"/>
            </xsl:element>
            <xsl:element name="StreetNumber">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/StreetNumber"/>
            </xsl:element>
            <xsl:element name="StreetSuffixAbbreviation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/StreetSuffixAbbreviation"/>
            </xsl:element>
            <xsl:element name="PostDirectionalAbbreviation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/PostDirectionalAbbreviation"/>
            </xsl:element>
            <xsl:element name="AddressUnitNumber">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/AddressUnitNumber"/>
            </xsl:element>
            <xsl:element name="Latitude">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/Latitude"/>
            </xsl:element>
            <xsl:element name="Longitude">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/Longitude"/>
            </xsl:element>
            <xsl:element name="AddressUnitTypeAbbreviation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/AddressUnitTypeAbbreviation"/>
            </xsl:element>
            <xsl:element name="DeliveryPointNumber">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/DeliveryPointNumber"/>
            </xsl:element>
            <xsl:element name="FIPSStateCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/FIPSStateCode"/>
            </xsl:element>
            <xsl:element name="FIPSCountyCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/FIPSCountyCode"/>
            </xsl:element>
            <xsl:element name="GeoCodingStatusCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/GeoCodingStatusCode"/>
            </xsl:element>
            <xsl:element name="EnclosingPostalCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/EnclosingPostalCode"/>
            </xsl:element>
            <xsl:element name="BuildingName">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/BuildingName"/>
            </xsl:element>
            <xsl:element name="AddressLine2">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/AddressLine2"/>
            </xsl:element>
            <xsl:element name="AddressLine3">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/AddressLine3"/>
            </xsl:element>
            <xsl:element name="StreetName">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/StandardizedAddress/StreetName"/>
            </xsl:element>
          </xsl:element>
          <xsl:element name="LocationInformation">
            <xsl:element name="IndustrialRiskInsurerCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/IndustrialRiskInsurerCode"/>
            </xsl:element>
            <xsl:element name="LicenseCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/LicenseCode"/>
            </xsl:element>
            <xsl:element name="LocationClassificationCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/LocationClassificationCode"/>
            </xsl:element>
            <xsl:element name="CountyCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/CountyCode"/>
            </xsl:element>
            <xsl:element name="CityCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/CityCode"/>
            </xsl:element>
            <xsl:element name="FireDistrictCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/FireDistrictCode"/>
            </xsl:element>
            <xsl:element name="MineSubsidenceCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/MineSubsidenceCode"/>
            </xsl:element>
            <xsl:element name="FloridaTortCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/FloridaTortCode"/>
            </xsl:element>
            <xsl:element name="TexasPlaceCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/TexasPlaceCode"/>
            </xsl:element>
            <xsl:element name="TexasCountyCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/TexasCountyCode"/>
            </xsl:element>
            <xsl:element name="TexasCityCode">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/TexasCityCode"/>
            </xsl:element>
            <xsl:element name="PublicProtectionInformation">
              <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/LocationInformation/PublicProtectionInformation"/>
            </xsl:element>
          </xsl:element>
          <xsl:element name="TaxingJurisdiction">
            <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/TaxingJurisdiction"/>
          </xsl:element>
          <xsl:element name="HazardousArea">
            <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/HazardousArea"/>
          </xsl:element>
          <xsl:element name="RatingTerritory">
            <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/RatingTerritory"/>
          </xsl:element>
          <xsl:element name="ReferralZone">
            <xsl:value-of select="GetLocationResponse/GetLocationReply/Location/ReferralZone"/>
          </xsl:element>
        </xsl:element>
        <xsl:element name="StatusInformation">
          <xsl:value-of select="GetLocationResponse/GetLocationReply/StatusInformation"/>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
