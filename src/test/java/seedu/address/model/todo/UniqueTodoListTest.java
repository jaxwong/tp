package seedu.address.model.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTodos.REVIEW_PROPOSAL;
import static seedu.address.testutil.TypicalTodos.SEND_INVITES;
import static seedu.address.testutil.TypicalTodos.UPDATE_DOCS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.todo.exceptions.DuplicateTodoException;
import seedu.address.model.todo.exceptions.TodoNotFoundException;
import seedu.address.testutil.TodoBuilder;

public class UniqueTodoListTest {

    private final UniqueTodoList uniqueTodoList = new UniqueTodoList();

    @Test
    public void contains_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.contains(null));
    }

    @Test
    public void contains_todoNotInList_returnsFalse() {
        assertFalse(uniqueTodoList.contains(REVIEW_PROPOSAL));
    }

    @Test
    public void contains_todoInList_returnsTrue() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        assertTrue(uniqueTodoList.contains(REVIEW_PROPOSAL));
    }

    @Test
    public void add_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.add(null));
    }

    @Test
    public void add_duplicateTodo_throwsDuplicateTodoException() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        assertThrows(DuplicateTodoException.class, () -> uniqueTodoList.add(REVIEW_PROPOSAL));
    }

    @Test
    public void setTodo_nullTargetTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.setTodo(null, REVIEW_PROPOSAL));
    }

    @Test
    public void setTodo_nullEditedTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.setTodo(REVIEW_PROPOSAL, null));
    }

    @Test
    public void setTodo_targetTodoNotInList_throwsTodoNotFoundException() {
        assertThrows(TodoNotFoundException.class, () -> uniqueTodoList.setTodo(REVIEW_PROPOSAL, REVIEW_PROPOSAL));
    }

    @Test
    public void setTodo_editedTodoIsSameTodo_success() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        uniqueTodoList.setTodo(REVIEW_PROPOSAL, REVIEW_PROPOSAL);
        UniqueTodoList expectedUniqueTodoList = new UniqueTodoList();
        expectedUniqueTodoList.add(REVIEW_PROPOSAL);
        assertEquals(expectedUniqueTodoList, uniqueTodoList);
    }

    @Test
    public void setTodo_editedTodoHasSameIdentity_success() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        Todo editedReviewProposal = new TodoBuilder(REVIEW_PROPOSAL)
                .withContactName("Different Contact")
                .withCompleted(true)
                .build();
        uniqueTodoList.setTodo(REVIEW_PROPOSAL, editedReviewProposal);
        UniqueTodoList expectedUniqueTodoList = new UniqueTodoList();
        expectedUniqueTodoList.add(editedReviewProposal);
        assertEquals(expectedUniqueTodoList, uniqueTodoList);
    }

    @Test
    public void setTodo_editedTodoHasDifferentIdentity_success() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        uniqueTodoList.setTodo(REVIEW_PROPOSAL, SEND_INVITES);
        UniqueTodoList expectedUniqueTodoList = new UniqueTodoList();
        expectedUniqueTodoList.add(SEND_INVITES);
        assertEquals(expectedUniqueTodoList, uniqueTodoList);
    }


    @Test
    public void remove_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.remove(null));
    }

    @Test
    public void remove_todoDoesNotExist_throwsTodoNotFoundException() {
        assertThrows(TodoNotFoundException.class, () -> uniqueTodoList.remove(REVIEW_PROPOSAL));
    }

    @Test
    public void remove_existingTodo_removesTodo() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        uniqueTodoList.remove(REVIEW_PROPOSAL);
        UniqueTodoList expectedUniqueTodoList = new UniqueTodoList();
        assertEquals(expectedUniqueTodoList, uniqueTodoList);
    }

    @Test
    public void setTodos_uniqueTodoList_replacesOwnListWithProvidedUniqueTodoList() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        UniqueTodoList expectedUniqueTodoList = new UniqueTodoList();
        expectedUniqueTodoList.add(SEND_INVITES);
        uniqueTodoList.setTodos(expectedUniqueTodoList.asUnmodifiableObservableList());
        assertEquals(expectedUniqueTodoList, uniqueTodoList);
    }

    @Test
    public void setTodos_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.setTodos((List<Todo>) null));
    }

    @Test
    public void setTodos_list_replacesOwnListWithProvidedList() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        List<Todo> todoList = Collections.singletonList(SEND_INVITES);
        uniqueTodoList.setTodos(todoList);
        UniqueTodoList expectedUniqueTodoList = new UniqueTodoList();
        expectedUniqueTodoList.add(SEND_INVITES);
        assertEquals(expectedUniqueTodoList, uniqueTodoList);
    }

    @Test
    public void setTodos_listWithDuplicateTodos_throwsDuplicateTodoException() {
        List<Todo> listWithDuplicateTodos = Arrays.asList(REVIEW_PROPOSAL, REVIEW_PROPOSAL);
        assertThrows(DuplicateTodoException.class, () -> uniqueTodoList.setTodos(listWithDuplicateTodos));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTodoList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        UniqueTodoList uniqueTodoListCopy = new UniqueTodoList();
        assertTrue(uniqueTodoList.equals(uniqueTodoListCopy));

        // same object -> returns true
        assertTrue(uniqueTodoList.equals(uniqueTodoList));

        // null -> returns false
        assertFalse(uniqueTodoList.equals(null));

        // different type -> returns false
        assertFalse(uniqueTodoList.equals(5));

        // different todos -> returns false
        UniqueTodoList differentUniqueTodoList = new UniqueTodoList();
        differentUniqueTodoList.add(REVIEW_PROPOSAL);
        assertFalse(uniqueTodoList.equals(differentUniqueTodoList));

        // same todos -> returns true
        UniqueTodoList uniqueTodoList1 = new UniqueTodoList();
        uniqueTodoList1.add(REVIEW_PROPOSAL);
        uniqueTodoList1.add(SEND_INVITES);

        UniqueTodoList uniqueTodoList2 = new UniqueTodoList();
        uniqueTodoList2.add(REVIEW_PROPOSAL);
        uniqueTodoList2.add(SEND_INVITES);

        assertTrue(uniqueTodoList1.equals(uniqueTodoList2));

        // different order but same todos -> returns false (order matters for list equality)
        UniqueTodoList uniqueTodoList3 = new UniqueTodoList();
        uniqueTodoList3.add(SEND_INVITES);
        uniqueTodoList3.add(REVIEW_PROPOSAL);

        assertFalse(uniqueTodoList1.equals(uniqueTodoList3));

        // different number of todos -> returns false
        UniqueTodoList uniqueTodoListWithExtra = new UniqueTodoList();
        uniqueTodoListWithExtra.add(REVIEW_PROPOSAL);
        uniqueTodoListWithExtra.add(SEND_INVITES);
        uniqueTodoListWithExtra.add(UPDATE_DOCS);

        assertFalse(uniqueTodoList1.equals(uniqueTodoListWithExtra));

        // empty lists -> returns true
        UniqueTodoList emptyList1 = new UniqueTodoList();
        UniqueTodoList emptyList2 = new UniqueTodoList();
        assertTrue(emptyList1.equals(emptyList2));
    }

    @Test
    public void hashCodeMethod() {
        // same todos -> same hash code
        UniqueTodoList uniqueTodoList1 = new UniqueTodoList();
        uniqueTodoList1.add(REVIEW_PROPOSAL);
        uniqueTodoList1.add(SEND_INVITES);

        UniqueTodoList uniqueTodoList2 = new UniqueTodoList();
        uniqueTodoList2.add(REVIEW_PROPOSAL);
        uniqueTodoList2.add(SEND_INVITES);

        assertEquals(uniqueTodoList1.hashCode(), uniqueTodoList2.hashCode());

        // different todos -> different hash code
        UniqueTodoList differentUniqueTodoList = new UniqueTodoList();
        differentUniqueTodoList.add(UPDATE_DOCS);

        assertFalse(uniqueTodoList1.hashCode() == differentUniqueTodoList.hashCode());
    }
}
