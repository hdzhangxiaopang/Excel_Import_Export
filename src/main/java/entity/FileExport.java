package entity;

import base.constants.UtilConstants;
import exception.FileExportException;
import org.apache.poi.ss.usermodel.Workbook;
import service.impl.CSVExport;
import service.impl.ExcelExport;

import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class FileExport {

    /**
     * 通过List<T> T可为bean或者Map<String,Object> 导出文件
     */
    public static ExportResult getExportResult(ExportConfig exportConfig, List<?> data) throws FileExportException {
        ExportType exportType = exportConfig.getExportType();
        switch (exportType) {
            case EXCEL_2007:
                Workbook workbook = new ExcelExport().getExportResult(data, exportConfig.getExportCells());
                ExportExcelResult exportExcelResult = new ExportExcelResult();
                exportExcelResult.setWorkbook(workbook);
                exportExcelResult.setFileName(exportConfig.getFileName());
                return exportExcelResult;
            case CSV:
                StringBuilder stringBuilder = new CSVExport().getExportResult(data, exportConfig.getExportCells());
                ExportCSVResult exportCSVResult = new ExportCSVResult();
                exportCSVResult.setResult(stringBuilder.toString());
                exportCSVResult.setFileName(exportConfig.getFileName());
                return exportCSVResult;

        }
        throw new FileExportException(UtilConstants.EXPORT_FILE_TYPE_NOTFOUND + exportType.getNumber());

    }
}
