package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.LinkEventCommand;
import seedu.address.model.event.EventAlias;

/**
 * Contains unit tests for LinkEventCommandParser
 */
public class LinkEventCommandParserTest {

    private LinkEventCommandParser parser = new LinkEventCommandParser();

    @Test
    public void parse_validSingleIndex_success() {
        EventAlias expectedAlias = new EventAlias("TSC2025");
        LinkEventCommand expectedCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON),
                expectedAlias);

        assertParseSuccess(parser, "1 " + PREFIX_EVENT_ALIAS + "TSC2025", expectedCommand);
    }

    @Test
    public void parse_validMultipleIndexes_success() {
        EventAlias expectedAlias = new EventAlias("TSC2025");
        LinkEventCommand expectedCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                expectedAlias);

        assertParseSuccess(parser, "1 2 3 " + PREFIX_EVENT_ALIAS + "TSC2025", expectedCommand);
    }

    @Test
    public void parse_validMultipleIndexesWithExtraSpaces_success() {
        EventAlias expectedAlias = new EventAlias("TSC2025");
        LinkEventCommand expectedCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                expectedAlias);

        assertParseSuccess(parser, "  1   2   3   " + PREFIX_EVENT_ALIAS + "TSC2025", expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        String expectedMesage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkEventCommand.MESSAGE_USAGE);

        // if user inputs command without index but with alias only
        assertParseFailure(parser, PREFIX_EVENT_ALIAS + "TSC2025", expectedMesage);
    }

    @Test
    public void parse_missingEventAlias_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkEventCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "1 ", expectedMessage);
    }

    @Test
    public void parse_emptyEventAlias_failure() {
        String expectedMessage = EventAlias.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, "1 " + PREFIX_EVENT_ALIAS, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = ParserUtil.MESSAGE_INVALID_INDEX;

        assertParseFailure(parser, "a " + PREFIX_EVENT_ALIAS + "TSC2025", expectedMessage);
        assertParseFailure(parser, "0 " + PREFIX_EVENT_ALIAS + "TSC2025", expectedMessage);
        assertParseFailure(parser, "-1 " + PREFIX_EVENT_ALIAS + "TSC2025", expectedMessage);
        assertParseFailure(parser, "1 a 3 " + PREFIX_EVENT_ALIAS + "TSC2025", expectedMessage);
    }

    @Test
    public void parse_emptyArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkEventCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, "       ", expectedMessage);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        String expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_ALIAS);

        assertParseFailure(parser, "1 "
                + PREFIX_EVENT_ALIAS + "TSC2025 "
                + PREFIX_EVENT_ALIAS + "TSC2025", expectedMessage);
    }

}
