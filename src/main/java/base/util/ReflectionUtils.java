package base.util;

import java.lang.reflect.Method;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class ReflectionUtils {

    /**
     * 获取object所对应的method的返回值
     */
    public static Object executeMethod(Object object, String methodName) throws Exception {
        Class<?> clazz = object.getClass();
        Method method = clazz.getMethod(methodName, new Class[0]);
        return method.invoke(object, new Object[0]);
    }

    /**
     * 根据property返回getXXX method name
     */
    public static String returnGetMethodName(String property) {
        return "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
    }


}
