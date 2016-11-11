/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.util.Date;

// komentarz że klasa immutable
class InputHandler {

    private String currency;
    private Date dateFrom;
    private Date dateTo;

    ExchangeRatesRequestParams readRequestParams() {
        final String input = readInput();
        parseInput(input);
        return new ExchangeRatesRequestParams(currency, dateFrom, dateTo);
    }

    private String readInput() {
        // TODO: implement
        return "";
    }

    private void parseInput(final String input) {
        final String[] params = input.split(" ");
        if (params.length != 3) {
            throw new IllegalArgumentException("Niewłaściwa liczba parametrów. Spodziewane parametry: <kurs> <data od> <data do>, np. EUR 2013-01-28 2013-01-31");
        }
        currency = parseCurrency(params[0]);
        dateFrom = parseDate(params[1]);
        dateTo = parseDate(params[2]);
    }

    private String parseCurrency(final String currency) {
        if (currency.length() != 3) {
            throw new IllegalArgumentException("Niewłaściwy format waluty. Spodziewany jest trzyliterowy kod, np. EUR, USD, CHF, GBP");
        }
        return currency.toUpperCase();
    }

    private Date parseDate(final String date) {
        try {
            // TODO: change to proper implementation
            return new Date(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Niewłaściwy format daty. Spodziewany jest rok, miesiąc i dzień, np. 2013-01-28");
        }
    }
}
