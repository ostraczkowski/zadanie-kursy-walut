/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.time.Year;

import static java.util.Objects.requireNonNull;

/**
 * Helper class with methods to access specific NBP resources.
 */
class ExchangeRatesRequestsHelper {

    private final static Logger LOG = LoggerFactory.getLogger(ExchangeRatesRequestsHelper.class);

    @Nonnull
    private static InputStream readTxtStream(@Nonnull final String url) throws IOException {
        return readStream(url, "text/plain");
    }

    @Nonnull
    private static InputStream readXmlStream(@Nonnull final String url) throws IOException {
        return readStream(url, "application/xml");
    }

    @Nonnull
    private static InputStream readStream(@Nonnull final String url, @Nonnull final String contentType) throws IOException {
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet(url);
        request.setHeader("Content-Type", contentType);
        LOG.debug("Reading stream from: " + url);
        final HttpResponse response = client.execute(request);
        return response.getEntity().getContent();
    }

    /**
     * @param currency        code of the currency.
     * @param startDateString start date of the period for which currency exchange rates should be calculated (inclusively)
     * @param endDateString   end date of the period for which currency exchange rates should be calculated (inclusively)
     * @return input stream to the result with exchange rates of given currency in given period of time.
     * @throws IOException
     */
    @Nonnull
    InputStream getQueryResultStream(@Nonnull final String currency, @Nonnull final String startDateString, @Nonnull final String endDateString)
            throws IOException {
        requireNonNull(currency, "'currency' must not be null");
        requireNonNull(startDateString, "'startDateString' must not be null");
        requireNonNull(endDateString, "'endDateString' must not be null");

        final String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/%s/%s", currency, startDateString, endDateString);
        return readXmlStream(url);
    }

    /**
     * @param fileName name of the file.
     * @return input stream to the file with currencies table of one particular day.
     * @throws IOException
     */
    @Nonnull
    InputStream getArchivedFileStream(@Nonnull final String fileName)
            throws IOException {
        requireNonNull(fileName, "'fileName' must not be null");

        final String url = String.format("http://www.nbp.pl/kursy/xml/%s", fileName);
        return readXmlStream(url);
    }

    /**
     * @param year must be 2002 or after but not after current year.
     * @return input stream to the index of archived files for given year.
     * @throws IOException
     */
    @Nonnull
    InputStream getArchivedFilesIndexStream(final int year)
            throws IOException {
        if (year < Year.now().getValue()) {
            final String url = String.format("http://www.nbp.pl/kursy/xml/dir%s.txt", year);
            return readTxtStream(url);
        } else {
            return readTxtStream("http://www.nbp.pl/kursy/xml/dir.txt");
        }
    }
}
