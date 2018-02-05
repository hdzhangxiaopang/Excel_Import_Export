package base.util;

import java.util.List;

/**
 * Created by zhangguilin on 2/1/2018.
 * 判断对象是否为空
 */
public class EmptyUtil {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof List)) {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String)) {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
