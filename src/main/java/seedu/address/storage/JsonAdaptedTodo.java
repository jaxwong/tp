package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoName;

/**
 * Jackson-friendly adapted version of {@link Todo}
 */
public class JsonAdaptedTodo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Todo's %s field is missing!";

    private final String todoName;
    private final String description;
    private final String contactName; // references Person by unique name
    private final boolean isCompleted;

    /**
     * Constructs a {@code JsonAdaptedTodo} with the given todo details
     */
    @JsonCreator
    public JsonAdaptedTodo(@JsonProperty("todoName") String todoName,
                           @JsonProperty("description") String description,
                           @JsonProperty("contactName") String contactName,
                           @JsonProperty("isCompleted") boolean isCompleted) {
        this.todoName = todoName;
        this.description = description;
        this.contactName = contactName;
        this.isCompleted = isCompleted;
    }

    /**
     * Converts a given {@code Todo} into this class for Jackson use.
     */
    public JsonAdaptedTodo(Todo source) {
        requireNonNull(source);
        this.todoName = source.getTodoName().toString();
        this.description = source.getTodoDescription();
        this.contactName = source.getContactName() == null
                ? null
                : source.getContactName().fullName;
        this.isCompleted = source.getIsCompleted();
    }

    /**
     * Converts this Jackson-friendly adapted todo object into the model's {@code Todo} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted todo.
     */
    public Todo toModelType() throws IllegalValueException {
        if (todoName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "todoName"));
        }
        if (!TodoName.isValidTodoName(todoName)) {
            throw new IllegalValueException(TodoName.MESSAGE_CONSTRAINTS);
        }
        TodoName modelTodoName = new TodoName(todoName);

        final Name modelContactName;
        if (contactName == null) {
            modelContactName = null;
        } else {
            try {
                modelContactName = new Name(contactName);
            } catch (IllegalArgumentException ex) {
                throw new IllegalValueException(ex.getMessage());
            }
        }

        return new Todo(modelTodoName, description, modelContactName, isCompleted);
    }
}
