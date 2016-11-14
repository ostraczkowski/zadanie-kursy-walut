/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

/**
 * Specific handler implementation to parse XML data retrieved via NBP API.
 */
class XmlFromApiHandler extends ExchangeRatesXmlHandler {

    @Override
    public void endElement(@Nonnull final String s, @Nonnull final String s1, @Nonnull final String elementName) throws SAXException {
        requireNonNull(s, "'s' must not be null");
        requireNonNull(s, "'s1' must not be null");
        requireNonNull(s, "'elementName' must not be null");

        switch (elementName) {
            case "Bid":
                bidRates.add(Double.parseDouble(tmpValue));
                tmpValue = "";
                break;

            case "Ask":
                askRates.add(Double.parseDouble(tmpValue));
                tmpValue = "";
                break;

            default:
                // ignore
        }
    }
}
