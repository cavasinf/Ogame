package florian.com.outerspacemanager.outerspacemanager;

import java.util.Date;
import java.util.UUID;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class Search {

    private UUID id;
    private Integer level;
    private Boolean building;
    private Integer amountOfEffectByLevel;
    private Integer amountOfEffectLevel0;
    private String effect;
    private Integer gasCostByLevel;
    private Integer gasCostLevel0;
    private Integer mineralCostByLevel;
    private Integer mineralCostLevel0;
    private String name;
    private Integer searchId;
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

    public Boolean isBuilding() {
        return building;
    }

    public Integer getAmountOfEffectByLevel() {
        return amountOfEffectByLevel;
    }

    public Integer getAmountOfEffectLevel0() {
        return amountOfEffectLevel0;
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

    public Integer getMineralCostByLevel() {
        return mineralCostByLevel;
    }

    public Integer getMineralCostLevel0() {
        return mineralCostLevel0;
    }

    public String getName() {
        return name;
    }

    public Integer getSearchId() {
        return searchId;
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

    public void setIsBuilding(Boolean building) {
        this.building = building;
    }

    public void setAmountOfEffectByLevel(Integer amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
    }

    public void setAmountOfEffectLevel0(Integer amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
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

    public void setMineralCostByLevel(Integer mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
    }

    public void setMineralCostLevel0(Integer mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public void setTimeToBuildByLevel(Integer timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
    }

    public void setTimeToBuildLevel0(Integer timeToBuildLevel0) {
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }

    // ============== CUSTOM ===============

    public Integer getTimeToBuild(boolean isItForDatabase) {
        Integer timeWithoutSpeedBuilding;
        if (!isItForDatabase)
            timeWithoutSpeedBuilding = timeToBuildLevel0 + level * timeToBuildByLevel;
        else
            timeWithoutSpeedBuilding =  timeToBuildLevel0 + (level - 1) * timeToBuildByLevel;

        return timeWithoutSpeedBuilding;
    }

    public Integer getSearchTimeLeft(String timeLaunched){
        int currentTime = (int) (new Date().getTime()/1000);
        Integer timeBetween = currentTime - Integer.parseInt(timeLaunched);
        return getTimeToBuild(false) - timeBetween;
    }
}
