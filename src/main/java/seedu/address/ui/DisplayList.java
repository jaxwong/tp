package seedu.address.ui;

/**
 * Possible lists to be shown to the user
 */
public enum DisplayList {
    NO_CHANGE(""),
    CONTACT("contact"),
    EVENT("event");

    /** Description of the type of list **/
    private final String listType;

    DisplayList(String listType) {
        this.listType = listType;
    }

    @Override
    public String toString() {
        return listType;
    }
}
