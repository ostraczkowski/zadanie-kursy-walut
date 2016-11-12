/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

/**
 * Immutable POJO which holds request parameters.
 */
class QueryParams {

    private final String currency;
    private final String startDate;
    private final String endDate;

    /**
     * @param currency  currency for which exchange rate should be checked
     * @param startDate start date of the period for which currency exchange rates should be calculated (inclusively)
     * @param endDate   end date of the period for which currency exchange rates should be calculated (inclusively)
     */
    QueryParams(final String currency, final String startDate, final String endDate) {
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    String getCurrency() {
        return currency;
    }

    String getStartDate() {
        return startDate;
    }

    String getEndDate() {
        return endDate;
    }
}
