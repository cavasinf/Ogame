package florian.com.outerspacemanager.outerspacemanager;

import java.io.Serializable;
import java.util.*;

/**
 * Created by fcavasin on 16/01/2018.
 */

public class User implements Serializable {
    private String email;
    private String username;
    private String password;
    private String token;
    private Float gas;
    private Float gasModifier;
    private Float minerals;
    private Float mineralsModifier;
    private Float points;

    public User(User user, String token, Float gas, Float gasModifier, Float minerals, Float mineralsModifier, Float points) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.token = token;
        this.gas = gas;
        this.gasModifier = gasModifier;
        this.minerals = minerals;
        this.mineralsModifier = mineralsModifier;
        this.points = points;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.email = null;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Float getGas() {
        return gas;
    }

    public Float getGasModifier() {
        return gasModifier;
    }

    public Float getMinerals() {
        return minerals;
    }

    public Float getMineralsModifier() {
        return mineralsModifier;
    }

    public Float getPoints() {
        return points;
    }

    public void setGas(Float gas) {
        this.gas = gas;
    }

    public void setMinerals(Float minerals) {
        this.minerals = minerals;
    }
}
