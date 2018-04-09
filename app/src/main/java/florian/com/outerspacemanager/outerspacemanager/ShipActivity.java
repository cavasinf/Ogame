package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Math.round;
import static java.lang.String.format;

public class ShipActivity extends AppCompatActivity {

    private User user;
    private Retrofit retrofit = Constant.retrofit;
    final ApiService service = retrofit.create(ApiService.class);
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
        Call<GetShipsResponse> request = service.getShips(userToken);

        request.enqueue(new Callback<GetShipsResponse>() {
            @Override
            public void onResponse(Call<GetShipsResponse> call, Response<GetShipsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    ShipListReceive = (List<Ship>)response.body().getShips();

                    final ShipAdapter adapter = new ShipAdapter(ShipActivity.this, ShipListReceive, user);

                    // CLICK on item
                    adapter.setOnEventListener(new OnListViewShipChildrenClick() {
                        @Override
                        public void OnClick(final int id,String amount, View v) {
                            if (v.isEnabled()) {

                                JSONObject jsonParams = new JSONObject();
                                try {
                                    jsonParams.put("amount", amount);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonParams.toString());
                                Call<CreateShipsResponse> requestCreateShip = service.createShips(userToken, id, body);

                                requestCreateShip.enqueue(new Callback<CreateShipsResponse>() {
                                    @Override
                                    public void onResponse(Call<CreateShipsResponse> call, Response<CreateShipsResponse> response) {
                                        if (response.code() > 199 && response.code() < 301) {

                                            //TODO : current time building + queue ???

//                                            DAOBuildingStatus daoBuildingStatus = new DAOBuildingStatus(getApplicationContext());
//                                            daoBuildingStatus.open();
//                                            int currentTime = (int) (new Date().getTime() / 1000);
//                                            daoBuildingStatus.createBuildingStatus(id, "true", String.valueOf(currentTime));

                                            refreshShipData(userToken, adapter);

                                            Toast toast = Toast.makeText(getApplicationContext(), "Construction vaisseau lancée", Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CreateShipsResponse> call, Throwable t) {

                                    }
                                });

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Impossible de lancer une construction sur ce vaisseau", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });

                    listViewConstruction.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetShipsResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });

    }


    private void refreshShipData(String userToken, final ShipAdapter adapter) {
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