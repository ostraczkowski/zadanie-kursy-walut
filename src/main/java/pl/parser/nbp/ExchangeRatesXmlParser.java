/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class with methods to parse XML response with exchange rates.
 */
class ExchangeRatesXmlParser {

    /**
     * @param inputStream input stream with XML data
     * @return exchange rates parsed from XML retrieved via NBP API.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    static ExchangeRatesXmlHandler parseXmlFromApi(final InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        XmlFromApiHandler xmlHandler = new XmlFromApiHandler();
        xmlHandler.parseDocument(inputStream);
        return xmlHandler;
    }

    /**
     * @param inputStream input stream with XML data
     * @param currency    currency for which values should be parsed.
     * @return XML handler with values parsed from XML.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    static ExchangeRatesXmlHandler parseXmlFromArchiveFile(final InputStream inputStream, final String currency) throws ParserConfigurationException, SAXException, IOException {
        XmlFromArchiveFileHandler xmlHandler = new XmlFromArchiveFileHandler(currency);
        xmlHandler.parseDocument(inputStream);
        return xmlHandler;
    }
}
