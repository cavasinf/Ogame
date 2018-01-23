package florian.com.outerspacemanager.outerspacemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Math.round;
import static java.lang.String.format;

public class MenuActivity extends AppCompatActivity {



    private TextView TextViewPseudonyme;
    private TextView TextViewScore;
    private TextView TextViewMetal;
    private TextView TextViewDeut;

    private ImageView ImageViewBackgroundResearch;

    private CardView CardViewShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        final User oldUser = (User) intent.getSerializableExtra(Constant.EXTRA_USER);

        TextViewPseudonyme = findViewById(R.id.textViewPseudonymeID);
        TextViewScore = findViewById(R.id.textViewScoreID);
        TextViewMetal = findViewById(R.id.textViewMetalID);
        TextViewDeut = findViewById(R.id.textViewDeutID);

        ImageViewBackgroundResearch = findViewById(R.id.imageViewBackgroundResearchID);

        // CLICK BUTTON RECHERCHE
        //

        ImageViewBackgroundResearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        ImageViewBackgroundResearch.setOnTouchListener(new View.OnTouchListener() {
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

        //View TextViewRecherche = findViewById(R.id.textViewResearchTextID);

        CardViewShip = findViewById(R.id.CardViewShipID);

        // CLICK BUTTON FLOTTE
        //

       // TextViewRecherche.setVisibility(View.GONE);

        CardViewShip.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MenuActivity.this, ShipActivity.class);
                        startActivity(intent);
                    }
                }
        );

        SharedPreferences settings = getSharedPreferences(Constant.PREFS_USER, 0);
        final String userToken = settings.getString("userToken", "");
        if (userToken != "") {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService service = retrofit.create(ApiService.class);

            Call<GetUserResponse> request = service.getUser(userToken);

            request.enqueue(new Callback<GetUserResponse>() {
                @Override
                public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                    if (response.code() > 199 && response.code() < 301) {
                        User user = new User(oldUser, userToken, response.body().getGas(), response.body().getGasModifier(), response.body().getMinerals(), response.body().getMineralsModifier(), response.body().getPoints());
                        // TODO : set user In DB
                        //
                        TextViewPseudonyme.setText(user.getUsername());
                        TextViewDeut.setText(format("%,d", round(user.getGas())));
                        TextViewMetal.setText(format("%,d", round(user.getMinerals())));
                        TextViewScore.setText("Score : " + user.getPoints());


                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {
                    Constant.ToastErrorConnection(getApplicationContext());
                }

            });

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Etes vous sur de quitter l'application ?").setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };


}
