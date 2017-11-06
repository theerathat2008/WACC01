package ASTNodes.AST_Stats;

import ASTNodes.AST_Node;

public class AST_StatBeginEnd extends AST_Stat {
  //Syntactic attributes
  AST_Stat statAST;
  //Semantic attribute


  // Assign the class variables when called
  public AST_StatBeginEnd(){
    this.statAST = null;
  }

  public boolean isEmbeddedNodesFull(){
    return statAST != null;
  }

  public void setSyntacticAttributes(String value){
    System.out.println("No String Syntactic Attributes");
  }


  public String getSyntacticAttributes(String strToGet){
    System.out.println("No String Syntactic Attributes");
    return null;
  }

  public AST_Node getEmbeddedAST(String astToGet, int counter){
    if(astToGet.equals("statAST")){
      return statAST;
    }
    System.out.println("Unrecognised AST Node.");
    return null;
  }

  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet){
    if(astToSet.equals("statement")){
      statAST = (AST_Stat) nodeToSet;
    } else {
      System.out.println("Unrecognised AST Node.");
    }
  }


  //Semantic Analysis and print error message if needed
  protected boolean CheckSemantics(){
    return true;
  }

  // Called from visitor
  public void Check(){
    if(CheckSemantics()){
      //Do symbol table stuff
    }
  }
}
