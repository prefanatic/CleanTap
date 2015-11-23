package io.github.prefanatic.cleantap.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import io.github.prefanatic.cleantap.data.dto.CheckInResponse;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxUntappdApi {
    private UntappdInterface api;
    private Map<String, String> authOptions = new HashMap<>();

    public RxUntappdApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.untappd.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        authOptions.put("client_id", "EFED9A1C92001BD10DEE9BA371181CBA0B0D831A");
        authOptions.put("client_secret", "EBEFFF7DE85FAFB4F7994A3059277A1859396818");

        api = retrofit.create(UntappdInterface.class);
    }

    public void setAuthToken(String authToken) {
        authOptions.clear();
        authOptions.put("access_token", authToken);
    }

    public Observable<BeerStats> searchForBeers(String beers) {
       return api.searchBeers(authOptions, beers, 0, 25, "checkin")
               .flatMapIterable(response -> response.response.beers.items)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeerExtended> getBeerInfo(long beerId) {
        return api.beerInfo(beerId, authOptions, "false")
                .doOnError(err -> Timber.e(err, "API Failure"))
                .map(response -> response.response.beer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CheckInResponse> checkinBeer(long beerId, String shout, float rating, Double geolat, Double geolng) {
        TimeZone zone = TimeZone.getDefault();

        return api.checkInBeer(authOptions, String.valueOf(zone.getRawOffset() / 3600000), zone.getID(), beerId, null, geolat, geolng, shout, String.format("%.2f", rating), null, null, null)
                .map(response -> response.response);
    }


}
