package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.CONCERT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EventAlias;
import seedu.address.model.event.EventName;

public class JsonAdaptedEventTest {
    private static final String INVALID_ALIAS = "a++";
    private static final String INVALID_NAME = "c++";

    private static final String VALID_ALIAS = CONCERT.getAlias();
    private static final String VALID_NAME = CONCERT.getName();
    private static final String VALID_START = CONCERT.getStart().toString();
    private static final String VALID_END = CONCERT.getEnd().toString();
    private static final String VALID_DESC = CONCERT.getDescription();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(CONCERT);
        assertEquals(CONCERT, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                INVALID_NAME, VALID_ALIAS, VALID_START, VALID_END, VALID_DESC);
        String expectedMessage = EventName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                null, VALID_ALIAS, VALID_START, VALID_END, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "name");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidAlias_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_NAME, INVALID_ALIAS, VALID_START, VALID_END, VALID_DESC);
        String expectedMessage = EventAlias.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullAlias_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_NAME, null, VALID_START, VALID_END, VALID_DESC);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "alias");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
