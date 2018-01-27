package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

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
                        LinearLayout LinearLayoutMainID = (LinearLayout) findViewById(R.id.LinearLayoutMainID);

                        //RelativeLayoutConstructBoxID
                        //
                        RelativeLayout RelativeLayoutConstructBoxID = new RelativeLayout(getApplicationContext());
                        RelativeLayoutConstructBoxID.setId(R.id.RelativeLayoutConstructBoxID);
                        RelativeLayoutConstructBoxID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                200));

                            //imageViewBoxID
                            //
                            ImageView imageViewBoxID = new ImageView(getApplicationContext());
                            imageViewBoxID.setId(R.id.imageViewBoxID);
                            imageViewBoxID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                    LayoutParams.WRAP_CONTENT));
                            imageViewBoxID.setPadding(20,0,20,0);
                            imageViewBoxID.setImageResource(R.drawable.build_box);
                            RelativeLayoutConstructBoxID.addView(imageViewBoxID);

                            //LinearLayoutBoxID
                            //
                            LinearLayout LinearLayoutBoxID = new LinearLayout(getApplicationContext());
                            LinearLayoutBoxID.setId(R.id.LinearLayoutBoxID);
                            LinearLayoutBoxID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                210));
                            LinearLayoutBoxID.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayoutBoxID.setPadding(10, 0, 10, 0);

                                //imageViewConstructionID
                                //
                                ImageView imageViewConstructionID = new ImageView(getApplicationContext());
                                imageViewConstructionID.setId(R.id.imageViewConstructionID);
                                imageViewConstructionID.setLayoutParams(new LayoutParams(105,
                                                114));
                                LinearLayout.LayoutParams imageViewConstructionIDlp = new LinearLayout.LayoutParams(105, 114);
                                imageViewConstructionIDlp.weight = 1.0f;
                                imageViewConstructionIDlp.setMargins(0,15,00,0);
                                imageViewConstructionID.setLayoutParams(imageViewConstructionIDlp);
                                imageViewConstructionID.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                imageViewConstructionID.setImageResource(R.drawable.batiment_usine_metal);
                                LinearLayoutBoxID.addView(imageViewConstructionID);

                                //LinearLayoutCol2ID
                                //
                                LinearLayout LinearLayoutCol2ID = new LinearLayout(getApplicationContext());
                                LinearLayoutCol2ID.setId(R.id.LinearLayoutCol2ID);
                                LinearLayout.LayoutParams LinearLayoutCol2IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.MATCH_PARENT);
                                LinearLayoutCol2IDlp.weight = 1.0f;
                                LinearLayoutCol2IDlp.setMargins(0,14,00,0);
                                LinearLayoutCol2ID.setLayoutParams(LinearLayoutCol2IDlp);
                                LinearLayoutCol2ID.setOrientation(LinearLayout.VERTICAL);
                                LinearLayoutCol2ID.setPadding(5, 0, 0, 0);

                                    //textView2ConstructionTitleID
                                    //
                                    TextView textView2ConstructionTitleID = new TextView(getApplicationContext());
                                    textView2ConstructionTitleID.setId(R.id.textView2ConstructionTitleID);
                                    LinearLayout.LayoutParams textView2ConstructionTitleIDlp = new LinearLayout.LayoutParams(105, 114);
                                    textView2ConstructionTitleIDlp.weight = 1.0f;
                                    textView2ConstructionTitleID.setLayoutParams(textView2ConstructionTitleIDlp);
                                    textView2ConstructionTitleID.setText(building.getName());
                                    textView2ConstructionTitleID.setTextColor(getResources().getColor(R.color.colorTitleOrange));
                                    textView2ConstructionTitleID.setTypeface(Typeface.DEFAULT_BOLD);
                                    LinearLayoutCol2ID.addView(textView2ConstructionTitleID);

                                    //LinearLayoutText1ID
                                    //
                                    LinearLayout LinearLayoutText1ID = new LinearLayout(getApplicationContext());
                                    LinearLayoutText1ID.setId(R.id.LinearLayoutText1ID);
                                    LinearLayout.LayoutParams LinearLayoutText1IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                            LayoutParams.WRAP_CONTENT);
                                    LinearLayoutText1IDlp.weight = 1.0f;
                                    LinearLayoutText1ID.setLayoutParams(LinearLayoutText1IDlp);
                                    LinearLayoutText1ID.setOrientation(LinearLayout.HORIZONTAL);

                                        //textViewProdTextID
                                        //
                                        TextView textViewProdTextID = new TextView(getApplicationContext());
                                        textViewProdTextID.setId(R.id.textViewProdTextID);
                                        LinearLayout.LayoutParams textViewProdTextIDlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                        textViewProdTextIDlp.weight = 1.0f;
                                        textViewProdTextID.setLayoutParams(textViewProdTextIDlp);
                                        textViewProdTextID.setText("Temps prod : ");
                                        textViewProdTextID.setTextColor(getResources().getColor(R.color.background_light));
                                        LinearLayoutText1ID.addView(textViewProdTextID);

                                        //textViewProdTimeID
                                        //
                                        TextView textViewProdTimeID = new TextView(getApplicationContext());
                                        textViewProdTimeID.setId(R.id.textViewProdTextID);
                                        LinearLayout.LayoutParams textViewProdTimeIDlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                                        textViewProdTimeIDlp.weight = 1.0f;
                                        textViewProdTimeID.setLayoutParams(textViewProdTimeIDlp);
                                        textViewProdTimeID.setText(round(building.getTimeToBuildByLevel() * (1+user.getMineralsModifier()))+"s");
                                        textViewProdTimeID.setTextColor(getResources().getColor(R.color.background_light));
                                        LinearLayoutText1ID.addView(textViewProdTimeID);

                                    LinearLayoutCol2ID.addView(LinearLayoutText1ID);


                        LinearLayoutBoxID.addView(LinearLayoutCol2ID);

                            RelativeLayoutConstructBoxID.addView(LinearLayoutBoxID);

                        LinearLayoutMainID.addView(RelativeLayoutConstructBoxID);


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
