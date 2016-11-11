package pl.parser.nbp;

/**
 * Created by oskar on 11.11.16.
 */
class ExchangeRatesCalculationResult {
    private Double average = null;
    private Double stdDeviation = null;

    ExchangeRatesCalculationResult(final double average, final double stdDeviation) {
        this.average = average;
        this.stdDeviation = stdDeviation;
    }

    Double getAverage() {
        return average;
    }

    Double getStdDeviation() {
        return stdDeviation;
    }
}