package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATETIME;
import static seedu.address.testutil.TypicalEvents.CONCERT;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.event.EventName;
import seedu.address.testutil.EventBuilder;



public class AddEventCommandParserTest {
    private static final AddEventCommandParser parser = new AddEventCommandParser();

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final String INVALID_ALIAS = "a++";
    private static final String INVALID_NAME = "c++";
    private static final String INVALID_START = "2024-12-12 24:24";
    private static final String INVALID_END = "2024-12-12 24:24";

    private static final String VALID_ALIAS = CONCERT.getAlias();
    private static final String VALID_NAME = CONCERT.getName();
    private static final String VALID_START = CONCERT.getStart().format(formatter);
    private static final String VALID_END = CONCERT.getEnd().format(formatter);
    private static final String VALID_DESC = CONCERT.getDescription();

    private static final String EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME + VALID_NAME;
    private static final String EVENT_ALIAS_DESC = " " + PREFIX_EVENT_ALIAS + VALID_ALIAS;
    private static final String START_DATE_DESC = " " + PREFIX_START + VALID_START;
    private static final String END_DATE_DESC = " " + PREFIX_END + VALID_END;
    private static final String DESCRIPTION_DESC = " " + PREFIX_DESC + VALID_DESC;


    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(CONCERT).build();
        AddEventCommand command = new AddEventCommand(expectedEvent);

        String userInput = EVENT_NAME_DESC
                + EVENT_ALIAS_DESC
                + START_DATE_DESC
                + END_DATE_DESC
                + DESCRIPTION_DESC;


        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_repeatedNonAliasValue_failure() {
        // repeated event name
        String userInput = EVENT_NAME_DESC + EVENT_NAME_DESC + EVENT_ALIAS_DESC
                + START_DATE_DESC + END_DATE_DESC + DESCRIPTION_DESC;

        assertParseFailure(parser, userInput,
                Messages.MESSAGE_DUPLICATE_FIELDS + "en/");
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = "Invalid command format! \n" + AddEventCommand.MESSAGE_USAGE;

        // missing name
        String userInput1 = EVENT_ALIAS_DESC + START_DATE_DESC + END_DATE_DESC + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput1, expectedMessage);

        // missing alias
        String userInput2 = EVENT_NAME_DESC + START_DATE_DESC + END_DATE_DESC + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput2, expectedMessage);


        // missing start
        String userInput3 = EVENT_NAME_DESC + EVENT_ALIAS_DESC + END_DATE_DESC + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput3, expectedMessage);

        // missing end
        String userInput4 = EVENT_NAME_DESC + EVENT_ALIAS_DESC + START_DATE_DESC + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput4, expectedMessage);

        // missing description
        String userInput5 = EVENT_NAME_DESC + EVENT_ALIAS_DESC + START_DATE_DESC + END_DATE_DESC;
        assertParseFailure(parser, userInput5, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String dateConstraints = MESSAGE_INVALID_DATETIME;
        // invalid alias
        String userInput1 = EVENT_NAME_DESC + " " + PREFIX_EVENT_ALIAS + INVALID_ALIAS
                + START_DATE_DESC + END_DATE_DESC + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput1, EventAlias.MESSAGE_CONSTRAINTS);

        // invalid name
        String userInput2 = " " + PREFIX_EVENT_NAME + INVALID_NAME + EVENT_ALIAS_DESC
                + START_DATE_DESC + END_DATE_DESC + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput2, EventName.MESSAGE_CONSTRAINTS);

        // invalid start datetime
        String userInput3 = EVENT_NAME_DESC + EVENT_ALIAS_DESC + " " + PREFIX_START
                + INVALID_START + END_DATE_DESC + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput3, dateConstraints);

        // invalid end datetime
        String userInput4 = EVENT_NAME_DESC + EVENT_ALIAS_DESC + START_DATE_DESC
                + " " + PREFIX_END + INVALID_END + DESCRIPTION_DESC;
        assertParseFailure(parser, userInput4, dateConstraints);
    }
}

