package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords and at preamble
        assertParseSuccess(parser, "    " + PREFIX_NAME + " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_oneArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new NameContainsKeywordsPredicate(List.of("Alice")));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice", expectedFindCommand);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

        // empty input
        assertParseFailure(parser, "     ", expectedMessage);

        // non-empty preamble
        assertParseFailure(parser, " random preamble " + PREFIX_NAME + "Alice", expectedMessage);

        // missing prefix
        assertParseFailure(parser, " Alice", expectedMessage);

        // missing name but with prefix
        assertParseFailure(parser, " " + PREFIX_NAME + "   ", expectedMessage);
    }
}
