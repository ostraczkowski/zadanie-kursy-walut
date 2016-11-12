/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.time.Period;

class RequestsManager {

    /**
     * Maximum period in days when NBP API can be used to retrieve the result.
     */
    private static final int MAX_API_PERIOD_DAYS = 93;

    /**
     * @param params parameters describing the request which has to be made.
     * @return POJO object with lists of BID and ASK rates.
     */
    ExchangeRatesResponseData readExchangeRates(final ExchangeRatesRequestParams params) {
        final int period = Period.between(params.getDateFrom(), params.getDateTo()).getDays();
        if (period <= MAX_API_PERIOD_DAYS) {
            return readExchangeRatesFromApi(params);
        } else {
            return readExchangeRatesFromArchives(params);
        }
    }

    private ExchangeRatesResponseData readExchangeRatesFromApi(final ExchangeRatesRequestParams params) {
        // TODO: implement
        // http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/{startDate}/{endDate}/
        return null;
    }

    private ExchangeRatesResponseData readExchangeRatesFromArchives(final ExchangeRatesRequestParams params) {
        // TODO: implement
        // http://www.nbp.pl/kursy/xml/dir.txt
        // odczytac kod nnn pierwszej tabeli
        // wczytac kolejne tabele
        // cnnnzrrmmdd.xml'
        return null;
    }
}
