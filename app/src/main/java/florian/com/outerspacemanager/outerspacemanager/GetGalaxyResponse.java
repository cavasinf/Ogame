package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by PC  FLORIAN on 04/03/2018.
 */

public class GetGalaxyResponse {


    private Integer size;
    private List<GalaxyUser> users;

    public GetGalaxyResponse(Integer size, List<GalaxyUser> users) {
        this.size = size;
        this.users = users;
    }

    public Integer getSize() {
        return size;
    }

    public List<GalaxyUser> getUsers() {
        return users;
    }
}
