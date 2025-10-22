package seedu.address.model.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TodoBuilder;

public class TodoTest {

    @Test
    public void getContactNameDisplay_noContact_returnsNoContactLinked() {
        Todo todo = new TodoBuilder()
                .withTodoName("Call TSC2025")
                .withDescription("Call Taylor Swift's manager")
                .withoutContactName()
                .build();

        assertEquals("No contact linked", todo.getContactNameDisplay());
    }

    @Test
    public void getContactNameDisplay_withContact_returnsName() {
        Todo todo = new TodoBuilder()
                .withTodoName("Call TSC2025")
                .withDescription("Call Taylor Swift's manager")
                .withContactName("Alex Yeoh")
                .build();

        assertEquals("Alex Yeoh", todo.getContactNameDisplay());
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        Todo a = new TodoBuilder()
                .withTodoName("Call TSC2025")
                .withDescription("Call manager")
                .withContactName("Alex Yeoh")
                .build();
        Todo b = new TodoBuilder(a).build();
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals() {
        Todo base = new TodoBuilder()
                .withTodoName("Call TSC2025")
                .withDescription("Call manager")
                .withContactName("Alex Yeoh")
                .build();

        // same values -> true
        Todo sameAsBase = new TodoBuilder(base).build();
        assertTrue(base.equals(sameAsBase));

        // same object -> true
        assertTrue(base.equals(base));

        // null -> false
        assertFalse(base.equals(null));

        // different type -> false
        assertFalse(base.equals(5));

        // different name -> false
        Todo diffName = new TodoBuilder(base).withTodoName("Draft email").build();
        assertFalse(base.equals(diffName));

        // different description -> false
        Todo diffDesc = new TodoBuilder(base).withDescription("Different description").build();
        assertFalse(base.equals(diffDesc));

        // different contact -> false (null vs non-null)
        Todo diffContact = new TodoBuilder(base).withoutContactName().build();
        assertFalse(base.equals(diffContact));

        // different isCompleted -> false
        Todo completedVariant = new Todo(
                new TodoName(base.getTodoName().toString()),
                base.getDescription(),
                base.getContactName(),
                true);
        assertFalse(base.equals(completedVariant));
    }

    @Test
    public void toStringMethod_withContact() {
        Todo todo = new TodoBuilder()
                .withTodoName("Call TSC2025")
                .withDescription("Call manager")
                .withContactName("Alex Yeoh")
                .build();

        String expected = "Todo{"
                + "todoName='" + todo.getTodoName() + '\''
                + ", description='" + todo.getDescription() + '\''
                + ", contactName='" + todo.getContactName() + '\''
                + ", isCompleted=" + todo.getIsCompleted()
                + '}';

        assertEquals(expected, todo.toString());
    }

    @Test
    public void toStringMethod_withoutContact() {
        Todo todo = new TodoBuilder()
                .withTodoName("Draft email")
                .withDescription("Write and send the draft")
                .withoutContactName()
                .build();

        String expected = "Todo{"
                + "todoName='" + todo.getTodoName() + '\''
                + ", description='" + todo.getDescription() + '\''
                + ", contactName='" + todo.getContactName() + '\''
                + ", isCompleted=" + todo.getIsCompleted()
                + '}';

        assertEquals(expected, todo.toString());
    }
}
