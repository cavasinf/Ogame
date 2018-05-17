package florian.com.outerspacemanager.outerspacemanager;

import java.util.UUID;


public class FleetAttackStatus {

    private UUID id;
    private String playerToAttack;
    private String dateAttackReturn;
    private String fleet;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlayerToAttack() {
        return playerToAttack;
    }

    public void setPlayerToAttack(String playerToAttack) {
        this.playerToAttack = playerToAttack;
    }

    public String getDateAttackReturn() {
        return dateAttackReturn;
    }

    public void setDateAttackReturn(String dateAttackReturn) {
        this.dateAttackReturn = dateAttackReturn;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }
}
