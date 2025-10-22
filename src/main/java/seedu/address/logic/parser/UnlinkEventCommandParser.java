package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnlinkEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LinkEventCommand object
 */
public class UnlinkEventCommandParser implements Parser<UnlinkEventCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the UnlinkEventCommand
     * and returns an UnlinkEventCommand object for execution
     * @throws ParseException if the user input does not confirm the expected format
     */
    @Override
    public UnlinkEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        // Ensure person index from the preamble is provided
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkEventCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        return new UnlinkEventCommand(index);
    }
}
