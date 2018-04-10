package florian.com.outerspacemanager.outerspacemanager;

import java.util.UUID;


public class SearchStatus {

    private UUID id;
    private String searchId;
    private String searching;
    private String dateSearching;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSearching() {
        return searching;
    }

    public void setSearching(String searching) {
        this.searching = searching;
    }

    public String getDateSearching() {
        return dateSearching;
    }

    public void setDateSearching(String dateSearching) {
        this.dateSearching = dateSearching;
    }
}
