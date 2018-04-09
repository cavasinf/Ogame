package florian.com.outerspacemanager.outerspacemanager;

/**
 * Created by fcavasin on 05/03/2018.
 */

public class CreateShipsResponse {

    private String internalCode;

    public CreateShipsResponse(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getCode() {
        return internalCode;
    }
}
