package seedu.address.model.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TodoNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TodoName(null));
    }

    @Test
    public void constructor_invalidTodoName_throwsIllegalArgumentException() {
        String invalidTodoName = "";
        assertThrows(IllegalArgumentException.class, () -> new TodoName(invalidTodoName));
    }

    @Test
    public void isValidTodoName() {
        // null todo name
        assertFalse(TodoName.isValidTodoName(null));

        // invalid todo name
        assertFalse(TodoName.isValidTodoName("")); // empty string
        assertFalse(TodoName.isValidTodoName(" ")); // spaces only
        assertFalse(TodoName.isValidTodoName("^")); // only non-alphanumeric characters
        assertFalse(TodoName.isValidTodoName("todo*")); // contains non-alphanumeric characters
        assertFalse(TodoName.isValidTodoName("todo@")); // contains @ symbol
        assertFalse(TodoName.isValidTodoName("todo#")); // contains # symbol
        assertFalse(TodoName.isValidTodoName("todo$")); // contains $ symbol
        assertFalse(TodoName.isValidTodoName("todo%")); // contains % symbol
        assertFalse(TodoName.isValidTodoName("todo!")); // contains ! symbol
        assertFalse(TodoName.isValidTodoName("todo?")); // contains ? symbol
        assertFalse(TodoName.isValidTodoName("todo.")); // contains . symbol
        assertFalse(TodoName.isValidTodoName("todo:")); // contains : symbol
        assertFalse(TodoName.isValidTodoName("todo;")); // contains ; symbol
        assertFalse(TodoName.isValidTodoName("todo(")); // contains ( symbol
        assertFalse(TodoName.isValidTodoName("todo)")); // contains ) symbol
        assertFalse(TodoName.isValidTodoName("todo[")); // contains [ symbol
        assertFalse(TodoName.isValidTodoName("todo]")); // contains ] symbol
        assertFalse(TodoName.isValidTodoName("todo{")); // contains { symbol
        assertFalse(TodoName.isValidTodoName("todo}")); // contains } symbol
        assertFalse(TodoName.isValidTodoName("todo|")); // contains | symbol
        assertFalse(TodoName.isValidTodoName("todo\\")); // contains \ symbol
        assertFalse(TodoName.isValidTodoName("todo/")); // contains / symbol
        assertFalse(TodoName.isValidTodoName("todo+")); // contains + symbol
        assertFalse(TodoName.isValidTodoName("todo=")); // contains = symbol
        assertFalse(TodoName.isValidTodoName("todo<")); // contains < symbol
        assertFalse(TodoName.isValidTodoName("todo>")); // contains > symbol
        assertFalse(TodoName.isValidTodoName("todo~")); // contains ~ symbol
        assertFalse(TodoName.isValidTodoName("todo`")); // contains ` symbol
        assertFalse(TodoName.isValidTodoName("todo\"")); // contains " symbol
        assertTrue(TodoName.isValidTodoName("todo ")); // trailing space (trimmed)
        assertTrue(TodoName.isValidTodoName(" todo")); // leading space (trimmed)
        assertTrue(TodoName.isValidTodoName("todo ")); // trailing space (trimmed)
        assertTrue(TodoName.isValidTodoName("todo  ")); // multiple trailing spaces (trimmed)
        assertTrue(TodoName.isValidTodoName("  todo")); // multiple leading spaces (trimmed)
        assertTrue(TodoName.isValidTodoName("todo  name")); // multiple spaces in middle (allowed by regex)
        assertFalse(TodoName.isValidTodoName("a".repeat(51))); // too long (51 characters)

        // valid todo name
        assertTrue(TodoName.isValidTodoName("Review Project")); // alphabets and spaces
        assertTrue(TodoName.isValidTodoName("12345")); // numbers only
        assertTrue(TodoName.isValidTodoName("Review Project 2")); // alphanumeric characters
        assertTrue(TodoName.isValidTodoName("Capital Todo")); // with capital letters
        assertTrue(TodoName.isValidTodoName("Review Project Proposal Document Q1")); // long names
        assertTrue(TodoName.isValidTodoName("Review Project's Proposal")); // with apostrophe
        assertTrue(TodoName.isValidTodoName("Review Project-Proposal")); // with hyphen
        assertTrue(TodoName.isValidTodoName("Review Project & Proposal")); // with ampersand
        assertTrue(TodoName.isValidTodoName("Review Project, Proposal")); // with comma
        assertTrue(TodoName.isValidTodoName("Review Project's & Proposal-Document, Q1")); // all allowed characters
        assertTrue(TodoName.isValidTodoName("a".repeat(50))); // exactly 50 characters
        assertTrue(TodoName.isValidTodoName("a")); // single character
    }

    @Test
    public void equals() {
        TodoName todoName = new TodoName("Valid Todo Name");

        // same values -> returns true
        assertTrue(todoName.equals(new TodoName("Valid Todo Name")));

        // same object -> returns true
        assertTrue(todoName.equals(todoName));

        // null -> returns false
        assertFalse(todoName.equals(null));

        // different types -> returns false
        assertFalse(todoName.equals(5.0f));

        // different values -> returns false
        assertFalse(todoName.equals(new TodoName("Other Valid Todo Name")));

        // case insensitive comparison
        assertTrue(todoName.equals(new TodoName("valid todo name")));
        assertTrue(todoName.equals(new TodoName("VALID TODO NAME")));
        assertTrue(todoName.equals(new TodoName("Valid TODO Name")));
    }

    @Test
    public void toString_returnsCorrectString() {
        TodoName todoName = new TodoName("Test Todo");
        assertEquals("Test Todo", todoName.toString());
    }

    @Test
    public void constructor_trimsWhitespace() {
        TodoName todoName = new TodoName("  Test Todo  ");
        assertEquals("Test Todo", todoName.toString());
    }
}
