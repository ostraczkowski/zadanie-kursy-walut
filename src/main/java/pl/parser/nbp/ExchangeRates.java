/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

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
    @Nonnull
    ExchangeRates(@Nonnull final Double[] bidRates, @Nonnull final Double[] askRates) {
        requireNonNull(bidRates, "'bidRates' must not be null");
        requireNonNull(askRates, "'askRates' must not be null");

        this.bidRates = bidRates;
        this.askRates = askRates;
    }

    @Nonnull
    Double[] getBidRates() {
        return bidRates;
    }

    @Nonnull
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
