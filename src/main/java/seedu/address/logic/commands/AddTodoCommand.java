package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;


/**
 * Adds a todo to the address book.
 */
public class AddTodoCommand extends Command {
    public static final String COMMAND_WORD = "add-todo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a todo to the address book. "
            + "Parameters: "
            + PREFIX_TODO_NAME + "TODO NAME "
            + PREFIX_TODO_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_NAME + "NAME (of contact)] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TODO_NAME + "Call for TSC2025 "
            + PREFIX_TODO_DESCRIPTION + "Call Taylor Swift's manager "
            + PREFIX_NAME + "Alex Yeoh ";
    public static final String MESSAGE_SUCCESS = "New Todo added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This todo already exists in the address book";

    private final Todo toAdd;

    /**
     * Creates an AddTodoCommand to add the specified {@code Todo}
     */
    public AddTodoCommand(Todo toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTodo(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }

        final Name contactName = toAdd.getContactName();
        if (contactName != null && !hasContactName(model, contactName)) {
            throw new CommandException("Contact not found: " + contactName.fullName);
        }

        model.addTodo(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    private boolean hasContactName(Model model, Name name) {
        return model.getAddressBook().getPersonList().stream()
                .map(Person::getName)
                .anyMatch(name::equals);
    }
}
