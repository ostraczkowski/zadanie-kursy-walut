/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.util.Arrays;

/**
 * Utility class with methods to calculate some statistics.
 */
class StatisticsCalculator {

    static double average(final double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Unexpected empty list");
        }
        return Arrays.stream(values).summaryStatistics().getAverage();
    }

    static double stdDeviation(final double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Unexpected empty list");
        }

        final double average = average(values);
        if (average == Double.NaN) {
            throw new IllegalArgumentException("Average value NaN");
        }

        double sumOfDeviations = Arrays.stream(values).reduce(0, (sum, x) -> sum + (x - average) * (x - average));
        return Math.sqrt(sumOfDeviations / values.length);
    }
}
