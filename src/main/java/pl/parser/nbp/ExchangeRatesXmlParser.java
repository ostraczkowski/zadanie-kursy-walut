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

    private static final ExchangeRatesXmlHandler XML_FROM_API_HANDLER = new XmlFromApiHandler();
    private static final ExchangeRatesXmlHandler XML_FROM_FILE_HANDLER = new XmlFromFileHandler();

    /**
     * @param inputStream input stream with XML data
     * @return exchange rates parsed from XML retrieved via NBP API.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    static ExchangeRates parseXmlFromApi(final InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        return parseXml(XML_FROM_API_HANDLER, inputStream);
    }

    /**
     * @param inputStream input stream with XML data
     * @return exchange rates parsed from XML retrieved from NBP archive data.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    static ExchangeRates parseXmlFromFile(final InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        return parseXml(XML_FROM_FILE_HANDLER, inputStream);
    }

    private static ExchangeRates parseXml(final ExchangeRatesXmlHandler xmlHandler, final InputStream xmlInputStream) throws IOException, SAXException, ParserConfigurationException {
        xmlHandler.parseDocument(xmlInputStream);
        return new ExchangeRates(xmlHandler.getBidRates(), xmlHandler.getAskRates());
    }
}
