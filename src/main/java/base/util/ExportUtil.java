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
            if (classType.endsWith(Type.END_WITH_STRING)) {
                cell.setCellValue((String) obj);
            } else if ((Type.INT.equals(classType)) || (classType.equals(Type.J_L_INTEGER)))
                cell.setCellValue(((Integer) obj).intValue());
            else if ((Type.DOUBLE.equals(classType)) || (classType.equals(Type.J_L_DOUBLE))) {
                bigDecimal = BigDecimal.valueOf(((Double) obj).doubleValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if ((Type.FLOAT.equals(classType)) || (classType.equals(Type.J_L_FLOAT))) {
                bigDecimal = BigDecimal.valueOf(((Float) obj).floatValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if ((classType.equals(Type.J_U_DATE)) || (classType.endsWith(Type.END_WITH_DATE)))
                cell.setCellValue(base.util.DateUtil.dataToString((Date) obj, base.util.DateUtil.YYYYMMDDHHMMSS));
            else if (classType.equals(Type.J_U_CALENDAR))
                cell.setCellValue((Calendar) obj);
            else if ((Type.CHAR.equals(classType)) || (classType.equals(Type.J_L_CHARACTER)))
                cell.setCellValue(obj.toString());
            else if ((Type.LONG.equals(classType)) || (classType.equals(Type.J_L_LONG)))
                cell.setCellValue(((Long) obj).longValue());
            else if ((Type.SHORT.equals(classType)) || (classType.equals(Type.J_L_SHORT)))
                cell.setCellValue(((Short) obj).shortValue());
            else if (classType.equals(Type.J_M_BIGDECIMAL)) {
                bigDecimal = (BigDecimal) obj;
                bigDecimal = BigDecimal.valueOf(bigDecimal.doubleValue());
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
            if (classType.endsWith(Type.END_WITH_STRING))
                stringBuilder.append((String) obj);
            else if ((Type.INT.equals(classType)) || (classType.equals(Type.J_L_INTEGER)))
                stringBuilder.append(((Integer) obj).intValue());
            else if ((Type.DOUBLE.equals(classType)) || (classType.equals(Type.J_L_DOUBLE))) {
                bigDecimal = BigDecimal.valueOf(((Double) obj).doubleValue());
                stringBuilder.append(bigDecimal.doubleValue());
            } else if ((Type.FLOAT.equals(classType)) || (classType.equals(Type.J_L_FLOAT))) {
                bigDecimal = BigDecimal.valueOf(((Float) obj).floatValue());
                stringBuilder.append(bigDecimal.doubleValue());
            } else if ((classType.equals(Type.J_U_DATE)) || (classType.endsWith(Type.END_WITH_DATE)))
                stringBuilder.append(DateUtil.dataToString((Date) obj, DateUtil.YYYYMMDDHHMMSS));
            else if (classType.equals(Type.J_U_CALENDAR))
                stringBuilder.append((Calendar) obj);
            else if ((Type.CHAR.equals(classType)) || (classType.equals(Type.J_L_CHARACTER)))
                stringBuilder.append(obj.toString());
            else if ((Type.LONG.equals(classType)) || (classType.equals(Type.J_L_LONG)))
                stringBuilder.append(((Long) obj).longValue());
            else if ((Type.SHORT.equals(classType)) || (classType.equals(Type.J_L_SHORT)))
                stringBuilder.append(((Short) obj).shortValue());
            else if (classType.equals(Type.J_M_BIGDECIMAL)) {
                bigDecimal = (BigDecimal) obj;
                bigDecimal = new BigDecimal(bigDecimal.intValue());
                stringBuilder.append(bigDecimal.intValue());
            } else {
                throw new FileExportException(UtilConstants.DATA_TYPE_ERROR + obj);
            }
        }

    }
}
