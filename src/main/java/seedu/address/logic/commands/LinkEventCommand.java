package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.Person;

/**
 * Links a Person Contact to an Event
 */
public class LinkEventCommand extends Command {
    public static final String COMMAND_WORD = "link-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links one or more persons to an event by its alias. "
            + "Parameters: INDEX " + PREFIX_EVENT_ALIAS + "EVENT_ALIAS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_ALIAS + "TSC2025";

    public static final String MESSAGE_SUCCESS = "Linked %1$d person(s) to event: %2$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found.";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate index found! Please try again";

    private final List<Index> indexes;
    private final EventAlias eventAlias;

    /**
     * Creates a LinkEventCommand to link a person to an event
     */
    public LinkEventCommand(List<Index> indexes, EventAlias eventAlias) {
        requireNonNull(indexes);
        requireNonNull(eventAlias);
        this.indexes = indexes;
        this.eventAlias = eventAlias;
        assert !indexes.isEmpty() : "Indexes cannot be empty!";
        assert eventAlias != null : "EventAlias cannot be null!";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Index> duplicateIndexes = new ArrayList<>();

        for (Index index : indexes) {
            assert index != null : "Index cannot be null!";
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            if (duplicateIndexes.contains(index)) {
                throw new CommandException(MESSAGE_DUPLICATE_INDEX);
            } else {
                duplicateIndexes.add(index);
            }
        }

        // Find the event by alias (this is case insensitive)
        Event event = model.getAddressBook().getEventList().stream()
                .filter(e -> e.getAlias().equalsIgnoreCase(eventAlias.toString()))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_EVENT_NOT_FOUND));

        List<Person> personsToLink = new ArrayList<>();
        for (Index index : indexes) {
            Person personToLink = lastShownList.get(index.getZeroBased());
            assert personToLink != null : "Person to link cannot be null!";
            personsToLink.add(personToLink);
        }

        for (Person personToEdit : personsToLink) {
            Person linkedPerson = new Person(
                    personToEdit.getName(),
                    personToEdit.getPhone(),
                    personToEdit.getEmail(),
                    personToEdit.getAddress(),
                    personToEdit.getTags(),
                    event.getEventAlias()
            );
            model.setPerson(personToEdit, linkedPerson);
        }


        return new CommandResult(String.format(MESSAGE_SUCCESS, indexes.size(), event.getEventAlias()));
    }
}
