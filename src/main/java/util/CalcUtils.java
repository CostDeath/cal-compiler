package util;

import model.CalVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.StatementUtils.genAssignStatement;

public abstract class CalcUtils {
    private static List<String> usedVars = new ArrayList<>();

    public static String performCalcs(List<String> calcs, Map<String, CalVar> symbolTable, List<String> currFunc) {
        String var = genTmpVar(symbolTable);
        performCalcs(calcs, var, symbolTable, currFunc);
        return var;
    }

    public static void performCalcs(List<String> calcs, String var, Map<String, CalVar> symbolTable, List<String> currFunc) {
        String multVarA = genTmpVar(symbolTable);
        String multVarB = genTmpVar(symbolTable);
        boolean isA = true;

        if (calcs.size() == 1) currFunc.add(genAssignStatement(var, calcs.remove(0)));
        else if (calcs.size() > 3 && calcs.get(3).equals("*")) currFunc.add(genAssignStatement(var, calcs.remove(0)));
        else currFunc.add(genAssignStatement(var, calcs.remove(0), calcs.remove(0), calcs.remove(0)));

        while (!calcs.isEmpty()) {
            if (calcs.size() > 2 && calcs.get(2).equals("*")) {
                if (isA) {
                    currFunc.add(genAssignStatement(multVarA, calcs.remove(1), calcs.remove(1), calcs.remove(1)));
                    calcs.add(1, multVarA);
                } else {
                    currFunc.add(genAssignStatement(multVarB, calcs.remove(1), calcs.remove(1), calcs.remove(1)));
                    calcs.add(1, multVarB);
                }
                isA = !isA;
            } else {
                currFunc.add(genAssignStatement(var, var, calcs.remove(0), calcs.remove(0)));
            }
        }
        usedVars.remove(multVarA);
        usedVars.remove(multVarB);
    }

    public static String performCondCalcs(List<String> calcs, Map<String, CalVar> symbolTable, List<String> currFunc) {
        String var = genTmpVar(symbolTable);
        performCondCalcs(calcs, var, symbolTable, currFunc);
        return var;
    }

    public static void performCondCalcs(List<String> calcs, String var, Map<String, CalVar> symbolTable, List<String> currFunc) {
        if (calcs.size() == 1) currFunc.add(genAssignStatement(var, calcs.remove(0)));
        else currFunc.add(genAssignStatement(var, calcs.remove(0), calcs.remove(0), calcs.remove(0)));

        while (!calcs.isEmpty()) {
            currFunc.add(genAssignStatement(var, var, calcs.remove(0), calcs.remove(0)));
        }
    }

    public static void clearCalcVars() {
        usedVars.clear();
    }

    private static String genTmpVar(Map<String, CalVar> symbolTable) {
        int count = 1;
        while (symbolTable.containsKey("tmp" + count) || usedVars.contains("tmp" + count)) count++;
        usedVars.add("tmp" + count);
        return "tmp" + count;
    }
}
