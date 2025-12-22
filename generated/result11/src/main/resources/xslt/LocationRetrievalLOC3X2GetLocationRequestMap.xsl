<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>

  <xsl:template match="/">
    <GetLocation3X2B xmlns="http://ei/location/location_retrieval_loc3x2b">
      <GetLocationRequest xmlns="http://ei/location/get_location_request_loc3x2b">
        <xsl:copy-of select="/*[local-name()='GetLocation']/*[local-name()='GetLocationRequest']/*"/>
      </GetLocationRequest>
    </GetLocation3X2B>
  </xsl:template>
</xsl:stylesheet>
