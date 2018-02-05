package entity;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@Data
public class ImportConfig {
    private Integer startRowNumber; //读取的起始行 起始为0
    private ImportFileType importFileType;
    private Integer resultType; //返回数据类型 返回maps 该方法暂时无用
    private List<ImportCell> importCells;

    public enum ParserType {
        XML
    }

    public enum ImportFileType {
        EXCEL
    }
}
