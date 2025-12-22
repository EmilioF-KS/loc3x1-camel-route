<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="/">
    <GetLocationListReply xmlns="http://ei/location/get_location_list_reply_loc3x1b">
      <xsl:copy-of select="//*[local-name()='LocationInformation']"/>
      <xsl:copy-of select="//*[local-name()='StandardizedAddress']"/>
      <xsl:copy-of select="//*[local-name()='FireDistrictCode']"/>
      <xsl:copy-of select="//*[local-name()='CityCode']"/>
      <xsl:copy-of select="//*[local-name()='CountyCode']"/>
      <xsl:copy-of select="//*[local-name()='PostalCode']"/>
      <xsl:copy-of select="//*[local-name()='StateOrProvinceCode']"/>
      <xsl:copy-of select="//*[local-name()='CountryCode']"/>
    </GetLocationListReply>
  </xsl:template>

</xsl:stylesheet>
