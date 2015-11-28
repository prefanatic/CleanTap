package io.github.prefanatic.cleantap;

import android.test.mock.MockContext;

import org.junit.Test;

import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.RxUntappdApi;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    RxUntappdApi api = new RxUntappdApi();
    Database database = new Database(new MockContext());

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        
    }
}