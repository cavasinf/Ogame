package florian.com.outerspacemanager.outerspacemanager;

import java.util.Date;
import java.util.UUID;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class Ship {

    private UUID id;
    private Integer amount;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getGasCost() {
        return gasCost;
    }

    public void setGasCost(Integer gasCost) {
        this.gasCost = gasCost;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Integer getMaxAttack() {
        return maxAttack;
    }

    public void setMaxAttack(Integer maxAttack) {
        this.maxAttack = maxAttack;
    }

    public Integer getMinAttack() {
        return minAttack;
    }

    public void setMinAttack(Integer minAttack) {
        this.minAttack = minAttack;
    }

    public Integer getMineralCost() {
        return mineralCost;
    }

    public void setMineralCost(Integer mineralCost) {
        this.mineralCost = mineralCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShield() {
        return shield;
    }

    public void setShield(Integer shield) {
        this.shield = shield;
    }

    public Integer getShipId() {
        return shipId;
    }

    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    public Integer getSpatioportLevelNeeded() {
        return spatioportLevelNeeded;
    }

    public void setSpatioportLevelNeeded(Integer spatioportLevelNeeded) {
        this.spatioportLevelNeeded = spatioportLevelNeeded;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getTimeToBuild() {
        return timeToBuild;
    }

    public void setTimeToBuild(Integer timeToBuild) {
        this.timeToBuild = timeToBuild;
    }

    // ============== CUSTOM ==============

    public Integer getBuildingTimeLeftWithoutEffects(String timeLaunched){
        int currentTime = (int) (new Date().getTime()/1000);
        Integer timeBetween = currentTime - Integer.parseInt(timeLaunched);
        return getTimeToBuild() - timeBetween;
    }
}

