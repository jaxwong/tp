package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnlinkEventCommand;

public class UnlinkEventCommandParserTest {

    private UnlinkEventCommandParser parser = new UnlinkEventCommandParser();

    @Test
    public void parse_validSingleIndex_success() {
        UnlinkEventCommand expectedCommand = new UnlinkEventCommand(Arrays.asList(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, "1", expectedCommand);
    }

    @Test
    public void parse_validMultipleIndexes_success() {
        UnlinkEventCommand expectedCommand = new UnlinkEventCommand(Arrays.asList(
                INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON
        ));

        assertParseSuccess(parser, "1 2 3", expectedCommand);
    }

    @Test
    public void parse_validMultipleIndexesWithExtraSpaces_success() {
        UnlinkEventCommand expectedCommand = new UnlinkEventCommand(Arrays.asList(
                INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON
        ));

        assertParseSuccess(parser, "1   2   3   ", expectedCommand);
    }


    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = ParserUtil.MESSAGE_INVALID_INDEX;

        assertParseFailure(parser, "a", expectedMessage);
        assertParseFailure(parser, "0", expectedMessage);
        assertParseFailure(parser, "-1", expectedMessage);
        assertParseFailure(parser, "1 a 3", expectedMessage);
    }

    @Test
    public void parse_emptyArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkEventCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " ", expectedMessage);
    }
}
