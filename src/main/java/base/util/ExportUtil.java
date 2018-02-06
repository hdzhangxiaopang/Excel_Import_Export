package base.util;

import base.constants.UtilConstants;
import entity.Type;
import exception.FileExportException;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangguilin on 2/6/2018.
 */
public class ExportUtil {
    /**
     * 填充数据
     */
    public static void setCellValue(Object obj, Cell cell) throws FileExportException {
        if (EmptyUtil.isNotEmpty(obj)) {
            BigDecimal bigDecimal = null;
            String classType = obj.getClass().getName();
            if (classType.endsWith(Type.endWithString)) {
                cell.setCellValue((String) obj);
            } else if ((Type.Int.equals(classType)) || (classType.equals(Type.Integer)))
                cell.setCellValue(((Integer) obj).intValue());
            else if ((Type.Double.equals(classType)) || (classType.equals(Type.DOUBLE))) {
                bigDecimal = new BigDecimal(((Double) obj).doubleValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if ((Type.Float.equals(classType)) || (classType.equals(Type.FLOAT))) {
                bigDecimal = new BigDecimal(((Float) obj).floatValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if ((classType.equals(Type.DATE)) || (classType.endsWith(Type.endWithDate)))
                cell.setCellValue(base.util.DateUtil.dataToString((Date) obj, base.util.DateUtil.YYYYMMDDHHMMSS));
            else if (classType.equals(Type.CALENDAR))
                cell.setCellValue((Calendar) obj);
            else if ((Type.Char.equals(classType)) || (classType.equals(Type.CHARACTER)))
                cell.setCellValue(obj.toString());
            else if ((Type.Long.equals(classType)) || (classType.equals(Type.LONG)))
                cell.setCellValue(((Long) obj).longValue());
            else if ((Type.Short.equals(classType)) || (classType.equals(Type.SHORT)))
                cell.setCellValue(((Short) obj).shortValue());
            else if (classType.equals(Type.BIGDECIMAL)) {
                bigDecimal = (BigDecimal) obj;
                bigDecimal = new BigDecimal(bigDecimal.doubleValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else {
                throw new FileExportException(UtilConstants.DATA_TYPE_ERROR + obj);
            }
        }
    }

    /**
     * 设置值
     */
    public static void setValue(Object obj, StringBuilder stringBuilder) throws FileExportException {
        if (EmptyUtil.isNotEmpty(obj)) {
            BigDecimal bigDecimal = null;
            String classType = obj.getClass().getName();
            if (classType.endsWith(Type.endWithString))
                stringBuilder.append((String) obj);
            else if ((Type.Int.equals(classType)) || (classType.equals(Type.Integer)))
                stringBuilder.append(((Integer) obj).intValue());
            else if ((Type.Double.equals(classType)) || (classType.equals(Type.DOUBLE))) {
                bigDecimal = new BigDecimal(((Double) obj).doubleValue());
                stringBuilder.append(bigDecimal.doubleValue());
            } else if ((Type.Float.equals(classType)) || (classType.equals(Type.FLOAT))) {
                bigDecimal = new BigDecimal(((Float) obj).floatValue());
                stringBuilder.append(bigDecimal.doubleValue());
            } else if ((classType.equals(Type.DATE)) || (classType.endsWith(Type.endWithDate)))
                stringBuilder.append(DateUtil.dataToString((Date) obj, DateUtil.YYYYMMDDHHMMSS));
            else if (classType.equals(Type.CALENDAR))
                stringBuilder.append((Calendar) obj);
            else if ((Type.Char.equals(classType)) || (classType.equals(Type.CHARACTER)))
                stringBuilder.append(obj.toString());
            else if ((Type.Long.equals(classType)) || (classType.equals(Type.LONG)))
                stringBuilder.append(((Long) obj).longValue());
            else if ((Type.Short.equals(classType)) || (classType.equals(Type.SHORT)))
                stringBuilder.append(((Short) obj).shortValue());
            else if (classType.equals(Type.BIGDECIMAL)) {
                bigDecimal = (BigDecimal) obj;
                bigDecimal = new BigDecimal(bigDecimal.intValue());
                stringBuilder.append(bigDecimal.intValue());
            } else {
                throw new FileExportException(UtilConstants.DATA_TYPE_ERROR + obj);
            }
        }

    }
}
