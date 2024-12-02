package util;

import gen.calParser.*;
import model.CalParam;
import model.CalVar;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AssignUtils {
    public static boolean isSingleVariable(Assignment_stmContext ctx) {
        if (ctx.exp() != null) return checkExpSingleVar(ctx.exp());
        else return checkCondSingleVar(ctx.cond());
    }

    public static boolean isSingleVariable(Return_stmContext ctx) {
        if (ctx.exp() != null) return checkExpSingleVar(ctx.exp());
        else return checkCondSingleVar(ctx.cond());
    }

    private static boolean checkExpSingleVar(ExpContext ctx) {
        String id = Optional.of(ctx).map(ExpContext::frag).map(FragContext::IDENTIFIER).map(ParseTree::getText).orElse("");
        return !id.isEmpty() && !ctx.frag().getText().startsWith("-");
    }

    private static boolean checkCondSingleVar(CondContext ctx) {
        String id = Optional.of(ctx).map(CondContext::IDENTIFIER).map(ParseTree::getText).orElse("");
        return !id.isEmpty();
    }

    public static void assignParam(CalParam param, Map<String, CalVar> symbolTable, List<String> localVars) {
        CalVar var = new CalVar(param.type(), true);
        var.assign();
        localVars.add(param.name());
        symbolTable.put(param.name(), var);
    }
}
