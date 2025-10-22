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
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnlinkEventCommand.
 */
public class UnlinkEventCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validSingleIndex_success() {
        // Link person first
        Person personToUnlink = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person linkedPerson = new Person(
                personToUnlink.getName(),
                personToUnlink.getPhone(),
                personToUnlink.getEmail(),
                personToUnlink.getAddress(),
                personToUnlink.getTags(),
                new EventAlias("TestEvent")
        );
        model.setPerson(personToUnlink, linkedPerson);

        // Create unlink command
        UnlinkEventCommand unlinkCommand = new UnlinkEventCommand(Arrays.asList(INDEX_FIRST_PERSON));

        // Expected person after unlinking
        Person expectedPerson = new Person(
                linkedPerson.getName(),
                linkedPerson.getPhone(),
                linkedPerson.getEmail(),
                linkedPerson.getAddress(),
                linkedPerson.getTags(),
                null
        );
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), expectedPerson);

        String expectedMessage = String.format(UnlinkEventCommand.MESSAGE_SUCCESS, 1);

        assertCommandSuccess(unlinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexes_success() {
        // Link persons first
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        Person linkedFirstPerson = new Person(
                firstPerson.getName(),
                firstPerson.getPhone(),
                firstPerson.getEmail(),
                firstPerson.getAddress(),
                firstPerson.getTags(),
                new EventAlias("TestEvent1")
        );
        Person linkedSecondPerson = new Person(
                secondPerson.getName(),
                secondPerson.getPhone(),
                secondPerson.getEmail(),
                secondPerson.getAddress(),
                secondPerson.getTags(),
                new EventAlias("TestEvent2")
        );

        model.setPerson(firstPerson, linkedFirstPerson);
        model.setPerson(secondPerson, linkedSecondPerson);

        // Create unlink command for multiple persons
        UnlinkEventCommand unlinkCommand = new UnlinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));

        // Expected persons after unlinking
        Person unlinkedFirstPerson = new Person(
                linkedFirstPerson.getName(),
                linkedFirstPerson.getPhone(),
                linkedFirstPerson.getEmail(),
                linkedFirstPerson.getAddress(),
                linkedFirstPerson.getTags(),
                null
        );
        Person unlinkedSecondPerson = new Person(
                linkedSecondPerson.getName(),
                linkedSecondPerson.getPhone(),
                linkedSecondPerson.getEmail(),
                linkedSecondPerson.getAddress(),
                linkedSecondPerson.getTags(),
                null
        );

        expectedModel.setPerson(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                unlinkedFirstPerson);
        expectedModel.setPerson(
                model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()),
                unlinkedSecondPerson);

        String expectedMessage = String.format(UnlinkEventCommand.MESSAGE_SUCCESS, 2);

        assertCommandSuccess(unlinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnlinkEventCommand unlinkCommand = new UnlinkEventCommand(Arrays.asList(outOfBoundIndex));

        assertThrows(CommandException.class, () -> unlinkCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleIndexesWithOneInvalid_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnlinkEventCommand unlinkCommand = new UnlinkEventCommand(
                Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex));

        assertThrows(CommandException.class, () -> unlinkCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unlinkPersonWithNoEvent_success() {
        // Person already has no event linked
        UnlinkEventCommand unlinkCommand = new UnlinkEventCommand(Arrays.asList(INDEX_FIRST_PERSON));

        Person personToUnlink = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person unlinkedPerson = new Person(
                personToUnlink.getName(),
                personToUnlink.getPhone(),
                personToUnlink.getEmail(),
                personToUnlink.getAddress(),
                personToUnlink.getTags(),
                null
        );
        expectedModel.setPerson(personToUnlink, unlinkedPerson);

        String expectedMessage = String.format(UnlinkEventCommand.MESSAGE_SUCCESS, 1);

        assertCommandSuccess(unlinkCommand, model, expectedMessage, expectedModel);
    }


}
