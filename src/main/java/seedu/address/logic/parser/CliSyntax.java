package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    // for contacts
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    // for events
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("en/");
    public static final Prefix PREFIX_EVENT_ALIAS = new Prefix("ea/");
    public static final Prefix PREFIX_START = new Prefix("st/");
    public static final Prefix PREFIX_END = new Prefix("et/");
    public static final Prefix PREFIX_DESC = new Prefix("d/");
    // for todos
    public static final Prefix PREFIX_TODO_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_TODO_DESCRIPTION = new Prefix("td/");
}
