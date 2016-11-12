/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.time.LocalDate;

/**
 * Immutable POJO which holds request parameters.
 */
class ExchangeRatesRequestParams {

    private final String currency;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    /**
     * @param currency currency for which exchange rate should be checked
     * @param dateFrom date from which the exchange rate should be calculated (inclusive)
     * @param dateTo   date to which the exchange rate should be calculated (inclusive)
     */
    ExchangeRatesRequestParams(final String currency, final LocalDate dateFrom, final LocalDate dateTo) {
        this.currency = currency;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    String getCurrency() {
        return currency;
    }

    LocalDate getDateFrom() {
        return dateFrom; // TODO: return copy
    }

    LocalDate getDateTo() {
        return dateTo; // TODO: return copy
    }
}
