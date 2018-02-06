package entity;

import lombok.Data;

/**
 * Created by zhangguilin on 2/5/2018.
 */
@Data
public class Api {
    private String ID;
    private String BACKEND_SYSTEM_ID;
    private String NAME;
    private String DESCRIPTION;
    private String METHOD;
    private String PATH;
    private Integer SCHEME;
    private Integer SECURITY_SCHEME;
    private Integer STATE;
    private Integer LIMIT_COUNT;
    private Integer LIMIT_TIME;
    private String LIMIT_UNIT;
    private Integer IS_DELETE;
}
