package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTodoCommand;

public class DeleteTodoCommandParserTest {

    private final DeleteTodoCommandParser parser = new DeleteTodoCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTodoCommand() {
        assertParseSuccess(parser, "1", new DeleteTodoCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTodoCommand.MESSAGE_USAGE));
    }
}
