package util;

import model.CalFunc;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class FuncUtils {
    private static final List<String> usedLabels = new ArrayList<>();

    public static List<Type> getFuncParamTypes(CalFunc func) {
        List<Type> types = new ArrayList<>();
        func.getParams().forEach(param -> types.add(param.type()));
        return types;
    }

    public static Type getFuncTypeFromName(List<CalFunc> funcs, String name) {
        return funcs.stream()
                .filter(func -> func.getName().equals(name))
                .map(CalFunc::getReturnType)
                .findFirst().orElse(null);
    }

    public static String genFuncName(List<CalFunc> funcs, List<CalFunc> neoFuncs, String base) {
        int count = 0;
        boolean found = false;
        while (!found) {
            count++;
            int currCount = count;
            found = funcs.stream().map(CalFunc::getName).noneMatch(func -> func.equals(base + currCount)) &&
                    neoFuncs.stream().map(CalFunc::getName).noneMatch(func -> func.equals(base + currCount)) &&
                    !usedLabels.contains(base + currCount);
        }
        usedLabels.add(base + count);
        return base + count;
    }
}
