/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

public class MainClass {

    public static void main(final String[] args) {
        try {
            final ExchangeRatesRequestParams requestParams = InputParser.parseInput(args);
            final RequestsHandler requestsHandler = new RequestsHandler();
            final ExchangeRatesResponseData responseData = requestsHandler.readExchangeRates(requestParams);

            final Double average = StatisticsCalculator.average(responseData.getBuyingRates());
            final Double stdDeviation = StatisticsCalculator.stdDeviation(responseData.getSellingRates());
            printResult(average, stdDeviation);
        } catch (IllegalArgumentException iae) {
            printError(iae.getMessage());
        }
    }

    private static void printResult(final double averageRage, final double standardDeviation) {
        System.out.println(averageRage);
        System.out.println(standardDeviation);
    }

    private static void printError(final String message) {
        System.out.println(message);
    }
}
