package excel_export.service;

import excel_export.common.ExportCell;
import excel_export.exception.FileExportException;

import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public interface FileExport {

    /**
     * 数据导出
     * */
    public Object getExportResult(List<?> data, List<ExportCell> exportCells) throws FileExportException;
}
