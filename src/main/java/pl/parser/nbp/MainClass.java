/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

public class MainClass {

    public static void main(final String[] args) {
        try {
            final ExchangeRatesRequestParams requestParams = InputParser.parseInput(args);
            final RequestsManager requestsManager = new RequestsManager();
            final ExchangeRatesResponseData responseData = requestsManager.readExchangeRates(requestParams);

            final Double average = StatisticsCalculator.average(responseData.getBidRates());
            final Double stdDeviation = StatisticsCalculator.stdDeviation(responseData.getAskRates());
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
