package florian.com.outerspacemanager.outerspacemanager;

/**
 * Created by fcavasin on 23/01/2018.
 */

public class GetUserResponse {

    private Float gas;
    private Float gasModifier;
    private Float minerals;
    private Float mineralsModifier;
    private Float points;

    public GetUserResponse(Float gas, Float gasModifier, Float minerals, Float mineralsModifier, Float points) {
        this.gas = gas;
        this.gasModifier = gasModifier;
        this.minerals = minerals;
        this.mineralsModifier = mineralsModifier;
        this.points = points;
    }

    public Float getGas() {
        return gas;
    }

    public void setGas(Float gas) {
        this.gas = gas;
    }

    public Float getGasModifier() {
        return gasModifier;
    }

    public void setGasModifier(Float gasModifier) {
        this.gasModifier = gasModifier;
    }

    public Float getMinerals() {
        return minerals;
    }

    public void setMinerals(Float minerals) {
        this.minerals = minerals;
    }

    public Float getMineralsModifier() {
        return mineralsModifier;
    }

    public void setMineralsModifier(Float mineralsModifier) {
        this.mineralsModifier = mineralsModifier;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }
}
