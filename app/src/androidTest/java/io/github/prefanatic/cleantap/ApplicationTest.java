package io.github.prefanatic.cleantap;

import android.app.Application;
import android.test.ApplicationTestCase;

import io.github.prefanatic.cleantap.data.Database;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import rx.schedulers.Schedulers;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        RxUntappdApi api = new RxUntappdApi();
        Database database = new Database(getContext());

        api.searchForBeers("All")
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .first()
                .subscribe(stats -> {
                    stats.id = stats.beer.bid;
                    System.out.println("Received " + stats.id);

                    database.getBeerStatsDao().addBeerStats(stats)
                            .subscribe(aLong -> {
                                System.out.println("Saved as " + aLong);

                                database.getBeerStatsDao().getStats()
                                        .flatMapIterable(list -> list)
                                        .subscribe(stat -> System.out.println("Reloaded " + stat.id));
                            });
                });
    }
}