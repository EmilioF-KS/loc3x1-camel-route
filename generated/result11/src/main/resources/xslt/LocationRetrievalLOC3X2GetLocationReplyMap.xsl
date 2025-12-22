<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>

  <xsl:template match="/">
    <GetLocationResponse xmlns="http://ei/location/location_retrieval_loc3x2">
      <GetLocationReply xmlns="http://ei/location/get_location_reply_loc3x2">
        <xsl:copy-of select="/*[local-name()='GetLocation3X2BResponse']/*[local-name()='GetLocationReply']/*"/>
      </GetLocationReply>
    </GetLocationResponse>
  </xsl:template>
</xsl:stylesheet>
