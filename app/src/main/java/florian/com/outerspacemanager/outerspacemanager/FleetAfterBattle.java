package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by fcavasin on 14/05/2018.
 */

class FleetAfterBattle {
    private Integer capacity;
    private List<Ship> fleet = null;
    private Integer survivingShips;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(List<Ship> fleet) {
        this.fleet = fleet;
    }

    public Integer getSurvivingShips() {
        return survivingShips;
    }

    public void setSurvivingShips(Integer survivingShips) {
        this.survivingShips = survivingShips;
    }
}
