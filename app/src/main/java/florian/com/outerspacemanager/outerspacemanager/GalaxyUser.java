package florian.com.outerspacemanager.outerspacemanager;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class GalaxyUser {

    private long points;
    private String username;

    public GalaxyUser(Integer points, String username) {
        this.points = points;
        this.username = username;
    }

    public Long getPoints() {
        return points;
    }

    public String getUsername() {
        return username;
    }
}
