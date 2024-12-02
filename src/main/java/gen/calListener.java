package gen;// Generated from C:/Users/Cost/IdeaProjects/csc1100-symmetric-encryption/cal-compiler/src/main/java/cal.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link calParser}.
 */
public interface calListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link calParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(calParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(calParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#decl_list}.
	 * @param ctx the parse tree
	 */
	void enterDecl_list(calParser.Decl_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#decl_list}.
	 * @param ctx the parse tree
	 */
	void exitDecl_list(calParser.Decl_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(calParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(calParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(calParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(calParser.Var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#const_decl}.
	 * @param ctx the parse tree
	 */
	void enterConst_decl(calParser.Const_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#const_decl}.
	 * @param ctx the parse tree
	 */
	void exitConst_decl(calParser.Const_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#var_type}.
	 * @param ctx the parse tree
	 */
	void enterVar_type(calParser.Var_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#var_type}.
	 * @param ctx the parse tree
	 */
	void exitVar_type(calParser.Var_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#func_list}.
	 * @param ctx the parse tree
	 */
	void enterFunc_list(calParser.Func_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#func_list}.
	 * @param ctx the parse tree
	 */
	void exitFunc_list(calParser.Func_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void enterFunc_decl(calParser.Func_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void exitFunc_decl(calParser.Func_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(calParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(calParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#return_type}.
	 * @param ctx the parse tree
	 */
	void enterReturn_type(calParser.Return_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#return_type}.
	 * @param ctx the parse tree
	 */
	void exitReturn_type(calParser.Return_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(calParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(calParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#stm_blk}.
	 * @param ctx the parse tree
	 */
	void enterStm_blk(calParser.Stm_blkContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#stm_blk}.
	 * @param ctx the parse tree
	 */
	void exitStm_blk(calParser.Stm_blkContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterStm(calParser.StmContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitStm(calParser.StmContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#assignment_stm}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_stm(calParser.Assignment_stmContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#assignment_stm}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_stm(calParser.Assignment_stmContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#func_call_stm}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call_stm(calParser.Func_call_stmContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#func_call_stm}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call_stm(calParser.Func_call_stmContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#if_stm}.
	 * @param ctx the parse tree
	 */
	void enterIf_stm(calParser.If_stmContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#if_stm}.
	 * @param ctx the parse tree
	 */
	void exitIf_stm(calParser.If_stmContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#while_stm}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stm(calParser.While_stmContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#while_stm}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stm(calParser.While_stmContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#skip_stm}.
	 * @param ctx the parse tree
	 */
	void enterSkip_stm(calParser.Skip_stmContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#skip_stm}.
	 * @param ctx the parse tree
	 */
	void exitSkip_stm(calParser.Skip_stmContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#return_stm}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stm(calParser.Return_stmContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#return_stm}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stm(calParser.Return_stmContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(calParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(calParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(calParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(calParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#func_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call(calParser.Func_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#func_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call(calParser.Func_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#arith_op}.
	 * @param ctx the parse tree
	 */
	void enterArith_op(calParser.Arith_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#arith_op}.
	 * @param ctx the parse tree
	 */
	void exitArith_op(calParser.Arith_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#comp_op}.
	 * @param ctx the parse tree
	 */
	void enterComp_op(calParser.Comp_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#comp_op}.
	 * @param ctx the parse tree
	 */
	void exitComp_op(calParser.Comp_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(calParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(calParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link calParser#frag}.
	 * @param ctx the parse tree
	 */
	void enterFrag(calParser.FragContext ctx);
	/**
	 * Exit a parse tree produced by {@link calParser#frag}.
	 * @param ctx the parse tree
	 */
	void exitFrag(calParser.FragContext ctx);
}