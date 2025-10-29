package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ArgumentTokenizerTest {

    private final Prefix unknownPrefix = new Prefix("--u");
    private final Prefix pSlash = new Prefix("p/");
    private final Prefix dashT = new Prefix("-t");
    private final Prefix hatQ = new Prefix("^Q");

    private void assertPreamblePresent(ArgumentMultimap argMultimap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMultimap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMultimap argMultimap) {
        assertTrue(argMultimap.getPreamble().isEmpty());
    }

    private void assertArgumentPresent(ArgumentMultimap argMultimap, Prefix prefix, String... expectedValues) {
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(prefix).get());
        assertEquals(expectedValues.length, argMultimap.getAllValues(prefix).size());
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(prefix).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, Prefix prefix) {
        assertFalse(argMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_emptyArgsString_noValues() throws ParseException {
        String argsString = "  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() throws ParseException {
        String argsString = "  some random string tag with leading and trailing spaces ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);
        assertPreamblePresent(argMultimap, argsString.trim());
    }

    @Test
    public void tokenize_oneArgument() throws ParseException {
        String argsString = "  Some preamble string p/ Argument value ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);
        assertPreamblePresent(argMultimap, "Some preamble string");
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

        argsString = " p/   Argument value ";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);
        assertPreambleEmpty(argMultimap);
        assertArgumentPresent(argMultimap, pSlash, "Argument value");
    }

    @Test
    public void tokenize_multipleArguments() throws ParseException {
        String argsString = "SomePreambleString -t dashT-Value p/pSlash value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentAbsent(argMultimap, hatQ);

        argsString = "Different Preamble String ^Q111 -t dashT-Value p/pSlash value";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "Different Preamble String");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentPresent(argMultimap, hatQ, "111");

        argsString = "";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);

        argsString = unknownPrefix + "some value";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertArgumentAbsent(argMultimap, unknownPrefix);
        assertPreamblePresent(argMultimap, argsString);
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() throws ParseException {
        String argsString = "SomePreambleString -t dashT-Value ^Q ^Q -t another dashT value p/ pSlash value -t";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argMultimap, hatQ, "", "");
    }

    @Test
    public void tokenize_multipleArgumentsJoined() throws ParseException {
        String argsString = "SomePreambleString pSlash joined-tjoined -t not joined^Qjoined";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString pSlash joined-tjoined");
        assertArgumentAbsent(argMultimap, pSlash);
        assertArgumentPresent(argMultimap, dashT, "not joined^Qjoined");
        assertArgumentAbsent(argMultimap, hatQ);
    }

    @Test
    public void equalsMethod() {
        Prefix aaa = new Prefix("aaa");
        assertEquals(aaa, aaa);
        assertEquals(aaa, new Prefix("aaa"));
        assertNotEquals(aaa, "aaa");
        assertNotEquals(aaa, new Prefix("aab"));
    }

    @Test
    public void tokenize_argumentWithSlash_throwsParseException() {
        checkSlashException("p/ value/with/slash", pSlash);
        checkSlashException("SomePreambleString -t dashT-Value p/value/with/slash", pSlash, dashT, hatQ);
        checkSlashException("p/ /starts/with/slash", pSlash);
        checkSlashException("p/ ends/with/slash/", pSlash);
        checkSlashException("p/ multiple/slashes/in/value", pSlash);
    }

    @Test
    public void tokenize_argumentWithSlashInPreamble_throwsParseException() {
        checkSlashException("preamble/with/slash p/ valid value", pSlash);
    }

    @Test
    public void tokenize_argumentWithSlashInMultipleArguments_throwsParseException() {
        checkSlashException("p/ value/with/slash -t validValue", pSlash, dashT);
        checkSlashException("p/ validValue -t value/with/slash", pSlash, dashT);
    }

    @Test
    public void tokenize_argumentWithSlashInRepeatedArguments_throwsParseException() {
        checkSlashException("p/ validValue p/ value/with/slash", pSlash);
        checkSlashException("p/ value/with/slash p/ validValue", pSlash);
    }

    @Test
    public void tokenize_argumentWithSlashInEmptyValue_throwsParseException() {
        checkSlashException("p/ /", pSlash);
        checkSlashException("p/ /", pSlash);
    }

    @Test
    public void tokenize_argumentWithSlashInEventPrefixes_throwsParseException() {
        Prefix eventAlias = new Prefix("ea/");
        Prefix eventName = new Prefix("en/");
        Prefix description = new Prefix("d/");

        checkSlashException("ea/ alias/with/slash", eventAlias);
        checkSlashException("en/ event/name/with/slash", eventName);
        checkSlashException("d/ description/with/slash", description);
        checkSlashException("ea/ validAlias en/ event/name/with/slash d/ validDescription",
                eventAlias, eventName, description);
    }

    // Helper method for cleaner exception assertions
    private void checkSlashException(String argsString, Prefix... prefixes) {
        ParseException thrown = assertThrows(ParseException.class, () ->
                ArgumentTokenizer.tokenize(argsString, prefixes)
        );
        // Message now includes the specific prefix when applicable; ensure the standard suffix remains
        assertTrue(thrown.getMessage().endsWith("cannot contain forward slashes(/)"));
    }
}
