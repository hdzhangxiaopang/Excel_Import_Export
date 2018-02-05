package entity;

import lombok.Data;

/**
 * Created by zhangguilin on 2/5/2018.
 */
@Data
public class API {
    private String ID;
    private String BACKEND_SYSTEM_ID;
    private String NAME;
    private String DESCRIPTION;
    private String METHOD;
    private String PATH;
    private String SCHEME;
    private String SECURITY_SCHEME;
    private String STATE;
    private String LIMIT_COUNT;
    private String LIMIT_TIME;
    private String LIMIT_UNIT;
    private String IS_DELETE;
}
