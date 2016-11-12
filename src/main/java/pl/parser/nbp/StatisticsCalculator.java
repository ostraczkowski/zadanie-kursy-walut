/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Utility class with methods to calculate required statistics.
 */
class StatisticsCalculator {

    /**
     * @param values values to calculate the statistic
     * @return average of given values or NaN
     */
    static Double average(final Double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Values list must not be empty");
        }
        return Arrays.stream(unbox(values)).summaryStatistics().getAverage();
    }

    /**
     * @param values values to calculate the statistic
     * @return standard deviation value or NaN
     */
    static Double stdDeviation(final Double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Values list must not be empty");
        }

        final Double average = average(values);
        if (average == Double.NaN) {
            throw new IllegalArgumentException("Average value must be an actual value but is NaN");
        }


        double sumOfDeviations = Arrays.stream(unbox(values)).reduce(0, (sum, x) -> sum + (x - average) * (x - average));
        return Math.sqrt(sumOfDeviations / values.length);
    }

    private static double[] unbox(final Double... values) {
        return Stream.of(values).mapToDouble(Double::doubleValue).toArray();
    }
}
