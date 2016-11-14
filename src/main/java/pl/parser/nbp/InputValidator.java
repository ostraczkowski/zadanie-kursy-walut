/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * Utility class with methods to validate input arguments.
 */
class InputValidator {

    final static String DATE_FORMAT = "yyyy-MM-dd";

    private final static String EARLIEST_DATE_AVAILABLE_STRING = "2002-01-02";
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final static LocalDate EARLIEST_DATE_AVAILABLE;

    static {
        try {
            EARLIEST_DATE_AVAILABLE = LocalDate.parse(EARLIEST_DATE_AVAILABLE_STRING, DTF);
        } catch (DateTimeParseException dtpe) {
            throw new RuntimeException(dtpe);
        }
    }

    private String errorMessage = null;

    /**
     * @param args arguments from command line which describes expected query.
     * @return POJO object with parameters of the request which has to be made.
     */
    boolean isInputValid(@Nonnull final String[] args) {
        requireNonNull(args, "'args' must not be null");

        if (args.length != 3) {
            errorMessage = "Invalid number of arguments, expected <currency> <date from> <date to> (e.g. EUR 2013-01-28 2013-01-31)";
            return false;
        } else if (!isCurrencyValid(args[0])) {
            return false;
        } else if (!areDatesValid(args[1], args[2])) {
            return false;
        }
        return true;
    }

    private boolean isCurrencyValid(@Nonnull final String currency) {
        requireNonNull(currency, "'currency' must not be null");

        final Pattern pattern = Pattern.compile("[a-zA-Z]{3}");
        final Matcher matcher = pattern.matcher(currency);
        if (!matcher.matches()) {
            errorMessage = "Invalid currency code, expected exactly three letters (e.g. EUR, USD, CHF, GBP)";
            return false;
        }
        return true;
    }

    private boolean areDatesValid(@Nonnull final String startDateString, @Nonnull final String endDateString) {
        requireNonNull(startDateString, "'startDateString' must not be null");
        requireNonNull(endDateString, "'endDateString' must not be null");

        final LocalDate startDate = LocalDate.parse(startDateString, DTF);
        final LocalDate endDate = LocalDate.parse(endDateString, DTF);
        if (startDate.isAfter(endDate)) {
            errorMessage = "Start date is after end date";
            return false;
        }
        if (startDate.isBefore(EARLIEST_DATE_AVAILABLE)) {
            errorMessage = "Start date is later than earliest date available (" + EARLIEST_DATE_AVAILABLE_STRING + ")";
            return false;
        }
        if (endDate.isAfter(LocalDate.now())) {
            errorMessage = "End date is from the future";
            return false;
        }
        return true;
    }

    String getErrorMessage() {
        return errorMessage;
    }
}
