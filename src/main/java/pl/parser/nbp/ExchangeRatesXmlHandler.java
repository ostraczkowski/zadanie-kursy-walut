/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class with common methods to handle XML response to requested query.
 */
abstract class ExchangeRatesXmlHandler extends DefaultHandler {

    final List<Double> bidRates = new LinkedList<>();
    final List<Double> askRates = new LinkedList<>();

    String tmpValue;

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }

    /**
     * @param is input stream with XML data to parse
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    void parseDocument(final InputStream is) throws ParserConfigurationException, SAXException, IOException {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        final SAXParser parser = factory.newSAXParser();
        parser.parse(is, this);
    }

    Double[] getBidRates() {
        return bidRates.toArray(new Double[bidRates.size()]);
    }

    Double[] getAskRates() {
        return askRates.toArray(new Double[askRates.size()]);
    }
}
