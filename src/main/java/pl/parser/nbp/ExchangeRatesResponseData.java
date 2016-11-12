/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

/**
 * Immutable POJO which holds response data.
 */
class ExchangeRatesResponseData {

    private final double[] bidRates;
    private final double[] askRates;

    /**
     * @param bidRates list of bid exchange rates for given currency read from NBP
     * @param askRates list of ask exchange rates for given currency read from NBP
     */
    ExchangeRatesResponseData(final double[] bidRates, final double[] askRates) {
        this.bidRates = bidRates;
        this.askRates = askRates;
    }

    double[] getBidRates() {
        return bidRates;
    }

    double[] getAskRates() {
        return askRates;
    }
}
