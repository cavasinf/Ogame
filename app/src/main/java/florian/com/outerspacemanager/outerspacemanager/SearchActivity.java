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

public class SearchActivity extends AppCompatActivity {

    private User user;
    private Retrofit retrofit = Constant.retrofit;
    final ApiService service = retrofit.create(ApiService.class);
    private List<Search> SearchListReceive;

    private TextView TextViewMetal;
    private TextView TextViewDeut;
    private ListView listViewConstruction;

    private Date currentDate;
    private List<SearchStatus> listSearchStatus = new ArrayList<SearchStatus>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        // GET SEARCHES
        //

        Retrofit retrofit = Constant.retrofit;

        final ApiService service = retrofit.create(ApiService.class);

        Call<GetSearchesResponse> request = service.getSearches(userToken);

        request.enqueue(new Callback<GetSearchesResponse>() {
            @Override
            public void onResponse(Call<GetSearchesResponse> call, Response<GetSearchesResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    SearchListReceive = (List<Search>) response.body().getSearches();
                    currentDate = Calendar.getInstance().getTime();


                    DAOSearchStatus daoSearchStatus = new DAOSearchStatus(getApplicationContext());
                    Environment.getExternalStorageDirectory();
                    daoSearchStatus.open();
                    listSearchStatus = daoSearchStatus.getAllSearchStatus();

                    List<SearchStatus> listOfSearchStatusToRemove = new ArrayList<>();

                    // open search DB
                    DAOSearch daoSearch = new DAOSearch(getApplicationContext());
                    daoSearch.open();
                    // clear all searches DB
                    daoSearch.deleteAllSearches();


                    for (Search search : SearchListReceive) {
                        // add building to DB
                        daoSearch.createSearch(search.getSearchId(), search.getLevel(), search.getAmountOfEffectByLevel(), search.getAmountOfEffectLevel0(), search.isBuilding(), search.getEffect(), search.getGasCostByLevel(), search.getGasCostLevel0(), search.getMineralCostByLevel(), search.getMineralCostLevel0(), search.getName(), search.getTimeToBuildByLevel(), search.getTimeToBuildLevel0());


                        //Clear search construction in DB
                        for (SearchStatus searchStatus : listSearchStatus) {
                            // if search in database and construction is done
                            if (searchStatus.getSearchId() != null) {
                                if (Objects.equals(search.getSearchId().toString(), searchStatus.getSearchId())) {
                                    int currentTime = (int) (new Date().getTime() / 1000);
                                    if (currentTime - Integer.parseInt(searchStatus.getDateSearching()) > search.getTimeToBuild(false)) {
                                        if (!daoSearchStatus.deleteSearchState(search.getSearchId()))
                                            Constant.displayToast(getApplicationContext(), "Error when delete in database");
                                        listOfSearchStatusToRemove.add(searchStatus);
                                    }
                                }
                            }
                        }
                    }
                    for (SearchStatus searchStatus : listOfSearchStatusToRemove) {
                        listSearchStatus.remove(searchStatus);
                    }

                    final SearchAdapter adapter = new SearchAdapter(SearchActivity.this, SearchListReceive, user, currentDate, listSearchStatus);


                    // CLICK on item
                    adapter.setOnEventListener(new OnListViewChildrenClick() {
                        @Override
                        public void OnClick(final int id, View v) {
                            if (v.isEnabled()) {

                                Call<CreateSearchResponse> requestLaunchSearch = service.launchSearch(userToken, id);

                                requestLaunchSearch.enqueue(new Callback<CreateSearchResponse>() {
                                    @Override
                                    public void onResponse(Call<CreateSearchResponse> call, Response<CreateSearchResponse> response) {
                                        if (response.code() > 199 && response.code() < 301) {

                                            DAOSearchStatus daoSearchStatus = new DAOSearchStatus(getApplicationContext());
                                            daoSearchStatus.open();
                                            int currentTime = (int) (new Date().getTime() / 1000);
                                            daoSearchStatus.createSearchStatus(id, "true", String.valueOf(currentTime));

                                            refreshSearchData(userToken, adapter);

                                            Toast toast = Toast.makeText(getApplicationContext(), "Recherche lancée", Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CreateSearchResponse> call, Throwable t) {

                                    }
                                });

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Impossible de lancer une recherche sur cette technologie", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
                    listViewConstruction.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetSearchesResponse> call, Throwable t) {
                Constant.ToastErrorConnection(getApplicationContext());
            }
        });
    }

    private void refreshSearchData(String userToken, final SearchAdapter adapter) {
        Call<GetSearchesResponse> request = service.getSearches(userToken);

        request.enqueue(new Callback<GetSearchesResponse>() {
            @Override
            public void onResponse(Call<GetSearchesResponse> call, Response<GetSearchesResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    adapter.clear();
                    adapter.addAll(response.body().getSearches());
                }
            }

            @Override
            public void onFailure(Call<GetSearchesResponse> call, Throwable t) {

            }
        });
    }
}