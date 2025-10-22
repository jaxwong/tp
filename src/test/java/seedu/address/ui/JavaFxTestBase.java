package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Base class for JavaFX UI component tests.
 * Handles JavaFX platform initialization and cleanup.
 */
public abstract class JavaFxTestBase {

    private static boolean platformStarted = false;

    @BeforeAll
    public static void initializeJavaFX() {
        if (!platformStarted) {
            try {
                // Start JavaFX platform
                Platform.startup(() -> {});
                platformStarted = true;
            } catch (IllegalStateException e) {
                // Platform already started, ignore
                platformStarted = true;
            }
        }
    }

    @AfterAll
    public static void cleanupJavaFX() {
        // Note: We don't call Platform.exit() here as it would affect other tests
        // The platform will be cleaned up when the JVM exits
    }

    /**
     * Creates a test stage for UI component testing.
     * @return a new Stage instance
     */
    protected Stage createTestStage() {
        if (Platform.isFxApplicationThread()) {
            return new Stage();
        }

        final Stage[] stageHolder = new Stage[1];
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stageHolder[0] = new Stage();
            latch.countDown();
        });

        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return stageHolder[0];
    }


    /**
     * Runs the given runnable on the JavaFX application thread.
     * @param runnable the code to run
     */
    protected void runOnFxThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    /**
     * Waits for JavaFX operations to complete.
     * This is useful for testing asynchronous operations.
     */
    protected void waitForFxOperations() {
        try {
            Thread.sleep(100); // Give JavaFX time to process
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
