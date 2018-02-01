package excel_import.common;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@Data
public class Configuration {
    private Integer startRowNumber; //读取的起始行 起始为0
    private ImportFileType importFileType;
    private Integer resultType; //返回数据类型 返回maps 该方法暂时无用
    private List<ImportCell> importCells;

    public enum PasreType{
        XML
    }
    public enum ImportFileType{
        EXCEL
    }
}
