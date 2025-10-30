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
     * Returns true if both todo have the same todoName, todoDescription.
     * @param otherTodo The todo to compare with this todo.
     * @return true if the other todo has the same todoName, todoDescription
     */
    public boolean isSameTodo(Todo otherTodo) {
        if (otherTodo == this) {
            return true;
        }

        return this.todoName.equals(otherTodo.todoName) && this.todoDescription.equalsIgnoreCase(otherTodo.todoDescription);
    }

    /**
     * Creates and returns a copy of this todo with the specified linked contact name.
     *
     * @param contactName the new linked contact name
     * @return a new Todo with the same properties but different linked contact name
     */
    public Todo withLinkedContactName(Name contactName) {
        return new Todo(this.todoName, this.todoDescription, contactName, this.isCompleted);
    }

    /**
     * Creates and returns a copy of this todo with the specified completion status.
     * @param isCompleted the new completion status
     * @return a new Todo with the same properties but different completion status
     */
    public Todo withCompletionStatus(boolean isCompleted) {
        return new Todo(this.todoName, this.todoDescription, this.contactName, isCompleted);
    }

    @Override
    public String toString() {
        return "Todo{"
                + "todoName='" + todoName + '\''
                + ", todoDescription='" + todoDescription + '\''
                + ", contactName='" + contactName + '\''
                + ", isCompleted=" + isCompleted
                + '}';
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
                && this.todoDescription.equalsIgnoreCase(otherTodo.todoDescription)
                && java.util.Objects.equals(contactName, otherTodo.contactName)
                && this.isCompleted == otherTodo.isCompleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoName, todoDescription, contactName, isCompleted);
    }

}
