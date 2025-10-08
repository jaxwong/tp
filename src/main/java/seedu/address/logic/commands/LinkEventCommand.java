package seedu.address.logic.commands;


import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Person;
import seedu.address.logic.Messages;

/**
 * Links a Person Contact to an Event
 */
public class LinkEventCommand extends Command {
    public static final String COMMAND_WORD = "link-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a person to an event. "
            + "Parameters: INDEX en/EVENT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_NAME + "Taylor Swift concert";

    public static final String MESSAGE_SUCCESS = "Linked %1$s to event: %2$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found.";

    private final Index index;
    private final EventName eventName;

    /**
     * Creates a LinkEventCommand to link a person to an event
     */
    public LinkEventCommand(Index index, EventName eventName) {
        requireNonNull(index);
        requireNonNull(eventName);
        this.index = index;
        this.eventName = eventName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Event event = model.getAddressBook().getEventList().stream()
                .filter(e -> e.getName().equalsIgnoreCase(eventName.toString()))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_EVENT_NOT_FOUND));

        Person linkedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                event
        );

        model.setPerson(personToEdit, linkedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(), event.getName()));
    }
}
