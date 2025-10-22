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
        assertThrows(IllegalArgumentException.class, () -> new TodoName(""));
        assertThrows(IllegalArgumentException.class, () -> new TodoName(" "));
        assertThrows(IllegalArgumentException.class, () -> new TodoName("@todo"));
        assertThrows(IllegalArgumentException.class, () -> new TodoName("a".repeat(51)));
    }

    @Test
    public void isValidTodoName() {
        assertFalse(TodoName.isValidTodoName(null));
        assertFalse(TodoName.isValidTodoName(""));
        assertFalse(TodoName.isValidTodoName(" "));
        assertFalse(TodoName.isValidTodoName("@todo"));
        assertFalse(TodoName.isValidTodoName("slash/"));
        assertFalse(TodoName.isValidTodoName("colon:"));
        assertFalse(TodoName.isValidTodoName("a".repeat(51)));

        assertTrue(TodoName.isValidTodoName("Call TSC2025"));
        assertTrue(TodoName.isValidTodoName("Email, follow-up"));
        assertTrue(TodoName.isValidTodoName("Lunch - team"));
        assertTrue(TodoName.isValidTodoName("Manager's review & notes"));
        assertTrue(TodoName.isValidTodoName("12345"));
        assertTrue(TodoName.isValidTodoName("a".repeat(50)));
        assertTrue(TodoName.isValidTodoName("  Trim me  "));
    }

    @Test
    public void equalsMethod() {
        TodoName a = new TodoName("Call TSC2025");
        assertTrue(a.equals(new TodoName("Call TSC2025")));
        assertTrue(a.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(5));
        assertFalse(a.equals(new TodoName("Call TSC2026")));
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
