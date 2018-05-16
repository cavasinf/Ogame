package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import static florian.com.outerspacemanager.outerspacemanager.Constant.userToken;
import static java.lang.Math.round;
import static java.lang.String.format;

public class GalaxyActivity extends AppCompatActivity {

    private User user;
    private List<GalaxyUser> GalaxyUserListReceive;
    private int fromUserNumber;

    private TextView TextViewMetal;
    private TextView TextViewDeut;
    private ListView listViewGalaxy;
    private EditText editTextBeginNumberID;
    private Button buttonRechercherID;
    private ImageView imageViewActionLeftID;
    private ImageView imageViewActionRightID;
    private TextView textViewPlayerDisplayedInfoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxy);

        TextViewMetal = findViewById(R.id.textViewMetalID);
        TextViewDeut = findViewById(R.id.textViewDeutID);
        listViewGalaxy = findViewById(R.id.ListViewGalaxyID);
        editTextBeginNumberID = findViewById(R.id.editTextBeginNumberID);
        buttonRechercherID = findViewById(R.id.buttonRechercherID);
        imageViewActionLeftID = findViewById(R.id.imageViewActionLeftID);
        imageViewActionRightID = findViewById(R.id.imageViewActionRightID);
        textViewPlayerDisplayedInfoID = findViewById(R.id.textViewPlayerDisplayedInfoID);

        fromUserNumber = Integer.parseInt(editTextBeginNumberID.getText().toString());

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

        // GET GALAXY_USERS
        //

        Retrofit retrofit = Constant.retrofit;
        final ApiService service = retrofit.create(ApiService.class);

        setListViewData(service);

        buttonRechercherID.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextBeginNumberID.getText().toString().equals(""))
                            editTextBeginNumberID.setText("0");
                        fromUserNumber = Integer.parseInt(editTextBeginNumberID.getText().toString());
                        setListViewData(service);
                    }
                }
        );

        imageViewActionLeftID.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Integer.parseInt(editTextBeginNumberID.getText().toString()) > 0)
                            editTextBeginNumberID.setText(String.valueOf(Integer.parseInt(editTextBeginNumberID.getText().toString()) - 1));
                    }
                }
        );

        imageViewActionRightID.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editTextBeginNumberID.setText(String.valueOf(Integer.parseInt(editTextBeginNumberID.getText().toString()) + 1));
                    }
                }
        );




    }

    private void setListViewData(ApiService service) {
        Call<GetGalaxyResponse> request = service.getGalaxy(userToken, fromUserNumber);
        request.enqueue(new Callback<GetGalaxyResponse>() {
            @Override
            public void onResponse(Call<GetGalaxyResponse> call, Response<GetGalaxyResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    GalaxyUserListReceive = (List<GalaxyUser>)response.body().getUsers();
                    GalaxyAdapter adapter = new GalaxyAdapter(GalaxyActivity.this, GalaxyUserListReceive, user);

                    // CLICK on attack button
                    adapter.setOnEventListener(new OnListViewUserChildrenClick() {
                        @Override
                        public void OnClick(final String playerToAttack, View v) {
                            Intent intent = new Intent(GalaxyActivity.this, SpaceShipActivity.class);
                            intent.putExtra(Constant.EXTRA_USER, user);
                            intent.putExtra(Constant.EXTRA_PLAYER_TO_ATTACK, playerToAttack);
                            startActivity(intent);
                        }
                    });

                    int maxDisplayed = 20 + fromUserNumber;
                    textViewPlayerDisplayedInfoID.setText("Joueurs de "+fromUserNumber+" a "+ maxDisplayed);

                    listViewGalaxy.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetGalaxyResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });
    }
}