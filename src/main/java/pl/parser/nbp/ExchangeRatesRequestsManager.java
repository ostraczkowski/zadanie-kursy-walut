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

class ExchangeRatesRequestsManager {


    static InputStream readExchangeRatesFromApi(final String currency, final String dateFrom, final String dateTo) throws IOException {
        final String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/%s/%s", currency, dateFrom, dateTo);
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet(url);
        request.setHeader("Content-Type", "application/xml");
        final HttpResponse response = client.execute(request);
        return response.getEntity().getContent();
    }

    static InputStream readExchangeRatesFromArchiveFiles(final String currency, final String dateFrom, final String dateTo) {
        // TODO: implement
        // http://www.nbp.pl/kursy/xml/dir.txt
        // odczytac kod nnn pierwszej tabeli
        // wczytac kolejne tabele

        // czytanie roku historycznego http://www.nbp.pl/kursy/xml/dirYYYY.txt
        // czytanie roku aktualnego http://www.nbp.pl/kursy/xml/dir.txt

        // cnnnzrrmmdd.xml'
        return null;
    }
}
