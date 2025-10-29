package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TypicalTodos;

public class DeleteTodoCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        for (Todo t : TypicalTodos.getTypicalTodos()) {
            model.addTodo(t);
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Todo todoToDelete = model.getFilteredTodoList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTodoCommand deleteTodoCommand = new DeleteTodoCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTodoCommand.MESSAGE_DELETE_TODO_SUCCESS,
                Messages.format(todoToDelete));

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        for (Todo t : model.getFilteredTodoList()) {
            expectedModel.addTodo(t);
        }
        expectedModel.deleteTodo(todoToDelete);

        assertCommandSuccess(deleteTodoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTodoList().size() + 1);
        DeleteTodoCommand deleteTodoCommand = new DeleteTodoCommand(outOfBoundIndex);

        assertCommandFailure(deleteTodoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Todo first = model.getFilteredTodoList().get(INDEX_FIRST_PERSON.getZeroBased());
        showTodoAtIndex(model, INDEX_FIRST_PERSON);

        DeleteTodoCommand deleteTodoCommand = new DeleteTodoCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteTodoCommand.MESSAGE_DELETE_TODO_SUCCESS, Messages.format(first));

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        // replicate the filtered state (single item), then delete it
        expectedModel.addTodo(first);
        expectedModel.deleteTodo(first);
        showNoTodo(expectedModel);

        assertCommandSuccess(deleteTodoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        int totalBeforeFilter = model.getFilteredTodoList().size();
        showTodoAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < totalBeforeFilter);

        DeleteTodoCommand deleteTodoCommand = new DeleteTodoCommand(outOfBoundIndex);

        assertCommandFailure(deleteTodoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteTodoCommand deleteFirst = new DeleteTodoCommand(INDEX_FIRST_PERSON);
        DeleteTodoCommand deleteSecond = new DeleteTodoCommand(INDEX_SECOND_PERSON);

        assertTrue(deleteFirst.equals(deleteFirst));
        assertTrue(deleteFirst.equals(new DeleteTodoCommand(INDEX_FIRST_PERSON)));
        assertFalse(deleteFirst.equals(1));
        assertFalse(deleteFirst.equals(null));
        assertFalse(deleteFirst.equals(deleteSecond));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteTodoCommand command = new DeleteTodoCommand(targetIndex);
        String expected = DeleteTodoCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }

    private void showTodoAtIndex(Model model, Index target) {
        Todo targetTodo = model.getFilteredTodoList().get(target.getZeroBased());
        model.updateFilteredTodoList(t -> t.equals(targetTodo));
        assertEquals(1, model.getFilteredTodoList().size());
    }

    private void showNoTodo(Model model) {
        model.updateFilteredTodoList(t -> false);
        assertTrue(model.getFilteredTodoList().isEmpty());
    }
}
