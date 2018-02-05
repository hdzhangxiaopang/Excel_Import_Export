package entity;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangguilin on 2/1/2018.
 */
@Data
public abstract class ImportResult<E> {
    public abstract List<E> getResult();
    private String resMsg; //返回信息
}
