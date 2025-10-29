package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTodo.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTodos.REVIEW_PROPOSAL;
import static seedu.address.testutil.TypicalTodos.UPDATE_DOCS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.todo.TodoName;

public class JsonAdaptedTodoTest {

    private static final String INVALID_TODO_NAME = "@";
    private static final String INVALID_CONTACT_NAME = "R@chel";

    @Test
    public void toModelType_validTodoWithContact_returnsTodo() throws Exception {
        JsonAdaptedTodo adapted = new JsonAdaptedTodo(REVIEW_PROPOSAL);
        assertEquals(REVIEW_PROPOSAL, adapted.toModelType());
    }

    @Test
    public void toModelType_validTodoWithoutContact_returnsTodo() throws Exception {
        JsonAdaptedTodo adapted = new JsonAdaptedTodo(UPDATE_DOCS);
        assertEquals(UPDATE_DOCS, adapted.toModelType());
    }

    @Test
    public void toModelType_invalidTodoName_throwsIllegalValueException() {
        JsonAdaptedTodo adapted = new JsonAdaptedTodo(
                INVALID_TODO_NAME,
                "Some description",
                "John Doe",
                false);
        String expectedMessage = TodoName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullTodoName_throwsIllegalValueException() {
        JsonAdaptedTodo adapted = new JsonAdaptedTodo(
                null,
                "Some description",
                "John Doe",
                false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "todoName");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidContactName_throwsIllegalValueException() {
        JsonAdaptedTodo adapted = new JsonAdaptedTodo(
                "Valid Task",
                "Some description",
                INVALID_CONTACT_NAME,
                false);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }
}
