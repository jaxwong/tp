package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.AliasContainsKeywordsPredicate;
import seedu.address.ui.DisplayList;

/**
 * Finds and lists all events in address book whose alias contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "find-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose aliases have any of "
            + "the specified keywords (case-insensitive) as a prefix and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_EVENT_ALIAS + " KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ALIAS + "tsc";

    private final AliasContainsKeywordsPredicate predicate;

    public FindEventCommand(AliasContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()),
                DisplayList.EVENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventCommand)) {
            return false;
        }

        FindEventCommand otherFindEventCommand = (FindEventCommand) other;
        return predicate.equals(otherFindEventCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
