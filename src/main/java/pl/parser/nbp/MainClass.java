/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainClass {

    public static void main(final String[] args) {
        // read the input
        final InputValidator inputValidator = new InputValidator();
        if (!inputValidator.isInputValid(args)) {
            printError(inputValidator.getErrorMessage());
            return;
        }
        final String currency = args[0];
        final String startDate = args[1];
        final String endDate = args[1];

        // read exchange rates
        final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
        final ExchangeRates exchangeRates;
        try {
            exchangeRates = exchangeRatesService.readExchangeRates(currency, startDate, endDate);
        } catch (IOException ioe) {
            printError("No connection to the server");
            return;
        } catch (ParserConfigurationException | SAXException e) {
            printError("Invalid XML data");
            return;
        }

        // calculate the result
        final Double average = StatisticsCalculator.average(exchangeRates.getBidRates());
        final Double stdDeviation = StatisticsCalculator.stdDeviation(exchangeRates.getAskRates());

        printResult(average, stdDeviation);

    }

    private static void printResult(final double averageRage, final double standardDeviation) {
        System.out.println(averageRage);
        System.out.println(standardDeviation);
    }

    private static void printError(final String message) {
        System.out.println("Error: " + message);
    }
}

