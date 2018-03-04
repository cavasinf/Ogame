package florian.com.outerspacemanager.outerspacemanager;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by fcavasin on 16/01/2018.
 */

public interface ApiService {

    // AUTH
    //

    @POST("auth/create")
    Call<CreateUserResponse> createUser(@Body User user);

    @POST("auth/login")
    Call<LoginUserResponse> loginUser(@Body User user);

    @GET("users/get")
    Call<GetUserResponse> getUser(@Header("x-access-token") String token);

    // BUILDING
    //

    @GET("buildings")
    Call<GetBuildingsResponse> getBuildings(@Header("x-access-token") String token);

    @GET("buildings/list")
    Call<GetUserResponse> getUserBuildings(@Header("x-access-token") String token);

    // SHIP
    //

    @GET("ships")
    Call<GetShipsResponse> getShips(@Header("x-access-token") String token);

    // SEARCH
    //
    @GET("searches/list")
    Call<GetSearchesResponse> getSearches(@Header("x-access-token") String token);

    // GALAXY
    //
    @GET("users/0/20")
    Call<GetGalaxyResponse> getGalaxy(@Header("x-access-token") String token);
}
