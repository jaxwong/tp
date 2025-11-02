package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATETIME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.model.event.EventAlias;
import seedu.address.model.event.EventName;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String VALID_EVENT_ALIAS = "TC2025";
    private static final String VALID_EVENT_NAME = "Tech Conference 2025";
    private static final String VALID_START_DATE = "2025-09-19 18:30";
    private static final String VALID_END_DATE = "2025-09-19 22:00";
    private static final String VALID_DESCRIPTION = "Annual tech conference";

    private static final String EVENT_ALIAS_DESC = " " + PREFIX_EVENT_ALIAS + VALID_EVENT_ALIAS;
    private static final String EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME;
    private static final String START_DATE_DESC = " " + PREFIX_START + VALID_START_DATE;
    private static final String END_DATE_DESC = " " + PREFIX_END + VALID_END_DATE;
    private static final String DESCRIPTION_DESC = " " + PREFIX_DESC + VALID_DESCRIPTION;

    private static final String INVALID_EVENT_ALIAS_DESC = " " + PREFIX_EVENT_ALIAS + "TC@2025"; // '@' not allowed
    private static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME + "T@Conference"; // '@' not allowed
    private static final String INVALID_START_DATE_DESC = " " + PREFIX_START + "invalid-date";
    private static final String INVALID_END_DATE_DESC = " " + PREFIX_END + "invalid-date";

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingEventAlias_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

        // missing event alias prefix
        assertParseFailure(parser, EVENT_NAME_DESC + START_DATE_DESC + END_DATE_DESC + DESCRIPTION_DESC,
                expectedMessage);

        // empty arguments
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidEventAlias_failure() {
        assertParseFailure(parser,
                INVALID_EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + START_DATE_DESC
                        + END_DATE_DESC
                        + DESCRIPTION_DESC,
                EventAlias.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidEventName_failure() {
        assertParseFailure(parser,
                EVENT_ALIAS_DESC
                        + INVALID_EVENT_NAME_DESC
                        + START_DATE_DESC
                        + END_DATE_DESC
                        + DESCRIPTION_DESC,
                EventName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStartDate_failure() {
        assertParseFailure(parser,
                EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + INVALID_START_DATE_DESC
                        + END_DATE_DESC
                        + DESCRIPTION_DESC,
                MESSAGE_INVALID_DATETIME);
    }

    @Test
    public void parse_invalidEndDate_failure() {
        assertParseFailure(parser,
                EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + START_DATE_DESC
                        + INVALID_END_DATE_DESC
                        + DESCRIPTION_DESC,
                MESSAGE_INVALID_DATETIME);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String validExpectedEventString = EVENT_ALIAS_DESC
                + EVENT_NAME_DESC
                + START_DATE_DESC
                + END_DATE_DESC
                + DESCRIPTION_DESC;

        // multiple event aliases
        assertParseFailure(parser, EVENT_ALIAS_DESC
                        + " "
                        + PREFIX_EVENT_ALIAS
                        + "OTHER2025"
                        + EVENT_NAME_DESC
                        + START_DATE_DESC
                        + END_DATE_DESC
                        + DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_ALIAS));

        // multiple event names
        assertParseFailure(parser,
                EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + " "
                        + PREFIX_EVENT_NAME
                        + "Other Event"
                        + START_DATE_DESC
                        + END_DATE_DESC
                        + DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME));

        // multiple start dates
        assertParseFailure(parser,
                EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + START_DATE_DESC
                        + " "
                        + PREFIX_START
                        + "2025-09-20 19:00"
                        + END_DATE_DESC
                        + DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START));

        // multiple end dates
        assertParseFailure(parser,
                EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + START_DATE_DESC
                        + END_DATE_DESC
                        + " "
                        + PREFIX_END
                        + "2025-09-20 23:00"
                        + DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END));

        // multiple descriptions
        assertParseFailure(parser,
                EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + START_DATE_DESC
                        + END_DATE_DESC
                        + DESCRIPTION_DESC
                        + " "
                        + PREFIX_DESC
                        + "Other description",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        assertParseFailure(parser, EVENT_ALIAS_DESC, EditEventCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_validArgs_success() {
        // edit event name only
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME).build();
        EditEventCommand expectedCommand = new EditEventCommand(new EventAlias(VALID_EVENT_ALIAS), descriptor);
        assertParseSuccess(parser, EVENT_ALIAS_DESC + EVENT_NAME_DESC, expectedCommand);

        // edit start date only
        descriptor = new EditEventDescriptorBuilder()
                .withStart(VALID_START_DATE).build();
        expectedCommand = new EditEventCommand(new EventAlias(VALID_EVENT_ALIAS), descriptor);
        assertParseSuccess(parser, EVENT_ALIAS_DESC + START_DATE_DESC, expectedCommand);

        // edit end date only
        descriptor = new EditEventDescriptorBuilder()
                .withEnd(VALID_END_DATE).build();
        expectedCommand = new EditEventCommand(new EventAlias(VALID_EVENT_ALIAS), descriptor);
        assertParseSuccess(parser, EVENT_ALIAS_DESC + END_DATE_DESC, expectedCommand);

        // edit description only
        descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION).build();
        expectedCommand = new EditEventCommand(new EventAlias(VALID_EVENT_ALIAS), descriptor);
        assertParseSuccess(parser, EVENT_ALIAS_DESC + DESCRIPTION_DESC, expectedCommand);

        // edit all fields
        descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME)
                .withStart(VALID_START_DATE)
                .withEnd(VALID_END_DATE)
                .withDescription(VALID_DESCRIPTION).build();
        expectedCommand = new EditEventCommand(new EventAlias(VALID_EVENT_ALIAS), descriptor);
        assertParseSuccess(parser,
                EVENT_ALIAS_DESC
                        + EVENT_NAME_DESC
                        + START_DATE_DESC
                        + END_DATE_DESC
                        + DESCRIPTION_DESC, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyPreamble_success() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME).build();
        EditEventCommand expectedCommand = new EditEventCommand(new EventAlias(VALID_EVENT_ALIAS), descriptor);
        // whitespace only preamble should be accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_ALIAS_DESC + EVENT_NAME_DESC, expectedCommand);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

        // non-empty preamble should be rejected
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_ALIAS_DESC + EVENT_NAME_DESC, expectedMessage);

        // gibberish preamble should be rejected
        assertParseFailure(parser, "wbeciwbeichowevowowovwe" + EVENT_ALIAS_DESC + EVENT_NAME_DESC, expectedMessage);

        // single word preamble should be rejected
        assertParseFailure(parser, "gibberish" + EVENT_ALIAS_DESC + EVENT_NAME_DESC, expectedMessage);
    }
}
