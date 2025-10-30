package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.CONCERT;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

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


    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(CONCERT).build();
        AddEventCommand command = new AddEventCommand(expectedEvent);

        String userInput = " en/Taylor Swift Concert"
                + " ea/TSC2025"
                + " s/2025-09-19 19:30"
                + " e/2025-09-19 23:30"
                + " d/Taylor's Swift Eras tour";

        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_repeatedNonAliasValue_failure() {
        // repeated event name
        String userInput = " en/" + VALID_NAME + " en/" + VALID_NAME
                + " ea/" + VALID_ALIAS + " s/" + VALID_START
                + " e/" + VALID_END + " d/" + VALID_DESC;

        assertParseFailure(parser, userInput,
                "Multiple values specified for the following single-valued field(s): en/");
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = "Invalid command format! \n" + AddEventCommand.MESSAGE_USAGE;

        // missing name
        String userInput1 = "ea/" + VALID_ALIAS + " s/" + VALID_START + " e/" + VALID_END + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput1, expectedMessage);

        // missing alias
        String userInput2 = "en/" + VALID_NAME + " s/" + VALID_START + " e/" + VALID_END + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput2, expectedMessage);


        // missing start
        String userInput3 = "en/" + VALID_NAME + " ea/" + VALID_ALIAS + " e/" + VALID_END + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput3, expectedMessage);

        // missing end
        String userInput4 = "en/" + VALID_NAME + " ea/" + VALID_ALIAS + " s/" + VALID_START + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput4, expectedMessage);

        // missing description
        String userInput5 = "en/" + VALID_NAME + " ea/" + VALID_ALIAS + " s/" + VALID_START + " e/" + VALID_END;
        assertParseFailure(parser, userInput5, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String dateConstraints = "Invalid date format. Expected format: yyyy-MM-dd HH:mm";
        // invalid alias
        String userInput1 = " en/" + VALID_NAME + " ea/" + INVALID_ALIAS
                + " s/" + VALID_START + " e/" + VALID_END + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput1, EventAlias.MESSAGE_CONSTRAINTS);

        // invalid name
        String userInput2 = " en/" + INVALID_NAME + " ea/" + VALID_ALIAS
                + " s/" + VALID_START + " e/" + VALID_END + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput2, EventName.MESSAGE_CONSTRAINTS);

        // invalid start datetime
        String userInput3 = " en/" + VALID_NAME + " ea/" + VALID_ALIAS
                + " s/" + INVALID_START + " e/" + VALID_END + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput3, dateConstraints);

        // invalid end datetime
        String userInput4 = " en/" + VALID_NAME + " ea/" + VALID_ALIAS
                + " s/" + VALID_START + " e/" + INVALID_END + " d/" + VALID_DESC;
        assertParseFailure(parser, userInput4, dateConstraints);
    }
}

