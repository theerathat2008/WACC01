package ASTNodes.AST_Exprs;

import ASTNodes.AST_Node;
import SymbolTable.SymbolTable;
import java.util.ArrayDeque;
import IdentifierObjects.IDENTIFIER;

/**
 * Class representing node in AST tree for EXPRESSIONS - USED AS BASE CLASS FOR OTHER EXPRESSIONS
 */
public class AST_Expr extends AST_Node{
  //Syntactic attributes
  String type;
  public IDENTIFIER identifier;

  /**
   * Gets all children nodes of current node
   * @return list of AST nodes that are the children of the current node
   */
  @Override
  public ArrayDeque<AST_Node> getNodes(){
    System.out.println("Base AST Node at: " + this.getClass().getSimpleName());
    return null;
  }

  /**
   * Returns true if the embedded Nodes have value
   */
  @Override
  public boolean isEmbeddedNodesFull(){
    return true;
  }

  /**
   * Sets syntactic attributes of class variables by assigning it a value
   * @param value - Value to be assigned to class variable
   */
  @Override
  public void setSyntacticAttributes(String value){
    System.out.println("Base AST Node");
  }


  /**
   * Gets syntactic attributes of class variables
   * @param strToGet - Value to be retrieved from class variable
   */
  @Override
  public String getSyntacticAttributes(String strToGet){
    System.out.println("Base AST Node");
    return null;
  }

  /**
   * @param astToGet Shows which child to get from current node
   * @param counter Shows which child of child to get from current node
   * @return Returns the required child AST Node (determined by the astToGet parameter)
   */
  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter){
    System.out.println("Base AST Node.");
    return null;
  }

  /**
   * @param astToSet Shows which child to set from current node
   * @param nodeToSet Shows which child of child to set from current node
   */
  @Override
  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet){
    System.out.println("Base AST Node.");
  }


  //Semantic Analysis and print error message if needed
  protected boolean CheckSemantics(SymbolTable ST){
    return true;
  }

  // Called from visitor
  public void Check(SymbolTable ST){
    if(CheckSemantics(ST)){
      //Do symbol table stuff
    }
  }

  /**
   * @return returns the type of the expression
   */
  public String getType() {
    return type;
  }

  /**
   * @param type - sets the type of the current expression
   */
  public void setType(String type) {}


  /**
   * Used for testing - Prints out contents of current AST node
   */
  @Override
  public void printContents(){
    System.out.println(this.getClass().getSimpleName() + ": ");
    System.out.println("BASE TYPE");

  }

}
