package ASTNodes.AST_Exprs;

import ASTNodes.AST_Node;
import ASTNodes.AST_Separator;

public class AST_ExprEnclosed extends AST_Expr{
  //Syntactic attributes
  AST_Separator leftSepAST;
  AST_Expr exprAST;
  AST_Separator rightSepAST;
  //Semantic attribute

  // Assign the class variables when called
  public AST_ExprEnclosed(){
    this.leftSepAST = null;
    this.exprAST = null;
    this.rightSepAST = null;

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
    return leftSepAST != null && exprAST != null && rightSepAST != null;
  }

  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter){
    if(astToGet.equals("leftSepAST")){
      return leftSepAST;
    } else if (astToGet.equals("exprAST")){
      return exprAST;
    } else if (astToGet.equals("rightSepAST")){
      return rightSepAST;
    }
    System.out.println("Unrecognised AST Node.");
    return null;
  }

  @Override
  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet){
    if(astToSet.equals("leftSepAST")){
      leftSepAST = (AST_Separator) nodeToSet;
    } else if (astToSet.equals("expr")){
      exprAST = (AST_Expr) nodeToSet;
    } else if (astToSet.equals("rightSepAST")){
      rightSepAST = (AST_Separator) nodeToSet;
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
    if(leftSepAST == null){
      System.out.println("leftSepAST: null");
    } else {
      System.out.println("leftSepAST: has content");
    }
    if(exprAST == null){
      System.out.println("exprAST: null");
    } else {
      System.out.println("exprAST: has content");
    }
    if(rightSepAST == null){
      System.out.println("rightSepAST: null");
    } else {
      System.out.println("rightSepAST: has content");
    }
  }
}
