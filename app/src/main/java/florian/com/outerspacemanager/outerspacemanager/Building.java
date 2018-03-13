package florian.com.outerspacemanager.outerspacemanager;

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
}
