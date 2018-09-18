package entity;

import lombok.Data;

/**
 * Created by zhangguilin on 2/5/2018.
 */
@Data
public class Api {
    private String id;
    private String backendSystemId;
    private String name;
    private String description;
    private String method;
    private String path;
    private Integer scheme;
    private Integer securityScheme;
    private Integer state;
    private Integer limitCount;
    private Integer limitTime;
    private String limitUnit;
    private Integer isDelete;
}
