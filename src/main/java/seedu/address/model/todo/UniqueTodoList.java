package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.todo.exceptions.DuplicateTodoException;
import seedu.address.model.todo.exceptions.TodoNotFoundException;

/**
 * A list of todos that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniqueTodoList implements Iterable<Todo> {

    private final ObservableList<Todo> internalList = FXCollections.observableArrayList();
    private final ObservableList<Todo> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent todo as the given argument.
     */
    public boolean contains(Todo toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a todo to the list.
     * The Todo must not already exist in the list.
     */
    public void add(Todo toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTodoException();
        }
        internalList.add(toAdd);
    }

    public void setTodos(List<Todo> todos) {
        requireAllNonNull(todos);
        if (!todosAreUnique(todos)) {
            throw new DuplicateTodoException();
        }
        internalList.setAll(todos);
    }

    /**
     * Removes the equivalent todo from the list.
     * The todo must exist in the list.
     */
    public void remove(Todo toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TodoNotFoundException();
        }
    }

    /**
     * Replaces the todo {@code target} in the list with {@code editedTodo}.
     * {@code target} must exist in the list.
     * The todo {@code editedTodo} must not be the same as another existing todo in the list.
     */
    public void setTodo(Todo target, Todo editedTodo) {
        requireAllNonNull(target, editedTodo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TodoNotFoundException();
        }

        if (!target.equals(editedTodo) && contains(editedTodo)) {
            throw new DuplicateTodoException();
        }
        internalList.set(index, editedTodo);
    }

    /**
     * Returns true if {@code todos} contains only unique todos.
     */
    private boolean todosAreUnique(List<Todo> todos) {
        for (int i = 0; i < todos.size() - 1; i++) {
            for (int j = i + 1; j < todos.size(); j++) {
                if (todos.get(i).equals(todos.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns an unmodifiable view of the internal list. */
    public ObservableList<Todo> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Todo> iterator() {
        return internalList.iterator();
    }
}
