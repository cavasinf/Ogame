package florian.com.outerspacemanager.outerspacemanager;


import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by PC  FLORIAN on 24/01/2018.
 */

public class BuildingRepository {

    public String CST_BUILDING_EFFECT_SPEED_BUILDING = "speed_building";
    public String CST_BUILDING_EFFECT_SPEED_FLEET = "speed_fleet";
    public String CST_BUILDING_EFFECT_MINERAL_MODIFIER = "mineral_modifier";

    private Retrofit retrofit = Constant.retrofit;
    final ApiService service = retrofit.create(ApiService.class);
    private List<Building> BuildingListReceive;


    public Building getBuildingByEffect(String userToken, String effectToSearch) throws Exception {

        // TODO put building in DB

        Call<GetBuildingsResponse> request = service.getUserBuildings(userToken);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                if (response.code() > 199 && response.code() < 301) {
                    BuildingListReceive = (List<Building>) response.body().getBuildings();
                    //TODO effect building
                    for (Building building : BuildingListReceive) {
                        /*if (Objects.equals(building.getEffect(), effectToSearch))
                            return building;*/
                    }
                }

            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {

            }
        });
return null;
    }

}
