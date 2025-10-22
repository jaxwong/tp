package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlinks one or more person from their linked event. "
            + "Parameters: INDEX [MORE INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_SUCCESS = "Unlinked %1$d persons(s) from their events";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate index found! Please try again";

    private final List<Index> indexes;

    /**
     * Creates a UnlinkEventCommand to unlink the person from his/her linked event
     */
    public UnlinkEventCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexes = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Index> duplicateIndexes = new ArrayList<>();

        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            if (duplicateIndexes.contains(index)) {
                throw new CommandException(MESSAGE_DUPLICATE_INDEX);
            } else {
                duplicateIndexes.add(index);
            }
        }

        List<Person> personsToUnlink = new ArrayList<>();
        for (Index index : indexes) {
            personsToUnlink.add(lastShownList.get(index.getZeroBased()));
        }

        for (Person personToUnlink : personsToUnlink) {
            Person unlinkedPerson = new Person(
                    personToUnlink.getName(),
                    personToUnlink.getPhone(),
                    personToUnlink.getEmail(),
                    personToUnlink.getAddress(),
                    personToUnlink.getTags(),
                    null
            );
            model.setPerson(personToUnlink, unlinkedPerson);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, indexes.size()));
    }

}
