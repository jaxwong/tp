package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unlinks a Person Contact from his/her linked Event
 */
public class UnlinkEventCommand extends Command {
    public static final String COMMAND_WORD = "unlink-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlnks a person from his/her linked event. "
            + "Parameters: INDEX "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Unlinked %1$s from event %2$s";

    private final Index index;

    /**
     * Creates a UnlinkEventCommand to unlink the person from his/her linked event
     */
    public UnlinkEventCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Person unlinkedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                null
        );

        model.setPerson(personToEdit, unlinkedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(), personToEdit.getEventAlias()));
    }

}
