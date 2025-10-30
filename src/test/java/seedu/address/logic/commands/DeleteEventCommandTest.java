package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ModelStub;

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
        public ObservableList<Event> getEventList() {
            return eventsObservable;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return personsObservable;
        }

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
