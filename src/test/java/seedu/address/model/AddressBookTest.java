package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void equals() {
        // same values -> returns true
        AddressBook addressBookCopy = new AddressBook();
        assertTrue(addressBook.equals(addressBookCopy));

        // same object -> returns true
        assertTrue(addressBook.equals(addressBook));

        // null -> returns false
        assertFalse(addressBook.equals(null));

        // different type -> returns false
        assertFalse(addressBook.equals(5));

        // different persons -> returns false
        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addPerson(ALICE);
        assertFalse(addressBook.equals(differentAddressBook));

        // different events -> returns false
        AddressBook addressBookWithEvents = new AddressBook();
        addressBookWithEvents.addEvent(seedu.address.testutil.TypicalEvents.CONCERT);
        assertFalse(addressBook.equals(addressBookWithEvents));

        // different todos -> returns false
        AddressBook addressBookWithTodos = new AddressBook();
        addressBookWithTodos.addTodo(seedu.address.testutil.TypicalTodos.REVIEW_PROPOSAL);
        assertFalse(addressBook.equals(addressBookWithTodos));

        // same persons, events, and todos -> returns true
        AddressBook completeAddressBook1 = new AddressBook();
        completeAddressBook1.addPerson(ALICE);
        completeAddressBook1.addEvent(seedu.address.testutil.TypicalEvents.CONCERT);
        completeAddressBook1.addTodo(seedu.address.testutil.TypicalTodos.REVIEW_PROPOSAL);

        AddressBook completeAddressBook2 = new AddressBook();
        completeAddressBook2.addPerson(ALICE);
        completeAddressBook2.addEvent(seedu.address.testutil.TypicalEvents.CONCERT);
        completeAddressBook2.addTodo(seedu.address.testutil.TypicalTodos.REVIEW_PROPOSAL);

        assertTrue(completeAddressBook1.equals(completeAddressBook2));

        // same persons but different events -> returns false
        AddressBook addressBookWithDifferentEvents = new AddressBook();
        addressBookWithDifferentEvents.addPerson(ALICE);
        addressBookWithDifferentEvents.addEvent(seedu.address.testutil.TypicalEvents.MEETING);
        assertFalse(completeAddressBook1.equals(addressBookWithDifferentEvents));

        // same persons and events but different todos -> returns false
        AddressBook addressBookWithDifferentTodos = new AddressBook();
        addressBookWithDifferentTodos.addPerson(ALICE);
        addressBookWithDifferentTodos.addEvent(seedu.address.testutil.TypicalEvents.CONCERT);
        addressBookWithDifferentTodos.addTodo(seedu.address.testutil.TypicalTodos.SEND_INVITES);
        assertFalse(completeAddressBook1.equals(addressBookWithDifferentTodos));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final ObservableList<Todo> todos = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Todo> getTodoList() {
            return todos;
        }
    }

}
