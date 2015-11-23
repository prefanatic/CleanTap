package io.github.prefanatic.cleantap;

import org.junit.Test;

import java.util.TimeZone;

import io.github.prefanatic.cleantap.data.RxUntappdApi;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    RxUntappdApi api = new RxUntappdApi();

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        TimeZone zone = TimeZone.getDefault();

        System.out.println(zone.getRawOffset());
    }
}