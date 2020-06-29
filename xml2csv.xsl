<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
    <xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
    <xsl:template match="/">
        <xsl:text>Measure,default-x,Pitch,Type,Duration,Staff,Stem&#xA;</xsl:text>
        <xsl:for-each select="//note">
		     <!--
		    <xsl:sort select="../@number" data-type="number" />
			<xsl:sort select="@default-x" data-type="number" />
			-->
			<xsl:if test="pitch">
			  <xsl:value-of select="../@number"/>
			  <xsl:value-of select="concat(',',@default-x,',',pitch/step,pitch/octave)"/>
			  <xsl:choose>
				<xsl:when test="pitch/alter=1">
					<xsl:value-of select="'#'"/>
				</xsl:when>
				<xsl:when test="pitch/alter=-1">
					<xsl:value-of select="'b'"/>
				</xsl:when>
			  </xsl:choose>
			  <xsl:value-of select="concat(',',type,',', duration,',',staff,',',stem,'&#xA;')"/>
			</xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>