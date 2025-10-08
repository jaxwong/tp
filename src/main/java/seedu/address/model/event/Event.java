package seedu.address.model.event;



import java.time.LocalDateTime;

public class Event {
    private final EventName name;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String description;

    public Event(EventName name, LocalDateTime start, LocalDateTime end, String description) {
        this.name = name;
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public String getName() {
        return this.name.toString();
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return name.equals(otherEvent.name)
                && start.equals(otherEvent.start)
                && end.equals(otherEvent.end)
                && description.equals(otherEvent.description);
    }

    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getStart().equals(getStart())
                && otherEvent.getEnd().equals(getEnd());
    }


}
