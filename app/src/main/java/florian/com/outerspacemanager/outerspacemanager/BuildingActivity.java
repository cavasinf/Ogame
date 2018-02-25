package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

import java.text.Normalizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Math.round;
import static java.lang.String.format;

public class BuildingActivity extends AppCompatActivity {

    User user;

    private TextView TextViewMetal;
    private TextView TextViewDeut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        TextViewMetal = findViewById(R.id.textViewMetalID);
        TextViewDeut = findViewById(R.id.textViewDeutID);

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
                        LinearLayout LinearLayoutMainID = (LinearLayout) findViewById(R.id.LinearLayoutScrollViewMainID);

                        //RelativeLayoutConstructBoxID
                        //
//                        RelativeLayout RelativeLayoutConstructBoxID = new RelativeLayout(getApplicationContext());
//                        RelativeLayoutConstructBoxID.setId(R.id.RelativeLayoutConstructBoxID);
//                        RelativeLayoutConstructBoxID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                                Constant.dspToPixel(getApplicationContext(),200)));

                            //imageViewBoxID
                            //
//                            ImageView imageViewBoxID = new ImageView(getApplicationContext());
//                            imageViewBoxID.setId(R.id.imageViewBoxID);
//                            imageViewBoxID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                                    LayoutParams.WRAP_CONTENT));
//                            imageViewBoxID.setPadding(Constant.dspToPixel(getApplicationContext(),10),0,Constant.dspToPixel(getApplicationContext(),10),0);
//                            imageViewBoxID.setImageResource(R.drawable.build_box);
//                            RelativeLayoutConstructBoxID.addView(imageViewBoxID);

                            //LinearLayoutBoxID
                            //
                            LinearLayout LinearLayoutBoxID = new LinearLayout(getApplicationContext());
                            LinearLayoutBoxID.setId(R.id.LinearLayoutBoxID);
                            LinearLayout.LayoutParams LinearLayoutBoxIDlp = new LayoutParams(LayoutParams.MATCH_PARENT,
                                Constant.dspToPixel(getApplicationContext(),170));
                            LinearLayoutBoxIDlp.setMargins(
                                    Constant.dspToPixel(getApplicationContext(),10),
                                    Constant.dspToPixel(getApplicationContext(),5),
                                    Constant.dspToPixel(getApplicationContext(),10),
                                    Constant.dspToPixel(getApplicationContext(),5));
                            LinearLayoutBoxID.setLayoutParams(LinearLayoutBoxIDlp);
                            LinearLayoutBoxID.setBackgroundResource(R.drawable.build_box);
                            LinearLayoutBoxID.setOrientation(LinearLayout.HORIZONTAL);

                                //imageViewConstructionID
                                //
                                ImageView imageViewConstructionID = new ImageView(getApplicationContext());
                                imageViewConstructionID.setId(R.id.imageViewConstructionID);
                                LinearLayout.LayoutParams imageViewConstructionIDlp = new LinearLayout.LayoutParams(Constant.dspToPixel(getApplicationContext(),105),
                                        Constant.dspToPixel(getApplicationContext(),114));
                                imageViewConstructionIDlp.weight = 1.0f;
                                imageViewConstructionID.setLayoutParams(imageViewConstructionIDlp);
                                imageViewConstructionID.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                String buildingName = Normalizer.normalize(building.getName().replace(" ","_"), Normalizer.Form.NFD);
                                buildingName = buildingName.replaceAll("[^\\p{ASCII}]", "");
                                imageViewConstructionID.setImageResource(getResources().getIdentifier(buildingName,"drawable",getPackageName())); //Check Value
                                LinearLayoutBoxID.addView(imageViewConstructionID);

