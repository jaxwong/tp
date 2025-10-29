package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.model.event.AliasContainsKeywordsPredicate;

public class FindEventCommandParserTest {
    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_validArgs_returnsFindEventCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedCommand =
                new FindEventCommand(new AliasContainsKeywordsPredicate(Arrays.asList("tsc2025", "concert")));
        assertParseSuccess(parser, " " + PREFIX_EVENT_ALIAS + " tsc2025 concert", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_EVENT_ALIAS + " \n tsc2025 \n \t concert  \t", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "     ", expectedMessage);
        assertParseFailure(parser, " gibberishpreamble " + PREFIX_EVENT_ALIAS + " concert", expectedMessage);
        assertParseFailure(parser, " Alice Pauline", expectedMessage);
        assertParseFailure(parser, " " + PREFIX_EVENT_ALIAS + " ", expectedMessage);
    }
}
