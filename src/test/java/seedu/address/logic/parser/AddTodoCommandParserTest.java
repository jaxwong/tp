package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTodoCommand;
import seedu.address.model.person.Name;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoName;

public class AddTodoCommandParserTest {

    private final AddTodoCommandParser parser = new AddTodoCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Todo expectedWithContact = new Todo(new TodoName("Review Project Proposal"),
                "Review the Q1 project proposal document", new Name("John Doe"));
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE
                        + " tn/Review Project Proposal"
                        + " td/Review the Q1 project proposal document"
                        + " n/John Doe",
                new AddTodoCommand(expectedWithContact));

        Todo expectedNoContact = new Todo(new TodoName("Update Documentation"),
                "Update API documentation for new features", null);
        assertParseSuccess(parser,
                " tn/Update Documentation"
                        + " td/Update API documentation for new features",
                new AddTodoCommand(expectedNoContact));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        assertParseFailure(parser,
                " tn/Review Project Proposal"
                        + " tn/Update Documentation"
                        + " td/Review the Q1 project proposal document",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_NAME));

        assertParseFailure(parser,
                " tn/Review Project Proposal"
                        + " td/Review the Q1 project proposal document"
                        + " td/Update API documentation for new features",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_DESCRIPTION));

        assertParseFailure(parser,
                " tn/Review Project Proposal"
                        + " td/Review the Q1 project proposal document"
                        + " n/John Doe"
                        + " n/John Doe",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser,
                " tn/Review Project Proposal"
                        + " td/Review the Q1 project proposal document"
                        + " n/John Doe"
                        + " tn/Update Documentation"
                        + " td/Update API documentation for new features"
                        + " n/Jane Roe",
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_TODO_NAME, PREFIX_TODO_DESCRIPTION, PREFIX_NAME));

    }

    @Test
    public void parse_optionalFieldMissing_success() {
        Todo expected = new Todo(new TodoName("Backup Database"),
                "Create weekly database backup", null);
        assertParseSuccess(parser,
                " tn/Backup Database"
                        + " td/Create weekly database backup",
                new AddTodoCommand(expected));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTodoCommand.MESSAGE_USAGE);

        assertParseFailure(parser,
                "Backup Database"
                        + " td/Create weekly database backup",
                expectedMessage);

        assertParseFailure(parser,
                " tn/Backup Database"
                        + " Create weekly database backup",
                expectedMessage);

        assertParseFailure(parser,
                "Backup Database Create weekly database backup",
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,
                " tn/@"
                        + " td/Review the Q1 project proposal document",
                TodoName.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                " tn/Review Project Proposal"
                        + " td/Review the Q1 project proposal document"
                        + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY
                        + " tn/Review Project Proposal"
                        + " td/Review the Q1 project proposal document",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTodoCommand.MESSAGE_USAGE));
    }
}
