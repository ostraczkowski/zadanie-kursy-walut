/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

/**
 * Specific handler implementation to parse XML data retrieved from NBP archive as a file.
 */
class XmlFromArchiveFileHandler extends ExchangeRatesXmlHandler {

    private final String currency;

    private double ratio;
    private String code;
    private double bidRate;
    private double askRate;

    XmlFromArchiveFileHandler(@Nonnull final String currency) {
        requireNonNull(currency, "'currency' must not be null");
        this.currency = currency;
    }

    @Override
    public void endElement(@Nonnull final String s, @Nonnull final String s1, @Nonnull final String elementName) throws SAXException {
        requireNonNull(s, "'s' must not be null");
        requireNonNull(s, "'s1' must not be null");
        requireNonNull(s, "'elementName' must not be null");

        switch (elementName) {
            case "przelicznik":
                ratio = Double.parseDouble(tmpValue);
                tmpValue = "";
                break;

            case "kod_waluty":
                code = tmpValue;
                tmpValue = "";
                break;

            case "kurs_kupna":
                bidRate = Double.parseDouble(tmpValue.replace(',', '.'));
                tmpValue = "";
                break;

            case "kurs_sprzedazy":
                askRate = Double.parseDouble(tmpValue.replace(',', '.'));
                tmpValue = "";
                break;

            case "pozycja":
                if (currency.equals(code)) {
                    bidRates.add(bidRate / ratio);
                    askRates.add(askRate / ratio);
                }
                break;

            default:
                // ignore
        }
    }
}
