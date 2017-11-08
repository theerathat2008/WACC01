package ASTNodes;


import SymbolTable.SymbolTable;
import org.antlr.v4.runtime.ParserRuleContext;
import src.FilePosition;

import java.util.ArrayDeque;


/**
 * Empty Base class for the AST Nodes that is generated for the parse tree
 */
public abstract class AST_Node {

  //private field to store parent node
  protected AST_Node parentNode;

  /**
   * Return the parent node of the node called
   */
  public AST_Node getParentNode() {
    return parentNode;
  }

  /**
   * set the parent node of the node parameter
   *
   * @param nodeToSet
   */
  public void setParentNode(AST_Node nodeToSet) {
    parentNode = nodeToSet;
  }

  /**
   * Return classname in string form
   */
  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }

  /**
   * Gets all children nodes of current node
   */
  public abstract ArrayDeque<AST_Node> getNodes();

  /**
   * Sets syntactic attributes of class variables by assigning it a value
   *
   * @param value - Value to be assigned to class variable
   */
  public abstract void setSyntacticAttributes(String value);

  /**
   * Gets syntactic attributes of class variables
   *
   * @param strToGet - Value to be retrieved from class variable
   */
  public abstract String getSyntacticAttributes(String strToGet);

  /**
   * Returns true if the embedded Nodes have values
   */
  public abstract boolean isEmbeddedNodesFull();

  /**
   * Return embeded AST nodes if they exist
   *
   * @param astToGet
   * @param counter
   */
  public abstract AST_Node getEmbeddedAST(String astToGet, int counter);

  /**
   * Set embeded AST nodes if they exist
   *
   * @param astToSet
   * @param nodeToSet
   */
  public abstract void setEmbeddedAST(String astToSet, AST_Node nodeToSet);

  /**
   * Semantic Analysis and print error message if needed
   *
   * @param ST
   */
  protected abstract boolean CheckSemantics(SymbolTable ST);

  /**
   * Called from visitor
   *
   * @param ST
   */
  public abstract void Check(SymbolTable ST);

  /**
   * Used for testing - Prints out contents of current AST node
   */
  public abstract void printContents();

}
