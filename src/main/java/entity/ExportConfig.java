package entity;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@Data
public class ExportConfig {
    private String fileName; //输出的文件名
    private ExportType exportType; //0表示 excel, 1表示 csv
    private List<ExportCell> exportCells;
}
