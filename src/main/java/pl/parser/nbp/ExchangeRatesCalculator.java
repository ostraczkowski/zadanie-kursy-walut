package pl.parser.nbp;

import java.util.Arrays;

class ExchangeRatesCalculator {

    private ExchangeRatesCalculationResult result;

    ExchangeRatesCalculator calculate(double... values) {
        double average = calculateAverage(values);
        double stdDeviation = calculateStdDeviation(average, values);
        result = new ExchangeRatesCalculationResult(average, stdDeviation);
        return this;
    }

    private Double calculateAverage(final double[] values) {
        if (values.length == 0) {
            return Double.NaN;
        }
        return Arrays.stream(values)
                .summaryStatistics()
                .getAverage();
    }

    private Double calculateStdDeviation(final Double average, final double... values) {
        if (average == null || average == Double.NaN || values.length == 0) {
            return Double.NaN;
        }
        final double sum = Arrays.stream(values)
                .summaryStatistics()
                .getSum();
        return Math.sqrt(sum / (values.length - 1));
    }

    ExchangeRatesCalculationResult getResult() {
        return result;
    }
}
