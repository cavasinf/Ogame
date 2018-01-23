package florian.com.outerspacemanager.outerspacemanager;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by fcavasin on 16/01/2018.
 */

public interface ApiService {
    @POST("auth/create")
    Call<CreateUserResponse> createUser(@Body User user);
}
