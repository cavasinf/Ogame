package florian.com.outerspacemanager.outerspacemanager;

import android.text.format.Time;

import java.util.Date;

/**
 * Created by PC  FLORIAN on 24/01/2018.
 */

public class Building {

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

    public Building(Integer level, Integer amountOfEffectByLevel, Integer amountOfEffectLevel0, Integer buildingId, Boolean building, String effect, Integer gasCostByLevel, Integer gasCostLevel0, String imageUrl, Integer mineralCostByLevel, Integer mineralCostLevel0, String name, Integer timeToBuildByLevel, Integer timeToBuildLevel0) {
        this.level = level;
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        this.buildingId = buildingId;
        this.building = building;
        this.effect = effect;
        this.gasCostByLevel = gasCostByLevel;
        this.gasCostLevel0 = gasCostLevel0;
        this.imageUrl = imageUrl;
        this.mineralCostByLevel = mineralCostByLevel;
        this.mineralCostLevel0 = mineralCostLevel0;
        this.name = name;
        this.timeToBuildByLevel = timeToBuildByLevel;
        this.timeToBuildLevel0 = timeToBuildLevel0;
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

    public Integer getTimeToBuild(boolean isItForDatabase) {
        Integer timeWithoutSpeedBuilding;
        if (!isItForDatabase)
            timeWithoutSpeedBuilding = timeToBuildLevel0 + level * timeToBuildByLevel;git add
        else
            timeWithoutSpeedBuilding =  timeToBuildLevel0 + (level - 1) * timeToBuildByLevel;

        return timeWithoutSpeedBuilding; //TODO minus speed_effect
    }

    public Integer getBuildingTimeLeft(String timeLaunched){
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
