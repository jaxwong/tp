package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;

/**
 * Deletes an event identified using its alias from the address book.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "delete-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by its event alias.\n"
            + "Parameters: " + PREFIX_EVENT_ALIAS + "EVENT_ALIAS\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ALIAS + "TSC2025";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "No event found with alias: %1$s";

    private final EventAlias eventAlias;

    public DeleteEventCommand(EventAlias eventAlias) {
        this.eventAlias = eventAlias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        Event eventToDelete = lastShownList.stream()
                .filter(e -> e.getAlias().equalsIgnoreCase(eventAlias.toString()))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_EVENT_NOT_FOUND, eventAlias)));

        model.deleteEvent(eventToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }
        DeleteEventCommand o = (DeleteEventCommand) other;
        return this.eventAlias.equals(o.eventAlias);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventAlias", eventAlias)
                .toString();
    }

}
