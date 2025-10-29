package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTodoCommand object
 */

public class EditTodoCommandParser implements Parser<EditTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTodoCommand
     * and returns an EditEventCommand object for execution
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_TODO_NAME,
                        PREFIX_TODO_DESCRIPTION,
                        PREFIX_NAME
                );

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTodoCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME, PREFIX_TODO_DESCRIPTION, PREFIX_NAME);

        EditTodoCommand.EditTodoDescriptor editTodoDescriptor = new EditTodoCommand.EditTodoDescriptor();

        if (argMultimap.getValue(PREFIX_TODO_NAME).isPresent()) {
            editTodoDescriptor.setTodoName(ParserUtil.parseTodoName(argMultimap.getValue(PREFIX_TODO_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_TODO_DESCRIPTION).isPresent()) {
            editTodoDescriptor.setTodoDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_TODO_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameInput = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (nameInput.isEmpty()) {
                editTodoDescriptor.setContactName(null);
                editTodoDescriptor.markContactUnlinked();
            } else {
                editTodoDescriptor.setContactName(ParserUtil.parseName(nameInput));
            }
        }

        if (!editTodoDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTodoCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTodoCommand(index, editTodoDescriptor);
    }

}
