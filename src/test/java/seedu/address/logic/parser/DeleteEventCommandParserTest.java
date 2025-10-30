package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.model.event.EventAlias;

public class DeleteEventCommandParserTest {

    private static final String VALID_EVENT_ALIAS = "TSC2025";
    private static final String INVALID_EVENT_ALIAS = "TSC@2025"; // '@' not allowed

    private static final String EVENT_ALIAS_DESC = " " + PREFIX_EVENT_ALIAS + VALID_EVENT_ALIAS;
    private static final String INVALID_EVENT_ALIAS_DESC = " " + PREFIX_EVENT_ALIAS + INVALID_EVENT_ALIAS;

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_missingEventAlias_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

        // missing event alias prefix
        assertParseFailure(parser, "", expectedMessage);

        // empty arguments
        assertParseFailure(parser, "   ", expectedMessage);
    }

    @Test
    public void parse_invalidEventAlias_failure() {
        assertParseFailure(parser, INVALID_EVENT_ALIAS_DESC, EventAlias.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validEventAlias_success() {
        DeleteEventCommand expectedCommand = new DeleteEventCommand(new EventAlias(VALID_EVENT_ALIAS));
        assertParseSuccess(parser, EVENT_ALIAS_DESC, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyPreamble_success() {
        DeleteEventCommand expectedCommand = new DeleteEventCommand(new EventAlias(VALID_EVENT_ALIAS));
        // whitespace only preamble should be accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_ALIAS_DESC, expectedCommand);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

        // non-empty preamble should be rejected
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_ALIAS_DESC, expectedMessage);

        // gibberish preamble should be rejected
        assertParseFailure(parser, "wbeciwbeichowevowowovwe" + EVENT_ALIAS_DESC, expectedMessage);

        // single word preamble should be rejected
        assertParseFailure(parser, "gibberish" + EVENT_ALIAS_DESC, expectedMessage);

        // multiple words preamble should be rejected
        assertParseFailure(parser, "some random text here" + EVENT_ALIAS_DESC, expectedMessage);
    }

    @Test
    public void parse_duplicateEventAlias_failure() {
        // DeleteEventCommandParser validates for duplicate prefixes and should reject them
        String duplicateAliasDesc = " " + PREFIX_EVENT_ALIAS + "OTHER2025";
        String expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_ALIAS);
        assertParseFailure(parser, EVENT_ALIAS_DESC + duplicateAliasDesc, expectedMessage);
    }
}
