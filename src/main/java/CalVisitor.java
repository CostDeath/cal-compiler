import error.DuplicatedVariableError;
import error.SemanticError;
import gen.calBaseVisitor;
import gen.calParser.Const_declContext;
import gen.calParser.ProgContext;
import gen.calParser.Var_declContext;
import model.CalVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalVisitor extends calBaseVisitor<CalVar<?>> {
    Map<String, CalVar<?>> symbolTable = new HashMap<>();
    List<SemanticError> errors = new ArrayList<>();

    @Override
    public CalVar<?> visitProg (ProgContext ctx) {
        visitChildren(ctx);
        if(!errors.isEmpty()) errors.forEach((err) -> System.out.println(err.getMessage()));
        else System.out.println(symbolTable.keySet());
        return null;
    }

    @Override
    public CalVar<?> visitVar_decl (Var_declContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if(symbolTable.containsKey(varName)) {
            errors.add(new DuplicatedVariableError(ctx));
            return null;
        }

        switch(ctx.var_type().getText().toLowerCase()) {
            case "int" -> symbolTable.put(varName, new CalVar<Integer>());
            case "bool" -> symbolTable.put(varName, new CalVar<Boolean>());
        }

        return null;
    }

    @Override
    public CalVar<?> visitConst_decl (Const_declContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if(symbolTable.containsKey(varName)) {
            errors.add(new DuplicatedVariableError(ctx));
            return null;
        }

        if(ctx.Int().getText().isEmpty()) symbolTable.put(varName, visit(ctx.BOOL()));

        return null;
    }

    @Override
    public Integer visitBOOL ( ctx) {
        String varName = ctx.IDENTIFIER().getText();
        if(symbolTable.containsKey(varName)) {
            errors.add(new DuplicatedVariableError(ctx));
            return 1;
        }

        if(ctx.Int().getText().isEmpty()) symbolTable.put(varName, visit(ctx.BOOL()));

        return 0;
    }

}
