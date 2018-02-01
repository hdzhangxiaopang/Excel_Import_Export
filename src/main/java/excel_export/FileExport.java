package excel_export;

import excel_export.common.ExportConfig;
import excel_export.common.ExportResult;
import excel_export.common.ExportType;
import excel_export.excel.ExportCSVResult;
import excel_export.excel.ExportExcelResult;
import excel_export.exception.FileExportException;
import excel_export.service.impl.CSVExport;
import excel_export.service.impl.ExcelExport;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class FileExport {
    public final static String EXPORT_XML_BASE_PATH = "/properties/framework/export/";

    /**
     * 通过List<T> T可为bean或者Map<String,Object> 导出文件
     * */
    public static ExportResult getExportResult(ExportConfig exportConfig, List<?> data) throws FileExportException{
        ExportType exportType = exportConfig.getExportType();
        switch (exportType){
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
        throw new FileExportException("找不到对应的export type, export type is " + exportType.getNumber());

    }
}
