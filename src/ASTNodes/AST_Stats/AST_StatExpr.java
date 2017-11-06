package ASTNodes.AST_Stats;

import ASTNodes.AST_Exprs.AST_Expr;
import ASTNodes.AST_Node;

public class AST_StatExpr extends AST_Stat{
  //Syntactic attributes
  AST_Expr expr;
  //Semantic attribute

  // Assign the class variables when called
  public AST_StatExpr(){
    this.expr = null;
  }

  public boolean isEmbeddedNodesFull(){
    return expr != null;
  }

  public void setSyntacticAttributes(String value){
    System.out.println("No String Syntactic Attributes");
  }


  public String getSyntacticAttributes(String strToGet){
    System.out.println("No String Syntactic Attributes");
    return null;
  }

  public AST_Node getEmbeddedAST(String astToGet, int counter){
    if(astToGet.equals("expr")){
      return expr;
    }
    System.out.println("Unrecognised AST Node.");
    return null;
  }

  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet){
    if(astToSet.equals("expr")){
      expr = (AST_Expr) nodeToSet;
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

}