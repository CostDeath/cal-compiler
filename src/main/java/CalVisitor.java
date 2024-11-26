import gen.calBaseVisitor;
import gen.calParser.Const_declContext;
import gen.calParser.ProgContext;
import gen.calParser.Var_declContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalVisitor extends calBaseVisitor<Integer> {
    Map<String, CalVar<?>> symbolTable = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    @Override
    public Integer visitProg (ProgContext ctx) {
        visitChildren(ctx);
        System.out.println(symbolTable.keySet());
        return 0;
    }

    @Override
    public Integer visitVar_decl (Var_declContext ctx) {
        switch(ctx.var_type().getText().toLowerCase()) {
            case "int":
                symbolTable.put(ctx.IDENTIFIER().getText(), new CalVar<Integer>());
                break;
            case "bool":
                symbolTable.put(ctx.IDENTIFIER().getText(), new CalVar<Boolean>());
                break;
            default:
                return 1;
        }

        return 0;
    }

    @Override
    public Integer visitConst_decl (Const_declContext ctx) {
//        if(ctx.)
//        switch(ctx.var_type().getText().toLowerCase()) {
//            case "int":
//                symbolTable.put(ctx.IDENTIFIER().getText(), new CalVar<Integer>());
//                break;
//            case "bool":
//                symbolTable.put(ctx.IDENTIFIER().getText(), new CalVar<Boolean>());
//                break;
//            default:
//                return 1;
//        }

        return 0;
    }

}
