package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTodoCommand;
import seedu.address.model.person.Name;
import seedu.address.model.todo.TodoName;

public class EditTodoCommandParserTest {
    private final EditTodoCommandParser parser = new EditTodoCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = "1 "
                + PREFIX_TODO_NAME + "Finish concert report for Alice "
                + PREFIX_TODO_DESCRIPTION + "Write up Summary "
                + PREFIX_NAME + "Alice Pauline";

        EditTodoCommand.EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.setTodoName(new TodoName("Finish concert report for Alice"));
        descriptor.setTodoDescription("Write up Summary");
        descriptor.setContactName(new Name("Alice Pauline"));

        EditTodoCommand expectedCommand = new EditTodoCommand(Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_unlinkContact_success() {
        String userInput = "1 " + PREFIX_NAME + " ";

        EditTodoCommand.EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.setContactName(null);
        descriptor.markContactUnlinked();

        EditTodoCommand expectedCommand = new EditTodoCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_unlinkContactAndEditDescription_success() {
        String userInput = "1 " + PREFIX_NAME + " " + PREFIX_TODO_DESCRIPTION + "Updated Descrp";

        EditTodoCommand.EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.markContactUnlinked();
        descriptor.setTodoDescription("Updated Descrp");

        EditTodoCommand expectedCommand = new EditTodoCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        String userInput = "1";
        assertParseFailure(parser, userInput, EditTodoCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String userInput = "a " + PREFIX_TODO_DESCRIPTION + "test descrip";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_trailingWhitespace_success() {
        String userInput = "1 "
                + PREFIX_TODO_NAME + "TaskName   "
                + PREFIX_TODO_DESCRIPTION + "  Finish later  ";
        EditTodoCommand.EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.setTodoName(new TodoName("TaskName"));
        descriptor.setTodoDescription("Finish later");

        EditTodoCommand expectedCommand = new EditTodoCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
