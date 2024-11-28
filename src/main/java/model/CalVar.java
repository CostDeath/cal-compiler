package model;

import java.lang.reflect.Type;

public class CalVar {
    private final boolean isVar;
    private final Type type;

    private boolean isAssigned;

    public Type getType() {
        return type;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public boolean isVar() {
        return isVar;
    }

    public void assign() {
        isAssigned = true;
    }

    public CalVar(Type type, boolean isVar) {
        this.type = type;
        this.isVar = isVar;
        this.isAssigned = !isVar;
    }

    @Override
    public String toString() {
        String s = "%s : %s : Is Assigned? %s";
        String typeName = type.getTypeName().split("\\.")[2];
        if(isVar) return String.format(s, "Variable", typeName, isAssigned);
        return String.format(s, "Constant", typeName, isAssigned);
    }
}
