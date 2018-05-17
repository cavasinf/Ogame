package florian.com.outerspacemanager.outerspacemanager;

import java.util.Date;
import java.util.UUID;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class ShipForAttack {

    private Integer shipId;
    private Integer amount;

    public ShipForAttack(Integer shipId, Integer amount) {
        this.shipId = shipId;
        this.amount = amount;
    }

    public Integer getShipId() {
        return shipId;
    }

    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

