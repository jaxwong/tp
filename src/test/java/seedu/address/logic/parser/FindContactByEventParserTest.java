package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindContactByEventCommand;
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.EventAliasMatchesPredicate;

public class FindContactByEventParserTest {
    private FindContactByEventParser parser = new FindContactByEventParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindContactByEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindContactByEventCommand() {
        EventAlias alias = new EventAlias("bp2026");
        FindContactByEventCommand expectedCommand =
                new FindContactByEventCommand(new EventAliasMatchesPredicate(alias), alias);
        assertParseSuccess(parser, " ea/bp2026", expectedCommand);
    }
}
