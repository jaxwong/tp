package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.model.Model;
import seedu.address.ui.DisplayList;

/**
 * Lists all events in the address book to the user.
 * This command displays all events in the UI using EventCard components.
 *
 */
public class ListEventsCommand extends Command {

    /** The command word used to invoke this command. */
    public static final String COMMAND_WORD = "list-events";

    /** Success message displayed when events are listed. */
    public static final String MESSAGE_SUCCESS = "Listed all events";

    /**
     * Executes the list-events command.
     *
     * @param model The model containing the address book data and event management functionality.
     *              Must not be null.
     * @return CommandResult containing success message.
     * @throws NullPointerException if model is null.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Update the filtered event list to show all events
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS, DisplayList.EVENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListEventsCommand)) {
            return false;
        }

        return true; // All ListEventsCommand instances are equal since they have no state
    }
}
