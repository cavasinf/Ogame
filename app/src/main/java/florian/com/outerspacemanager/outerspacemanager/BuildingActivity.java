package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Math.round;
import static java.lang.String.format;

public class BuildingActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        // GET USER FILLED
        //

        Intent intent = getIntent();
        final User oldUser = (User) intent.getSerializableExtra(Constant.EXTRA_USER);

        SharedPreferences settings = getSharedPreferences(Constant.PREFS_USER, 0);
        final String userToken = settings.getString("userToken", "");
        if (userToken != "") {

            Retrofit retrofit = Constant.retrofit;

            ApiService service = retrofit.create(ApiService.class);

            Call<GetUserResponse> request = service.getUser(userToken);

            request.enqueue(new Callback<GetUserResponse>() {
                @Override
                public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                    if (response.code() > 199 && response.code() < 301) {
                        user = new User(oldUser, userToken, response.body().getGas(), response.body().getGasModifier(), response.body().getMinerals(), response.body().getMineralsModifier(), response.body().getPoints());
                        // TODO : set user In DB
                        //
                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {
                    Constant.ToastErrorConnection(getApplicationContext());
                }

            });

        }

        // GET BUILDINGS
        //

        Retrofit retrofit = Constant.retrofit;

        ApiService service = retrofit.create(ApiService.class);

        Call<GetBuildingsResponse> request = service.getBuildings(userToken);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    for (Building building:response.body().getBuildings()) {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutMainID);

                        TextView textViewNameID = new TextView(getApplicationContext());
                        textViewNameID.setId(R.id.textViewBuildingNameID);
                        textViewNameID.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
                        textViewNameID.setText(building.getName());
                        textViewNameID.setPadding(20, 20, 20, 20);
                        textViewNameID.setTextColor(Color.argb(255, 255, 255, 255));
                        linearLayout.addView(textViewNameID);
                    }

                    // TODO : set user In DB
                    //
                }
            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });

        // GET USER BUILDING
        //

       /* Retrofit retrofit = Constant.retrofit;

        ApiService service = retrofit.create(ApiService.class);

        Call<GetUserResponse> request = service.getUserBuildings(user.getToken());

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.code() > 199 && response.code() < 301) {

                    // TODO : set user In DB
                    //
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });*/
    }
}
