package florian.com.outerspacemanager.outerspacemanager;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class Ship {

    private Integer capacity;
    private Integer gasCost;
    private Integer life;
    private Integer maxAttack;
    private Integer minAttack;
    private Integer mineralCost;
    private String name;
    private Integer shield;
    private Integer shipId;
    private Integer spatioportLevelNeeded;
    private Integer speed;
    private Integer timeToBuild;

    public Ship(Integer capacity, Integer gasCost, Integer life, Integer maxAttack, Integer minAttack, Integer mineralCost, String name, Integer shield, Integer shipId, Integer spatioportLevelNeeded, Integer speed, Integer timeToBuild) {
        this.capacity = capacity;
        this.gasCost = gasCost;
        this.life = life;
        this.maxAttack = maxAttack;
        this.minAttack = minAttack;
        this.mineralCost = mineralCost;
        this.name = name;
        this.shield = shield;
        this.shipId = shipId;
        this.spatioportLevelNeeded = spatioportLevelNeeded;
        this.speed = speed;
        this.timeToBuild = timeToBuild;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getGasCost() {
        return gasCost;
    }

    public Integer getLife() {
        return life;
    }

    public Integer getMaxAttack() {
        return maxAttack;
    }

    public Integer getMinAttack() {
        return minAttack;
    }

    public Integer getMineralCost() {
        return mineralCost;
    }

    public String getName() {
        return name;
    }

    public Integer getShield() {
        return shield;
    }

    public Integer getShipId() {
        return shipId;
    }

    public Integer getSpatioportLevelNeeded() {
        return spatioportLevelNeeded;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getTimeToBuild() {
        return timeToBuild;
    }
}
