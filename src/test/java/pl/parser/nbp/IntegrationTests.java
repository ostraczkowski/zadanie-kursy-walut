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
        ExchangeRatesServiceTest.class})
/**
 * Runs only integration test.
 */
public class IntegrationTests {
}
