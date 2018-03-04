package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Math.round;
import static java.lang.String.format;

public class ShipActivity extends AppCompatActivity {

    private User user;
    private List<Ship> ShipListReceive;

    private TextView TextViewMetal;
    private TextView TextViewDeut;
    private ListView listViewConstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);

        TextViewMetal = findViewById(R.id.textViewMetalID);
        TextViewDeut = findViewById(R.id.textViewDeutID);
        listViewConstruction = findViewById(R.id.ListViewConstructionID);

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
                        TextViewDeut.setText(format("%,d", round(user.getGas())));
                        TextViewMetal.setText(format("%,d", round(user.getMinerals())));
                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {
                    Constant.ToastErrorConnection(getApplicationContext());
                }

            });
        }

        // GET SHIPS
        //

        Retrofit retrofit = Constant.retrofit;

        ApiService service = retrofit.create(ApiService.class);

        Call<GetShipsResponse> request = service.getShips(userToken);

        request.enqueue(new Callback<GetShipsResponse>() {
            @Override
            public void onResponse(Call<GetShipsResponse> call, Response<GetShipsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    ShipListReceive = (List<Ship>)response.body().getShips();

                    ShipAdapter adapter = new ShipAdapter(ShipActivity.this, ShipListReceive, user);
                    // TODO CLICK BUTTON
//                    adapter.setOnItemClickListener(BuildingActivity.this);

                    listViewConstruction.setAdapter(adapter);

                    // TODO : set user In DB
                    //
                }
            }

            @Override
            public void onFailure(Call<GetShipsResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });

    }
}