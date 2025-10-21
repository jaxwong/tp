package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Name;

/**
 * Represents a Todo in the address book.
 */
public class Todo {
    private final TodoName todoName;
    private final String todoDescription;
    private final Name contactName; // may be null
    private final boolean isCompleted;

    /**
     * Constructs a Todo with the specified details.
     * @param todoName The name of the todo. Must not be null and must be a valid todo name.
     * @param todoDescription The description of the todo. Must not be null.
     * @param contactName The name of the linked contact. Can be null.
     */
    public Todo(TodoName todoName, String todoDescription, Name contactName) {
        requireAllNonNull(todoName, todoDescription);
        this.todoName = todoName;
        this.todoDescription = todoDescription;
        this.contactName = contactName;
        this.isCompleted = false;
    }

    /**
     * Separate constructor for loading from database into the code.
     * @param isCompleted true if the todo is marked as completed.
     */
    public Todo(TodoName todoName, String todoDescription, Name contactName, boolean isCompleted) {
        requireAllNonNull(todoName, todoDescription, isCompleted);
        this.todoName = todoName;
        this.todoDescription = todoDescription;
        this.contactName = contactName;
        this.isCompleted = isCompleted;
    }

    /**
     * Returns the name of the todo.
     */
    public TodoName getTodoName() {
        return todoName;
    }

    /**
     * Returns the description of the todo
     */
    public String getTodoDescription() {
        return todoDescription;
    }

    /**
     * Returns the linked contact Name
     */
    public Name getContactName() {
        return contactName;
    }

    /**
     * Returns the name of the linked contact as a string.
     */
    public String getContactNameDisplay() {
        if (contactName != null) {
            return contactName.toString();
        }
        return "No contact linked";
    }

    /**
     * Returns true if the todo is marked as completed.
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Returns true if both events have the same name, start and end.
     * This defines a weaker notion of equality between two events.
     * @param otherTodo The todo to compare with this todo.
     * @return true if the other todo has the same TodoName, TodoDescription, ContactName and isCompleted
     */
    public boolean isSameTodo(Todo otherTodo) {
        if (otherTodo == this) {
            return true;
        }

        return this.equals(otherTodo);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Todo)) {
            return false;
        }
        Todo otherTodo = (Todo) other;
        return Objects.equals(todoName, otherTodo.todoName)
                && Objects.equals(todoDescription, otherTodo.todoDescription)
                && Objects.equals(contactName, otherTodo.contactName)
                && Objects.equals(isCompleted, otherTodo.isCompleted);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(todoName);
    }
}
