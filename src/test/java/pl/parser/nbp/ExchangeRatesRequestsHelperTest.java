/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for getting proper stream data from NBP.
 */
@Category(IntegrationTest.class)
public class ExchangeRatesRequestsHelperTest {

    private final ExchangeRatesRequestsHelper requestsHelper = new ExchangeRatesRequestsHelper();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGetQueryResulStreamUnderMaxPeriod() throws Exception {
        // given
        final InputStream sampleXmlStream = this.getClass().getClassLoader().getResourceAsStream("sample-api-under-93-days-data.xml");
        final String sampleXmlString = readStream(sampleXmlStream);
        final LocalDate startDate = LocalDate.parse("2016-09-01", InputValidator.DTF);
        final LocalDate endDate = LocalDate.parse("2016-09-30", InputValidator.DTF);
        assertTrue(ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)) < ExchangeRatesService.MAX_API_PERIOD_DAYS);

        // when
        final InputStream xmlStream = requestsHelper.getQueryResultStream("EUR", startDate.toString(), endDate.toString());
        final String xmlString = readStream(xmlStream);

        // then
        assertEquals(sampleXmlString.replaceAll("\\s+", ""), xmlString.replaceAll("\\s+", ""));
    }

    @Test
    public void shouldGetQueryResulStreamAtMaxPeriod() throws Exception {
        // given
        final InputStream sampleXmlStream = this.getClass().getClassLoader().getResourceAsStream("sample-api-at-93-days-data.xml");
        final String sampleXmlString = readStream(sampleXmlStream);
        final LocalDate startDate = LocalDate.parse("2016-09-01", InputValidator.DTF);
        final LocalDate endDate = LocalDate.parse("2016-11-14", InputValidator.DTF);
        long between = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
        assertTrue(between == ExchangeRatesService.MAX_API_PERIOD_DAYS);

        // when
        final InputStream xmlStream = requestsHelper.getQueryResultStream("EUR", startDate.toString(), endDate.toString());
        final String xmlString = readStream(xmlStream);

        // then
        assertEquals(sampleXmlString.replaceAll("\\s+", ""), xmlString.replaceAll("\\s+", ""));
    }

    @Test
    public void shouldThrowExceptionWhenGettingQueryResulStreamOverMaxPeriod() throws Exception {
        // given
        final LocalDate startDate = LocalDate.parse("2016-09-01", InputValidator.DTF);
        final LocalDate endDate = LocalDate.parse("2016-12-31", InputValidator.DTF);
        assertTrue(ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)) > ExchangeRatesService.MAX_API_PERIOD_DAYS);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("URL formula failed");

        // when
        requestsHelper.getQueryResultStream("EUR", startDate.toString(), endDate.toString());
    }

    @Test
    public void shouldThrowExceptionWhenGettingQueryResulStreamForInvalidCurrency() throws Exception {
        // given
        final LocalDate startDate = LocalDate.parse("2016-09-01", InputValidator.DTF);
        final LocalDate endDate = LocalDate.parse("2016-09-30", InputValidator.DTF);
        assertTrue(ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)) < ExchangeRatesService.MAX_API_PERIOD_DAYS);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No data for given parameters");

        // when
        requestsHelper.getQueryResultStream("XXX", startDate.toString(), endDate.toString());
    }

    @Test
    public void shouldGetArchivedFileStream() throws Exception {
        // given
        final InputStream sampleXmlStream = this.getClass().getClassLoader().getResourceAsStream("sample-archive-file-data.xml");
        final String sampleXmlString = readStream(sampleXmlStream);

        // when
        final InputStream xmlStream = requestsHelper.getArchivedFileStream("c073z070413.xml");
        final String xmlString = readStream(xmlStream);

        // then
        assertEquals(sampleXmlString, xmlString);
    }

    @Test
    public void shouldGetArchivedFilesIndexStream() throws Exception {
        // given
        final InputStream sampleIndexStream = this.getClass().getClassLoader().getResourceAsStream("sample-index.txt");
        final String sampleIndexString = readStream(sampleIndexStream);

        // when
        final InputStream indexStream = requestsHelper.getArchivedFilesIndexStream(2013);
        final String indexString = readStream(indexStream);

        // then
        assertEquals(sampleIndexString, indexString);
    }

    private String readStream(@Nonnull final InputStream inputStream) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}