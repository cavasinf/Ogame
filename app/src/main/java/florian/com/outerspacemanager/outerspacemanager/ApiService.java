package florian.com.outerspacemanager.outerspacemanager;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by fcavasin on 16/01/2018.
 */

public interface ApiService {
    @POST("auth/create")
    Call<CreateUserResponse> createUser(@Body User user);

    @POST("auth/login")
    Call<LoginUserResponse> loginUser(@Body User user);

    @GET("users/get")
    Call<GetUserResponse> getUser(@Header("x-access-token") String token);


}
