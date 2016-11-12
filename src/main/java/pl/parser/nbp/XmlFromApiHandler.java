/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

class XmlFromApiHandler extends ExchangeRatesXmlHandler {

    @Override
    public void endElement(String s, String s1, String elementName) throws SAXException {
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
