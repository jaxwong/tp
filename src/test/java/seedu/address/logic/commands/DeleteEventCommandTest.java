package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.EventBuilder;

public class DeleteEventCommandTest {
    @Test
    public void execute_eventExists_deletesAndSucceeds() throws Exception {
        Event tsc = new EventBuilder().withAlias("TSC2025").build();
        Event other = new EventBuilder().withAlias("MEET123").build();
        ModelStubWithEvents modelStub = new ModelStubWithEvents(tsc, other);

        DeleteEventCommand cmd = new DeleteEventCommand(tsc.getEventAlias());
        CommandResult result = cmd.execute(modelStub);

        assertEquals(String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(tsc)),
                result.getFeedbackToUser());
        assertEquals(Arrays.asList(other), modelStub.events);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        Event other = new EventBuilder().withAlias("MEET123").build();
        ModelStubWithEvents modelStub = new ModelStubWithEvents(other);

        DeleteEventCommand cmd = new DeleteEventCommand(
                new EventBuilder().withAlias("TSC2025").build().getEventAlias());

        assertThrows(CommandException.class,
                String.format(DeleteEventCommand.MESSAGE_EVENT_NOT_FOUND, "TSC2025"), () -> cmd.execute(modelStub));
    }

    @Test
    public void toStringMethod() {
        Event e = new EventBuilder().withAlias("TSC2025").build();
        DeleteEventCommand cmd = new DeleteEventCommand(e.getEventAlias());
        String expected = DeleteEventCommand.class.getCanonicalName()
                + "{eventAlias=" + e.getEventAlias() + "}";
        assertEquals(expected, cmd.toString());
    }

    @Test
    public void equals_sameAliasTrue_differentFalse() {
        DeleteEventCommand a = new DeleteEventCommand(new EventBuilder().withAlias("TSC2025").build().getEventAlias());
        DeleteEventCommand aCopy = new DeleteEventCommand(
                new EventBuilder().withAlias("TSC2025").build().getEventAlias());
        DeleteEventCommand b = new DeleteEventCommand(new EventBuilder().withAlias("MEET123").build().getEventAlias());

        assertTrue(a.equals(a));
        assertTrue(a.equals(aCopy));
        assertFalse(a.equals(null));
        assertFalse(a.equals(1));
        assertFalse(a.equals(b));
    }

    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasTodo(Todo todo) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deleteTodo(Todo todo) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addTodo(Todo todo) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setTodo(Todo target, Todo editedTodo) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Todo> getFilteredTodoList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void updateFilteredTodoList(Predicate<Todo> predicate) {
            throw new AssertionError("Should not be called.");
        }

    }

    private static class ModelStubWithEvents extends ModelStub {
        final ArrayList<Event> events = new ArrayList<>();
        final ArrayList<Person> persons = new ArrayList<>();
        private final ObservableList<Event> eventsObservable = FXCollections.observableList(events);
        private final ObservableList<Person> personsObservable = FXCollections.observableArrayList(persons);


        ModelStubWithEvents(Event... initial) {
            for (Event e : initial) {
                requireNonNull(e);
                events.add(e);
            }
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return eventsObservable;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() { return personsObservable; }

        @Override
        public void deleteEvent(Event event) {
            events.remove(event);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
