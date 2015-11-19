package io.github.prefanatic.cleantap.data;

import java.util.Map;

import io.github.prefanatic.cleantap.data.dto.ApiReturn;
import io.github.prefanatic.cleantap.data.dto.BeerInfoResponse;
import io.github.prefanatic.cleantap.data.dto.BeerSearchResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

public interface UntappdInterface {
    @GET("/v4/search/beer")
    Observable<ApiReturn<BeerSearchResponse>> searchBeers(
            @QueryMap Map<String, String> authOptions,
            @Query("q") String query,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("sort") String sort
    );

    @GET("/v4/beer/info/{beerId}")
    Observable<ApiReturn<BeerInfoResponse>> beerInfo(
            @Path("beerId") long beerId,
            @QueryMap Map<String, String> authOptions,
            @Query("compact") String compact
    );
}
