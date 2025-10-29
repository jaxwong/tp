package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.EventAlias;

/**
 * Tests that a {@code Person}'s {@code linkedEventAlias} matches the given EventAlias.
 */
public class EventAliasMatchesPredicate implements Predicate<Person> {
    private final EventAlias alias;

    public EventAliasMatchesPredicate(EventAlias alias) {
        this.alias = alias;
    }

    @Override
    public boolean test(Person person) {
        if (person.getEventAlias() == null) {
            return false;
        }
        return person.getEventAlias().equals(alias);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventAliasMatchesPredicate)) {
            return false;
        }

        EventAliasMatchesPredicate otherEventAliasMatchesPredicate = (EventAliasMatchesPredicate) other;
        return alias.equals(otherEventAliasMatchesPredicate.alias);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("event alias", alias).toString();
    }

}