                                //LinearLayoutCol2ID
                                //
                                LinearLayout LinearLayoutCol2ID = new LinearLayout(getApplicationContext());
                                LinearLayoutCol2ID.setId(R.id.LinearLayoutCol2ID);
                                LinearLayout.LayoutParams LinearLayoutCol2IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.MATCH_PARENT);
                                LinearLayoutCol2IDlp.setMargins(0,Constant.dspToPixel(getApplicationContext(),-2),0,0);
                                LinearLayoutCol2IDlp.weight = 1.0f;
                                LinearLayoutCol2ID.setLayoutParams(LinearLayoutCol2IDlp);
                                LinearLayoutCol2ID.setOrientation(LinearLayout.VERTICAL);
                                LinearLayoutCol2ID.setPadding(Constant.dspToPixel(getApplicationContext(),5), 0, 0, 0);

                                    //textView2ConstructionTitleID
                                    //
                                    TextView textView2ConstructionTitleID = new TextView(getApplicationContext());
                                    textView2ConstructionTitleID.setId(R.id.textView2ConstructionTitleID);
                                    LinearLayout.LayoutParams textView2ConstructionTitleIDlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 0);
                                    textView2ConstructionTitleIDlp.weight = 1.0f;
                                    textView2ConstructionTitleID.setLayoutParams(textView2ConstructionTitleIDlp);
                                    textView2ConstructionTitleID.setText(building.getName()); //Check Value
                                    textView2ConstructionTitleID.setTextColor(getResources().getColor(R.color.colorTitleOrange));
                                    textView2ConstructionTitleID.setTypeface(Typeface.DEFAULT_BOLD);
                                    LinearLayoutCol2ID.addView(textView2ConstructionTitleID);

                                    //LinearLayoutText1ID
                                    //
                                    LinearLayout LinearLayoutText1ID = new LinearLayout(getApplicationContext());
                                    LinearLayoutText1ID.setId(R.id.LinearLayoutText1ID);
                                    LinearLayout.LayoutParams LinearLayoutText1IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                            0);
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
                                        textViewProdTimeID.setText(round(building.getTimeToBuildByLevel() * (1+user.getMineralsModifier()))+"s"); //CHeck Value
                                        textViewProdTimeID.setTextColor(getResources().getColor(R.color.background_light));
                                        LinearLayoutText1ID.addView(textViewProdTimeID);

                                    LinearLayoutCol2ID.addView(LinearLayoutText1ID);

                                    //LinearLayoutText2ID
                                    //
                                    LinearLayout LinearLayoutText2ID = new LinearLayout(getApplicationContext());
                                    LinearLayoutText2ID.setId(R.id.LinearLayoutText2ID);
                                    LinearLayout.LayoutParams LinearLayoutText2IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                    0);
                                    LinearLayoutText2IDlp.weight = 1.0f;
                                    LinearLayoutText2ID.setLayoutParams(LinearLayoutText2IDlp);
                                    LinearLayoutText2ID.setGravity(Gravity.BOTTOM );
                                    LinearLayoutText2ID.setOrientation(LinearLayout.HORIZONTAL);

                                        //textViewProdNextTextID
                                        //
                                        TextView textViewProdNextTextID = new TextView(getApplicationContext());
                                        textViewProdNextTextID.setId(R.id.textViewProdNextTextID);
                                        LinearLayout.LayoutParams textViewProdNextTextIDlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                        textViewProdNextTextIDlp.weight = 1.0f;
                                        textViewProdNextTextID.setLayoutParams(textViewProdNextTextIDlp);
                                        textViewProdNextTextID.setText("Pour niveau ");
                                        textViewProdNextTextID.setTextColor(getResources().getColor(R.color.background_light));
                                        LinearLayoutText2ID.addView(textViewProdNextTextID);

                                        //textViewProdNextLevelID
                                        //
                                        TextView textViewProdNextLevelID = new TextView(getApplicationContext());
                                        textViewProdNextLevelID.setId(R.id.textViewProdNextLevelID);
                                        LinearLayout.LayoutParams textViewProdNextLevelIDlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                        textViewProdNextLevelIDlp.weight = 1.0f;
                                        textViewProdNextLevelID.setLayoutParams(textViewProdNextLevelIDlp);
                                        textViewProdNextLevelID.setText((building.getLevel()+1)+" :"); // Check Value
                                        textViewProdNextLevelID.setTextColor(getResources().getColor(R.color.background_light));
                                        LinearLayoutText2ID.addView(textViewProdNextLevelID);


                                    LinearLayoutCol2ID.addView(LinearLayoutText2ID);

                                    //LinearLayoutResourcesID
                                    //
                                    LinearLayout LinearLayoutResourcesID = new LinearLayout(getApplicationContext());
                                    LinearLayoutResourcesID.setId(R.id.LinearLayoutResourcesID);
                                    LinearLayout.LayoutParams LinearLayoutResourcesIDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                    LayoutParams.WRAP_CONTENT);
                                    LinearLayoutResourcesIDlp.weight = 1.0f;
                                    LinearLayoutResourcesID.setLayoutParams(LinearLayoutResourcesIDlp);
                                    LinearLayoutResourcesID.setGravity(Gravity.BOTTOM );
                                    LinearLayoutResourcesID.setOrientation(LinearLayout.HORIZONTAL);


                                        //LinearLayoutResources1ID
                                        //
                                        LinearLayout LinearLayoutResources1ID = new LinearLayout(getApplicationContext());
                                        LinearLayoutResources1ID.setId(R.id.LinearLayoutResources1ID);
                                        LinearLayout.LayoutParams LinearLayoutResources1IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT);
                                        LinearLayoutResources1IDlp.weight = 1.0f;
                                        LinearLayoutResources1IDlp.setMargins(Constant.dspToPixel(getApplicationContext(),2),0,Constant.dspToPixel(getApplicationContext(),2),0);
                                        LinearLayoutResources1ID.setLayoutParams(LinearLayoutResources1IDlp);
                                        LinearLayoutResources1ID.setOrientation(LinearLayout.VERTICAL);


                                            //imageViewRessource1ID
                                            //
                                            ImageView imageViewRessource1ID = new ImageView(getApplicationContext());
                                            imageViewRessource1ID.setId(R.id.imageViewRessource1ID);
                                            LinearLayout.LayoutParams imageViewRessource1IDlp = new LayoutParams(LayoutParams.MATCH_PARENT,
                                                LayoutParams.WRAP_CONTENT);
                                            imageViewRessource1IDlp.weight = 1.0f;
                                            imageViewRessource1ID.setLayoutParams(imageViewRessource1IDlp);
                                            imageViewRessource1ID.setImageResource(R.drawable.metal_icon_mini);
                                            LinearLayoutResources1ID.addView(imageViewRessource1ID);

                                            //textViewRessource1ID
                                            //
                                            TextView textViewRessource1ID = new TextView(getApplicationContext());
                                            textViewRessource1ID.setId(R.id.textViewRessource1ID);
                                            LinearLayout.LayoutParams textViewRessource1IDlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                                            textViewRessource1IDlp.weight = 1.0f;
                                            textViewRessource1ID.setLayoutParams(textViewRessource1IDlp);
                                            textViewRessource1ID.setGravity(Gravity.CENTER );
                                            textViewRessource1ID.setText(format("%,d",Constant.costMineralBuilding(building))); // Check Value
                                            textViewRessource1ID.setTextColor(getResources().getColor(R.color.background_light));
                                            LinearLayoutResources1ID.addView(textViewRessource1ID);

                                        LinearLayoutResourcesID.addView(LinearLayoutResources1ID);

                                        //LinearLayoutResources2ID
                                        //
                                        LinearLayout LinearLayoutResources2ID = new LinearLayout(getApplicationContext());
                                        LinearLayoutResources2ID.setId(R.id.LinearLayoutResources2ID);
                                        LinearLayout.LayoutParams LinearLayoutResources2IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT);
                                        LinearLayoutResources2IDlp.setMargins(Constant.dspToPixel(getApplicationContext(),2),0,Constant.dspToPixel(getApplicationContext(),2),0);
                                        LinearLayoutResources2IDlp.weight = 1.0f;
                                        LinearLayoutResources2ID.setLayoutParams(LinearLayoutResources2IDlp);
                                        LinearLayoutResources2ID.setOrientation(LinearLayout.VERTICAL);

                                            //imageViewRessource2ID
                                            //
                                            ImageView imageViewRessource2ID = new ImageView(getApplicationContext());
                                            imageViewRessource2ID.setId(R.id.imageViewRessource2ID);
                                            LinearLayout.LayoutParams imageViewRessource2IDlp = new LayoutParams(LayoutParams.MATCH_PARENT,
                                                    LayoutParams.WRAP_CONTENT);
                                            imageViewRessource2IDlp.weight = 1.0f;
                                            imageViewRessource2ID.setLayoutParams(imageViewRessource2IDlp);
                                            imageViewRessource2ID.setImageResource(R.drawable.deut_icon_mini);
                                            LinearLayoutResources2ID.addView(imageViewRessource2ID);

                                            //textViewRessource2ID
                                            //
                                            TextView textViewRessource2ID = new TextView(getApplicationContext());
                                            textViewRessource2ID.setId(R.id.textViewRessource1ID);
                                            LinearLayout.LayoutParams textViewRessource2IDlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                                            textViewRessource2IDlp.weight = 1.0f;
                                            textViewRessource2ID.setLayoutParams(textViewRessource2IDlp);
                                            textViewRessource2ID.setGravity(Gravity.CENTER );
                                            textViewRessource2ID.setText(format("%,d",Constant.costGasBuilding(building))); // Check Value
                                            textViewRessource2ID.setTextColor(getResources().getColor(R.color.background_light));
                                            LinearLayoutResources2ID.addView(textViewRessource2ID);

                                        LinearLayoutResourcesID.addView(LinearLayoutResources2ID);

                                    LinearLayoutCol2ID.addView(LinearLayoutResourcesID);

                        LinearLayoutBoxID.addView(LinearLayoutCol2ID);

                        //LinearLayoutCol3ID
                        //
                        LinearLayout LinearLayoutCol3ID = new LinearLayout(getApplicationContext());
                        LinearLayoutCol3ID.setId(R.id.LinearLayoutCol3ID);
                        LinearLayout.LayoutParams LinearLayoutCol3IDlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        LinearLayoutCol3IDlp.setMargins(0,Constant.dspToPixel(getApplicationContext(),-2),0,0);
                        LinearLayoutCol3IDlp.weight = 1.0f;
                        LinearLayoutCol3ID.setLayoutParams(LinearLayoutCol3IDlp);
                        LinearLayoutCol3ID.setOrientation(LinearLayout.VERTICAL);

                            //LinearLayoutTitle1ID
                            //
                            LinearLayout LinearLayoutTitle1ID = new LinearLayout(getApplicationContext());
                            LinearLayoutTitle1ID.setId(R.id.LinearLayoutTitle1ID);
                            LinearLayout.LayoutParams LinearLayoutTitle1IDlp = new LayoutParams(LayoutParams.MATCH_PARENT,
                                    LayoutParams.MATCH_PARENT);
                            LinearLayoutTitle1ID.setLayoutParams(LinearLayoutTitle1IDlp);
                            LinearLayoutTitle1ID.setOrientation(LinearLayout.HORIZONTAL);

                                //textViewConstructionLevelTextID
                                //
                                TextView textViewConstructionLevelTextID = new TextView(getApplicationContext());
                                textViewConstructionLevelTextID.setId(R.id.textViewConstructionLevelTextID);
                                LinearLayout.LayoutParams textViewConstructionLevelTextIDlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                textViewConstructionLevelTextIDlp.weight = 1.0f;
                                textViewConstructionLevelTextID.setLayoutParams(textViewConstructionLevelTextIDlp);
                                textViewConstructionLevelTextID.setText("Niveau");
                                textViewConstructionLevelTextID.setTextColor(getResources().getColor(R.color.colorTitleOrange));
                                textViewConstructionLevelTextID.setTypeface(Typeface.DEFAULT_BOLD);
                                LinearLayoutTitle1ID.addView(textViewConstructionLevelTextID);

                                //textViewConstructionLevelID
                                //
                                TextView textViewConstructionLevelID = new TextView(getApplicationContext());
                                textViewConstructionLevelID.setId(R.id.textViewConstructionLevelID);
                                LinearLayout.LayoutParams textViewConstructionLevelIDlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                textViewConstructionLevelIDlp.weight = 1.0f;
                                textViewConstructionLevelID.setLayoutParams(textViewConstructionLevelIDlp);
                                textViewConstructionLevelID.setText("0"); // Check Value
                                textViewConstructionLevelID.setTextColor(getResources().getColor(R.color.colorTitleOrange));
                                textViewConstructionLevelID.setTypeface(Typeface.DEFAULT_BOLD);
                                LinearLayoutTitle1ID.addView(textViewConstructionLevelID);

                            LinearLayoutCol3ID.addView(LinearLayoutTitle1ID);

                            //RelativeLayoutConstructButtonID
                            //
                            RelativeLayout RelativeLayoutConstructButtonID = new RelativeLayout(getApplicationContext());
                            RelativeLayoutConstructButtonID.setId(R.id.RelativeLayoutConstructButtonID);
                            LinearLayout.LayoutParams RelativeLayoutConstructButtonIDlp = new LinearLayout.LayoutParams(Constant.dspToPixel(getApplicationContext(),120),
                                    Constant.dspToPixel(getApplicationContext(),50));
                            RelativeLayoutConstructButtonIDlp.setMargins(0 ,Constant.dspToPixel(getApplicationContext(),100) ,0 ,0);
                            RelativeLayoutConstructButtonID.setLayoutParams(RelativeLayoutConstructButtonIDlp);
                            RelativeLayoutConstructButtonID.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL );

                                //imageViewConstructButtonBackgroundID
                                //
                                ImageView imageViewConstructButtonBackgroundID = new ImageView(getApplicationContext());
                                imageViewConstructButtonBackgroundID.setId(R.id.imageViewConstructButtonBackgroundID);
                                LinearLayout.LayoutParams imageViewConstructButtonBackgroundIDlp = new LayoutParams(LayoutParams.MATCH_PARENT,
                                        LayoutParams.MATCH_PARENT);
                                imageViewConstructButtonBackgroundID.setLayoutParams(imageViewConstructButtonBackgroundIDlp);
                                imageViewConstructButtonBackgroundID.setImageResource(R.drawable.button_develop_normal);
                                RelativeLayoutConstructButtonID.addView(imageViewConstructButtonBackgroundID);

                                //textViewConstruciontButtonTextID
                                //
                                TextView textViewConstruciontButtonTextID = new TextView(getApplicationContext());
                                textViewConstruciontButtonTextID.setId(R.id.textViewConstruciontButtonTextID);
                                LinearLayout.LayoutParams textViewConstruciontButtonTextIDlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                                textViewConstruciontButtonTextID.setLayoutParams(textViewConstruciontButtonTextIDlp);
                                textViewConstruciontButtonTextID.setGravity(Gravity.CENTER);
                                textViewConstruciontButtonTextID.setText("CONSTRUIRE");
                                textViewConstruciontButtonTextID.setTextColor(getResources().getColor(R.color.background_light));
                                textViewConstruciontButtonTextID.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                                textViewConstruciontButtonTextID.setTypeface(Typeface.DEFAULT_BOLD);
                                RelativeLayoutConstructButtonID.addView(textViewConstruciontButtonTextID);

                            LinearLayoutCol3ID.addView(RelativeLayoutConstructButtonID);

                        LinearLayoutBoxID.addView(LinearLayoutCol3ID);

//                    RelativeLayoutConstructBoxID.addView(LinearLayoutBoxID);

//                LinearLayoutMainID.addView(RelativeLayoutConstructBoxID);
               LinearLayoutMainID.addView(LinearLayoutBoxID);


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
