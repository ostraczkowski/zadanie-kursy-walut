/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import java.util.Arrays;

/**
 * Immutable POJO which holds response data.
 */
class ExchangeRates {

    private final Double[] bidRates;
    private final Double[] askRates;

    /**
     * @param bidRates list of bid exchange rates for given currency read from NBP
     * @param askRates list of ask exchange rates for given currency read from NBP
     */
    ExchangeRates(final Double[] bidRates, final Double[] askRates) {
        // TODO: nullcheck
        this.bidRates = bidRates;
        this.askRates = askRates;
    }

    Double[] getBidRates() {
        return bidRates;
    }

    Double[] getAskRates() {
        return askRates;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExchangeRates)) {
            return false;
        }
        final ExchangeRates other = (ExchangeRates) obj;
        return Arrays.equals(this.bidRates, other.bidRates) && Arrays.equals(this.askRates, other.askRates);
    }
}
