package florian.com.outerspacemanager.outerspacemanager;

import java.util.List;

/**
 * Created by PC  FLORIAN on 24/01/2018.
 */

public class PostAttackPlayerResponse {

    private String code;
    private Long attackTime;

    public PostAttackPlayerResponse(String code, Long attackTime) {
        this.code = code;
        this.attackTime = attackTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getAttackTime() {
        return attackTime;
    }

    public void setAttackTime(Long attackTime) {
        this.attackTime = attackTime;
    }
}