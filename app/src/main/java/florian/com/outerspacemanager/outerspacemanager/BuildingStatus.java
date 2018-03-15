package florian.com.outerspacemanager.outerspacemanager;

import java.util.UUID;


public class BuildingStatus {

    private UUID id;
    private String buildingId;
    private String building;
    private String dateConstruction;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDateConstruction() {
        return dateConstruction;
    }

    public void setDateConstruction(String dateConstruction) {
        this.dateConstruction = dateConstruction;
    }
}
