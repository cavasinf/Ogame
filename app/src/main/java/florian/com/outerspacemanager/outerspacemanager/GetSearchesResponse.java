package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by PC  FLORIAN on 24/01/2018.
 */

public class GetSearchesResponse {

    private Integer size;
    private List<Search> searches;

    public GetSearchesResponse(Integer size, List<Search> searches) {
        this.size = size;
        this.searches = searches;
    }

    public Integer getSize() {
        return size;
    }

    public List<Search> getSearches() {
        return searches;
    }
}