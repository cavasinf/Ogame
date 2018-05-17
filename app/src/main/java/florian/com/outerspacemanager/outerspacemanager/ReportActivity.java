package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Math.round;
import static java.lang.String.format;

public class ReportActivity extends AppCompatActivity {

    //TODO : Report activity + adapter x  2 ?

    private User user;
    private Retrofit retrofit = Constant.retrofit;
    final ApiService service = retrofit.create(ApiService.class);
    private List<Building> BuildingListReceive;

    private TextView TextViewMetal;
    private TextView TextViewDeut;
    private ListView listViewConstruction;

    private Date currentDate;
    private List<BuildingStatus> listBuildingStatus = new ArrayList<BuildingStatus>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

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

        // GET BUILDINGS
        //

        Call<GetBuildingsResponse> request = service.getUserBuildings(userToken);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    BuildingListReceive = (List<Building>) response.body().getBuildings();
                    currentDate = Calendar.getInstance().getTime();


                    DAOBuildingStatus daoBuildingStatus = new DAOBuildingStatus(getApplicationContext());
                    Environment.getExternalStorageDirectory();
                    daoBuildingStatus.open();
                    listBuildingStatus = daoBuildingStatus.getAllBuildingStatus();

                    List<BuildingStatus> listOfBuildingStatusToRemove = new ArrayList<>();

                    // open building DB
                    DAOBuilding daoBuilding = new DAOBuilding(getApplicationContext());
                    daoBuilding.open();
                    // clear all buildings DB
                    daoBuilding.deleteAllBuildings();

                    for (Building building : BuildingListReceive) {
                        // add building to DB
                        daoBuilding.createBuilding(building.getBuildingId(),building.getLevel(),building.getAmountOfEffectByLevel(),building.getAmountOfEffectLevel0(),building.isBuilding(),building.getEffect(),building.getGasCostByLevel(),building.getGasCostLevel0(),building.getImageUrl(),building.getMineralCostByLevel(),building.getMineralCostLevel0(),building.getName(),building.getTimeToBuildByLevel(),building.getTimeToBuildLevel0());

                        //Clear building construction in DB
                        for (BuildingStatus buildingStatus : listBuildingStatus) {
                            // if building in database and construction is done
                            if (buildingStatus.getBuildingId() != null) {
                                if (Objects.equals(building.getBuildingId().toString(), buildingStatus.getBuildingId())) {
                                    int currentTime = (int) (new Date().getTime() / 1000);
                                    if (currentTime - Integer.parseInt(buildingStatus.getDateConstruction()) > building.getTimeToBuild(false)) {
                                        if (!daoBuildingStatus.deleteBuildingState(building.getBuildingId()))
                                            Constant.displayToast(getApplicationContext(), "Error when delete in database");
                                        listOfBuildingStatusToRemove.add(buildingStatus);
                                    }
                                }
                            }
                        }
                    }
                    for (BuildingStatus buildingStatus : listOfBuildingStatusToRemove) {
                        listBuildingStatus.remove(buildingStatus);
                    }

                    final BuildingAdapter adapter = new BuildingAdapter(ReportActivity.this, BuildingListReceive, user, currentDate, listBuildingStatus);


                    // CLICK on item
                    adapter.setOnEventListener(new OnListViewChildrenClick() {
                        @Override
                        public void OnClick(final int id, View v) {
                            if (v.isEnabled()) {

                                Call<CreateBuildingsResponse> requestCreateBuilding = service.createBuildings(userToken, id);

                                requestCreateBuilding.enqueue(new Callback<CreateBuildingsResponse>() {
                                    @Override
                                    public void onResponse(Call<CreateBuildingsResponse> call, Response<CreateBuildingsResponse> response) {
                                        if (response.code() > 199 && response.code() < 301) {

                                            DAOBuildingStatus daoBuildingStatus = new DAOBuildingStatus(getApplicationContext());
                                            daoBuildingStatus.open();
                                            int currentTime = (int) (new Date().getTime() / 1000);
                                            daoBuildingStatus.createBuildingStatus(id, "true", String.valueOf(currentTime));

                                            refreshBuildingData(userToken, adapter);

                                            Toast toast = Toast.makeText(getApplicationContext(), "Construction lancée", Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CreateBuildingsResponse> call, Throwable t) {

                                    }
                                });

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Impossible de lancer une construction sur ce batiment", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
                    listViewConstruction.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });

    }

    private void refreshBuildingData(String userToken, final BuildingAdapter adapter) {
        Call<GetBuildingsResponse> request = service.getUserBuildings(userToken);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    adapter.clear();
                    adapter.addAll(response.body().getBuildings());
                }
            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {

            }
        });
    }

}