package ASTNodes.AST_Exprs;

import ASTNodes.AST_Node;
import SymbolTable.SymbolTable;

import java.util.ArrayDeque;

public class AST_ExprLiter extends AST_Expr{
  //Syntactic attributes
  String constant;    //TODO change to content
  String literal;
  //Semantic attribute

  // Assign the class variables when called
  public AST_ExprLiter(){
    this.constant = null;
    this.literal = null;

  }

  @Override
  public ArrayDeque<AST_Node> getNodes(){
    System.out.println("Terminal AST Node at: " + this.getClass().getSimpleName());
    return null;
  }


  @Override
  public void setSyntacticAttributes(String value){
    if(constant == null){
      this.constant = value;
    } else if(literal == null){
      this.literal = value;
    } else {
      System.out.println("Unrecognised String Attribute" + this.getClass().getSimpleName());
    }
  }


  @Override
  public String getSyntacticAttributes(String strToGet){
    if(strToGet.equals("constant")){
      return constant;
    } else if(strToGet.equals("literal")){
      return literal;
    } else {
      System.out.println("Unrecognised String Attribute" + this.getClass().getSimpleName());
      return null;
    }
  }

  @Override
  public boolean isEmbeddedNodesFull(){
    return true;
  }

  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter){
    System.out.println("Terminal AST Node at: " + this.getClass().getSimpleName());
    return null;
  }

  @Override
  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet){
    System.out.println("Terminal AST Node at: " + this.getClass().getSimpleName());
  }


  //Semantic Analysis and print error message if needed
  protected boolean CheckSemantics(SymbolTable ST){
    return true;
  }

  // Called from visitor
  public void Check(SymbolTable ST){
    if(CheckSemantics(ST)){
      if (literal.equals("INT_LITER")) {
        setType("int");
      } else if (literal.equals("BOOL_LITER")) {
        setType("bool");
      } else if (literal.equals("CHAR_LITER")) {
        setType("char");
      } else if(literal.equals("STRING_LITER")) {
        setType("string");
      } else if(literal.equals("PAIR_LITER")) {
        setType("pair");
      } else {
        setType("null");
      }
    }
  }

  @Override
  public void printContents(){
    System.out.println(this.getClass().getSimpleName() + ": ");
    System.out.println("constant: " + constant);
    System.out.println("literal: " + literal);

  }
}
