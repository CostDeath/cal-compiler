package gen;// Generated from C:/Users/Cost/IdeaProjects/csc1100-symmetric-encryption/cal-compiler/src/main/java/cal.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link calParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface calVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link calParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(calParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#decl_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl_list(calParser.Decl_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(calParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_decl(calParser.Var_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#const_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConst_decl(calParser.Const_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#var_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_type(calParser.Var_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#func_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_list(calParser.Func_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#func_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_decl(calParser.Func_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(calParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#return_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_type(calParser.Return_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(calParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#stm_blk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStm_blk(calParser.Stm_blkContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStm(calParser.StmContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#assignment_stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_stm(calParser.Assignment_stmContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#func_call_stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_call_stm(calParser.Func_call_stmContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#if_stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stm(calParser.If_stmContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#while_stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stm(calParser.While_stmContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#skip_stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkip_stm(calParser.Skip_stmContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#return_stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stm(calParser.Return_stmContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(calParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(calParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#func_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_call(calParser.Func_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#arith_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith_op(calParser.Arith_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#comp_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_op(calParser.Comp_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(calParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link calParser#frag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrag(calParser.FragContext ctx);
}