package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.EditEventCommand;

/**
 * A utility class for Event.
 */
public class EventUtil {
    /**
     * Returns the part of command string for the given {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventCommand.EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        descriptor.getEventName().ifPresent(name -> sb.append(PREFIX_EVENT_NAME).append(name.fullName).append(" "));
        descriptor.getStart().ifPresent(start -> sb.append(PREFIX_START).append(start.format(formatter)).append(" "));
        descriptor.getEnd().ifPresent(end -> sb.append(PREFIX_END).append(end.format(formatter)).append(" "));
        descriptor.getDescription().ifPresent(desc -> sb.append(PREFIX_DESC).append(desc).append(" "));

        return sb.toString();
    }
}
