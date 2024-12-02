package util;

import java.lang.reflect.Type;

public abstract class TypeUtils {
    public static Type getTypeFromName(String name) {
        return switch (name) {
            case "int" -> Integer.class;
            case "bool" -> Boolean.class;
            case "void" -> Void.class;
            default -> String.class;
        };
    }

    public static String getNameFromType(Type type) {
        return switch (type.getTypeName()) {
            case "java.lang.Integer" -> "int";
            case "java.lang.Boolean" -> "bool";
            case "java.lang.Void" -> "void";
            default -> "unknown";
        };
    }

}
