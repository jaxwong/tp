package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoName;

/**
 * A utility class to help with building Todo objects.
 */
public class TodoBuilder {

    public static final String DEFAULT_TODO_NAME = "Call Alic";
    public static final String DEFAULT_TODO_DESCRIPTION = "Find out about event requirements.";
    public static final String DEFAULT_NAME = "Alice Pauline";

    private TodoName todoName;
    private String description;
    private Name name;

    /**
     * Creates a {@code TodoBuilder} with the default details.
     */
    public TodoBuilder() {
        todoName = new TodoName(DEFAULT_TODO_NAME);
        description = DEFAULT_TODO_DESCRIPTION;
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the TodoBuilder with the data of {@code todoToCopy}
     */
    public TodoBuilder(Todo todoToCopy) {
        todoName = todoToCopy.getTodoName();
        description = todoToCopy.getDescription();
        name = todoToCopy.getContactName();
    }

    /**
     * Sets the {@code TodoName} of the @{code Todo} that we are building.
     */
    public TodoBuilder withTodoName(String todoName) {
        this.todoName = new TodoName(todoName);
        return this;
    }

    /**
     * Sets the description of the {@code Todo} that we are building.
     */
    public TodoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Todo} that we are building.
     */
    public TodoBuilder withContactName(String name) {
        this.name = (name == null) ? null : new Name(name);
        return this;
    }

    public Todo build() {
        return new Todo(todoName, description, name);
    }

}
