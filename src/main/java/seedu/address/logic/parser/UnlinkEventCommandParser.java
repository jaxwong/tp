package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnlinkEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LinkEventCommand object
 */
public class UnlinkEventCommandParser implements Parser<UnlinkEventCommand> {

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

        List<Index> indexes = parseIndexes(argMultimap.getPreamble());

        return new UnlinkEventCommand(indexes);
    }

    /**
     * Parses a string containing multiple space-separated indices into a List of Index objects
     * @param preamble The preamble string containing the indices
     * @return List of parsed Index objects
     * @throws ParseException if any index is invalid
     */
    private List<Index> parseIndexes(String preamble) throws ParseException {
        String trimmedPreamble = preamble.trim();
        String[] indexStrings = trimmedPreamble.split("\\s+");
        List<Index> indexes = new ArrayList<>();

        for (String indexString : indexStrings) {
            indexes.add(ParserUtil.parseIndex(indexString));
        }

        return indexes;
    }
}
