package io.github.prefanatic.cleantap.data;

import java.util.Map;

import io.github.prefanatic.cleantap.data.dto.ApiReturn;
import io.github.prefanatic.cleantap.data.dto.BeerInfoResponse;
import io.github.prefanatic.cleantap.data.dto.BeerSearchResponse;
import io.github.prefanatic.cleantap.data.dto.CheckInResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
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

    @FormUrlEncoded
    @POST("/v4/checkin/add")
    Observable<ApiReturn<CheckInResponse>> checkInBeer(
            @QueryMap Map<String, String> authOptions,
            @Field("gmt_offset") String gmtOffset,
            @Field("timezone") String timezone,
            @Field("bid") long beerId,
            @Field("foursquare_id") String foursquareId,
            @Field("geolat") Double latitude,
            @Field("geolng") Double longitude,
            @Field("shout") String shout,
            @Field("rating") String rating,
            @Field("facebook") String facebook,
            @Field("twitter") String twitter,
            @Field("foursquare") String foursquare
    );
}
