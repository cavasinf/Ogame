package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

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

    private Report ReportReceive;
    private List<Ship> ShipListAttackerReceive;
    private List<Ship> ShipListDefenderReceive;

    private ListView ListViewAttackerShip;
    private ListView ListViewDefenderShip;

    private TextView textViewTime;
    private TextView textViewRessourceMetal;
    private TextView textViewRessourceGas;
    private TextView textViewAttackerName;
    private TextView textViewDefenderName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ListViewAttackerShip = findViewById(R.id.ListViewAttackerShipID);
        ListViewDefenderShip = findViewById(R.id.ListViewDefenderShipID);

        textViewTime = findViewById(R.id.textViewTimeID);
        textViewRessourceMetal = findViewById(R.id.textViewRessourceMetalID);
        textViewRessourceGas = findViewById(R.id.textViewRessourceGasID);
        textViewAttackerName = findViewById(R.id.textViewAttackerNameID);
        textViewDefenderName = findViewById(R.id.textViewDefenderNameID);

        SharedPreferences settings = getSharedPreferences(Constant.PREFS_USER, 0);
        final String userToken = settings.getString("userToken", "");

        Intent intent = getIntent();
        final Integer reportNumber = (Integer) intent.getSerializableExtra(Constant.EXTRA_REPORT_TO_DISPLAY);


        // GET Report
        //

        Call<GetReportResponse> request = service.getReport(userToken, reportNumber+1);
        request.enqueue(new Callback<GetReportResponse>() {
            @Override
            public void onResponse(Call<GetReportResponse> call, Response<GetReportResponse> response) {
                if (response.code() > 199 && response.code() < 301) {

                    ReportReceive = (Report) response.body().getReports().get(0);

                    textViewTime.setText(Constant.convertTimeInSecondsToReadableDate(ReportReceive.getDate()));
                    textViewRessourceMetal.setText(format("%,d", round(ReportReceive.getMineralsWon())));
                    textViewRessourceGas.setText(format("%,d", round(ReportReceive.getGasWon())));
                    textViewAttackerName.setText(ReportReceive.getFrom());
                    textViewDefenderName.setText(ReportReceive.getTo());

                    ReportAdapter adapterAttacker = new ReportAdapter(ReportActivity.this, ReportReceive.getAttackerFleetAfterBattle().getFleet());
                    ListViewAttackerShip.setAdapter(adapterAttacker);

                    ReportAdapter adapterDefender = new ReportAdapter(ReportActivity.this, ReportReceive.getDefenderFleetAfterBattle().getFleet());
                    ListViewDefenderShip.setAdapter(adapterDefender);
                }
            }

            @Override
            public void onFailure(Call<GetReportResponse> call, Throwable t) {
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