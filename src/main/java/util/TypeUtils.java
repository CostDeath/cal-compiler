package util;

import java.lang.reflect.Type;

public abstract class TypeUtils {
    public static Type getTypeFromRaw(String raw) {
        if(raw.matches("[0-9]+")) return Integer.class;
        else if (raw.matches("(true | false)")) return Boolean.class;
        return String.class;
    }

    public static Type getTypeFromName(String name) {
        return switch(name) {
            case "int" -> Integer.class;
            case "boolean" -> Boolean.class;
            default -> String.class;
        };
    }

    //public static CalVar<Boolean>
}
