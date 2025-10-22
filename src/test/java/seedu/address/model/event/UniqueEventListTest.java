package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.CONCERT;
import static seedu.address.testutil.TypicalEvents.MEETING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;


public class UniqueEventListTest {
    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(CONCERT));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(CONCERT);
        assertTrue(uniqueEventList.contains(CONCERT));
    }


    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(CONCERT);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(CONCERT));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, CONCERT));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(CONCERT, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(CONCERT, CONCERT));
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(CONCERT);
        uniqueEventList.setEvent(CONCERT, CONCERT);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(CONCERT);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }


    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(CONCERT);
        uniqueEventList.setEvent(CONCERT, MEETING);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(MEETING);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(CONCERT);
        uniqueEventList.add(MEETING);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(CONCERT, MEETING));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(CONCERT));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(CONCERT);
        uniqueEventList.remove(CONCERT);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(CONCERT);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(MEETING);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(CONCERT);
        List<Event> eventList = Collections.singletonList(MEETING);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(MEETING);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvent_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(CONCERT, CONCERT);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicateEvents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}
