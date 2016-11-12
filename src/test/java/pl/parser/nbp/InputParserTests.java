/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class InputParserTests {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern(InputParser.DATE_FORMAT);

    @Test
    public void testParsingInput() {
        // given
        final String currencyArg = "EUR";
        final String dateFromArg = "2013-01-28";
        final String dateToArg = "2013-01-31";

        // when
        final QueryParams params = InputParser.parseInput(new String[]{currencyArg, dateFromArg, dateToArg});

        // then
        assertEquals(currencyArg, params.getCurrency());
        assertEquals(LocalDate.parse(dateFromArg, DTF), params.getDateFrom());
        assertEquals(LocalDate.parse(dateToArg, DTF), params.getDateTo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParsingInputWithTooHistoricalDate() {
        // given
        final String currencyArg = "EUR";
        final String dateFromArg = "2002-01-01"; // 2002-01-02 is the earliest date available
        final String dateToArg = "2013-01-31";

        // when
        InputParser.parseInput(new String[]{currencyArg, dateFromArg, dateToArg});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParsingInputWithInvalidCurrencyCode() {
        // given
        final String currencyArg = "EU";
        final String dateFromArg = "2013-01-28";
        final String dateToArg = "2013-01-31";

        // when
        InputParser.parseInput(new String[]{currencyArg, dateFromArg, dateToArg});
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParsingInputWithDateFromAfterDateTo() {
        // given
        final String currencyArg = "EUR";
        final String dateFromArg = "2013-01-31";
        final String dateToArg = "2013-01-28";

        // when
        InputParser.parseInput(new String[]{currencyArg, dateFromArg, dateToArg});
    }
}
