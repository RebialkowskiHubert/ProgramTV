<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html lang="pl">
<head>
	<title>Program telewizyjny</title>
</head>
<body>
	<h1>Program telewizyjny:</h1>
	<table border="3">
		<tr>
			<th>Data emisji</th>
			<th>Kanał</th>
			<th>Godzina</th>
			<th>Nazwa programu</th>
			<th>Krótki opis</th>
		</tr>
		<xsl:for-each select="eksport/listadata/kanal/listaprog">
		<tr>
			<td><xsl:value-of select="../../data"/></td>
			<td><xsl:value-of select="../kanal"/></td>
			<td><xsl:value-of select="start"/></td>
			<td><xsl:value-of select="nazwa"/></td>
			<td><xsl:value-of select="kopis"/></td>
		</tr>
		</xsl:for-each>
	</table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
