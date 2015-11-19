package io.github.prefanatic.cleantap;

import org.junit.Test;

import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import rx.functions.Action1;

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
        api.getBeerInfo(16630)
                .subscribe(new Action1<BeerExtended>() {
                    @Override
                    public void call(BeerExtended beerExtended) {
                        System.out.println(beerExtended.beer_name + " has a rating of " + beerExtended.rating_score);
                    }
                });
    }
}