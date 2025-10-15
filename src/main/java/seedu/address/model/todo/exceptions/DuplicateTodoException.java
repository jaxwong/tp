package seedu.address.model.todo.exceptions;

/**
 * Signals that the operation will result in duplicate Events.
 */
public class DuplicateTodoException extends RuntimeException {
    public DuplicateTodoException() {
        super("Operation would result in duplicate todo");
    }
}