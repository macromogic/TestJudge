package xyz.macromogic.testjudge.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodChecker {
    public static class Entity {
        private boolean isPrivate;
        private boolean isStatic;
        private Class<?> returnType;
        private String name;
        private Class<?>[] paramTypes;

        public boolean isPrivate() {
            return isPrivate;
        }

        public boolean isStatic() {
            return isStatic;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public String getName() {
            return name;
        }

        public Class<?>[] getParamTypes() {
            return paramTypes;
        }

        public Entity(boolean isPrivate, boolean isStatic, Class<?> returnType, String name, Class<?>... paramTypes) {
            this.isPrivate = isPrivate;
            this.isStatic = isStatic;
            this.returnType = returnType;
            this.name = name;
            this.paramTypes = paramTypes;
        }
    }

    public static void check(Class<?> cls, Entity entity) throws NoSuchMethodException {
        if (entity.isPrivate()) {
            Method method = cls.getDeclaredMethod(entity.getName(), entity.getParamTypes());
            method.setAccessible(true);
            if (!method.getReturnType().equals(entity.getReturnType()) || Modifier.isStatic(method.getModifiers()) != entity.isStatic()) {
                throw new NoSuchMethodException();
            }
            method.setAccessible(false);
        } else {
            Method method = cls.getMethod(entity.getName(), entity.getParamTypes());
            if (!method.getReturnType().equals(entity.getReturnType()) || Modifier.isStatic(method.getModifiers()) != entity.isStatic()) {
                throw new NoSuchMethodException();
            }
        }
    }
}