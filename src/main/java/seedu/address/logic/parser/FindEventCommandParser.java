package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;

import java.util.Arrays;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.AliasContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindEventCommandParser implements Parser<FindEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns a FindEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ALIAS);

        if (!argMultimap.getPreamble().isEmpty()
                || !argMultimap.getValue(PREFIX_EVENT_ALIAS).isPresent()
                || argMultimap.getValue(PREFIX_EVENT_ALIAS).get().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ALIAS);

        String[] aliasKeywords = argMultimap.getValue(PREFIX_EVENT_ALIAS).get().trim().split("\\s+");

        return new FindEventCommand(new AliasContainsKeywordsPredicate(Arrays.asList(aliasKeywords)));
    }
}
