package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Name;

/**
 * Represents a Todo in the address book.
 */
public class Todo {
    private final TodoName todoName;
    private final String description;
    private final Name contactName; // may be null
    private boolean isCompleted;

    /**
     * Constructs a Todo with the specified details.
     * @param todoName The name of the todo. Must not be null and must be a valid todo name.
     * @param description The description of the todo. Must not be null.
     * @param contactName The name of the linked contact. Can be null.
     */
    public Todo(TodoName todoName, String description, Name contactName) {
        requireAllNonNull(todoName, description);
        this.todoName = todoName;
        this.description = description;
        this.contactName = contactName;
        this.isCompleted = false;
    }

    /**
     * Separate constructor for loading from database into the code.
     * @param isCompleted true if the todo is marked as completed.
     */
    public Todo(TodoName todoName, String description, Name contactName, boolean isCompleted) {
        requireAllNonNull(todoName, description, isCompleted);
        this.todoName = todoName;
        this.description = description;
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
    public String getDescription() {
        return description;
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Todo)) {
            return false;
        }
        Todo otherTodo = (Todo) other;
        return this.todoName.equals(otherTodo.todoName)
                && this.description.equals(otherTodo.description)
                && this.contactName.equals(otherTodo.contactName)
                && this.isCompleted == otherTodo.isCompleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoName, description, contactName, isCompleted);
    }

}
