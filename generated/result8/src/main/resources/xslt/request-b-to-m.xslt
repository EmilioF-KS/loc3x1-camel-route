<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="/">
    <GetLocationListRequest xmlns="http://ei/location/get_location_list_request_loc3x1m">
      <xsl:copy-of select="//*[local-name()='AddressLine1']"/>
      <xsl:copy-of select="//*[local-name()='AddressLine2']"/>
      <xsl:copy-of select="//*[local-name()='CityName']"/>
      <xsl:copy-of select="//*[local-name()='StateOrProvinceCode']"/>
      <xsl:copy-of select="//*[local-name()='PostalStateAbbreviation']"/>
      <xsl:copy-of select="//*[local-name()='PostalCode']"/>
      <xsl:copy-of select="//*[local-name()='LocationPlaceCode']"/>
      <xsl:copy-of select="//*[local-name()='CountryCode']"/>
      <xsl:copy-of select="//*[local-name()='CountryAbbreviation']"/>
    </GetLocationListRequest>
  </xsl:template>

</xsl:stylesheet>
