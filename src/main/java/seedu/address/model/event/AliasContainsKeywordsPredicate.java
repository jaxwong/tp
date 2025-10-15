package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Events}'s {@code EventAlias} matches any of the keywords given.
 */
public class AliasContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public AliasContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.startsWithIgnoreCase(event.getAlias(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AliasContainsKeywordsPredicate)) {
            return false;
        }

        AliasContainsKeywordsPredicate otherAliasContainsKeywordsPredicate = (AliasContainsKeywordsPredicate) other;
        return keywords.equals(otherAliasContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
