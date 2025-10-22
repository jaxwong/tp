package seedu.address.ui;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

/**
 * Utility class for JavaFX testing operations.
 */
public class JavaFxTestUtils {

    /**
     * Runs the given runnable on the JavaFX application thread and waits for completion.
     * This is useful for testing JavaFX operations that need to complete before assertions.
     * @param runnable the code to run on the JavaFX thread
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public static void runOnFxThreadAndWait(Runnable runnable) throws InterruptedException {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                try {
                    runnable.run();
                } finally {
                    latch.countDown();
                }
            });
            latch.await(5, TimeUnit.SECONDS);
        }
    }

    /**
     * Waits for JavaFX operations to complete by giving the platform time to process.
     * This is a simple delay-based approach for testing.
     * @param milliseconds the number of milliseconds to wait
     */
    public static void waitForFxOperations(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Waits for JavaFX operations to complete with a default delay.
     */
    public static void waitForFxOperations() {
        waitForFxOperations(100);
    }

    /**
     * Checks if the JavaFX platform is initialized and running.
     * @return true if the platform is initialized, false otherwise
     */
    public static boolean isJavaFxInitialized() {
        try {
            Platform.isFxApplicationThread();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }
}
