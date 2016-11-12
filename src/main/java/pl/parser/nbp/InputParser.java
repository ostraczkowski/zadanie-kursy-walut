/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ulitity class with methods to parse input parameters.
 */
class InputParser {

    final static String DATE_FORMAT = "yyyy-MM-dd";

    private final static DateFormat DF = new SimpleDateFormat(DATE_FORMAT);

    static ExchangeRatesRequestParams parseInput(final String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments. Expected <currency> <date from> <date to>, e.g. EUR 2013-01-28 2013-01-31");
        }
        final String currency = parseCurrency(args[0]);
        final Date dateFrom = parseDate(args[1]);
        final Date dateTo = parseDate(args[2]);
        return new ExchangeRatesRequestParams(currency, dateFrom, dateTo);
    }

    private static String parseCurrency(final String currency) {
        if (currency.length() != 3) { // TODO: implement regex check
            throw new IllegalArgumentException("Invalid currency code. Expected exactly three letters, e.g. EUR, USD, CHF, GBP");
        }
        return currency.toUpperCase();
    }

    private static Date parseDate(final String date) {
        try {
            return DF.parse(date);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid date format. Expected format is <year>-<month>-<day>, e.g. 2013-01-28");
        }
    }
}
