/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainClass {

    public static void main(final String[] args) {
        try {
            final QueryParams queryParams = InputParser.parseInput(args);
            final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
            final ExchangeRates exchangeRates = exchangeRatesService.readExchangeRates(
                    queryParams.getCurrency(),
                    queryParams.getDateFrom(),
                    queryParams.getDateTo()
            );

            final Double average = StatisticsCalculator.average(exchangeRates.getBidRates());
            final Double stdDeviation = StatisticsCalculator.stdDeviation(exchangeRates.getAskRates());
            printResult(average, stdDeviation);

        } catch (IllegalArgumentException iae) {
            printError(iae.getMessage());
        } catch (IOException ioe) {
            printError("No connection to the server");
        } catch (ParserConfigurationException e) {
            // TODO: handle properly
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO: handle properly
            e.printStackTrace();
        }
    }

    private static void printResult(final double averageRage, final double standardDeviation) {
        System.out.println(averageRage);
        System.out.println(standardDeviation);
    }

    private static void printError(final String message) {
        System.out.println("Error: " + message);
    }
}
