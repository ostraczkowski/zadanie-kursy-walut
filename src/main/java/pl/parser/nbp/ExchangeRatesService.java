/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;

class ExchangeRatesService {

    /**
     * Maximum period in days when NBP API can be used to retrieve the result.
     */
    private static final int MAX_API_PERIOD_DAYS = 93;

    /**
     * @param currency currency code for which the request has to be made.
     * @param dateFrom start date of the period for which currency exchange rates should be calculated (inclusively)
     * @param dateTo end date of the period for which currency exchange rates should be calculated (inclusively)
     * @return POJO object with lists of BID and ASK rates.
     */
    ExchangeRates readExchangeRates(final String currency, final LocalDate dateFrom, final LocalDate dateTo) throws IOException, ParserConfigurationException, SAXException {
        final int period = Period.between(dateFrom, dateTo).getDays();
        if (period <= MAX_API_PERIOD_DAYS) {
            final InputStream inputStream = ExchangeRatesRequestsManager.readExchangeRatesFromApi(currency, dateFrom, dateTo);
            return ExchangeRatesXmlParser.parseXmlFromApi(inputStream);
        } else {
            final InputStream inputStream = ExchangeRatesRequestsManager.readExchangeRatesFromArchiveFiles(currency, dateFrom, dateTo);
            return ExchangeRatesXmlParser.parseXmlFromArchiveFile(inputStream);
        }
    }
}
