package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event CONCERT = new EventBuilder()
            .withName("Taylor Swift Concert")
            .withAlias("TSC2025")
            .withStart(LocalDateTime.of(2025, 9, 19, 19, 30))
            .withEnd(LocalDateTime.of(2025, 9, 19, 23, 30))
            .withDescription("Taylor's Swift Eras tour")
            .build();

    public static final Event MEETING = new EventBuilder()
            .withName("Team Meeting")
            .withAlias("MEET24")
            .withStart(LocalDateTime.of(2024, 12, 25, 10, 0))
            .withEnd(LocalDateTime.of(2024, 12, 25, 11, 0))
            .withDescription("Weekly team standup meeting")
            .build();

    public static final Event CONFERENCE = new EventBuilder()
            .withName("Tech Conference")
            .withAlias("TECHCONF24")
            .withStart(LocalDateTime.of(2024, 12, 30, 9, 0))
            .withEnd(LocalDateTime.of(2024, 12, 30, 17, 0))
            .withDescription("Annual technology conference")
            .build();

    public static final Event WORKSHOP = new EventBuilder()
            .withName("Java Workshop")
            .withAlias("JAVA25")
            .withStart(LocalDateTime.of(2025, 1, 15, 14, 0))
            .withEnd(LocalDateTime.of(2025, 1, 15, 16, 0))
            .withDescription("Introduction to Java programming")
            .build();

    public static final Event PARTY = new EventBuilder()
            .withName("Birthday Party")
            .withAlias("BDAY25")
            .withStart(LocalDateTime.of(2025, 2, 14, 18, 0))
            .withEnd(LocalDateTime.of(2025, 2, 14, 22, 0))
            .withDescription("John's 25th birthday celebration")
            .build();

    public static final Event SEMINAR = new EventBuilder()
            .withName("AI Seminar")
            .withAlias("AI25")
            .withStart(LocalDateTime.of(2025, 3, 10, 13, 0))
            .withEnd(LocalDateTime.of(2025, 3, 10, 15, 0))
            .withDescription("Future of Artificial Intelligence")
            .build();

    public static final Event EXHIBITION = new EventBuilder()
            .withName("Art Exhibition")
            .withAlias("ART25")
            .withStart(LocalDateTime.of(2025, 4, 5, 10, 0))
            .withEnd(LocalDateTime.of(2025, 4, 5, 18, 0))
            .withDescription("Modern art showcase")
            .build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Add all typical events into the specified {@code AddressBook}
     */
    public static void addTypicalEvents(AddressBook ab) {
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
    }

    /**
     * Returns a list of typical events for testing.
     */
    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(CONCERT, MEETING, CONFERENCE, WORKSHOP, PARTY, SEMINAR, EXHIBITION));
    }
}
