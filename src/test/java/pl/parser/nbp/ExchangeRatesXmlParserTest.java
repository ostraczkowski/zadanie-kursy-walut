/**
 * Created by Oskar Strączkowski on 12.11.16.
 */
package pl.parser.nbp;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class ExchangeRatesXmlParserTest {

    @Test
    public void shouldParseXmlFromApi() {
        // given
        final Double[] expectedBidRates = new Double[]{4.3067, 4.3297, 4.3164, 4.3114, 4.2980, 4.2759, 4.2806, 4.2978, 4.3130, 4.3057, 4.2979, 4.2775, 4.2662, 4.2519, 4.2600, 4.2561, 4.2355, 4.2500, 4.2526, 4.2412, 4.2499, 4.2654};
        final Double[] expectedAskRates = new Double[]{4.3937, 4.4171, 4.4036, 4.3986, 4.3848, 4.3623, 4.3670, 4.3846, 4.4002, 4.3927, 4.3847, 4.3639, 4.3524, 4.3377, 4.3460, 4.3421, 4.3211, 4.3358, 4.3386, 4.3268, 4.3357, 4.3516};
        final InputStream sampleXmlStream = this.getClass().getClassLoader().getResourceAsStream("sample-api-under-93-days-data.xml");
        assertNotNull(sampleXmlStream);

        // when
        final ExchangeRatesXmlHandler actualResult;
        try {
            actualResult = ExchangeRatesResponseParser.parseXmlFromApi(sampleXmlStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            fail(e.getMessage());
            return;
        }

        // then
        assertArrayEquals(expectedBidRates, actualResult.getBidRates());
        assertArrayEquals(expectedAskRates, actualResult.getAskRates());
    }

    @Test
    public void shouldParseXmlFromArchiveFile() {
        // given
        final Double[] expectedBidRates = new Double[]{3.7976};
        final Double[] expectedAskRates = new Double[]{3.8744};
        final String currency = "EUR";
        final InputStream sampleXmlStream = this.getClass().getClassLoader().getResourceAsStream("sample-archive-file-data.xml");
        assertNotNull(sampleXmlStream);

        // when
        final ExchangeRatesXmlHandler actualResult;
        try {
            actualResult = ExchangeRatesResponseParser.parseXmlFromArchiveFile(sampleXmlStream, currency);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            fail(e.getMessage());
            return;
        }

        // then
        assertArrayEquals(expectedBidRates, actualResult.getBidRates());
        assertArrayEquals(expectedAskRates, actualResult.getAskRates());
    }
}
