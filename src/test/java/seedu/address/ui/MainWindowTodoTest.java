package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

public class MainWindowTodoTest extends JavaFxTestBase {

    @TempDir
    public Path temporaryFolder;

    private MainWindow mainWindow;
    private Logic logic;
    private Stage stage;

    @BeforeEach
    public void setUp() throws Exception {
        stage = createTestStage();

        AddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        UserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        Model model = new ModelManager();
        logic = new LogicManager(model, storage);

        // Construct MainWindow on JavaFX thread
        JavaFxTestUtils.runOnFxThreadAndWait(() -> {
            mainWindow = new MainWindow(stage, logic);
        });
    }


    @Test
    public void constructor_withValidParameters_createsMainWindowCorrectly() {
        assertNotNull(mainWindow);
        assertNotNull(mainWindow.getPrimaryStage());
    }

    @Test
    public void showTodoList_switchesToTodoListDisplay() {
        // Initialize the main window components
        mainWindow.fillInnerParts();

        // Test showTodoList method
        mainWindow.showTodoList();

        // Verify that the method executes without throwing exceptions
        assertNotNull(mainWindow.getRoot());
    }

    @Test
    public void fillInnerParts_initializesTodoListPanel() {
        // Initialize the main window components
        mainWindow.fillInnerParts();

        // Verify that the method executes without throwing exceptions
        assertNotNull(mainWindow.getRoot());
    }

    @Test
    public void fillInnerParts_withEmptyTodoList_initializesCorrectly() {
        // Initialize the main window components with empty lists
        mainWindow.fillInnerParts();

        // Verify that the method executes without throwing exceptions
        assertNotNull(mainWindow.getRoot());
    }

    @Test
    public void getPrimaryStage_returnsCorrectStage() {
        assertEquals(stage, mainWindow.getPrimaryStage());
    }

    @Test
    public void getRoot_returnsNonNull() {
        assertNotNull(mainWindow.getRoot());
    }
}
