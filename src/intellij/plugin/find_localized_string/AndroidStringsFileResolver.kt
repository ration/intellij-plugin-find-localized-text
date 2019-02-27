package intellij.plugin.find_localized_string

import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXParseException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class AndroidStringsFileResolver : LocalizedTextResolver {
    override fun resolve(file: CharSequence, text: Regex): Set<String> {
        val matches = mutableSetOf<String>()
        return try {
            val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val doc = docBuilder.parse(InputSource(StringReader(file.toString())))
            val pattern = "/resources/string/text()"
            val xpath = XPathFactory.newInstance().newXPath()
            val nodeList = xpath.evaluate(pattern, doc, XPathConstants.NODESET) as NodeList
            for (i in 0 until nodeList.length) {
                if (nodeList.item(i).textContent.contains(text) && (nodeList.item(i).parentNode.attributes.getNamedItem("name")) != null) {
                    matches += "@string/"+(nodeList.item(i).parentNode.attributes.getNamedItem("name")).nodeValue
                }
            }
            matches
        } catch (ex: SAXParseException) {
            setOf()
        }
    }

    override val fileExtension = "xml"
    override val fileNamePattern = Regex("strings")
}
