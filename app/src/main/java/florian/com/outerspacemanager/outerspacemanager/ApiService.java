package florian.com.outerspacemanager.outerspacemanager;

import okhttp3.RequestBody;
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
    Call<GetBuildingsResponse> getUserBuildings(@Header("x-access-token") String token);

    @POST("buildings/create/{buildingId}")
    Call<CreateBuildingsResponse> createBuildings(@Header("x-access-token") String token, @Path("buildingId") int id);

    // SPACE SHIP
    //
    @GET("fleet/list")
    Call<GetSpaceShipsResponse> getSpaceShip(@Header("x-access-token") String token);

    // SHIP
    //
    @GET("ships")
    Call<GetShipsResponse> getShips(@Header("x-access-token") String token);

    @POST("ships/create/{shipId}")
    Call<CreateShipsResponse> createShips(@Header("x-access-token") String token, @Path("shipId") int id, @Body RequestBody body);

    // SEARCH
    //
    @GET("searches/list")
    Call<GetSearchesResponse> getSearches(@Header("x-access-token") String token);

    @POST("searches/create/{searchId}")
    Call<CreateSearchResponse> launchSearch(@Header("x-access-token") String token, @Path("searchId") int id);

    // GALAXY
    //
    @GET("users/0/20")
    Call<GetGalaxyResponse> getGalaxy(@Header("x-access-token") String token);
    // TODO : galaxy 0 - 20 dynamic

    // GALAXY
    //
    @GET("reports/0/20")
    Call<GetReportResponse> getReports(@Header("x-access-token") String token);
    // TODO : report 0 - 20 dynamic

}
