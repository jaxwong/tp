package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LinkEventCommand.
 */
public class LinkEventCommandTest {
    private Model model;
    private Model expectedModel;
    private Event testEvent;
    private EventAlias testEventAlias;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        testEvent = new EventBuilder().withAlias("TSC2025").build();
        testEventAlias = testEvent.getEventAlias();

        model.addEvent(testEvent);
        expectedModel.addEvent(testEvent);
    }

    @Test
    public void execute_validSingleIndexValidEvent_success() {
        LinkEventCommand linkCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON),
                testEventAlias);

        Person personToLink = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person linkedPerson = new Person(
                personToLink.getName(),
                personToLink.getPhone(),
                personToLink.getEmail(),
                personToLink.getAddress(),
                personToLink.getTags(),
                testEvent.getEventAlias()
        );
        expectedModel.setPerson(personToLink, linkedPerson);

        String expectedMessage = String.format(
                LinkEventCommand.MESSAGE_SUCCESS,
                1,
                testEvent.getEventAlias());

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexesValidEvent_success() {
        LinkEventCommand linkCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                testEventAlias);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        Person linkedFirstPerson = new Person(
                firstPerson.getName(),
                firstPerson.getPhone(),
                firstPerson.getEmail(),
                firstPerson.getAddress(),
                firstPerson.getTags(),
                testEvent.getEventAlias()
        );
        Person linkedSecondPerson = new Person(
                secondPerson.getName(),
                secondPerson.getPhone(),
                secondPerson.getEmail(),
                secondPerson.getAddress(),
                secondPerson.getTags(),
                testEvent.getEventAlias()
        );

        expectedModel.setPerson(firstPerson, linkedFirstPerson);
        expectedModel.setPerson(secondPerson, linkedSecondPerson);

        String expectedMessage = String.format(
                LinkEventCommand.MESSAGE_SUCCESS,
                2,
                testEvent.getEventAlias());

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LinkEventCommand linkCommand = new LinkEventCommand(
                Arrays.asList(outOfBoundIndex),
                testEventAlias);

        assertThrows(CommandException.class, () -> linkCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleIndexesWithOneInvalid_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LinkEventCommand linkCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex),
                testEventAlias);

        assertThrows(CommandException.class, () -> linkCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        EventAlias nonExistentAlias = new EventAlias("NONEXISTENT");
        LinkEventCommand linkCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON),
                nonExistentAlias);

        assertThrows(CommandException.class, () -> linkCommand.execute(model),
                LinkEventCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void execute_caseInsensitiveEventAlias_success() {
        // Test with different case
        EventAlias lowerCaseAlias = new EventAlias("tsc2025");
        LinkEventCommand linkCommand = new LinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON),
                lowerCaseAlias);

        Person personToLink = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person linkedPerson = new Person(
                personToLink.getName(),
                personToLink.getPhone(),
                personToLink.getEmail(),
                personToLink.getAddress(),
                personToLink.getTags(),
                testEvent.getEventAlias()
        );
        expectedModel.setPerson(personToLink, linkedPerson);

        String expectedMessage = String.format(
                LinkEventCommand.MESSAGE_SUCCESS,
                1,
                testEvent.getEventAlias());

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }



}
