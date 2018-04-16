package florian.com.outerspacemanager.outerspacemanager;

import android.os.Environment;
import android.text.format.Time;

import java.util.Date;
import java.util.UUID;

/**
 * Created by PC  FLORIAN on 24/01/2018.
 */

public class Building {

    private UUID id;
    private Integer level;
    private Integer amountOfEffectByLevel;
    private Integer amountOfEffectLevel0;
    private Integer buildingId;
    private Boolean building;
    private String effect;
    private Integer gasCostByLevel;
    private Integer gasCostLevel0;
    private String imageUrl;
    private Integer mineralCostByLevel;
    private Integer mineralCostLevel0;
    private String name;
    private Integer timeToBuildByLevel;
    private Integer timeToBuildLevel0;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getAmountOfEffectByLevel() {
        return amountOfEffectByLevel;
    }

    public Integer getAmountOfEffectLevel0() {
        return amountOfEffectLevel0;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public Boolean isBuilding() {
        return building;
    }

    public String getEffect() {
        return effect;
    }

    public Integer getGasCostByLevel() {
        return gasCostByLevel;
    }

    public Integer getGasCostLevel0() {
        return gasCostLevel0;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getMineralCostByLevel() {
        return mineralCostByLevel;
    }

    public Integer getMineralCostLevel0() {
        return mineralCostLevel0;
    }

    public String getName() {
        return name;
    }

    public Integer getTimeToBuildByLevel() {
        return timeToBuildByLevel;
    }

    public Integer getTimeToBuildLevel0() {
        return timeToBuildLevel0;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setAmountOfEffectByLevel(Integer amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
    }

    public void setAmountOfEffectLevel0(Integer amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public void setIsBuilding(Boolean building) {
        this.building = building;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setGasCostByLevel(Integer gasCostByLevel) {
        this.gasCostByLevel = gasCostByLevel;
    }

    public void setGasCostLevel0(Integer gasCostLevel0) {
        this.gasCostLevel0 = gasCostLevel0;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMineralCostByLevel(Integer mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
    }

    public void setMineralCostLevel0(Integer mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeToBuildByLevel(Integer timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
    }

    public void setTimeToBuildLevel0(Integer timeToBuildLevel0) {
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }

    // ============== CUSTOM ==============

    public Integer getTimeToBuild(boolean isItForDatabase) {
        Integer timeWithoutSpeedBuilding;

        if (!isItForDatabase)
            timeWithoutSpeedBuilding = timeToBuildLevel0 + level * timeToBuildByLevel;
        else
            timeWithoutSpeedBuilding =  timeToBuildLevel0 + (level - 1) * timeToBuildByLevel;

        return timeWithoutSpeedBuilding;
    }

    public Integer getBuildingTimeLeftWithoutEffects(String timeLaunched){
        int currentTime = (int) (new Date().getTime()/1000);
        Integer timeBetween = currentTime - Integer.parseInt(timeLaunched);
        return getTimeToBuild(false) - timeBetween;
    }

    public Integer getCostMineral (Building building,Boolean... isCheckAfterConstruction){
        double cost;
        if (building.getLevel() == 0)
            cost = building.getMineralCostLevel0();
        else {
            cost = building.getMineralCostLevel0() + building.getMineralCostByLevel()*building.getLevel();
        }
        return (int) Math.round(cost);
    }

    public Integer getCostGas (Building building){
        double cost;
        if (building.getLevel() == 0)
            cost = building.getGasCostLevel0();
        else {
            cost = building.getGasCostLevel0() + building.getGasCostByLevel()*building.getLevel();
        }
        return (int) Math.round(cost);
    }

}
