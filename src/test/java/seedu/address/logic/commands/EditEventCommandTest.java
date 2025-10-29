package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.AddressBookBuilder.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.CONCERT;
import static seedu.address.testutil.TypicalEvents.MEETING;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event originalEvent = model.getFilteredEventList().get(0);
        EventAlias originalAlias = originalEvent.getEventAlias();
        Event editedEvent = new EventBuilder().withAlias(originalAlias.value).build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(originalAlias, descriptor);

        String expectedMessage =
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(originalEvent, editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedFilteredList_success() {
        ObservableList<Event> eventList = model.getFilteredEventList();
        Event originalEvent = eventList.get(eventList.size() - 1);
        EventAlias originalAlias = originalEvent.getEventAlias();
        Event editedEvent = new EventBuilder(originalEvent).withName("Ed Sheeran Concert")
                .withStart(originalEvent.getStart().minusHours(1)).build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName("Ed Sheeran Concert").withStart(originalEvent.getStart().minusHours(1)).build();
        EditEventCommand editEventCommand = new EditEventCommand(originalAlias, descriptor);

        String expectedMessage =
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(originalEvent, editedEvent);

        model.updateFilteredEventList(event -> false);
        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        Event originalEvent = model.getFilteredEventList().get(0);
        EventAlias originalAlias = originalEvent.getEventAlias();
        EditEventCommand editEventCommand =
                new EditEventCommand(originalAlias, new EditEventCommand.EditEventDescriptor());
        Event editedEvent = originalEvent;

        String expectedMessage =
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventWithSameFields_success() {
        ObservableList<Event> eventList = model.getFilteredEventList();
        Event originalEvent = eventList.get(1);
        EventAlias originalAlias = originalEvent.getEventAlias();
        Event editedEvent = new EventBuilder(eventList.get(0)).withAlias(originalAlias.value).build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(originalAlias, descriptor);

        String expectedMessage =
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(originalEvent, editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noEventWithEventAlias_failure() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(CONCERT).build();
        EditEventCommand editEventCommand = new EditEventCommand(new EventAlias("nonexistent"), descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(MEETING).build();
        final EditEventCommand standardCommand = new EditEventCommand(CONCERT.getEventAlias(), descriptor);

        // same values -> returns true
        EditEventCommand.EditEventDescriptor copyDescriptor = new EditEventDescriptorBuilder(descriptor).build();
        EditEventCommand commandWithSameValues = new EditEventCommand(CONCERT.getEventAlias(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different alias -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(MEETING.getEventAlias(), copyDescriptor)));

        // different descriptor -> returns false
        EditEventCommand.EditEventDescriptor differentDescriptor =
                new EditEventDescriptorBuilder().withEventName("Eras Tour").build();
        assertFalse(standardCommand.equals(new EditEventCommand(CONCERT.getEventAlias(), differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        EditEventCommand.EditEventDescriptor editEventDescriptor = new EditEventCommand.EditEventDescriptor();
        EditEventCommand editEventCommand = new EditEventCommand(CONCERT.getEventAlias(), editEventDescriptor);
        String expected = EditEventCommand.class.getCanonicalName() + "{eventAlias=" + CONCERT.getAlias()
                + ", editEventDescriptor=" + editEventDescriptor + "}";
        assertEquals(expected, editEventCommand.toString());
    }

}
