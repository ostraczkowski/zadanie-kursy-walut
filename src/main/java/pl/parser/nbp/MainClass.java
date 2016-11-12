/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Main class of the application.
 */
public class MainClass {

    /**
     * Main method of the application.
     *
     * @param args exactly three arguments expected given as <currency> <date from> <date to> (e.g. EUR 2013-01-28 2013-01-31)
     */
    public static void main(final String[] args) {
        final QueryParams queryParams;
        try {
            queryParams = readAndValidateQueryParams(args);
        } catch (IllegalArgumentException iae) {
            printError(iae.getMessage());
            return;
        }

        final ExchangeRates exchangeRates;
        try {
            exchangeRates = readExchangeRates(queryParams);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            printError(e.getMessage());
            return;
        }

        // calculate the result
        final Double average = StatisticsCalculator.average(exchangeRates.getBidRates());
        final Double stdDeviation = StatisticsCalculator.stdDeviation(exchangeRates.getAskRates());

        printResult(average, stdDeviation);
    }

    private static QueryParams readAndValidateQueryParams(final String[] args) throws IllegalArgumentException {
        final InputValidator inputValidator = new InputValidator();
        if (!inputValidator.isInputValid(args)) {
            throw new IllegalArgumentException(inputValidator.getErrorMessage());
        }
        return new QueryParams(args[0], args[1], args[2]);
    }

    private static ExchangeRates readExchangeRates(final QueryParams queryParams) throws IOException, ParserConfigurationException, SAXException {
        final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
        try {
            return exchangeRatesService.readExchangeRates(
                    queryParams.getCurrency(),
                    queryParams.getStartDate(),
                    queryParams.getEndDate()
            );
        } catch (IOException ioe) {
            throw new IOException("No connection to the server", ioe);
        } catch (ParserConfigurationException pce) {
            throw new ParserConfigurationException("Invalid XML parser configuration");
        } catch (SAXException se) {
            throw new SAXException("Invalid XML data", se);
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
