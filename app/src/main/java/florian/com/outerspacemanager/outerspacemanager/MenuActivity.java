package florian.com.outerspacemanager.outerspacemanager;

import android.annotation.SuppressLint;
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

    private ImageView ImageViewBackgroundBuilding;
    private ImageView ImageViewBackgroundShip;
    private ImageView ImageViewBackgroundResearch;
    private ImageView ImageViewBackgroundSpaceShip;
    private ImageView ImageViewBackgroundGalaxy;
    private ImageView ImageViewPlanetID;
    private ImageView ImageViewMessageID;

    public static User user;

    private CardView CardViewShip;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        TextViewPseudonyme = findViewById(R.id.textViewPseudonymeID);
        TextViewScore = findViewById(R.id.textViewScoreID);
        TextViewMetal = findViewById(R.id.textViewMetalID);
        TextViewDeut = findViewById(R.id.textViewDeutID);

        ImageViewBackgroundBuilding= findViewById(R.id.imageViewBackgroundBuildingID);
        ImageViewBackgroundShip= findViewById(R.id.imageViewBackgroundShipID);
        ImageViewBackgroundResearch = findViewById(R.id.imageViewBackgroundResearchID);
        ImageViewBackgroundSpaceShip = findViewById(R.id.imageViewBackgroundSpaceShipID);
        ImageViewBackgroundGalaxy = findViewById(R.id.imageViewBackgroundGalaxyID);
        ImageViewPlanetID = findViewById(R.id.imageViewPlanetID);
        ImageViewMessageID = findViewById(R.id.imageViewMessageID);

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

                        TextViewPseudonyme.setText(user.getUsername());
                        TextViewDeut.setText(format("%,d", round(user.getGas())));
                        TextViewMetal.setText(format("%,d", round(user.getMinerals())));
                        TextViewScore.setText("Score : " + format("%,d",round(user.getPoints())));

                        String planetImageName = Constant.definePlanetByUserName(user.getUsername());
                        ImageViewPlanetID.setImageResource(getApplicationContext().getResources().getIdentifier(planetImageName, "drawable", getApplicationContext().getPackageName()));



                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {
                    Constant.ToastErrorConnection(getApplicationContext());
                }

            });

        }



        // CLICK BUTTON MESSAGE
        //

        ImageViewMessageID.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    view.setSelected(true);
                    Intent intent = new Intent(MenuActivity.this, MessageActivity.class);
                    intent.putExtra(Constant.EXTRA_USER, user);
                    startActivity(intent);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    view.setSelected(false);
                }
                return true;
            }
        });


        // CLICK BUTTON BATIMENT
        //

        ImageViewBackgroundBuilding.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        ImageViewBackgroundBuilding.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    view.setSelected(true);
                    Intent intent = new Intent(MenuActivity.this, BuildingActivity.class);
                    intent.putExtra(Constant.EXTRA_USER, user);
                    startActivity(intent);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    view.setSelected(false);
                }
                return true;
            }
        });

        // CLICK BUTTON  CHANTIER SPATIAL
        //

        ImageViewBackgroundShip.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        ImageViewBackgroundShip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    view.setSelected(true);
                    Intent intent = new Intent(MenuActivity.this, ShipActivity.class);
                    intent.putExtra(Constant.EXTRA_USER, user);
                    startActivity(intent);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    view.setSelected(false);
                }
                return true;
            }
        });

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
                    Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                    intent.putExtra(Constant.EXTRA_USER, user);
                    startActivity(intent);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    view.setSelected(false);
                }
                return true;
            }
        });

        // CLICK BUTTON FLOTTE
        //

        ImageViewBackgroundSpaceShip.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }
        );

        // TODO : Fleet Attack Status button

        ImageViewBackgroundSpaceShip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    view.setSelected(true);
                    Intent intent = new Intent(MenuActivity.this, SpaceShipActivity.class);
                    intent.putExtra(Constant.EXTRA_USER, user);
                    startActivity(intent);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    view.setSelected(false);
                }
                return true;
            }
        });

        // CLICK BUTTON GALAXIE
        //

        ImageViewBackgroundGalaxy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        ImageViewBackgroundGalaxy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    view.setSelected(true);
                    Intent intent = new Intent(MenuActivity.this, GalaxyActivity.class);
                    intent.putExtra(Constant.EXTRA_USER, user);
                    startActivity(intent);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    view.setSelected(false);
                }
                return true;
            }
        });

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
