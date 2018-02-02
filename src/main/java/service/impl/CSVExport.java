package service.impl;

import base.util.DateUtil;
import base.util.EmptyUtil;
import base.util.ReflectionUtils;
import entity.ExportCell;
import exception.FileExportException;
import service.FileExport;
import org.apache.commons.collections4.CollectionUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class CSVExport implements FileExport {

    @Override
    public StringBuilder getExportResult(List<?> data, List<ExportCell> exportCells) throws FileExportException {
        StringBuilder stringBuilder = new StringBuilder();
        createTitles(exportCells,stringBuilder);
        if(CollectionUtils.isNotEmpty(data)){
            if(List.class.isAssignableFrom(data.getClass())){
                if(data.get(0) instanceof Map){
                    createContentByMap((List<Map>) data,exportCells,stringBuilder);
                }else{
                    createContentRowsByBean((List<Object>) data, exportCells, stringBuilder);
                }
            }else{
                throw new FileExportException("传入的data数据格式有误，请检查是否属于list");
            }
        }
        return stringBuilder;
    }

    /**
     * 解析
     * */
    private void createContentRowsByBean(List<Object> data,List<ExportCell> exportCells,StringBuilder stringBuilder) throws FileExportException {
        for(Object o : data){
            int colLen = exportCells.size();
            for(int colNum = 0;colNum < colLen;colNum++){
                ExportCell exportCell = exportCells.get(colNum);
                Object obj = null;
                try {
                    obj = ReflectionUtils.excuteMethod(o, ReflectionUtils.returnGetMethodName(exportCell.getAlias()));
                } catch (Exception e) {
                    throw new FileExportException("执行executeMethod  出错 Alias is " + exportCell.getAlias() + " at " + e);
                }
                setValue(obj, stringBuilder);
                if (colNum != colLen - 1) {
                    stringBuilder.append(",");
                } else {
                    addLineSeparator(stringBuilder);
                }
            }
        }
    }

    /**
     * 解析Map填充内容
     * */
    private void createContentByMap(List<Map> data, List<ExportCell> exportCells, StringBuilder stringBuilder) throws FileExportException{
        for (Map map : data){
            int colLen = exportCells.size();
            for(int colNum = 0;colNum < colLen;colNum++){
                ExportCell exportCell = exportCells.get(colNum);
                Object obj = null;
                obj = map.get(exportCell.getAlias());
                setValue(obj,stringBuilder);
                if(colNum != colLen-1){
                    stringBuilder.append(",");
                }else{
                    addLineSeparator(stringBuilder);
                }
            }
        }
    }


    /**
     * 设置值
     * */
    private void setValue(Object obj, StringBuilder stringBuilder) throws FileExportException {
        if (EmptyUtil.isNotEmpty(obj)) {
            BigDecimal bigDecimal = null;
            String classType = obj.getClass().getName();
            if (classType.endsWith("String"))
                stringBuilder.append((String) obj);
            else if (("int".equals(classType)) || (classType.equals("java.lang.Integer")))
                stringBuilder.append(((Integer) obj).intValue());
            else if (("double".equals(classType)) || (classType.equals("java.lang.Double"))) {
                bigDecimal = new BigDecimal(((Double) obj).doubleValue());
                stringBuilder.append(bigDecimal.doubleValue());
            } else if (("float".equals(classType)) || (classType.equals("java.lang.Float"))) {
                bigDecimal = new BigDecimal(((Float) obj).floatValue());
                stringBuilder.append(bigDecimal.doubleValue());
            } else if ((classType.equals("java.util.Date")) || (classType.endsWith("Date")))
                stringBuilder.append(DateUtil.dataToString((Date) obj, DateUtil.YYYYMMDDHHMMSS));
            else if (classType.equals("java.util.Calendar"))
                stringBuilder.append((Calendar) obj);
            else if (("char".equals(classType)) || (classType.equals("java.lang.Character")))
                stringBuilder.append(obj.toString());
            else if (("long".equals(classType)) || (classType.equals("java.lang.Long")))
                stringBuilder.append(((Long) obj).longValue());
            else if (("short".equals(classType)) || (classType.equals("java.lang.Short")))
                stringBuilder.append(((Short) obj).shortValue());
            else if (classType.equals("java.math.BigDecimal")) {
                bigDecimal = (BigDecimal) obj;
                bigDecimal = new BigDecimal(bigDecimal.intValue());
                stringBuilder.append(bigDecimal.intValue());
            } else {
                throw new FileExportException("data type error !  obj is " + obj);
            }
        }

    }

    /**
     * 设置标题
     * */
    private void createTitles(List<ExportCell> exportCells,StringBuilder stringBuilder){
        int length = exportCells.size();
        for(int i=0;i<length;i++){
            ExportCell exportCell = exportCells.get(i);
            stringBuilder.append(exportCell.getTitle());
            if(i !=length - 1){
                stringBuilder.append(",");
            }else{
                addLineSeparator(stringBuilder);
            }
        }

    }

    /**
     * 添加行的分割符
     * */
    private void addLineSeparator(StringBuilder stringBuilder){
        stringBuilder.append(System.getProperty("line.separator"));
    }
}
