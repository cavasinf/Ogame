package florian.com.outerspacemanager.outerspacemanager;

import java.util.UUID;


public class ShipStatus {

    private UUID id;
    private String shipId;
    private String number;
    private String dateConstructionLaunch;
    private String dateConstructionEnd;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateConstructionLaunch() {
        return dateConstructionLaunch;
    }

    public void setDateConstructionLaunch(String dateConstructionLaunch) {
        this.dateConstructionLaunch = dateConstructionLaunch;
    }

    public String getDateConstructionEnd() {
        return dateConstructionEnd;
    }

    public void setDateConstructionEnd(String dateConstructionEnd) {
        this.dateConstructionEnd = dateConstructionEnd;
    }
}
