/**
 * Created by Oskar Strączkowski on 14.11.16.
 */
package pl.parser.nbp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        ExchangeRatesRequestsHelperTest.class,
        ExchangeRatesServiceTest.class,
        ExchangeRatesXmlParserTest.class,
        InputValidatorTest.class,
        StatisticsCalculatorTest.class})
/**
 * Runs all tests available, both unit and integration.
 */
public class AllTests {
}
