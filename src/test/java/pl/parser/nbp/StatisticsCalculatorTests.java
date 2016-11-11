/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StatisticsCalculatorTests {

    private double[] testValues;
    private Double expectedAverage;
    private Double expectedStdDeviation;

    public StatisticsCalculatorTests(final double[] testValues, final Double average, final Double stdDeviation) {
        this.testValues = testValues;
        this.expectedAverage = average;
        this.expectedStdDeviation = stdDeviation;
    }

    @Parameterized.Parameters
    public static Collection getTestParameters() {
        return Arrays.asList(new Object[][] {
                { new double[] { 0.1 }, 0.1, 0.0 },
                { new double[] { 0.1, 0.9 }, 0.5, 0.4 },
                { new double[] { 600.0, 470.0, 170.0, 430.0, 300.0 }, 394.0, 147.32277488562318 }
        });
    }

    @Test
    public void testAverageCalculation() {
        final Double actualResult = StatisticsCalculator.average(testValues);
        assertEquals(expectedAverage, actualResult);
    }

    @Test
    public void testStdDeviationCalculation() {
        final Double actualResult = StatisticsCalculator.stdDeviation(testValues);
        assertEquals(expectedStdDeviation, actualResult);
    }
}
