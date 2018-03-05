package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class GetGalaxyResponse {


    private List<GalaxyUser> users;

    public GetGalaxyResponse( List<GalaxyUser> users) {
        this.users = users;
    }

    public List<GalaxyUser> getUsers() {
        return users;
    }
}
