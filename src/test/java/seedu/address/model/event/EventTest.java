package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.CONCERT;
import static seedu.address.testutil.TypicalEvents.MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(CONCERT.isSameEvent(CONCERT));

        // null -> returns false
        assertFalse(CONCERT.isSameEvent(null));

        // same alias, all other attributes different -> returns true
        Event editedConcert = new EventBuilder(CONCERT).withName(MEETING.getName()).withStart(MEETING.getStart())
                .withEnd(MEETING.getEnd()).withDescription(MEETING.getDescription()).build();
        assertTrue(CONCERT.isSameEvent(editedConcert));

        // different alias, all other attributes same -> returns false
        editedConcert = new EventBuilder(CONCERT).withAlias(MEETING.getAlias()).build();
        assertFalse(CONCERT.isSameEvent(editedConcert));

        // alias differs in case, all other attributes same -> returns true
        Event editedMeeting = new EventBuilder(MEETING).withAlias(MEETING.getAlias().toLowerCase()).build();
        assertTrue(MEETING.isSameEvent(editedMeeting));
    }

    @Test
    public void toStringMethod() {
        String expected = "Event{"
                + "name='" + CONCERT.getName() + '\''
                + " (" + CONCERT.getEventAlias() + ")"
                + ", start=" + CONCERT.getStart()
                + ", end=" + CONCERT.getEnd()
                + ", description='" + CONCERT.getDescription() + '\''
                + '}';
        assertEquals(expected, CONCERT.toString());
    }
}
