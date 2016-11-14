/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

/**
 * Immutable POJO which holds request parameters.
 */
class QueryParams {

    private final String currency;
    private final String startDateString;
    private final String endDateString;

    /**
     * @param currency  currency for which exchange rate should be checked
     * @param startDateString start date of the period for which currency exchange rates should be calculated (inclusively)
     * @param endDateString   end date of the period for which currency exchange rates should be calculated (inclusively)
     */
    QueryParams(@Nonnull final String currency, @Nonnull final String startDateString, @Nonnull final String endDateString) {
        requireNonNull(currency, "'currency' must not be null");
        requireNonNull(startDateString, "'startDateString' must not be null");
        requireNonNull(endDateString, "'endDateString' must not be null");

        this.currency = currency;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
    }

    @Nonnull
    String getCurrency() {
        return currency;
    }

    @Nonnull
    String getStartDateString() {
        return startDateString;
    }

    @Nonnull
    String getEndDateString() {
        return endDateString;
    }
}
