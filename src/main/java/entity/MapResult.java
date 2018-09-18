package entity;

import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by zhangguilin on 2/1/2018.
 */
@EqualsAndHashCode
public class MapResult<Map> extends ImportResult {

    public static final String LINE_NUM_KEY = "lineNum";

    public static final String IS_LINE_LEGAL_KEY = "isLineLegal";

    private List<Map> result;

    @Override
    public List<Map> getResult() {
        return result;
    }

    public void setResult(List<Map> result) {
        this.result = result;
    }

}
