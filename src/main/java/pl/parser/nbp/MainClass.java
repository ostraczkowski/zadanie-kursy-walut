/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
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
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
            return;
        }

        final ExchangeRates exchangeRates;
        try {
            exchangeRates = readExchangeRates(queryParams);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            printError(e.getMessage());
            return;
        }

        try {
            printResult(exchangeRates.getAvgBidRate(), exchangeRates.getStdDevAskRate());
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    @Nonnull
    private static QueryParams readAndValidateQueryParams(@Nonnull final String[] args)
            throws IllegalArgumentException {
        final InputValidator inputValidator = new InputValidator();
        if (!inputValidator.isInputValid(args)) {
            throw new IllegalArgumentException(inputValidator.getErrorMessage());
        }
        return new QueryParams(args[0], args[1], args[2]);
    }

    @Nonnull
    private static ExchangeRates readExchangeRates(@Nonnull final QueryParams queryParams)
            throws IOException, ParserConfigurationException, SAXException {
        final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
        try {
            return exchangeRatesService.readExchangeRates(
                    queryParams.getCurrency(),
                    queryParams.getStartDateString(),
                    queryParams.getEndDateString()
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
        System.out.println(String.format("%.4f", averageRage));
        System.out.println(String.format("%.4f", standardDeviation));
    }

    private static void printError(@Nonnull final String message) {
        System.out.println("Error: " + message);
    }
}
