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
        final ExchangeRates exchangeRates;
        try {
            final QueryParams queryParams = readAndValidateQueryParams(args);
            exchangeRates = readExchangeRates(queryParams);
            printResult(exchangeRates.getAvgBidRate(), exchangeRates.getStdDevAskRate());
        } catch (IllegalArgumentException | IOException | ParserConfigurationException | SAXException e) {
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
        } catch (IOException e) {
            throw new IOException("No connection to the server", e);
        } catch (ParserConfigurationException e) {
            throw new ParserConfigurationException("Invalid XML configuration");
        } catch (SAXException e) {
            throw new SAXException("Invalid XML data", e);
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
