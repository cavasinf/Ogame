package florian.com.outerspacemanager.outerspacemanager;

import java.util.Date;
import java.util.UUID;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class ShipForAttack {

    private Integer id;
    private Integer amount;

    public ShipForAttack(Integer id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

