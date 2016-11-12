/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ulitity class with methods to parse input parameters.
 */
class InputParser {

    final static String DATE_FORMAT = "yyyy-MM-dd";

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final static LocalDate EARLIEST_DATE_AVAILABLE;

    static {
        try {
            EARLIEST_DATE_AVAILABLE = LocalDate.parse("2002-01-02", DTF);
        } catch (DateTimeParseException dtpe) {
            throw new RuntimeException(dtpe);
        }
    }

    static ExchangeRatesRequestParams parseInput(final String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments. Expected <currency> <date from> <date to>, e.g. EUR 2013-01-28 2013-01-31");
        }
        final String currency = parseCurrency(args[0]);
        final LocalDate dateFrom = parseDate(args[1]);
        final LocalDate dateTo = parseDate(args[2]);
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("Date From is after Date To");
        }
        return new ExchangeRatesRequestParams(currency, dateFrom, dateTo);
    }

    private static String parseCurrency(final String currency) {
        final Pattern pattern = Pattern.compile("[a-zA-Z]{3}");
        final Matcher matcher = pattern.matcher(currency);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid currency code. Expected exactly three letters, e.g. EUR, USD, CHF, GBP");
        }
        return currency.toUpperCase();
    }

    private static LocalDate parseDate(final String dateString) {
        final LocalDate date;
        try {
            date = LocalDate.parse(dateString, DTF);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalArgumentException("Invalid date format. Expected format is <year>-<month>-<day>, e.g. 2013-01-28");
        }
        if (date.isBefore(EARLIEST_DATE_AVAILABLE)) {
            throw new IllegalArgumentException("Earliest date available is 2002-01-02");
        }
        return date;
    }
}
