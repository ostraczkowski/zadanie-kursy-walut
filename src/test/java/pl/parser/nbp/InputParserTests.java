/**
 * Created by Oskar Strączkowski on 11.11.16.
 */
package pl.parser.nbp;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class InputParserTests {

    @Test
    public void testParsingInput() throws ParseException {
        // given
        final DateFormat df = new SimpleDateFormat(InputParser.DATE_FORMAT);
        final String currencyArg = "EUR";
        final String dateFromArg = "2013-01-28";
        final String dateToArg = "2013-01-31";

        // when
        final ExchangeRatesRequestParams params = InputParser.parseInput(new String[]{currencyArg, dateFromArg, dateToArg});

        // then
        assertEquals(currencyArg, params.getCurrency());
        assertEquals(df.parse(dateFromArg), params.getDateFrom());
        assertEquals(df.parse(dateToArg), params.getDateTo());
    }
}
