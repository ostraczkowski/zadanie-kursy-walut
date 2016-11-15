/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputValidatorTest {

    private InputValidator inputValidator = new InputValidator();

    @Test
    public void testValidatingValidInput() {
        // given
        final String currencyArg = "EUR";
        final String dateFromArg = "2013-01-28";
        final String dateToArg = "2013-01-31";

        // when
        boolean result = inputValidator.isInputValid(new String[]{currencyArg, dateFromArg, dateToArg});

        // then
        assertEquals(true, result);
    }

    @Test
    public void testValidatingInvalidInputWithTooHistoricalDate() {
        // given
        final String currencyArg = "EUR";
        final String dateFromArg = "2002-01-01"; // 2002-01-02 is the earliest date available
        final String dateToArg = "2013-01-31";

        // when
        boolean result = inputValidator.isInputValid(new String[]{currencyArg, dateFromArg, dateToArg});

        // then
        assertEquals(false, result);
    }

    @Test
    public void testValidatingInvalidInputWithInvalidCurrencyCode() {
        // given
        final String currencyArg = "EU";
        final String dateFromArg = "2013-01-28";
        final String dateToArg = "2013-01-31";

        // when
        boolean result = inputValidator.isInputValid(new String[]{currencyArg, dateFromArg, dateToArg});

        // then
        assertEquals(false, result);
    }

    @Test
    public void testValidatingInvalidInputWithDateFromAfterDateTo() {
        // given
        final String currencyArg = "EUR";
        final String dateFromArg = "2013-01-31";
        final String dateToArg = "2013-01-28";

        // when
        boolean result = inputValidator.isInputValid(new String[]{currencyArg, dateFromArg, dateToArg});

        // then
        assertEquals(false, result);
    }
}
