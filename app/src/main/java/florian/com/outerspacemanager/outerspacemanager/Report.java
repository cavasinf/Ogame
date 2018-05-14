package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by fcavasin on 14/05/2018.
 */

class Report {

    private List<AttackerFleet> attackerFleet = null;
    private FleetAfterBattle fleetAfterBattle;
    private Integer date;
    private List<Ship> defenderFleet = null;
    private FleetAfterBattle defenderFleetAfterBattle;
    private String from;
    private Double gasWon;
    private Double mineralsWon;
    private String to;
    private String type;

    public List<AttackerFleet> getAttackerFleet() {
        return attackerFleet;
    }

    public void setAttackerFleet(List<AttackerFleet> attackerFleet) {
        this.attackerFleet = attackerFleet;
    }

    public FleetAfterBattle getFleetAfterBattle() {
        return fleetAfterBattle;
    }

    public void setFleetAfterBattle(FleetAfterBattle fleetAfterBattle) {
        this.fleetAfterBattle = fleetAfterBattle;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public List<Ship> getDefenderFleet() {
        return defenderFleet;
    }

    public void setDefenderFleet(List<Ship> defenderFleet) {
        this.defenderFleet = defenderFleet;
    }

    public FleetAfterBattle getDefenderFleetAfterBattle() {
        return defenderFleetAfterBattle;
    }

    public void setDefenderFleetAfterBattle(FleetAfterBattle defenderFleetAfterBattle) {
        this.defenderFleetAfterBattle = defenderFleetAfterBattle;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Double getGasWon() {
        return gasWon;
    }

    public void setGasWon(Double gasWon) {
        this.gasWon = gasWon;
    }

    public Double getMineralsWon() {
        return mineralsWon;
    }

    public void setMineralsWon(Double mineralsWon) {
        this.mineralsWon = mineralsWon;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
