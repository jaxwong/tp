package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.CONCERT;
import static seedu.address.testutil.TypicalEvents.MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(CONCERT).build();
        EditEventCommand.EditEventDescriptor descriptorWithSameValues =
                new EditEventDescriptorBuilder(descriptor).build();
        assertTrue(descriptor.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different values -> returns false
        EditEventCommand.EditEventDescriptor differentDescriptor = new EditEventDescriptorBuilder(MEETING).build();
        assertFalse(descriptor.equals(differentDescriptor));

        // different event name -> returns false
        EditEventCommand.EditEventDescriptor editedDescriptor =
                new EditEventDescriptorBuilder(descriptor).withEventName("Different Name").build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different start -> returns false
        editedDescriptor =
                new EditEventDescriptorBuilder(descriptor).withStart(CONCERT.getStart().minusHours(1)).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different end -> returns false
        editedDescriptor =
                new EditEventDescriptorBuilder(descriptor).withStart(CONCERT.getEnd().plusHours(1)).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different description -> returns false
        editedDescriptor = new EditEventDescriptorBuilder(descriptor).withDescription("Different Description").build();
        assertFalse(descriptor.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditEventCommand.EditEventDescriptor editEventDescriptor = new EditEventCommand.EditEventDescriptor();
        String expected = EditEventCommand.EditEventDescriptor.class.getCanonicalName() + "{name="
                + editEventDescriptor.getEventName().orElse(null) + ", start="
                + editEventDescriptor.getStart().orElse(null) + ", end="
                + editEventDescriptor.getEnd().orElse(null) + ", description="
                + editEventDescriptor.getDescription().orElse(null) + "}";
        assertEquals(expected, editEventDescriptor.toString());
    }
}
