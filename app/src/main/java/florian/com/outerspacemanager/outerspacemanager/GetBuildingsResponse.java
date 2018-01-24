package florian.com.outerspacemanager.outerspacemanager;

/**
 * Created by PC  FLORIAN on 24/01/2018.
 */

public class GetBuildingsResponse {

    private Integer size;
    private Building[] buildings = new Building[size];

    public GetBuildingsResponse(Integer size, Building[] buildings) {
        this.size = size;
        this.buildings = buildings;
    }

    public Integer getSize() {
        return size;
    }

    public Building[] getBuildings() {
        return buildings;
    }
}
