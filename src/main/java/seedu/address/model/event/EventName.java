package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventName(String)}
 */
public class EventName {

    public static final String MESSAGE_CONSTRAINTS =
            "Event names should be 1–100 characters long and may only contain letters, numbers, spaces, "
                    + "apostrophes ('), hyphens (-), ampersands (&), and commas (,). Extra whitespace is trimmed.";

    /*
     * Length constraint ensures the name is between 1 and 100 characters.
     */
    public static final String VALIDATION_REGEX = "^[A-Za-z0-9'&,\\- ]{1,100}$";


    public final String fullName;

    /**
     * Constructs a {@code EventName}.
     *
     * @param name A valid event name.
     */
    public EventName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        checkArgument(isValidEventName(trimmedName), MESSAGE_CONSTRAINTS);
        fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidEventName(String test) {
        return test != null
                && test.trim().length() >= 1
                && test.trim().length() <= 100
                && test.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

}
