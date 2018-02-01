package base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class DateUtil {

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String dataToString(Date date,String dateFormat){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }
}
