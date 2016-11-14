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
public class StatisticsCalculatorTest {

    // given
    private Double[] testValues;
    private Double expectedAverage;
    private Double expectedStdDeviation;

    @Parameterized.Parameters
    public static Collection getTestParameters() {
        return Arrays.asList(new Object[][]{
                {new Double[]{0.1}, 0.1, 0.0},
                {new Double[]{0.1, 0.9}, 0.5, 0.4},
                {new Double[]{600.0, 470.0, 170.0, 430.0, 300.0}, 394.0, 147.32277488562318}
        });
    }

    public StatisticsCalculatorTest(final Double[] testValues, final Double average, final Double stdDeviation) {
        this.testValues = testValues;
        this.expectedAverage = average;
        this.expectedStdDeviation = stdDeviation;
    }

    @Test
    public void testAverageCalculation() {
        // when
        final Double actualResult = StatisticsCalculator.average(testValues);

        // then
        assertEquals(expectedAverage, actualResult);
    }

    @Test
    public void testStdDeviationCalculation() {
        // when
        final Double actualResult = StatisticsCalculator.stdDeviation(testValues);

        // then
        assertEquals(expectedStdDeviation, actualResult);
    }
}
