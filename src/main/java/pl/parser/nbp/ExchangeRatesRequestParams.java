package pl.parser.nbp;

import java.util.Date;

/**
 * Created by oskar on 11.11.16.
 */
class ExchangeRatesRequestParams {

    private final String currency;
    private final Date dateFrom;
    private final Date dateTo;

    ExchangeRatesRequestParams(final String currency, final Date dateFrom, final Date dateTo) {
        this.currency = currency;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getDateFrom() {
        // TODO: immutable
        return dateFrom;
    }

    public Date getDateTo() {
        // TODO: immutable
        return dateTo;
    }
}
