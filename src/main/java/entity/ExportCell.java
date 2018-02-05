package entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@Data
public class ExportCell {

    private String name;
    private String title; //导出标题中文
    private String alias; //对应的别名
    private String width; //单元格的宽度
    private Export export; //是否导出

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Export{
        NOT_EXPORT(0),
        EXPORT(1);
        private int value;

        public static Export getExport(int value){
            switch (value) {
                case 0:
                    return NOT_EXPORT;
                case 1:
                    return EXPORT;
                default:
                    return EXPORT;
            }
        }
    }

}
