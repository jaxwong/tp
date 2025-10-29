package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventAliasTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAlias(null));
    }

    @Test
    public void constructor_invalidAlias_throwsIllegalArgumentException() {
        String invalidAlias = "    ";
        assertThrows(IllegalArgumentException.class, () -> new EventAlias(invalidAlias));
    }

    @Test
    public void isValidAlias() {
        // invalid alias
        assertFalse(EventAlias.isValidAlias("")); // empty string
        assertFalse(EventAlias.isValidAlias(" ")); // spaces only
        assertFalse(EventAlias.isValidAlias("Taylor Swift")); // alias with space
        assertFalse(EventAlias.isValidAlias("Taylor*Swift")); // alias with disallowed symbol
        assertFalse(EventAlias.isValidAlias("a".repeat(21))); // exceeds character limit

        // valid alias
        assertTrue(EventAlias.isValidAlias("peter-jack")); // alphabets only
        assertTrue(EventAlias.isValidAlias("12345")); // numbers only
        assertTrue(EventAlias.isValidAlias("peter-the_2nd")); // alphanumeric characters
        assertTrue(EventAlias.isValidAlias("a".repeat(20))); // just within character limit

    }

    @Test
    public void equals() {
        EventAlias alias = new EventAlias("Valid-Alias");

        // same values -> returns true
        assertTrue(alias.equals(new EventAlias("Valid-Alias")));

        // same object -> returns true
        assertTrue(alias.equals(alias));

        // null -> returns false
        assertFalse(alias.equals(null));

        // different types -> returns false
        assertFalse(alias.equals(5.0f));

        // different values -> returns false
        assertFalse(alias.equals(new EventAlias("Other-Valid-Alias")));
    }

}
