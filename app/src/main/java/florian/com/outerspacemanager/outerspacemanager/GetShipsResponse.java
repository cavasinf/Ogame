package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class GetShipsResponse {


    private Double currentUserMinerals;
    private Double currentUserGas;
    private Integer size;
    private List<Ship> ships = null;

    public Double getCurrentUserMinerals() {
        return currentUserMinerals;
    }

    public void setCurrentUserMinerals(Double currentUserMinerals) {
        this.currentUserMinerals = currentUserMinerals;
    }

    public Double getCurrentUserGas() {
        return currentUserGas;
    }

    public void setCurrentUserGas(Double currentUserGas) {
        this.currentUserGas = currentUserGas;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

}
