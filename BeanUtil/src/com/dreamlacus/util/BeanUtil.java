package com.dreamlacus.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class BeanUtil {
    private BeanUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T, P> P getValue(T target, String expression) throws Exception {
        Object temp = target;
        if (expression != null && !"".equals(expression)) {
            String[] propArr = expression.replace("[", ".[").split("\\.");
            for (String prop : propArr) {
                if (prop.indexOf("[") > -1 || prop.indexOf("]") > -1) {
                    if (prop.length() > 4 && prop.indexOf("['") == 0 && prop.indexOf("']") == prop.length() - 2) {
                        String key = prop.substring(2, prop.length() - 2);
                        if (temp instanceof Map) {
                            temp = ((Map<?, ?>) temp).get(key);
                        }
                    } else if (prop.length() > 2 && prop.indexOf("[") == 0 && prop.indexOf("]") == prop.length() - 1) {
                        String indexStr = prop.substring(1, prop.length() - 1);
                        int index = Integer.valueOf(indexStr).intValue();
                        if (temp instanceof List) {
                            ((List<?>) temp).get(index);
                        }
                    }
                } else {
                    String getMethodName = "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
                    temp = invoke(target, getMethodName);
                }
            }
        }
        // TODO 通过反射实现获取属性值
        return (P) temp;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T, P> void setValue(T target, String expression, P value) throws Exception {
        if (expression != null && !"".equals(expression)) {
            String[] propArr = expression.replace("[", ".[").split("\\.");
            String prop = propArr[propArr.length - 1];
            String exp = expression.substring(0, expression.length() - prop.length());
            Object temp = getValue(target, exp);
            if (prop.length() > 0) {
                if (prop.indexOf("[") > -1 || prop.indexOf("]") > -1) {
                    if (prop.length() > 4 && prop.indexOf("['") == 0 && prop.indexOf("']") == prop.length() - 2) {
                        String key = prop.substring(2, prop.length() - 2);
                        if (temp instanceof Map) {
                            ((Map) temp).put(key, value);
                        }
                    } else if (prop.length() > 2 && prop.indexOf("[") == 0 && prop.indexOf("]") == prop.length() - 1) {
                        String indexStr = prop.substring(1, prop.length() - 1);
                        if (temp instanceof List) {
                            if ("+".equals(indexStr)) {
                                ((List) temp).add(value);
                            } else {
                                int index = Integer.valueOf(indexStr).intValue();
                                ((List) temp).set(index, value);
                            }
                        }

                    }
                } else {
                    String setMethodName = "set" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
                    invoke(target, setMethodName, value);
                }
            }
        }
        // TODO 通过反射实现设置属性值
    }

    @SuppressWarnings("unchecked")
    public static <T, R> R invoke(T target, String name, Object... args) throws Exception {
        Class<?>[] classes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        R result = null;
        try {
            Method method = target.getClass().getMethod(name, classes);
            result = (R) method.invoke(target, args);
        } catch (Exception e) {
            boolean found = false;
            for (Method method : target.getClass().getDeclaredMethods()) {
                if (method.getName().equals(name) && method.getParameterTypes().length == args.length) {
                    boolean matching = true;
                    for (int i = 0; i < args.length; i++) {
                        if (!method.getParameterTypes()[i].isInstance(args[i])) {
                            matching = false;
                            break;
                        }
                    }
                    if (matching) {
                        method.invoke(target, args);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                throw e;
            }
        }
        return result;
    }
}
