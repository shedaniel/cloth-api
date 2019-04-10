package me.shedaniel.cloth.api;

import me.shedaniel.cloth.ClothEventsInitializer;

import java.lang.reflect.Field;
import java.util.Optional;

public class ReflectionUtils {
    
    @Deprecated
    public static <T> Optional<T> getField(Object parent, Class<T> clazz, String... possibleNames) {
        for(String possibleName : possibleNames)
            try {
                Field field = parent.getClass().getDeclaredField(possibleName);
                if (!field.isAccessible())
                    field.setAccessible(true);
                return Optional.ofNullable(clazz.cast(field.get(parent)));
            } catch (Exception e) {
            }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to get " + possibleNames + " from %s", clazz.getName());
        return Optional.empty();
    }
    
    public static <T> Optional<T> getField(Object parent, Class<T> clazz, int index) {
        try {
            Field field = parent.getClass().getDeclaredFields()[index];
            if (!field.isAccessible())
                field.setAccessible(true);
            return Optional.ofNullable(clazz.cast(field.get(parent)));
        } catch (Exception e) {
        }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to get #" + index + " from %s", clazz.getName());
        return Optional.empty();
    }
    
    @Deprecated
    public static <T> Optional<T> getStaticField(Class parentClass, Class<T> clazz, String... possibleNames) {
        for(String possibleName : possibleNames)
            try {
                Field field = parentClass.getDeclaredField(possibleName);
                if (!field.isAccessible())
                    field.setAccessible(true);
                return Optional.ofNullable(clazz.cast(field.get(null)));
            } catch (Exception e) {
            }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to get " + possibleNames + " from %s", clazz.getName());
        return Optional.empty();
    }
    
    public static <T> Optional<T> getStaticField(Class parentClass, Class<T> clazz, int index) {
        try {
            Field field = parentClass.getDeclaredFields()[index];
            if (!field.isAccessible())
                field.setAccessible(true);
            return Optional.ofNullable(clazz.cast(field.get(null)));
        } catch (Exception e) {
        }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to get #" + index + " from %s", clazz.getName());
        return Optional.empty();
    }
    
    public static void setField(Object parent, Object to, int index) {
        setField(parent, parent.getClass(), to, index);
    }
    
    public static void setField(Object parent, Class parentClass, Object to, int index) {
        try {
            Field f = parentClass.getDeclaredFields()[index];
            if (!f.isAccessible())
                f.setAccessible(true);
            f.set(parent, to);
            return;
        } catch (Exception e) {
        }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to set #" + index + " from %s", parent.getClass().getName());
        printAllFields(parent.getClass());
    }
    
    public static void setStaticField(Class parent, Object to, int index) {
        try {
            Field f = parent.getDeclaredFields()[index];
            if (!f.isAccessible())
                f.setAccessible(true);
            f.set(null, to);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to set #" + index + " from %s", parent.getName());
        printAllFields(parent);
    }
    
    public static void setField(Object parent, Object to, String... possibleNames) {
        setField(parent, parent.getClass(), to, possibleNames);
    }
    
    public static void setField(Object parent, Class parentClass, Object to, String... possibleNames) {
        for(String possibleName : possibleNames)
            try {
                Field f = parentClass.getDeclaredField(possibleName);
                if (!f.isAccessible())
                    f.setAccessible(true);
                f.set(parent, to);
                return;
            } catch (Exception e) {
            }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to set " + String.join(", ", possibleNames) + " from %s", parent.getClass().getName());
        printAllFields(parent.getClass());
    }
    
    public static void setStaticField(Class parent, Object to, String... possibleNames) {
        for(String possibleName : possibleNames)
            try {
                Field f = parent.getDeclaredField(possibleName);
                if (!f.isAccessible())
                    f.setAccessible(true);
                f.set(null, to);
                return;
            } catch (Exception e) {
            }
        ClothEventsInitializer.LOGGER.warn("Reflection failed! Trying to set " + String.join(", ", possibleNames) + " from %s", parent.getName());
        printAllFields(parent);
    }
    
    public static void printAllFields(Class o) {
        for(int i = 0; i < o.getDeclaredFields().length; i++)
            System.out.printf("%d: %s%n", i, o.getDeclaredFields()[i].getName());
    }
    
    public static class ReflectionException extends Exception {}
    
}
