package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoName;

/**
 * Parses input arguments and creates a new AddTodoCommand object
 */
public class AddTodoCommandParser implements Parser<AddTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTodoCommand
     * and returns an AddTodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddTodoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_TODO_NAME, PREFIX_TODO_DESCRIPTION, PREFIX_NAME);

        if (!arePrefixesPresent(argMultiMap, PREFIX_TODO_NAME, PREFIX_TODO_DESCRIPTION)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTodoCommand.MESSAGE_USAGE));
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME, PREFIX_TODO_DESCRIPTION, PREFIX_NAME);

        TodoName todoName = ParserUtil.parseTodoName(argMultiMap.getValue(PREFIX_TODO_NAME).get());
        String todoDescription = ParserUtil.parseDescription(argMultiMap.getValue(PREFIX_TODO_DESCRIPTION).get());
        Name contactName = argMultiMap.getValue(PREFIX_NAME).isPresent()
                ? ParserUtil.parseName(argMultiMap.getValue(PREFIX_NAME).get())
                : null;

        Todo todo = new Todo(todoName, todoDescription, contactName);

        return new AddTodoCommand(todo);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
