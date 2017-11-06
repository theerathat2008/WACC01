package ASTNodes.AST_Stats.AST_StatAssignRHSs;

import ASTNodes.AST_Exprs.AST_Expr;
import ASTNodes.AST_Node;

public class AST_StatNewPairRHS extends AST_StatAssignRHS{
  //Syntactic attributes
  AST_Expr ast_expr_first;
  AST_Expr ast_expr_second;
  //Semantic attribute


  // Assign the class variables when called
  public AST_StatNewPairRHS(){
    this.ast_expr_first = null;
    this.ast_expr_second = null;
  }

  @Override
  public void setSyntacticAttributes(String value){
    System.out.println("No String Syntactic Attributes");
  }

  @Override
  public String getSyntacticAttributes(String strToGet){
    System.out.println("No String Syntactic Attributes");
    return null;
  }

  @Override
  public boolean isEmbeddedNodesFull(){
    return ast_expr_first != null && ast_expr_second != null;
  }

  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter){
    if(astToGet.equals("ast_expr_first")){
      return ast_expr_first;
    } else if (astToGet.equals("ast_expr_second")){
      return ast_expr_second;
    }
    System.out.println("Unrecognised AST Node.");
    return null;
  }

  @Override
  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet){
    if(astToSet.equals("expr")){
      if(ast_expr_first == null){
        ast_expr_first = (AST_Expr) nodeToSet;
      } else if (ast_expr_second == null){
        ast_expr_second = (AST_Expr) nodeToSet;
      } else {
        System.out.println("If and then in AST_StatIf have already been assigned.");
      }
    } else if (astToSet.equals("ast_expr_second")){
      ast_expr_second = (AST_Expr) nodeToSet;
    } else {
      System.out.println("Unrecognised AST Node.");
    }
  }


  //Semantic Analysis and print error message if needed
  @Override
  protected boolean CheckSemantics(){
    return true;
  }

  // Called from visitor
  @Override
  public void Check(){
    if(CheckSemantics()){
      //Do symbol table stuff
    }
  }

  @Override
  public void printContents(){
    if(ast_expr_first == null){
      System.out.println("ast_expr_first: null");
    } else {
      System.out.println("ast_expr_first: has content");
    }
    if(ast_expr_second == null){
      System.out.println("ast_expr_second: null");
    } else {
      System.out.println("ast_expr_second: has content");
    }
  }
}
