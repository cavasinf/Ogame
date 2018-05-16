package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Math.round;
import static java.lang.String.format;

public class SpaceShipActivity extends AppCompatActivity {

    private User user;
    private Retrofit retrofit = Constant.retrofit;
    final ApiService service = retrofit.create(ApiService.class);
    private List<Ship> ShipListReceive;
    private String playerToAttack = "";

    private TextView TextViewMetal;
    private TextView TextViewDeut;
    private GridView GridViewFleet;

    private TextView textViewPlayerToAttackID;
    private RelativeLayout RelativeLayoutAttackButtonID;

    public static ArrayMap<Integer, Integer> numberOfShipForAttack = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_ship);

        TextViewMetal = findViewById(R.id.textViewMetalID);
        TextViewDeut = findViewById(R.id.textViewDeutID);
        GridViewFleet = findViewById(R.id.GridViewFleetID);

        textViewPlayerToAttackID = findViewById(R.id.textViewPlayerToAttackID);
        RelativeLayoutAttackButtonID = findViewById(R.id.RelativeLayoutAttackButtonID);

        Intent intent = getIntent();

        if (intent.hasExtra(Constant.EXTRA_PLAYER_TO_ATTACK))
            playerToAttack = (String) intent.getSerializableExtra(Constant.EXTRA_PLAYER_TO_ATTACK);

        // GET USER FILLED
        //

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

        // PLAYER WILL ATTACK
        //

        if (!playerToAttack.equals("")){
            textViewPlayerToAttackID.setText(playerToAttack);

            RelativeLayoutAttackButtonID.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // Pressed
                        view.setSelected(true);

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        // Released
                        view.setSelected(false);
                    }
                    return true;
                }
            });
        }

        // GET SPACE SHIPS
        //
        Call<GetSpaceShipsResponse> request = service.getSpaceShip(userToken);

        request.enqueue(new Callback<GetSpaceShipsResponse>() {
            @Override
            public void onResponse(Call<GetSpaceShipsResponse> call, Response<GetSpaceShipsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    ShipListReceive = (List<Ship>)response.body().getShips();

                    final SpaceShipAdapter adapter = new SpaceShipAdapter(SpaceShipActivity.this, ShipListReceive, user);

                    // CLICK set max ship
                    adapter.setOnEventListener(new OnGridViewSpaceShipChildrenMaxClick() {
                        @Override
                        public void OnClick(final int amount, View v) {

                        }
                    });

                    GridViewFleet.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetSpaceShipsResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });

    }


    private void refreshSpaceShipData(String userToken, final ShipAdapter adapter) {
        Call<GetShipsResponse> request = service.getShips(userToken);

        request.enqueue(new Callback<GetShipsResponse>() {
            @Override
            public void onResponse(Call<GetShipsResponse> call, Response<GetShipsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    adapter.clear();
                    adapter.addAll(response.body().getShips());
                }
            }

            @Override
            public void onFailure(Call<GetShipsResponse> call, Throwable t) {

            }
        });
    }
}