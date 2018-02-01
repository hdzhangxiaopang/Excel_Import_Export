package excel_import.service.impl;

import base.constants.UtilConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import excel_import.common.Configuration;
import excel_import.common.ImportCell;
import excel_import.common.ImportResult;
import excel_import.common.MapResult;
import excel_import.exception.FileImportException;
import excel_import.service.FileImport;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class ExcelImport extends FileImport {

    private Configuration configuration;

    @Override
    public ImportResult getImportResult(File file, String fileName) throws FileImportException {
        if(configuration == null){
            throw new FileImportException(UtilConstants.IMPORT_CONFIGURATION_ISNULL);
        }
        StringBuilder builder = new StringBuilder();
        Workbook workbook = null;
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
        if("xls".equals(extension)){
            try {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                throw new FileImportException(e,e.getMessage());
            }
        }else if("xlsx".equals(extension)){
            try {
                workbook= new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                throw new FileImportException(e,e.getMessage());
            }
        }else{
            throw new FileImportException(UtilConstants.IMPORT_UNSUPPORT_FILE_STYLE);
        }
        List<Map> result = readExcel(workbook, configuration, builder);
        MapResult mapResult = new MapResult();
        mapResult.setResult(result);
        mapResult.setResMsg(builder.toString());
        return mapResult;
    }

    private List<Map> readExcel(Workbook workbook, Configuration configuration, StringBuilder builder) {
        Sheet sheet = workbook.getSheetAt(0);
        int startRowNumber = configuration.getStartRowNumber();
        List<ImportCell> importCells = configuration.getImportCells();
        int phyRows = sheet.getPhysicalNumberOfRows();
        List<Map> results = Lists.newLinkedList();
        for (int i = startRowNumber; i < phyRows; i++) {
            Row row = sheet.getRow(i);
            if(row == null){
                continue;
            }
            /**
             * poi获取正确的行数很难，这里约定前三个值都为空，自动放弃该行。
             * */
            if(isCellEmpty(row.getCell(0)) && isCellEmpty(row.getCell(1)) && isCellEmpty(row.getCell(2))){
                continue;
            }
            Map<String, Object> maps = Maps.newLinkedHashMap();
            maps.put(MapResult.IS_LINE_LEGAL_KEY,true);
            for (ImportCell importCell : importCells){
                setValue(maps,importCell,row,builder,i,startRowNumber);
            }
            results.add(maps);

        }
        return results;
    }

    private void setValue(Map<String, Object> maps, ImportCell importCell, Row row, StringBuilder builder, int i, int startRowNumber) {

    }

    private boolean isCellEmpty(Cell cell){
        if(cell == null){
            return true;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BLANK){
            return true;
        }
        if(cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isEmpty(cell.getStringCellValue())){
            return true;
        }
        return false;
    }
}
