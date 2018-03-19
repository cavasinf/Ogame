package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;
import java.util.Objects;

/**
 * Created by PC  FLORIAN on 24/01/2018.
 */

public class GetBuildingsResponse {


    private Integer size;
    private List<Building> buildings;

    public GetBuildingsResponse(Integer size, List<Building> buildings) {
        this.size = size;
        this.buildings = buildings;
    }

    public Integer getSize() {
        return size;
    }

    public List<Building> getBuildings() {
        return buildings;
    }


}