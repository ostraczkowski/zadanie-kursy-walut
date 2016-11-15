/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Integration tests for reading exchange rates data from NBP.
 */
@Category(IntegrationTest.class)
@RunWith(Parameterized.class)
public class ExchangeRatesServiceTest {

    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

    // given
    private String currency;
    private String startDate;
    private String endDate;
    private Double expectedAvgBidRate;
    private Double expectedStdDevAskRate;

    @Parameterized.Parameters
    public static Collection getTestParameters() {
        return Arrays.asList(new Object[][]{
                {"EUR", "2002-01-02", "2002-01-02", 3.5016, 0.0}, // 1-day query
                {"EUR", "2013-01-28", "2013-01-31", 4.150525, 0.012477053939131748}, // 3-days query
                {"EUR", "2002-01-02", "2002-03-31", 3.580911111111111, 0.03967698056299293}, // over MAX_API_PERIOD_DAYS query (reading from files should be used here)
//                {"EUR", "2002-01-02", "2016-09-01", 4.055777462887989, 0.29801378039587295}, // over MAX_API_PERIOD_DAYS query, huge test (reading from files should be used here)
        });
    }

    public ExchangeRatesServiceTest(final String currency, final String startDate,  final String endDate,  final Double expectedAvgBidRate, final Double expectedStdDevAskRate) {
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedAvgBidRate = expectedAvgBidRate;
        this.expectedStdDevAskRate = expectedStdDevAskRate;
    }

    @Test
    public void testReadingExchangeRates() {
        // when
        final ExchangeRates exchangeRates;
        try {
            exchangeRates = exchangeRatesService.readExchangeRates(currency, startDate, endDate);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            fail(e.getMessage());
            return;
        }

        // then
        assertEquals(expectedAvgBidRate, exchangeRates.getAvgBidRate());
        assertEquals(expectedStdDevAskRate, exchangeRates.getStdDevAskRate());
    }
}
