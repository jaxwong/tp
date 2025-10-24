package seedu.address.testutil;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setEventName(event.getEventName());
        descriptor.setStart(event.getStart());
        descriptor.setEnd(event.getEnd());
        descriptor.setDescription(event.getDescription());
    }

    /**
     * Sets the {@code EventName} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventName(String eventName) {
        descriptor.setEventName(new EventName(eventName));
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} start of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStart(String start) {
        try {
            descriptor.setStart(ParserUtil.parseDate(start));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + start);
        }
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} end of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEnd(String end) {
        try {
            descriptor.setEnd(ParserUtil.parseDate(end));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + end);
        }
        return this;
    }

    /**
     * Sets the {@code String} description of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
