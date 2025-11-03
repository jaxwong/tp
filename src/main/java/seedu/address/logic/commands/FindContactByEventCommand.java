package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.EventAliasMatchesPredicate;
import seedu.address.ui.DisplayList;

/**
 * Finds and lists all persons in address book whose linkedEventAlias matches the given EventAlias.
 */
public class FindContactByEventCommand extends Command {
    public static final String COMMAND_WORD = "find-by-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose linked EventAlias "
            + "matches the given EventAlias and displays them as a list with index numbers.\n"
            + "Parameters: ea/EVENT_ALIAS\n"
            + "Example: " + COMMAND_WORD + " ea/TaylorSwift";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found.";

    private final EventAliasMatchesPredicate predicate;
    private final EventAlias eventAlias;

    /**
     * Creates a FindContactByEventCommand to find all the relevant persons
     */
    public FindContactByEventCommand(EventAliasMatchesPredicate predicate, EventAlias eventAlias) {
        requireAllNonNull(predicate, eventAlias);
        this.predicate = predicate;
        this.eventAlias = eventAlias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert predicate != null : "Predicate must have been validated";
        // Find the event by alias (this is case insensitive)
        Event event = model.getEventList().stream()
                .filter(e -> e.getAlias().equalsIgnoreCase(eventAlias.toString()))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_EVENT_NOT_FOUND));
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                DisplayList.PERSON);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindContactByEventCommand)) {
            return false;
        }
        FindContactByEventCommand otherCommand = (FindContactByEventCommand) other;
        return predicate.equals(otherCommand.predicate);
    }
}
