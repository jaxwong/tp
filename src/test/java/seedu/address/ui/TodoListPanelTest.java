package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;

public class TodoListPanelTest extends JavaFxTestBase {

    @Test
    public void constructor_withEmptyList_createsPanelCorrectly() {
        ObservableList<Todo> emptyList = FXCollections.observableArrayList();
        TodoListPanel todoListPanel = new TodoListPanel(emptyList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void constructor_withSingleTodo_createsPanelCorrectly() {
        Todo todo = new TodoBuilder()
                .withTodoName("Test Todo")
                .withDescription("Test Description")
                .withContactName("John Doe")
                .withCompleted(false)
                .build();

        ObservableList<Todo> todoList = FXCollections.observableArrayList(Arrays.asList(todo));
        TodoListPanel todoListPanel = new TodoListPanel(todoList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void constructor_withMultipleTodos_createsPanelCorrectly() {
        Todo todo1 = new TodoBuilder()
                .withTodoName("First Todo")
                .withDescription("First Description")
                .withContactName("John Doe")
                .withCompleted(false)
                .build();

        Todo todo2 = new TodoBuilder()
                .withTodoName("Second Todo")
                .withDescription("Second Description")
                .withContactName("Jane Smith")
                .withCompleted(true)
                .build();

        Todo todo3 = new TodoBuilder()
                .withTodoName("Third Todo")
                .withDescription("Third Description")
                .withoutContactName()
                .withCompleted(false)
                .build();

        ObservableList<Todo> todoList = FXCollections.observableArrayList(Arrays.asList(todo1, todo2, todo3));
        TodoListPanel todoListPanel = new TodoListPanel(todoList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void constructor_withTodosWithSpecialCharacters_createsPanelCorrectly() {
        Todo todo1 = new TodoBuilder()
                .withTodoName("Review Project's & Proposal-Document, Q1")
                .withDescription("Review document with special characters")
                .withContactName("John Conor99")
                .withCompleted(false)
                .build();

        Todo todo2 = new TodoBuilder()
                .withTodoName("Meeting with Dr Smith-Jones")
                .withDescription("Discuss project timeline")
                .withContactName("Dr Smith Jones")
                .withCompleted(true)
                .build();

        ObservableList<Todo> todoList = FXCollections.observableArrayList(Arrays.asList(todo1, todo2));
        TodoListPanel todoListPanel = new TodoListPanel(todoList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void constructor_withTodosWithLongNames_createsPanelCorrectly() {
        Todo todo1 = new TodoBuilder()
                .withTodoName("This is a very long todo name - might exceed")
                .withDescription("Short description")
                .withContactName("John Doe")
                .withCompleted(false)
                .build();

        Todo todo2 = new TodoBuilder()
                .withTodoName("Another very long todo name - might exceed")
                .withDescription("Another short description")
                .withContactName("Jane Smith")
                .withCompleted(true)
                .build();

        ObservableList<Todo> todoList = FXCollections.observableArrayList(Arrays.asList(todo1, todo2));
        TodoListPanel todoListPanel = new TodoListPanel(todoList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void constructor_withMixedCompletedStatus_createsPanelCorrectly() {
        Todo completedTodo = new TodoBuilder()
                .withTodoName("Completed Todo")
                .withDescription("This todo is completed")
                .withContactName("John Doe")
                .withCompleted(true)
                .build();

        Todo incompleteTodo = new TodoBuilder()
                .withTodoName("Incomplete Todo")
                .withDescription("This todo is not completed")
                .withContactName("Jane Smith")
                .withCompleted(false)
                .build();

        ObservableList<Todo> todoList = FXCollections.observableArrayList(Arrays.asList(completedTodo, incompleteTodo));
        TodoListPanel todoListPanel = new TodoListPanel(todoList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void constructor_withTodosWithNullContacts_createsPanelCorrectly() {
        Todo todoWithContact = new TodoBuilder()
                .withTodoName("Todo with Contact")
                .withDescription("This todo has a contact")
                .withContactName("John Doe")
                .withCompleted(false)
                .build();

        Todo todoWithoutContact = new TodoBuilder()
                .withTodoName("Todo without Contact")
                .withDescription("This todo has no contact")
                .withoutContactName()
                .withCompleted(false)
                .build();

        ObservableList<Todo> todoList = FXCollections.observableArrayList(
                Arrays.asList(todoWithContact, todoWithoutContact));
        TodoListPanel todoListPanel = new TodoListPanel(todoList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void getRoot_returnsNonNull() {
        ObservableList<Todo> emptyList = FXCollections.observableArrayList();
        TodoListPanel todoListPanel = new TodoListPanel(emptyList);

        assertNotNull(todoListPanel.getRoot());
    }

    @Test
    public void constructor_withLargeList_createsPanelCorrectly() {
        // Create a list with many todos
        ObservableList<Todo> largeList = FXCollections.observableArrayList();
        for (int i = 1; i <= 100; i++) {
            Todo todo = new TodoBuilder()
                    .withTodoName("Todo " + i)
                    .withDescription("Description for todo " + i)
                    .withContactName("Contact " + i)
                    .withCompleted(i % 2 == 0) // Alternate between completed and incomplete
                    .build();
            largeList.add(todo);
        }

        TodoListPanel todoListPanel = new TodoListPanel(largeList);

        assertNotNull(todoListPanel);
        assertNotNull(todoListPanel.getRoot());
    }
}
