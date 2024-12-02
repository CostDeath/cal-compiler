import error.*;
import gen.calBaseVisitor;
import gen.calParser.*;
import model.CalFunc;
import model.CalParam;
import model.CalVar;
import model.GenericVal;

import java.lang.reflect.Type;
import java.util.*;

import static util.AssignUtils.assignParam;
import static util.AssignUtils.isSingleVariable;
import static util.CalcUtils.*;
import static util.FuncUtils.*;
import static util.StatementUtils.*;
import static util.TypeUtils.getTypeFromName;

public class CalVisitor extends calBaseVisitor<GenericVal<?>> {
    private static final boolean skipFunctionalityToggle = false;

    Map<String, CalVar> symbolTable = new HashMap<>();
    List<CalFunc> functions = new ArrayList<>();
    List<CalFunc> neoFunctions = new ArrayList<>();
    List<SemanticError> errors = new ArrayList<>();
    List<SemanticError> warnings = new ArrayList<>();

    List<List<String>> currFunctions = new ArrayList<>();
    List<String> localVars = new ArrayList<>();
    String currFunctionName = "";
    String currWhileName = "";
    boolean skipChecks;
    boolean hasReturned = false;

    @Override
    public GenericVal<List<String>> visitProg(ProgContext ctx) {
        // Visit the code
        List<String> decls = new ArrayList<>();
        if (ctx.decl_list() != null) decls = visitDecl_list(ctx.decl_list()).getValue();
        if (ctx.func_list() != null) visitFunc_list(ctx.func_list());
        List<String> mainCode = visitMain(ctx.main()).getValue();

        if (!errors.isEmpty()) errors.forEach(err -> System.out.println(err.getMessage()));
        else { // Generate intermediate code
            if(!warnings.isEmpty()) warnings.forEach(warn -> System.out.println(warn.getMessage()));

            List<String> tacCode = new ArrayList<>(decls);
            neoFunctions.forEach(func -> tacCode.addAll(func.getCode()));
            functions.forEach(func -> tacCode.addAll(func.getCode()));
            tacCode.addAll(mainCode);
            return new GenericVal<>(tacCode);
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
            Type funcType = getTypeFromName(func.return_type().getText());
            List<CalParam> params = new ArrayList<>();
            if (func.params() != null) params = visitParams(func.params()).getValue();
            functions.add(new CalFunc(funcName, funcType, params));
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
        localVars.clear();
        currFunctionName = ctx.IDENTIFIER().getText();
        CalFunc currFunc = functions.stream()
                .filter(func -> func.getName().equals(currFunctionName))
                .findFirst().get();

        List<String> paramDecls = new ArrayList<>();
        currFunc.getParams().forEach(param -> {
            if(symbolTable.containsKey(param.name())) {
                errors.add(new DuplicatedVariableError(param, ctx.params()));
                return;
            }
            assignParam(param, symbolTable, localVars);
            paramDecls.add(genGetParamStatement(param.name(), paramDecls.size() + 1));
        });
        currFunc.addCode(paramDecls);

        if (ctx.decl_list() != null) currFunc.addCode(visitDecl_list(ctx.decl_list()).getValue());

        currFunctions.add(new ArrayList<>());
        if(ctx.stm_blk() != null) visitStm_blk(ctx.stm_blk());

        localVars.forEach(var -> { // Check all local vars were assigned and read
            if(!symbolTable.get(var).isAssigned()) warnings.add(new UnassignedVariableError(var, currFunctionName));
            else if(!symbolTable.get(var).isRead()) warnings.add(new UnusedVariableError(var, currFunctionName));
            symbolTable.remove(var);
        });
        if(!hasReturned) {
            if(currFunc.getReturnType() == Void.class) currFunctions.get(0).add(genReturnStatement());
            else errors.add(new MissingReturnStatementError(currFunctionName));
        }
        hasReturned = false;
        currFunc.addCode(currFunctions.get(0));
        currFunctions.remove(0);
        return null;
    }

    @Override
    public GenericVal<List<String>> visitMain(MainContext ctx) {
        // Set up
        localVars.clear();
        currFunctionName = "main";
        CalFunc currFunc = new CalFunc(currFunctionName, null, null);

        // Add declarations
        if (ctx.decl_list() != null) currFunc.addCode(visitDecl_list(ctx.decl_list()).getValue());

        // Visit statements
        currFunctions.add(new ArrayList<>());
        if(ctx.stm_blk() != null) visitStm_blk(ctx.stm_blk());
        else warnings.add(new EmptyMainBodyError());

        // Clean up
        localVars.forEach(var -> { // Check all local vars were assigned and read
            if(!symbolTable.get(var).isAssigned()) warnings.add(new UnassignedVariableError(var, currFunctionName));
            else if(!symbolTable.get(var).isRead()) warnings.add(new UnusedVariableError(var, currFunctionName));
            symbolTable.remove(var);
        });
        symbolTable.keySet().forEach(var -> { // Check all global vars were assigned and read
            if(!symbolTable.get(var).isAssigned()) warnings.add(new UnassignedVariableError(var));
            else if(!symbolTable.get(var).isRead()) warnings.add(new UnusedVariableError(var));
        });
        functions.forEach(func -> { // Check all functions were called
            if(!func.isCalled()) warnings.add(new UnusedFunctionError(func.getName()));
        });
        currFunc.addCode(currFunctions.get(0));
        currFunctions.remove(0);
        return new GenericVal<>(currFunc.getCode());
    }

    // Declarations

    @Override
    public GenericVal<?> visitVar_decl(Var_declContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (symbolTable.containsKey(varName)) {
            errors.add(new DuplicatedVariableError(ctx));
            return null;
        }

        localVars.add(varName);
        switch (ctx.var_type().getText().toLowerCase()) {
            case "int" -> symbolTable.put(varName, new CalVar(Integer.class, true));
            case "bool" -> symbolTable.put(varName, new CalVar(Boolean.class, true));
        }
        return null;
    }

    @Override
    public GenericVal<?> visitConst_decl(Const_declContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (symbolTable.containsKey(varName)) {
            errors.add(new DuplicatedVariableError(ctx));
            return null;
        }

        localVars.add(varName);
        if (ctx.Int() == null) {
            symbolTable.put(varName, new CalVar(Boolean.class, false));
            currFunctions.get(0).add(genAssignStatement(varName, ctx.BOOL().getText()));
        } else {
            symbolTable.put(varName, new CalVar(Integer.class, false));
            currFunctions.get(0).add(genAssignStatement(varName, ctx.NUM().getText()));
        }

        return null;
    }

    // Statements

    @Override
    public GenericVal<?> visitStm_blk(Stm_blkContext ctx) {
        if(ctx != null) {
            for(int i = 0; i < ctx.getChildCount(); i++) {
                if(!hasReturned) visitStm(ctx.stm(i));
                else warnings.add(new UnreachableCodeError(ctx.stm(i)));
            }
        }
        return null;
    }

    @Override
    public GenericVal<?> visitAssignment_stm(Assignment_stmContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if (!symbolTable.containsKey(varName)) {
            errors.add(new NoSuchVariableError(ctx));
            return null;
        }
        if (!symbolTable.get(varName).isVar()) {
            errors.add(new IllegalAssignmentError(ctx));
            return null;
        }

        if(ctx.exp() != null) {
            Type varType = symbolTable.get(varName).getType();
            if(ctx.exp().func_call() != null) {
                currFunctions.get(0).add(genAssignStatement(varName, visitFunc_call(ctx.exp().func_call()).getValue()));
                symbolTable.get(varName).assign();

                Type funcType = getFuncTypeFromName(functions, ctx.exp().func_call().IDENTIFIER().getText());
                if(funcType != varType) errors.add(new MismatchedReturnTypeError(ctx, ctx.exp().func_call(), funcType, varType));

                return null;
            }
            if(isSingleVariable(ctx)) {
                skipChecks = true;
                Type newVarType = symbolTable.get(ctx.exp().frag().IDENTIFIER().getText()).getType();
                if(varType != newVarType) errors.add(new MismatchedReturnTypeError(ctx, newVarType, varType));
            }
            else if(varType != Integer.class) errors.add(new MismatchedReturnTypeError(ctx, Integer.class, varType));

            String tmpVar = performCalcs(visitExp(ctx.exp()).getValue(), symbolTable, currFunctions.get(0));
            currFunctions.get(0).add(genAssignStatement(varName, tmpVar));
            symbolTable.get(varName).assign();
            clearCalcVars();
            skipChecks = false;

        } else {
            Type varType = symbolTable.get(varName).getType();
            if(ctx.cond().func_call() != null) {
                currFunctions.get(0).add(genAssignStatement(varName, visitFunc_call(ctx.cond().func_call()).getValue()));
                symbolTable.get(varName).assign();

                Type funcType = getFuncTypeFromName(functions, ctx.cond().func_call().IDENTIFIER().getText());
                if(funcType != varType) errors.add(new MismatchedReturnTypeError(ctx, ctx.cond().func_call(), funcType, varType));

                return null;
            }
            if(isSingleVariable(ctx)) {
                skipChecks = true;
                Type newVarType = symbolTable.get(ctx.exp().frag().IDENTIFIER().getText()).getType();
                if(varType != newVarType) errors.add(new MismatchedReturnTypeError(ctx, newVarType, varType));
            }
            else if(varType != Boolean.class) errors.add(new MismatchedReturnTypeError(ctx, Boolean.class, varType));

            String tmpVar = performCondCalcs(visitCond(ctx.cond()).getValue(), symbolTable, currFunctions.get(0));
            currFunctions.get(0).add(genAssignStatement(varName, tmpVar));
            symbolTable.get(varName).assign();
            clearCalcVars();
            skipChecks = false;

        }
        return null;
    }

    @Override
    public GenericVal<?> visitFunc_call_stm(Func_call_stmContext ctx) {
        currFunctions.get(0).add(visitFunc_call(ctx.func_call()).getValue());
        return null;
    }

    @Override
    public GenericVal<String> visitFunc_call(Func_callContext ctx) {
        CalFunc calledFunc = functions.stream()
                .filter(func -> func.getName().equals(ctx.IDENTIFIER().getText()))
                .findFirst().orElse(null);
        if(calledFunc == null) {
            errors.add(new NoSuchFunctionError(ctx));
            return new GenericVal<>("");
        }

        List<Type> requiredParams = getFuncParamTypes(calledFunc);
        Collections.reverse(requiredParams);
        List<Type> givenArgs = new ArrayList<>();
        if(ctx.args() != null) givenArgs = visitArgs(ctx.args()).getValue();
        if(!givenArgs.equals(requiredParams)) {
            errors.add(new MismatchedArgumentsError(ctx, requiredParams, givenArgs));
            return new GenericVal<>("");
        }

        calledFunc.call();
        return new GenericVal<>(genCallStatement(ctx.IDENTIFIER().getText(), givenArgs.size()));
    }

    @Override
    public GenericVal<List<Type>> visitArgs(ArgsContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        if(!symbolTable.containsKey(name)) {
            errors.add(new NoSuchVariableError(ctx));
            return new GenericVal<>(new ArrayList<>());
        }
        if(!symbolTable.get(name).isAssigned()) {
            errors.add(new UnassignedVariableError(ctx));
            return new GenericVal<>(new ArrayList<>());
        }

        symbolTable.get(name).read();
        currFunctions.get(0).add(genSetParamStatement(name));
        List<Type> type = new ArrayList<>(List.of(symbolTable.get(name).getType()));
        if (ctx.args() != null) type.addAll(visitArgs(ctx.args()).getValue());

        return new GenericVal<>(type);
    }

    @Override
    public GenericVal<?> visitIf_stm(If_stmContext ctx) {
        // Create new neo function
        CalFunc currFunc = new CalFunc(genFuncName(functions, neoFunctions, "if" + currFunctionName), null, null);
        neoFunctions.add(currFunc);

        // Create the condition
        String tmpVar = performCondCalcs(visitCond(ctx.cond()).getValue(), symbolTable, currFunctions.get(0));
        currFunctions.get(0).add(genIfStatement(tmpVar, currFunc.getName()));
        clearCalcVars();

        // Create the return label
        String returnName = genFuncName(functions, neoFunctions, "if" + currFunctionName);

        // Visit statements
        currFunctions.add(0, new ArrayList<>());
        if(ctx.stm_blk() != null) visitStm_blk(ctx.stm_blk(0));
        if(!hasReturned) currFunctions.get(0).add(genGotoStatement(returnName));

        // Clean up
        boolean ifStmWasReturned = hasReturned;
        hasReturned = false;
        currFunc.addCode(currFunctions.get(0));
        currFunctions.remove(0);


        // Do it again for else statement
        if(ctx.Else() != null) {
            // Create new neo function for else
            currFunc = new CalFunc(genFuncName(functions, neoFunctions, "else" + currFunctionName), null, null);
            neoFunctions.add(currFunc);

            // Visit statements for else
            currFunctions.get(0).add(genElseStatement(tmpVar, currFunc.getName()));
            currFunctions.add(0, new ArrayList<>());
            if(ctx.stm_blk() != null) visitStm_blk(ctx.stm_blk(1));
            if(!hasReturned) currFunctions.get(0).add(genGotoStatement(returnName));

            // Clean up
            currFunc.addCode(currFunctions.get(0));
            currFunctions.remove(0);
        }
        hasReturned = ifStmWasReturned && hasReturned;
        if(!hasReturned) currFunctions.get(0).add(genLabelStatement(returnName));
        return null;
    }

    @Override
    public GenericVal<?> visitWhile_stm(While_stmContext ctx) {
        // Create new neo function
        CalFunc currFunc = new CalFunc(genFuncName(functions, neoFunctions, "while" + currFunctionName), null, null);
        neoFunctions.add(currFunc);

        // Create the condition
        String tmpVar = performCondCalcs(visitCond(ctx.cond()).getValue(), symbolTable, currFunctions.get(0));
        currFunctions.get(0).add(genIfStatement(tmpVar, currFunc.getName()));
        clearCalcVars();

        // Create the return label
        String returnName = genFuncName(functions, neoFunctions, "while" + currFunctionName);

        // Visit statements
        String prevWhileName = currWhileName;
        currWhileName = currFunc.getName();
        currFunctions.add(0, new ArrayList<>());
        if(ctx.stm_blk() != null) visitStm_blk(ctx.stm_blk());

        // Create loop
        tmpVar = performCondCalcs(visitCond(ctx.cond()).getValue(), symbolTable, currFunctions.get(0));
        currFunctions.get(0).add(genIfStatement(tmpVar, currFunc.getName()));
        clearCalcVars();
        currFunctions.get(0).add(genGotoStatement(returnName));

        // Clean up
        currFunc.addCode(currFunctions.get(0));
        currFunctions.remove(0);
        currFunctions.get(0).add(genLabelStatement(returnName));
        hasReturned = false;
        return null;
    }

    @Override
    public GenericVal<List<String>> visitSkip_stm(Skip_stmContext ctx) {
        if(skipFunctionalityToggle) {
            if(currWhileName.isEmpty()) errors.add(new IllegalKeywordError(ctx));
            else currFunctions.get(0).add(genGotoStatement(currWhileName));
        }
        return null;
    }

    @Override
    public GenericVal<?> visitReturn_stm(Return_stmContext ctx) {
        if(currFunctionName.equals("main")) {
            errors.add(new IllegalKeywordError(ctx));
            return null;
        }
        // Returning an expression
        Type funcType = functions.stream().filter(func -> func.getName().equals(currFunctionName)).findFirst().orElse(null).getReturnType();
        if(ctx.exp() != null) {
            if(isSingleVariable(ctx)) {
                skipChecks = true; // in case of incorrect labelling of a variable's nature (exp or cond)
                Type givenType = symbolTable.get(ctx.exp().frag().IDENTIFIER().getText()).getType();
                if(funcType != givenType) errors.add(new MismatchedReturnTypeError(ctx, currFunctionName, givenType, funcType));
            }
            else if(funcType != Integer.class) errors.add(new MismatchedReturnTypeError(ctx, currFunctionName, funcType, Integer.class));

            String tmpVar = performCalcs(visitExp(ctx.exp()).getValue(), symbolTable, currFunctions.get(0));
            currFunctions.get(0).add(genReturnStatement(tmpVar));
            clearCalcVars();
            skipChecks = false;
        }
        // Returning a condition
        else if(ctx.cond() != null) {
            if(isSingleVariable(ctx)) {
                skipChecks = true; // in case of incorrect labelling of a variable's nature (exp or cond)
                Type givenType = symbolTable.get(ctx.cond().IDENTIFIER().getText()).getType();
                if(funcType != givenType) errors.add(new MismatchedReturnTypeError(ctx, currFunctionName, givenType, funcType));
            }
            else if(funcType != Boolean.class) errors.add(new MismatchedReturnTypeError(ctx, currFunctionName, funcType, Boolean.class));

            String tmpVar = performCondCalcs(visitCond(ctx.cond()).getValue(), symbolTable, currFunctions.get(0));
            currFunctions.get(0).add(genReturnStatement(tmpVar));
            clearCalcVars();
            skipChecks = false;
        }
        // Returning nothing
        else currFunctions.get(0).add(genReturnStatement());
        hasReturned = true;
        return null;
    }

    // Expressions

    @Override
    public GenericVal<List<String>> visitExp(ExpContext ctx) {
        List<String> calcs = new ArrayList<>();
        if(ctx.func_call() != null) calcs.add(visitFunc_call(ctx.func_call()).getValue());
        else if(ctx.frag() != null) {
            if(ctx.frag().IDENTIFIER() != null) {
                if(!symbolTable.containsKey(ctx.frag().IDENTIFIER().getText())) errors.add(new NoSuchVariableError(ctx));
                else if(!symbolTable.get(ctx.frag().IDENTIFIER().getText()).isAssigned()) errors.add(new UnassignedVariableError(ctx));
                else if(!skipChecks && symbolTable.get(ctx.frag().IDENTIFIER().getText()).getType() != Integer.class) {
                    Type varType = symbolTable.get(ctx.frag().IDENTIFIER().getText()).getType();
                    errors.add(new IllegalOperationError(ctx, varType));
                }
                symbolTable.get(ctx.frag().IDENTIFIER().getText()).read();
                if(!ctx.frag().getText().startsWith("-")) calcs.add(ctx.frag().getText());
                else calcs.addAll(List.of(ctx.frag().IDENTIFIER().getText(), "*", "-1"));
            }
            else {
                if (!ctx.frag().getText().startsWith("-")) calcs.add(ctx.frag().getText());
                else calcs.addAll(List.of("0", "-", ctx.frag().NUM().getText().replace("-", "")));
            }
        } else if(ctx.LOB() != null) {
            List<String> ops = visitExp(ctx.exp(0)).getValue();
            calcs.add(performCalcs(ops, symbolTable, currFunctions.get(0)));
        }else {
            calcs.addAll(visitExp(ctx.exp(0)).getValue());
            calcs.add(ctx.arith_op().getText());
            calcs.addAll(visitExp(ctx.exp(1)).getValue());
        }

        return new GenericVal<>(calcs);
    }

    @Override
    public GenericVal<List<String>> visitCond(CondContext ctx) {
        List<String> calcs = new ArrayList<>();
        if(ctx.func_call() != null) calcs.add(visitFunc_call(ctx.func_call()).getValue());
        else if(ctx.BOOL() != null) calcs.add(ctx.BOOL().getText());
        else if(ctx.IDENTIFIER() != null) {
            if(!symbolTable.containsKey(ctx.IDENTIFIER().getText())) errors.add(new NoSuchVariableError(ctx));
            else if(!symbolTable.get(ctx.IDENTIFIER().getText()).isAssigned()) errors.add(new UnassignedVariableError(ctx));
            else if(!skipChecks && symbolTable.get(ctx.IDENTIFIER().getText()).getType() != Boolean.class) {
                Type varType = symbolTable.get(ctx.IDENTIFIER().getText()).getType();
                errors.add(new IllegalOperationError(ctx, varType));
            }
            symbolTable.get(ctx.IDENTIFIER().getText()).read();
            calcs.add(ctx.IDENTIFIER().getText());
        }
        else if(ctx.LOB() != null) {
            List<String> ops = visitCond(ctx.cond(0)).getValue();
            calcs.add(performCondCalcs(ops, symbolTable, currFunctions.get(0)));
        }
        else if(ctx.comp_op() != null) {
            List<String> leftOps = visitExp(ctx.exp(0)).getValue();
            List<String> rightOps = visitExp(ctx.exp(1)).getValue();
            calcs.add(performCalcs(leftOps, symbolTable, currFunctions.get(0)));
            calcs.add(ctx.comp_op().getText());
            calcs.add(performCalcs(rightOps, symbolTable, currFunctions.get(0)));
        }
        else if(ctx.NOT() != null) {
            calcs.addAll(List.of("true", "!="));
            calcs.addAll(visitCond(ctx.cond(0)).getValue());
        }
        else if(ctx.AND() != null) {
            calcs.addAll(visitCond(ctx.cond(0)).getValue());
            calcs.add("&&");
            calcs.addAll(visitCond(ctx.cond(1)).getValue());
        }
        else if(ctx.OR() != null) {
            calcs.addAll(visitCond(ctx.cond(0)).getValue());
            calcs.add("||");
            calcs.addAll(visitCond(ctx.cond(1)).getValue());
        }
        else if(ctx.EQ() != null) {
            calcs.addAll(visitCond(ctx.cond(0)).getValue());
            calcs.add("==");
            calcs.addAll(visitCond(ctx.cond(1)).getValue());
        }

        return new GenericVal<>(calcs);
    }
}
