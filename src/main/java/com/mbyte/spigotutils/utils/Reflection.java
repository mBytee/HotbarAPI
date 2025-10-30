package com.mbyte.spigotutils.utils;

import java.lang.reflect.Constructor;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public class Reflection {

    public static boolean hasConstructorWithParameters(Class<?> clazz, Class<?>... parametersType) {
        boolean result = false;
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == parametersType.length) {
                for (int i = 0; i < parametersType.length; i++) {
                    result = constructor.getParameterTypes()[i].equals(parametersType[i]);
                }
            }
        }
        return result;
    }

}
