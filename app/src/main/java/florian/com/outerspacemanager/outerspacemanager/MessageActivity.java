package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static florian.com.outerspacemanager.outerspacemanager.Constant.userToken;
import static java.lang.Math.round;
import static java.lang.String.format;

public class MessageActivity extends AppCompatActivity {

    private User user;
    private List<Report> ReportListReceive;
    private int fromMessageNumber;

    private TextView TextViewMetal;
    private TextView TextViewDeut;
    private ListView listViewMessage;
    private EditText editTextBeginNumberID;
    private Button buttonRechercherID;
    private ImageView imageViewActionLeftID;
    private ImageView imageViewActionRightID;
    private TextView textViewMessageDisplayedInfoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        TextViewMetal = findViewById(R.id.textViewMetalID);
        TextViewDeut = findViewById(R.id.textViewDeutID);
        listViewMessage = findViewById(R.id.ListViewMessageID);
        editTextBeginNumberID = findViewById(R.id.editTextBeginNumberID);
        buttonRechercherID = findViewById(R.id.buttonRechercherID);
        imageViewActionLeftID = findViewById(R.id.imageViewActionLeftID);
        imageViewActionRightID = findViewById(R.id.imageViewActionRightID);
        textViewMessageDisplayedInfoID = findViewById(R.id.textViewMessageDisplayedInfoID);

        fromMessageNumber = Integer.parseInt(editTextBeginNumberID.getText().toString());

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

        // GET REPORT_USER
        //
        Retrofit retrofit = Constant.retrofit;
        final ApiService service = retrofit.create(ApiService.class);

        setListViewData(service);

        buttonRechercherID.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextBeginNumberID.getText().toString() == "")
                            editTextBeginNumberID.setText("0");
                        fromMessageNumber = Integer.parseInt(editTextBeginNumberID.getText().toString());
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
        Call<GetReportResponse> request = service.getReports(userToken, fromMessageNumber);
        request.enqueue(new Callback<GetReportResponse>() {
            @Override
            public void onResponse(Call<GetReportResponse> call, Response<GetReportResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    ReportListReceive = (List<Report>) response.body().getReports();
                    ReportAdapter adapter = new ReportAdapter(MessageActivity.this, ReportListReceive, user, fromMessageNumber);

                    // CLICK on show more
                    adapter.setOnEventListener(new OnListViewChildrenClick() {
                        @Override
                        public void OnClick(final int id, View v) {
                            //TODO : activity to detail report
                        }
                    });

                    int maxDisplayed = response.body().getSize() + fromMessageNumber;
                    textViewMessageDisplayedInfoID.setText("Messages de "+fromMessageNumber+" a "+ maxDisplayed);

                    listViewMessage.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetReportResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }

        });
    }
}