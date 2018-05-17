<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html lang="pl">
<head>
	<title>Szczegóły programu</title>
</head>
<body>
	<xsl:for-each select="eksport/listadata/kanal/listaprog">
	<h1><xsl:value-of select="nazwa"/></h1>
	</xsl:for-each>
	<xsl:for-each select="eksport/listadata/kanal">
	<h3>Kanał: <xsl:value-of select="kanal"/></h3>
	</xsl:for-each>
	<xsl:for-each select="eksport/listadata">
	<h3>Data emisji: <xsl:value-of select="data"/></h3>
	</xsl:for-each>
	<xsl:for-each select="eksport/listadata/kanal/listaprog">
	<h3>Godzina emisji: <xsl:value-of select="start"/></h3>
	<h4><xsl:value-of select="kopis"/></h4>
	<p><xsl:value-of select="dopis"/></p>
	</xsl:for-each>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
