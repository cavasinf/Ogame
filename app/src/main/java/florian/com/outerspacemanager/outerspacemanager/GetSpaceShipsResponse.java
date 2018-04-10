package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class GetSpaceShipsResponse {


    private Integer size;
    private List<Ship> ships = null;

    public Integer getSize() {
        return size;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
