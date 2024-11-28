import error.DuplicatedVariableError;
import error.SemanticError;
import gen.calBaseVisitor;
import gen.calParser.*;
import model.CalFunc;
import model.CalParam;
import model.CalVar;
import model.GenericVal;

import java.util.*;

import static util.AssignUtils.assignParam;
import static util.StatementUtils.genAssignStatement;
import static util.StatementUtils.genGetParamStatement;
import static util.TypeUtils.getTypeFromName;

public class CalVisitor extends calBaseVisitor<GenericVal<?>> {
    Map<String, CalVar> symbolTable = new HashMap<>();
    List<CalFunc> functions = new ArrayList<>();
    List<SemanticError> errors = new ArrayList<>();

    List<List<String>> currFunctions = new ArrayList<>();
    List<String> localVars = new ArrayList<>();
    String currFunctionName = "";

    @Override
    public GenericVal<?> visitProg(ProgContext ctx) {
        List<String> decls = new ArrayList<>();
        if (ctx.decl_list() != null) decls = visitDecl_list(ctx.decl_list()).getValue();

        visit(ctx.func_list());

        if (!errors.isEmpty()) errors.forEach((err) -> System.out.println(err.getMessage()));
        else {
            System.out.println("\n----= Symbol Table =---\n");
            symbolTable.keySet().forEach((key) -> System.out.println(key + " : " + symbolTable.get(key)));
            System.out.println("\n----= Instructions =---\n");
            decls.forEach(System.out::println);
        }
        return null;
    }

    // Functions / Scope

    @Override
    public GenericVal<List<String>> visitDecl_list(Decl_listContext ctx) {
        currFunctions.add(new ArrayList<>());
        visitChildren(ctx);
        List<String> decls = new ArrayList<>(currFunctions.get(0));
        currFunctions.remove(0);
        return new GenericVal<>(decls);
    }

    @Override
    public GenericVal<?> visitFunc_list(Func_listContext ctx) {
        ctx.func_decl().forEach(func -> {
            String funcName = func.IDENTIFIER().getText();
            List<CalParam> params = new ArrayList<>();
            if (func.params() != null) params = visitParams(func.params()).getValue();
            functions.add(new CalFunc(funcName, params));
        });

        visitChildren(ctx);
        return null;
    }

    @Override
    public GenericVal<List<CalParam>> visitParams(ParamsContext ctx) {
        CalParam currParam = new CalParam(ctx.IDENTIFIER().getText(), getTypeFromName(ctx.var_type().getText()));
        if (ctx.params() == null) return new GenericVal<>(new ArrayList<>(List.of(currParam)));

        GenericVal<List<CalParam>> paramsList = visitParams(ctx.params());
        paramsList.getValue().add(currParam);
        return paramsList;
    }

    @Override
    public GenericVal<?> visitFunc_decl(Func_declContext ctx) {
        currFunctionName = ctx.IDENTIFIER().getText();
        CalFunc currFunc = functions.stream()
                .filter(func -> func.getName().equals(currFunctionName))
                .findFirst()
                .orElse(null);
        List<String> paramDecls = new ArrayList<>();
        Optional.of(currFunc).map(CalFunc::getParams).orElse(new ArrayList<>()).forEach(param -> {
            if(symbolTable.containsKey(param.name())) {

                return;
            }
            assignParam(param, symbolTable, localVars);
            paramDecls.add(genGetParamStatement(param.name(), paramDecls.size() + 1));
        });
        currFunctions.add(paramDecls);

        //visitChildren(ctx);

        //localVars.forEach(var -> );
        currFunc.addCode(currFunctions.get(0));
        currFunctions.remove(0);
        return null;
    }

    // Declarations

    @Override
    public GenericVal<String> visitVar_decl(Var_declContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (symbolTable.containsKey(varName)) {
            errors.add(new DuplicatedVariableError(ctx));
            return null;
        }

        switch (ctx.var_type().getText().toLowerCase()) {
            case "int" -> symbolTable.put(varName, new CalVar(Integer.class, true));
            case "bool" -> symbolTable.put(varName, new CalVar(Boolean.class, true));
        }
        return new GenericVal<>(varName);
    }

    @Override
    public GenericVal<String> visitConst_decl(Const_declContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (symbolTable.containsKey(varName)) {
            errors.add(new DuplicatedVariableError(ctx));
            return null;
        }

        if (ctx.Int() == null) {
            symbolTable.put(varName, new CalVar(Boolean.class, false));
            currFunctions.get(0).add(genAssignStatement(varName, ctx.BOOL().getText()));
        } else {
            symbolTable.put(varName, new CalVar(Integer.class, false));
            currFunctions.get(0).add(genAssignStatement(varName, ctx.NUM().getText()));
        }

        return new GenericVal<>(varName);
    }

    // Statements

//    @Override
//    public Void visitAssignment_stm(Assignment_stmContext ctx) {
//        String varName = ctx.IDENTIFIER().getText();
//        if (!symbolTable.containsKey(varName)) {
//            errors.add(new /*no such var err*/);
//            return null;
//        }
//
//        CalVar<?> var = symbolTable.get(varName);
//        if (!ctx.) {
//            errors.add(new /*no such var err*/);
//            return null;
//        }
//
//    }

    // Expressions

//    @Override
//    public CalVar<Boolean> visitCond(CondContext ctx) {
//        if(!ctx.LOB().getText().isEmpty()) return visitCond(ctx.cond(0));
//        if(!ctx.NOT().getText().isEmpty()) {
//            currFunctions.getLast().add(String.format(""));
//            return new CalVar<>(!visitCond(ctx.cond(0)).getValue());
//        }
//
//
//        return null;
//    }

}
