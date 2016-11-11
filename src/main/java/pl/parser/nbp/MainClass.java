package pl.parser.nbp;

/**
 * Created by oskar on 11.11.16.
 */
public class MainClass {

    public static void main(final String[] args) {
        final InputHandler inputHandler = new InputHandler();
        try {
            final ExchangeRatesRequestParams requestParams = inputHandler.readRequestParams();
            final RequestsHandler requestsHandler = new RequestsHandler();
            final ExchangeRatesResponseData responseData = requestsHandler.readExchangeRates(requestParams);
            final ExchangeRatesCalculator calculator = new ExchangeRatesCalculator();
            final ExchangeRatesCalculationResult result = calculator.calculate(responseData.getSellingRates()).getResult();
            printResult(result.getAverage(), result.getStdDeviation());
        } catch (Exception e) {
            printError(e.getMessage());
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
