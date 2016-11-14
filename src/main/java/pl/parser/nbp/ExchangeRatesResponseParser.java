/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.requireNonNull;

/**
 * Utility class with methods to parse XML response with exchange rates.
 */
class ExchangeRatesResponseParser {

    /**
     * @param xmlStream input stream with XML data
     * @return exchange rates parsed from XML retrieved via NBP API.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Nonnull
    static ExchangeRatesXmlHandler parseXmlFromApi(@Nonnull final InputStream xmlStream)
            throws ParserConfigurationException, SAXException, IOException {
        requireNonNull(xmlStream, "'xmlStream' must not be null");

        XmlFromApiHandler xmlHandler = new XmlFromApiHandler();
        xmlHandler.parseDocument(xmlStream);
        return xmlHandler;
    }

    /**
     * @param xmlStream input stream with XML data
     * @param currency  currency for which values should be parsed.
     * @return XML handler with values parsed from XML.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Nonnull
    static ExchangeRatesXmlHandler parseXmlFromArchiveFile(@Nonnull final InputStream xmlStream, @Nonnull final String currency)
            throws ParserConfigurationException, SAXException, IOException {
        requireNonNull(xmlStream, "'xmlStream' must not be null");
        requireNonNull(currency, "'currency' must not be null");

        XmlFromArchiveFileHandler xmlHandler = new XmlFromArchiveFileHandler(currency);
        xmlHandler.parseDocument(xmlStream);
        return xmlHandler;
    }
}
