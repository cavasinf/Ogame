package florian.com.outerspacemanager.outerspacemanager;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class Search {

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

    public Search(Integer level, Boolean building, Integer amountOfEffectByLevel, Integer amountOfEffectLevel0, String effect, Integer gasCostByLevel, Integer gasCostLevel0, Integer mineralCostByLevel, Integer mineralCostLevel0, String name, Integer searchId, Integer timeToBuildByLevel, Integer timeToBuildLevel0) {
        this.level = level;
        this.building = building;
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        this.effect = effect;
        this.gasCostByLevel = gasCostByLevel;
        this.gasCostLevel0 = gasCostLevel0;
        this.mineralCostByLevel = mineralCostByLevel;
        this.mineralCostLevel0 = mineralCostLevel0;
        this.name = name;
        this.searchId = searchId;
        this.timeToBuildByLevel = timeToBuildByLevel;
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }

    public Integer getLevel() {
        return level;
    }

    public Boolean getBuilding() {
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
}
