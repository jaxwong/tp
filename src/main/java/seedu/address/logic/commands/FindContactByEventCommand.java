package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EventAliasMatchesPredicate;
import seedu.address.ui.DisplayList;

/**
 * Finds and lists all persons in address book whose linkedEventAlias matches the given EventAlias.
 */
public class FindContactByEventCommand extends Command {
    public static final String COMMAND_WORD = "find-by-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose linkedEventAlias"
            + "matches the given EventAlias and displays them as a list with index numbers.\n"
            + "Parameters: EventAlias\n"
            + "Example: " + COMMAND_WORD + " ea/TaylorSwift";

    private final EventAliasMatchesPredicate predicate;

    /**
     * Creates a FindContactByEventCommand to find all the relevant persons
     */
    public FindContactByEventCommand(EventAliasMatchesPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
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
