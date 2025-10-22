package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;

public class TodoCardTest extends JavaFxTestBase {

    @Test
    public void constructor_todoWithAllFields_createsCardCorrectly() {
        Todo todo = new TodoBuilder()
                .withTodoName("Review Project Proposal")
                .withDescription("Review the Q1 project proposal document")
                .withContactName("John Doe")
                .withCompleted(false)
                .build();

        TodoCard todoCard = new TodoCard(todo, 1);

        // Check that the card is created
        assertNotNull(todoCard);
        assertNotNull(todoCard.getRoot());

        // Check that the todo is stored correctly
        assertEquals(todo, todoCard.todo);
    }

    @Test
    public void constructor_todoWithNullContact_createsCardCorrectly() {
        Todo todo = new TodoBuilder()
                .withTodoName("Review Project Proposal")
                .withDescription("Review the Q1 project proposal document")
                .withoutContactName()
                .withCompleted(false)
                .build();

        TodoCard todoCard = new TodoCard(todo, 2);

        // Check that the card is created
        assertNotNull(todoCard);
        assertEquals(todo, todoCard.todo);
    }

    @Test
    public void constructor_completedTodo_createsCardCorrectly() {
        Todo todo = new TodoBuilder()
                .withTodoName("Review Project Proposal")
                .withDescription("Review the Q1 project proposal document")
                .withContactName("John Doe")
                .withCompleted(true)
                .build();

        TodoCard todoCard = new TodoCard(todo, 3);

        // Check that the card is created
        assertNotNull(todoCard);
        assertEquals(todo, todoCard.todo);
    }

    @Test
    public void constructor_todoWithLongName_createsCardCorrectly() {
        Todo todo = new TodoBuilder()
                .withTodoName("This is a very long todo name - might exceed")
                .withDescription("Short description")
                .withContactName("John Doe")
                .withCompleted(false)
                .build();

        TodoCard todoCard = new TodoCard(todo, 4);

        // Check that the card is created
        assertNotNull(todoCard);
        assertEquals(todo, todoCard.todo);
    }

    @Test
    public void constructor_todoWithSpecialCharacters_createsCardCorrectly() {
        Todo todo = new TodoBuilder()
                .withTodoName("Review Project's & Proposal-Document, Q1")
                .withDescription("Review document with special characters")
                .withContactName("John Connor99")
                .withCompleted(false)
                .build();

        TodoCard todoCard = new TodoCard(todo, 5);

        // Check that the card is created
        assertNotNull(todoCard);
        assertEquals(todo, todoCard.todo);
    }

    @Test
    public void constructor_todoWithEmptyDescription_createsCardCorrectly() {
        Todo todo = new TodoBuilder()
                .withTodoName("Simple Todo")
                .withDescription("")
                .withContactName("John Doe")
                .withCompleted(false)
                .build();

        TodoCard todoCard = new TodoCard(todo, 6);

        // Check that the card is created
        assertNotNull(todoCard);
        assertEquals(todo, todoCard.todo);
    }

    @Test
    public void constructor_withDifferentIndex_createsCardCorrectly() {
        Todo todo = new TodoBuilder().withTodoName("Test Todo").build();

        TodoCard todoCard1 = new TodoCard(todo, 1);
        TodoCard todoCard2 = new TodoCard(todo, 10);
        TodoCard todoCard3 = new TodoCard(todo, 100);

        // Check that all cards are created correctly
        assertNotNull(todoCard1);
        assertNotNull(todoCard2);
        assertNotNull(todoCard3);

        // Check that the same todo is stored in all cards
        assertEquals(todo, todoCard1.todo);
        assertEquals(todo, todoCard2.todo);
        assertEquals(todo, todoCard3.todo);
    }

    @Test
    public void getRoot_returnsNonNull() {
        Todo todo = new TodoBuilder().withTodoName("Test Todo").build();
        TodoCard todoCard = new TodoCard(todo, 1);

        assertNotNull(todoCard.getRoot());
    }

    @Test
    public void todoField_returnsCorrectTodo() {
        Todo todo = new TodoBuilder()
                .withTodoName("Test Todo")
                .withDescription("Test Description")
                .withContactName("Test Contact")
                .withCompleted(true)
                .build();

        TodoCard todoCard = new TodoCard(todo, 1);

        assertEquals(todo, todoCard.todo);
        assertEquals("Test Todo", todoCard.todo.getTodoName().toString());
        assertEquals("Test Description", todoCard.todo.getTodoDescription());
        assertEquals("Test Contact", todoCard.todo.getContactName().toString());
        assertTrue(todoCard.todo.getIsCompleted());
    }
}
