package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;

import seedu.address.logic.commands.FindContactByEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.EventAliasMatchesPredicate;

/**
 * Parses input arguments and creates a new FindContactByEvent object
 */
public class FindContactByEventParser implements Parser<FindContactByEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindContactByEventCommand
     * and returns a FindContactByEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContactByEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ALIAS);

        if (!argMultimap.getValue(PREFIX_EVENT_ALIAS).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactByEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ALIAS);

        EventAlias alias = ParserUtil.parseEventAlias(argMultimap.getValue(PREFIX_EVENT_ALIAS).get());
        return new FindContactByEventCommand(new EventAliasMatchesPredicate(alias));

    }
}
