/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Service which reads exchange rates for given currency and given period of time.
 */
class ExchangeRatesService {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern(InputValidator.DATE_FORMAT);

    private final ExchangeRatesRequestsHelper requestsHelper = new ExchangeRatesRequestsHelper(); // TODO: DJ

    /**
     * Maximum period in days when NBP API can be used to retrieve the result.
     */
    private static final int MAX_API_PERIOD_DAYS = 93;

    /**
     * @param currency        currency code for which the request has to be made.
     * @param startDateString start date of the period for which currency exchange rates should be calculated (inclusively)
     * @param endDateString   end date of the period for which currency exchange rates should be calculated (inclusively)
     * @return POJO object with lists of BID and ASK rates.
     */
    ExchangeRates readExchangeRates(final String currency, final String startDateString, final String endDateString)
            throws IOException, ParserConfigurationException, SAXException {
        LocalDate startDate = LocalDate.parse(startDateString, DTF);
        LocalDate endDate = LocalDate.parse(endDateString, DTF);
        final int period = Period.between(startDate, endDate).getDays();
        if (period <= MAX_API_PERIOD_DAYS) {
            return readExchangeRatesFromQueryResult(currency, startDateString, endDateString);
        } else {
            return readExchangeRatesFromArchivedFiles(currency, startDate, endDate);
        }
    }

    private ExchangeRates readExchangeRatesFromQueryResult(final String currency, final String startDateString, final String endDateString)
            throws IOException, ParserConfigurationException, SAXException {
        final InputStream xmlInputStream = requestsHelper.getQueryResultStream(currency, startDateString, endDateString);
        final ExchangeRatesXmlHandler xmlHandler = ExchangeRatesResponseParser.parseXmlFromApi(xmlInputStream); // TODO: DJ
        return new ExchangeRates(xmlHandler.getBidRates(), xmlHandler.getAskRates());
    }

    private ExchangeRates readExchangeRatesFromArchivedFiles(final String currency, final LocalDate startDate, final LocalDate endDate)
            throws ParserConfigurationException, SAXException, IOException {
        final List<Double> tmpBidRates = new LinkedList<>();
        final List<Double> tmpAskRates = new LinkedList<>();
        for (int year = startDate.getYear(); year <= endDate.getYear(); year++) {
            final InputStream indexStream = requestsHelper.getArchivedFilesIndexStream(year);
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(indexStream))) {
                while (buffer.ready()) {
                    final String fileName = buffer.readLine();
                    final LocalDate fileDate = getFileDateFromName(fileName);
                    if (isFileInPeriod(startDate, endDate, fileDate)) {
                        final InputStream xmlStream = requestsHelper.getArchivedFileStream(fileName);
                        final ExchangeRatesXmlHandler xmlHandler = ExchangeRatesResponseParser.parseXmlFromArchiveFile(xmlStream, currency);
                        tmpBidRates.add(xmlHandler.getBidRates()[0]);
                        tmpAskRates.add(xmlHandler.getAskRates()[0]);
                    }
                }
            }
        }
        final Double[] bidRates = tmpBidRates.toArray(new Double[tmpBidRates.size()]);
        final Double[] askRates = tmpAskRates.toArray(new Double[tmpAskRates.size()]);
        return new ExchangeRates(bidRates, askRates);
    }

    private LocalDate getFileDateFromName(final String fileName) {
        final String fileDateString = String.format("20%s-%s-%s", fileName.substring(5, 7), fileName.substring(7, 9), fileName.substring(9, 11));
        return LocalDate.parse(fileDateString, DTF);
    }

    private boolean isFileInPeriod(final LocalDate startDate, final LocalDate endDate, final LocalDate fileDate) {
        return startDate.minusDays(1).isBefore(fileDate) && fileDate.isBefore(endDate.plusDays(1));
    }
}
