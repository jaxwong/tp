package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.logic.commands.EditEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventCommand.EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventCommand.EditEventDescriptor();
    }

    /**
     * Returns an {@code EditEventDescriptor} that is a copy of the given {@code descriptor}.
     */
    public EditEventDescriptorBuilder(EditEventCommand.EditEventDescriptor descriptor) {
        this.descriptor = new EditEventCommand.EditEventDescriptor();
        this.descriptor.setEventName(descriptor.getEventName().orElse(null));
        this.descriptor.setStart(descriptor.getStart().orElse(null));
        this.descriptor.setEnd(descriptor.getEnd().orElse(null));
        this.descriptor.setDescription(descriptor.getDescription().orElse(null));
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details.
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setEventName(event.getEventName());
        descriptor.setStart(event.getStart());
        descriptor.setEnd(event.getEnd());
        descriptor.setDescription(event.getDescription());
    }

    /**
     * Sets the {@code eventName} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventName(String eventName) {
        descriptor.setEventName(new EventName(eventName));
        return this;
    }

    /**
     * Sets the {@code start} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStart(LocalDateTime start) {
        descriptor.setStart(start);
        return this;
    }

    /**
     * Sets the {@code end} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEnd(LocalDateTime end) {
        descriptor.setEnd(end);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }


    public EditEventCommand.EditEventDescriptor build() {
        return descriptor;
    }
}
