package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.AddressBookBuilder.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.JOHN;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.EventAliasMatchesPredicate;
import seedu.address.ui.DisplayList;

public class FindContactByEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EventAliasMatchesPredicate firstPredicate =
                new EventAliasMatchesPredicate(new EventAlias("first"));
        EventAliasMatchesPredicate secondPredicate =
                new EventAliasMatchesPredicate(new EventAlias("second"));

        EventAlias firstAlias = new EventAlias("first");
        EventAlias secondAlias = new EventAlias("second");

        FindContactByEventCommand findFirstCommand = new FindContactByEventCommand(firstPredicate, firstAlias);
        FindContactByEventCommand findSecondCommand = new FindContactByEventCommand(secondPredicate, secondAlias);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindContactByEventCommand findFirstCommandCopy = new FindContactByEventCommand(firstPredicate, firstAlias);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different alias -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noMatch_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EventAliasMatchesPredicate predicate = preparePredicate("TECHCONF24");
        FindContactByEventCommand command = new FindContactByEventCommand(predicate, new EventAlias("TECHCONF24"));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, DisplayList.PERSON);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_match_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        EventAliasMatchesPredicate predicate = preparePredicate("TSC2025");
        FindContactByEventCommand command = new FindContactByEventCommand(predicate, new EventAlias("TSC2025"));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, DisplayList.PERSON);
        assertEquals(Collections.singletonList(JOHN), model.getFilteredPersonList());
    }


    @Test
    public void execute_match_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        EventAliasMatchesPredicate predicate = preparePredicate("MEET24");
        FindContactByEventCommand command = new FindContactByEventCommand(predicate, new EventAlias("MEET24"));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, DisplayList.PERSON);
        assertEquals(Arrays.asList(ELLE, FIONA), model.getFilteredPersonList());
    }


    /**
     * Parses {@code userInput} into a {@code EventAliasMatchesPredicate}.
     */
    private EventAliasMatchesPredicate preparePredicate(String userInput) {
        return new EventAliasMatchesPredicate(new EventAlias(userInput));
    }

}
