package florian.com.outerspacemanager.outerspacemanager;

/**
 * Created by fcavasin on 16/01/2018.
 */

public class CreateUserResponse {
    private String token;
    private String expires;

    public CreateUserResponse(String token, String expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
