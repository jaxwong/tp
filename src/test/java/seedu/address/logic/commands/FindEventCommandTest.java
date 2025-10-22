package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.AddressBookBuilder.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.CONCERT;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.AliasContainsKeywordsPredicate;
import seedu.address.ui.DisplayList;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AliasContainsKeywordsPredicate firstPredicate =
                new AliasContainsKeywordsPredicate(Collections.singletonList("first"));
        AliasContainsKeywordsPredicate secondPredicate =
                new AliasContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        AliasContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, DisplayList.EVENT);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_noMatch_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        AliasContainsKeywordsPredicate predicate = preparePredicate("25 day tsc2025a");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, DisplayList.EVENT);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        AliasContainsKeywordsPredicate predicate = preparePredicate("tsc2025 JAVA2 MEEt24");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, DisplayList.EVENT);
        assertEquals(Arrays.asList(CONCERT, MEETING, WORKSHOP), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        AliasContainsKeywordsPredicate predicate = new AliasContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindEventCommand findEventCommand = new FindEventCommand(predicate);
        String expected = FindEventCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findEventCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code AliasContainsKeywordsPredicate}.
     */
    private AliasContainsKeywordsPredicate preparePredicate(String userInput) {
        return new AliasContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
