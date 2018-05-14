package florian.com.outerspacemanager.outerspacemanager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static florian.com.outerspacemanager.outerspacemanager.Constant.userToken;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPseudonyme;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;

    private Retrofit retrofit = Constant.retrofit;
    final ApiService service = retrofit.create(ApiService.class);
    private List<Building> BuildingListReceive;
    private List<Search> SearchListReceive;

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Cela signifie que la permission à déjà été
                //demandé et l'utilisateur l'a refusé
            } else {
                //Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        editTextEmail = findViewById(R.id.editTextEmailID);
        editTextPseudonyme = findViewById(R.id.editTextPseudonymeID);
        editTextPassword = findViewById(R.id.editTextPasswordID);
        btnLogin = findViewById(R.id.buttonConnectID);
        btnRegister = findViewById(R.id.buttonRegisterID);

        //TEMPLATE CLICK BUTTON
        //

        /*btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );*/

        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final User user = new User(editTextPseudonyme.getText().toString(), editTextPassword.getText().toString());

                        Retrofit retrofit = Constant.retrofit;

                        final ApiService service = retrofit.create(ApiService.class);

                        Call<LoginUserResponse> request = service.loginUser(user);

                        request.enqueue(new Callback<LoginUserResponse>() {
                            @Override
                            public void onResponse(Call<LoginUserResponse> call, final Response<LoginUserResponse> response) {
                                if (response.code() > 199 && response.code() < 301) {
                                    SharedPreferences settings = getSharedPreferences(Constant.PREFS_USER, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("userToken", response.body().getToken());
                                    userToken = response.body().getToken();
                                    editor.commit();
                                    final Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    intent.putExtra(Constant.EXTRA_USER, user);
                                    Constant.User = user;

                                    // ============ CHECK FOR DB INFO ============
                                    // open building DB
                                    final DAOBuilding daoBuilding = new DAOBuilding(getApplicationContext());
                                    daoBuilding.open();

                                    if (daoBuilding.getNumberOfRows() == 0) {
                                        //There is no Buildings in the DB
                                        //Need to populate DB for later

                                        // GET BUILDINGS
                                        //
                                        Call<GetBuildingsResponse> requestBuildings = service.getUserBuildings(userToken);

                                        requestBuildings.enqueue(new Callback<GetBuildingsResponse>() {
                                            @Override
                                            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                                                if (response.code() > 199 && response.code() < 301) {
                                                    BuildingListReceive = (List<Building>) response.body().getBuildings();
                                                    for (Building building : BuildingListReceive) {
                                                        // add building to DB
                                                        daoBuilding.createBuilding(building.getBuildingId(), building.getLevel(), building.getAmountOfEffectByLevel(), building.getAmountOfEffectLevel0(), building.isBuilding(), building.getEffect(), building.getGasCostByLevel(), building.getGasCostLevel0(), building.getImageUrl(), building.getMineralCostByLevel(), building.getMineralCostLevel0(), building.getName(), building.getTimeToBuildByLevel(), building.getTimeToBuildLevel0());
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {

                                            }
                                        });
                                    }
                                        // open search DB
                                        final DAOSearch daoSearch = new DAOSearch(getApplicationContext());
                                        daoSearch.open();

                                        if (daoSearch.getNumberOfRows() == 0) {
                                            //There is no Searches in the DB
                                            //Need to populate DB for later

                                            // GET Searches
                                            //
                                            Call<GetSearchesResponse> request = service.getSearches(userToken);

                                            request.enqueue(new Callback<GetSearchesResponse>() {
                                                @Override
                                                public void onResponse(Call<GetSearchesResponse> call, Response<GetSearchesResponse> response) {
                                                    if (response.code() > 199 && response.code() < 301) {
                                                        SearchListReceive = (List<Search>) response.body().getSearches();
                                                        for (Search search : SearchListReceive) {
                                                            // add building to DB
                                                            daoSearch.createSearch(search.getSearchId(), search.getLevel(), search.getAmountOfEffectByLevel(), search.getAmountOfEffectLevel0(), search.isBuilding(), search.getEffect(), search.getGasCostByLevel(), search.getGasCostLevel0(), search.getMineralCostByLevel(), search.getMineralCostLevel0(), search.getName(), search.getTimeToBuildByLevel(), search.getTimeToBuildLevel0());
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<GetSearchesResponse> call, Throwable t) {

                                                }
                                            });
                                        }

                                        // ==================================

                                    startActivity(intent);
                                    finish();
                                } else if (response.code() > 499 && response.code() < 601) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Erreur Serveur, merci de reessayer plus tard !", Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginUserResponse> call, Throwable t) {
                                Constant.ToastErrorConnection(getApplicationContext());
                            }
                        });
                    }
                }
        );

        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTextEmail.getText().toString() != "" && editTextPseudonyme.getText().toString() != "" && editTextPassword.getText().toString() != "") {
                            User user = new User(editTextEmail.getText().toString(), editTextPseudonyme.getText().toString(), editTextPassword.getText().toString());

                            Retrofit retrofit = Constant.retrofit;

                            ApiService service = retrofit.create(ApiService.class);

                            Call<CreateUserResponse> request = service.createUser(user);

                            request.enqueue(new Callback<CreateUserResponse>() {
                                @Override
                                public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                                    if (response.code() > 199 && response.code() < 301) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Utilisateur enregistré", Toast.LENGTH_LONG);
                                        toast.show();
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CreateUserResponse> call, Throwable t) {

                                }
                            });

                        } else {
                            if (editTextEmail.getText().toString() != "") {
                                Toast toast = Toast.makeText(getApplicationContext(), "Veuillez entrer un Email !", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            if (editTextPseudonyme.getText().toString() != "") {
                                Toast toast = Toast.makeText(getApplicationContext(), "Veuillez entrer un Pseudonyme !", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            if (editTextPassword.getText().toString() != "") {
                                Toast toast = Toast.makeText(getApplicationContext(), "Veuillez entrer un Mot de passe !", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                }
        );


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est garantie
                } else {
                    // La permission est refusée
                }
                return;
            }
        }
    }
}
