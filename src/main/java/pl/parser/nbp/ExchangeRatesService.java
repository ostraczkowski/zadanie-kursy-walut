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
import java.time.format.DateTimeFormatter;

/**
 * Service which reads exchange rates for given currency and given period of time.
 */
class ExchangeRatesService {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern(InputValidator.DATE_FORMAT);

    /**
     * Maximum period in days when NBP API can be used to retrieve the result.
     */
    private static final int MAX_API_PERIOD_DAYS = 93;

    /**
     * @param currency currency code for which the request has to be made.
     * @param startDate start date of the period for which currency exchange rates should be calculated (inclusively)
     * @param endDate end date of the period for which currency exchange rates should be calculated (inclusively)
     * @return POJO object with lists of BID and ASK rates.
     */
    ExchangeRates readExchangeRates(final String currency, final String startDate, final String endDate) throws IOException, ParserConfigurationException, SAXException {
        final int period = Period.between(LocalDate.parse(startDate, DTF),  LocalDate.parse(endDate, DTF)).getDays();
        if (period <= MAX_API_PERIOD_DAYS) {
            final InputStream inputStream = ExchangeRatesRequestsManager.readExchangeRatesFromApi(currency, startDate, endDate);
            return ExchangeRatesXmlParser.parseXmlFromApi(inputStream);
        } else {
            final InputStream inputStream = ExchangeRatesRequestsManager.readExchangeRatesFromArchiveFiles(currency, startDate, endDate);
            return ExchangeRatesXmlParser.parseXmlFromArchiveFile(inputStream);
        }
    }
}
