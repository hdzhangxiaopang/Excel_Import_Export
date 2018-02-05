package service.impl;

import base.constants.UtilConstants;
import base.util.EmptyUtil;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import entity.ImportConfig;
import entity.ImportCell;
import entity.ImportResult;
import entity.MapResult;
import exception.FileImportException;
import service.FileImport;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class ExcelImport extends FileImport {

    private ImportConfig importConfig;

    public ExcelImport(ImportConfig importConfig) {
        this.importConfig = importConfig;
    }

    @Override
    public ImportResult getImportResult(File file, String fileName) throws FileImportException {
        if (EmptyUtil.isEmpty(importConfig)) {
            throw new FileImportException(UtilConstants.IMPORT_CONFIGURATION_ISNULL);
        }
        StringBuilder builder = new StringBuilder();
        Workbook workbook = null;
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            try {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                throw new FileImportException(e, e.getMessage());
            }
        } else if ("xlsx".equals(extension)) {
            try {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                throw new FileImportException(e, e.getMessage());
            }
        } else {
            throw new FileImportException(UtilConstants.IMPORT_UNSUPPORT_FILE_STYLE);
        }
        List<Map> result = readExcel(workbook, importConfig, builder);
        MapResult mapResult = new MapResult();
        mapResult.setResult(result);
        mapResult.setResMsg(builder.toString());
        return mapResult;
    }

    private List<Map> readExcel(Workbook workbook, ImportConfig importConfig, StringBuilder builder) {
        Sheet sheet = workbook.getSheetAt(0);
        int startRowNumber = importConfig.getStartRowNumber();
        List<ImportCell> importCells = importConfig.getImportCells();
        int phyRows = sheet.getPhysicalNumberOfRows();
        List<Map> results = Lists.newLinkedList();
        for (int i = startRowNumber; i < phyRows; i++) {
            Row row = sheet.getRow(i);
            if (EmptyUtil.isEmpty(row)) {
                continue;
            }
            /**
             * poi获取正确的行数很难，这里约定前三个值都为空，自动放弃该行。
             * */
            if (isCellEmpty(row.getCell(0)) && isCellEmpty(row.getCell(1)) && isCellEmpty(row.getCell(2))) {
                continue;
            }
            Map<String, Object> maps = Maps.newLinkedHashMap();
            maps.put(MapResult.IS_LINE_LEGAL_KEY, true);
            for (ImportCell importCell : importCells) {
                setValue(maps, importCell, row, builder, i, startRowNumber);
            }
            results.add(maps);

        }
        return results;
    }

    private void setValue(Map<String, Object> maps, ImportCell importCell, Row row, StringBuilder builder, int line, int startRowNumber) {
        int num = importCell.getNumber();
        int showLine = line + startRowNumber;
        int showColumn = num + startRowNumber;
        maps.put(MapResult.LINE_NUM_KEY, showLine);
        ImportCell.CellType cellType = importCell.getCellType();
        ImportCell.NullAble nullAble = importCell.getNullAble();
        String errMsg = null;
        String key = importCell.getKey();
        Cell cell = row.getCell(num);
        int rawCellType = Cell.CELL_TYPE_BLANK;
        if (EmptyUtil.isNotEmpty(cell)) {
            rawCellType = cell.getCellType();
        }
        if (EmptyUtil.isEmpty(cell)|| rawCellType == Cell.CELL_TYPE_BLANK || (rawCellType == Cell.CELL_TYPE_STRING && StringUtils.isEmpty(cell.getStringCellValue()))) {
            if (nullAble == ImportCell.NullAble.NUll_ALLOWED) {
                maps.put(key, Optional.absent());
            } else {
                errMsg = String.format("line:%d,column:%d is null,but null is not allowed by setting \n", showLine, showColumn);
                setErrMsg(errMsg, maps, builder);
            }
        } else {
            switch (cellType) {
                case INT:
                    if (rawCellType == Cell.CELL_TYPE_STRING) {
                        String temp = cell.getStringCellValue();
                        if (!StringUtils.isNumeric(temp)) {
                            errMsg = String.format("line:%d,column:%d is not number \n", showLine, showColumn);
                            setErrMsg(errMsg, maps, builder);
                        }
                        maps.put(key, Integer.valueOf(temp));
                    }
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double temp = cell.getNumericCellValue();
                        maps.put(key, temp.intValue());
                    }
                    break;

                case STRING:
                    String temp = null;
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        temp = String.valueOf(cell.getNumericCellValue());
                        maps.put(key, temp);
                    }
                    if (rawCellType == Cell.CELL_TYPE_STRING) {
                        temp = cell.getStringCellValue();
                        maps.put(key, temp);
                    }
                    errMsg = String.format("line:%d,column:%d is not string\n", showLine, showColumn);
                    setErrMsg(errMsg, maps, builder);
                    break;

                case FLOAT:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double tempA = cell.getNumericCellValue();
                        maps.put(key, tempA.floatValue());
                    } else {
                        errMsg = String.format("line:%d,column:%d is not float\n", showLine, showColumn);
                        setErrMsg(errMsg, maps, builder);
                    }
                    break;

                case DATE:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                        maps.put(key, javaDate);
                    } else {
                        errMsg = String.format("line:%d,column:%d is not date\n", showLine, showColumn);
                        setErrMsg(errMsg, maps, builder);
                    }
                    break;

                case BIGDECIMAL:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double tempB = cell.getNumericCellValue();
                        maps.put(key, BigDecimal.valueOf(tempB));
                    } else {
                        errMsg = String.format("line:%d,column:%d is not bigDecimal\n", showLine, showColumn);
                        setErrMsg(errMsg, maps, builder);
                    }
                    break;

                case DOUBLE:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double tempC = cell.getNumericCellValue();
                        maps.put(key, tempC);
                    } else {
                        errMsg = String.format("line:%d,column:%d is not double\n", showLine, showColumn);
                        setErrMsg(errMsg, maps, builder);
                    }
                    break;
            }
        }

    }

    private void setErrMsg(String errMsg, Map<String, Object> maps, StringBuilder sb) {
        sb.append(errMsg);
        maps.put(MapResult.IS_LINE_LEGAL_KEY, false);
    }

    private boolean isCellEmpty(Cell cell) {
        if (EmptyUtil.isEmpty(cell)) {
            return true;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isEmpty(cell.getStringCellValue())) {
            return true;
        }
        return false;
    }
}
