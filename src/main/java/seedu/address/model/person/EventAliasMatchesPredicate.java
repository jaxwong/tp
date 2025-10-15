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
    public String toString() {
        return new ToStringBuilder(this).add("event alias", alias).toString();
    }

}
