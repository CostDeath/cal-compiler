package util;

import model.CalParam;
import model.CalVar;

import java.util.List;
import java.util.Map;

public abstract class AssignUtils {
    public static void assignParam(CalParam param, Map<String, CalVar> symbolTable, List<String> localVars) {
        CalVar var = new CalVar(param.type(), true);
        assignVarNoStm(param.name(), var, symbolTable, localVars);
    }

    private static void assignVarNoStm(String name, CalVar var, Map<String, CalVar> symbolTable, List<String> localVars) {
        localVars.add(name);
        assignVarNoStm(name, var, symbolTable);
    }

    private static void assignVarNoStm(String name, CalVar var, Map<String, CalVar> symbolTable) {
        var.assign();
        symbolTable.put(name, var);
    }
}
