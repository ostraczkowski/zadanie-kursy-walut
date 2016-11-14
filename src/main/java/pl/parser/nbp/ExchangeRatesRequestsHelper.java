/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.Year;

/**
 * Helper class with methods to access specific NBP resources.
 */
class ExchangeRatesRequestsHelper {

    private static InputStream readXmlStream(final String url) throws IOException {
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet(url);
        request.setHeader("Content-Type", "application/xml");
        final HttpResponse response = client.execute(request);
        return response.getEntity().getContent();
    }

    /**
     * @param currency code of the currency.
     * @param startDate start date of the period for which currency exchange rates should be calculated (inclusively)
     * @param endDate   end date of the period for which currency exchange rates should be calculated (inclusively)
     * @return input stream to the result with exchange rates of given currency in given period of time.
     * @throws IOException
     */
    InputStream getQueryResultStream(final String currency, final String startDate, final String endDate) throws IOException {
        final String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/%s/%s", currency, startDate, endDate);
        return readXmlStream(url);
    }

    /**
     * @param fileName name of the file.
     * @return input stream to the file with currencies table of one particular day.
     * @throws IOException
     */
    InputStream getArchivedFileStream(final String fileName) throws IOException {
        final String url = String.format("http://www.nbp.pl/kursy/xml/%s", fileName);
        return readXmlStream(url);
    }

    /**
     * @param year must be 2002 or after but not after current year.
     * @return input stream to the index of archived files for given year.
     * @throws IOException
     */
    InputStream getArchivedFilesIndexStream(final int year) throws IOException {
        if (year < Year.now().getValue()) {
            final String url = String.format("http://www.nbp.pl/kursy/xml/dir%s.txt", year);
            return readXmlStream(url);
        } else {
            return readXmlStream("http://www.nbp.pl/kursy/xml/dir.txt");
        }
    }
}
