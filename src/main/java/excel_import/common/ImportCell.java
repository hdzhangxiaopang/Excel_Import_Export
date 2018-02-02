package excel_import.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@Data
public class ImportCell {

    private Integer number; //excel对应的序列,起始为0
    private String key; //存储的map对应的key
    private CellType cellType; //0:int,1:float,2:string,3:date,4:bigDecimal
    private NullAble nullAble; //是否允许为空 0:允许 ,1:不允许

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum NullAble{
        NUll_ALLOWED(0),
        NULL_NOT_ALLOWED(1);
        private final int val;

        public static NullAble getNullAble(int val){
            switch (val){
                case 0:
                    return NUll_ALLOWED;
                case 1:
                    return NULL_NOT_ALLOWED;
                default:
                    return NUll_ALLOWED;
            }
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum CellType{
        INT(0,"int"),
        FLOAT(1,"float"),
        STRING(2,"string"),
        DATE(3,"date"),
        BIGDECIMAL(4,"bigdecimal"),
        DOUBLE(5,"double");

        private final int type;
        private final String description;

        public static CellType getCellType(int val){
            switch (val){
                case 0:
                    return INT;
                case 1:
                    return FLOAT;
                case 2:
                    return STRING;
                case 3:
                    return DATE;
                case 4:
                    return BIGDECIMAL;
                case 5:
                    return DOUBLE;
                default:
                    return STRING;
            }
        }

        public static CellType getCellType(String str){
            switch (str){
                case "Int":
                    return INT;
                case "Float":
                    return FLOAT;
                case "String":
                    return STRING;
                case "Date":
                    return DATE;
                case "BigDecimal":
                    return BIGDECIMAL;
                case "Double":
                    return DOUBLE;
                default:
                    return STRING;
            }
        }

    }

}
