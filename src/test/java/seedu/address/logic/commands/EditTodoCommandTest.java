package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTodos.getTypicalTodos;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditTodoCommand.EditTodoDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTodoCommand.
 */
public class EditTodoCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        for (Todo todo: getTypicalTodos()) {
            model.addTodo(todo);
        }
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Todo todoToEdit = model.getFilteredTodoList().get(0);
        Todo editedTodo = new TodoBuilder(todoToEdit)
                .withTodoName("Updated Todo Name")
                .withDescription("Updated Description")
                .withContactName("Alice Pauline")
                .withCompleted(true)
                .build();

        EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.setTodoName(editedTodo.getTodoName());
        descriptor.setTodoDescription(editedTodo.getTodoDescription());
        descriptor.setContactName(editedTodo.getContactName());
        descriptor.setCompleted(editedTodo.getIsCompleted());

        EditTodoCommand command = new EditTodoCommand(Index.fromOneBased(1), descriptor);

        String expectedMessage = String.format(EditTodoCommand.MESSAGE_EDIT_TODO_SUCCESS, Messages.format(editedTodo));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setTodo(todoToEdit, editedTodo);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTodoList().size() + 1);
        EditTodoDescriptor descriptor = new EditTodoDescriptor();
        descriptor.setTodoDescription("This test should fail");

        EditTodoCommand command = new EditTodoCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidContactName_throwsCommandException() {
        Todo todoToEdit = model.getFilteredTodoList().get(0);
        Todo editedTodo = new TodoBuilder(todoToEdit)
                .withContactName("Nonexistent Person")
                .build();

        EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.setContactName(editedTodo.getContactName());

        EditTodoCommand command = new EditTodoCommand(Index.fromOneBased(1), descriptor);

        assertCommandFailure(command, model, "Contact not found: Nonexistent Person");
    }

    @Test
    public void execute_noFieldsEdited_throwsCommandException() {
        EditTodoCommand command = new EditTodoCommand(Index.fromOneBased(1), new EditTodoDescriptor());

        assertCommandFailure(command, model, EditTodoCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_duplicateTodo_throwsCommandException() {
        List<Todo> todoList = model.getFilteredTodoList();
        Todo firstTodo = todoList.get(0);

        // Edit the next todo to become identical to the firstTodo
        EditTodoDescriptor descriptor = new EditTodoDescriptor();
        descriptor.setTodoName(firstTodo.getTodoName());
        descriptor.setTodoDescription(firstTodo.getTodoDescription());
        descriptor.setContactName(firstTodo.getContactName());
        descriptor.setCompleted(firstTodo.getIsCompleted());

        EditTodoCommand command = new EditTodoCommand(Index.fromOneBased(2), descriptor);

        assertCommandFailure(command, model, EditTodoCommand.MESSAGE_DUPLICATE_TODO);
    }

    @Test
    public void execute_unlinkContact_success() {
        Todo todoToEdit = model.getFilteredTodoList().get(0);
        Todo editedTodo = new TodoBuilder(todoToEdit)
                .withoutContactName()
                .build();

        EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.markContactUnlinked();

        EditTodoCommand command = new EditTodoCommand(Index.fromOneBased(1), descriptor);

        String expectedMessage = String.format(EditTodoCommand.MESSAGE_EDIT_TODO_SUCCESS, Messages.format(editedTodo));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setTodo(todoToEdit, editedTodo);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unlinkContactAndEditDescription_success() {
        Todo todoToEdit = model.getFilteredTodoList().get(0);
        Todo editedTodo = new TodoBuilder(todoToEdit)
                .withoutContactName()
                .withDescription("Updated new description while unlinking")
                .build();

        EditTodoDescriptor descriptor = new EditTodoCommand.EditTodoDescriptor();
        descriptor.markContactUnlinked();
        descriptor.setTodoDescription("Updated new description while unlinking");

        EditTodoCommand command = new EditTodoCommand(Index.fromOneBased(1), descriptor);

        String expectedMessage = String.format(EditTodoCommand.MESSAGE_EDIT_TODO_SUCCESS, Messages.format(editedTodo));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setTodo(todoToEdit, editedTodo);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        EditTodoDescriptor descriptor = new EditTodoDescriptor();
        descriptor.setTodoDescription("Desc");

        EditTodoCommand command1 = new EditTodoCommand(Index.fromOneBased(1), descriptor);
        EditTodoCommand command2 = new EditTodoCommand(Index.fromOneBased(1), descriptor);
        EditTodoCommand command3 = new EditTodoCommand(Index.fromOneBased(2), descriptor);

        // samve values -> true
        assertTrue(command1.equals(command2));

        // same object -> true
        assertTrue(command1.equals(command1));

        // null -> false
        assertFalse(command1.equals(null));

        // different type -> false
        assertFalse(command1.equals(1));

        // different index -> false
        assertFalse(command1.equals(command3));


    }
}
