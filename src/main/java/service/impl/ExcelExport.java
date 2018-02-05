package service.impl;

import base.util.EmptyUtil;
import base.util.ReflectionUtils;
import entity.ExportCell;
import exception.FileExportException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.FileExport;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class ExcelExport implements FileExport {
    @Override
    public Workbook getExportResult(List<?> data, List<ExportCell> exportCells) throws FileExportException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        createTitleRow(workbook, titleRow, exportCells, sheet);
        /**
         * 判断data.getClass() 是不是 List.class.isAssignableFrom的子类或子接口
         * */
        if (List.class.isAssignableFrom(data.getClass())) {
            if (CollectionUtils.isNotEmpty(data)) {
                if (data.get(0) instanceof Map) {
                    createContentRowsByMap(workbook, (List<Map>) data, exportCells, sheet);
                } else if (data.get(0) instanceof List) {
                    createContentRowsByBean(workbook, (List<Object>) data, exportCells, sheet);
                }
            }
        }
        return workbook;
    }

    /**
     * 解析List数据
     */
    private void createContentRowsByBean(Workbook workbook, List<Object> dataList, List<ExportCell> exportCells, Sheet sheet) throws FileExportException {
        int rowNum = 1;
        if (CollectionUtils.isNotEmpty(dataList)) {
            CellStyle cellStyle = createCellStyle(workbook);
            for (Object o : dataList) {
                Row row = sheet.createRow(rowNum);
                row.setHeightInPoints(23.0F);
                for (int colNum = 0; colNum < exportCells.size(); colNum++) {
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(cellStyle);
                    ExportCell exportCell = exportCells.get(colNum);
                    Object obj = null;
                    try {
                        ReflectionUtils.excuteMethod(o, ReflectionUtils.returnGetMethodName(exportCell.getAlias()));
                    } catch (Exception e) {
                        throw new FileExportException("执行executeMethod  出错 Alias is " + exportCell.getAlias() + " at " + e.getMessage());
                    }
                }
            }
        }
    }


    /**
     * 解析Map数据
     */
    private static void createContentRowsByMap(Workbook workbook, List<Map> dataList, List<ExportCell> exportCells, Sheet sheet) throws FileExportException {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int rowNum = 1;
            CellStyle cellStyle = createCellStyle(workbook);
            for (Map map : dataList) {
                Row row = sheet.createRow(rowNum);
                row.setHeightInPoints(23.0F);
                for (int colNum = 0; colNum < exportCells.size(); colNum++) {
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(cellStyle);
                    ExportCell exportCell = exportCells.get(colNum);
                    Object obj = null;
                    obj = map.get(exportCell.getAlias());
                    setCellValue(obj, cell);
                }
                ++rowNum;
            }
        }
    }

    /**
     * 填充数据
     */
    private static void setCellValue(Object obj, Cell cell) throws FileExportException {
        if (EmptyUtil.isNotEmpty(obj)) {
            BigDecimal bigDecimal = null;
            String classType = obj.getClass().getName();
            if (classType.endsWith("String")) {
                cell.setCellValue((String) obj);
            } else if (("int".equals(classType)) || (classType.equals("java.lang.Integer")))
                cell.setCellValue(((Integer) obj).intValue());
            else if (("double".equals(classType)) || (classType.equals("java.lang.Double"))) {
                bigDecimal = new BigDecimal(((Double) obj).doubleValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if (("float".equals(classType)) || (classType.equals("java.lang.Float"))) {
                bigDecimal = new BigDecimal(((Float) obj).floatValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if ((classType.equals("java.util.Date")) || (classType.endsWith("Date")))
                cell.setCellValue(base.util.DateUtil.dataToString((Date) obj, base.util.DateUtil.YYYYMMDDHHMMSS));
            else if (classType.equals("java.util.Calendar"))
                cell.setCellValue((Calendar) obj);
            else if (("char".equals(classType)) || (classType.equals("java.lang.Character")))
                cell.setCellValue(obj.toString());
            else if (("long".equals(classType)) || (classType.equals("java.lang.Long")))
                cell.setCellValue(((Long) obj).longValue());
            else if (("short".equals(classType)) || (classType.equals("java.lang.Short")))
                cell.setCellValue(((Short) obj).shortValue());
            else if (classType.equals("java.math.BigDecimal")) {
                bigDecimal = (BigDecimal) obj;
                bigDecimal = new BigDecimal(bigDecimal.doubleValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else {
                throw new FileExportException("data type error !  obj is " + obj);
            }
        }
    }

    /**
     * 设置标题行
     */
    private void createTitleRow(Workbook workbook, Row titleRow, List<ExportCell> exportCells, Sheet sheet) {
        CellStyle cellStyle = createCellStyle(workbook);
        titleRow.setHeightInPoints(25.0F);
        Font font = workbook.createFont();
        font.setColor((short) 12);
        cellStyle.setFont(font);
        cellStyle.setFillBackgroundColor((short) 13);
        int i = 0;
        for (ExportCell exportCell : exportCells) {
            sheet.setColumnWidth(i, 3200);
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(exportCell.getTitle());
            cell.setCellStyle(cellStyle);
            ++i;
        }
    }

    /**
     * 修改正文样式
     */
    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        return cellStyle;
    }
}
