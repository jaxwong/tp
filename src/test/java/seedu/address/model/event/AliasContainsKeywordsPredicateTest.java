package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class AliasContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AliasContainsKeywordsPredicate firstPredicate = new AliasContainsKeywordsPredicate(firstPredicateKeywordList);
        AliasContainsKeywordsPredicate secondPredicate = new AliasContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AliasContainsKeywordsPredicate firstPredicateCopy =
                new AliasContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_aliasContainsKeywords_returnsTrue() {
        // One keyword
        AliasContainsKeywordsPredicate predicate =
                new AliasContainsKeywordsPredicate(Collections.singletonList("TSC2025"));
        assertTrue(predicate.test(new EventBuilder().withAlias("TSC2025").build()));

        // Multiple keywords
        predicate = new AliasContainsKeywordsPredicate(Arrays.asList("TSC2025", "CONCERT"));
        assertTrue(predicate.test(new EventBuilder().withAlias("TSC2025").build()));

        // Mixed-case keyword
        predicate = new AliasContainsKeywordsPredicate(Collections.singletonList("tSc2025"));
        assertTrue(predicate.test(new EventBuilder().withAlias("TSC2025").build()));

        // Prefix keyword
        predicate = new AliasContainsKeywordsPredicate(Collections.singletonList("TSC"));
        assertTrue(predicate.test(new EventBuilder().withAlias("TSC2025").build()));
    }

    @Test
    public void test_aliasDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AliasContainsKeywordsPredicate predicate = new AliasContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withAlias("TSC2025").build()));

        // Non-matching keyword
        predicate = new AliasContainsKeywordsPredicate(Arrays.asList("CONCERT"));
        assertFalse(predicate.test(new EventBuilder().withAlias("TSC2025").build()));

        // Keywords match name, start time, end time and description, but does not match alias
        predicate = new AliasContainsKeywordsPredicate(
                Arrays.asList("Taylor Swift Concert", "2025-09-19 19:30", "2025-09-19 23:30", "Eras Tour"));
        assertFalse(predicate.test(new EventBuilder()
                .withName("Taylor Swift Concert")
                .withStart(LocalDateTime.of(2025, 9, 19, 19, 30))
                .withEnd(LocalDateTime.of(2025, 9, 19, 23, 30))
                .withDescription("Eras Tour")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AliasContainsKeywordsPredicate predicate = new AliasContainsKeywordsPredicate(keywords);

        String expected = AliasContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
