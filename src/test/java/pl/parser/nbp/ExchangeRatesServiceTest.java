/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.junit.Test;
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
@RunWith(Parameterized.class)
public class ExchangeRatesServiceTest {

    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService(); // TODO: DI

    // given
    private String currency;
    private String startDate;
    private String endDate;
    private Double expectedAvgBidRate;
    private Double expectedStdDevAskRate;

    @Parameterized.Parameters
    public static Collection getTestParameters() {
        return Arrays.asList(new Object[][]{
                {"EUR", "2013-01-28", "2013-01-31", 4.150525, 0.012477053939131748},
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
