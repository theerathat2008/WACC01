package ASTNodes;

import ASTNodes.AST_Stats.AST_Stat;
import IdentifierObjects.FunctionObj;
import SymbolTable.SymbolTable;
import ASTNodes.AST_TYPES.AST_Type;
import org.antlr.v4.runtime.ParserRuleContext;
import src.ErrorMessages.FunctionRedeclarationError;
import src.FilePosition;
import java.util.ArrayDeque;

/**
 * Class representing node in AST tree for FUNCTION
 */

public class AST_FuncDecl extends AST_Node {
  //Syntactic attributes
  public AST_Type ast_type;
  String funcName;
  int numOfChildren;
  AST_ParamList paramList;
  AST_Stat statement;
  ParserRuleContext ctx;

  //Semantic attribute

  /**
   * Class constructor
   * Assign the member variables when called and set the number of children
   */
  public AST_FuncDecl(int numOfChildren, ParserRuleContext ctx){
    this.numOfChildren = numOfChildren;
    this.ast_type = null;
    this.funcName = null;
    this.paramList = null;
    this.statement = null;
    this.ctx = ctx;
  }

  /**
   * Gets all children nodes of current node
   * @return list of AST nodes that are the children of the current node
   */
  @Override
  public ArrayDeque<AST_Node> getNodes(){
    ArrayDeque<AST_Node> returnList = new ArrayDeque<>();
    returnList.addLast(ast_type);
    if(paramList != null){
      returnList.addLast(paramList);
    }
    returnList.addLast(statement);
    return returnList;
  }


  /**
   * returns true if the function has any parameters
   *
   */

  public boolean checkForParamList(){
    return numOfChildren == 8;
  }


  /**
   * Returns true if the embedded Nodes have values
   */
  @Override
  public boolean isEmbeddedNodesFull(){
    if(numOfChildren == 7){
      return ast_type != null && statement != null;
    }
    return ast_type != null && statement != null && paramList != null;
  }

  /**
   * Sets syntactic attributes of class variables by assigning it a value
   * @param value - Value to be assigned to class variable
   */
  @Override
  public void setSyntacticAttributes(String value){
    if (funcName == null){
      this.funcName = value;
    } else {
      System.out.println("Unrecognised String Attribute" + this.getClass().getSimpleName());
    }
  }

  /**
   * Gets syntactic attributes of class variables
   * @param strToGet - Value to be retrieved from class variable
   */
  @Override
  public String getSyntacticAttributes(String strToGet){
    if (strToGet.equals("funcName")){
      return funcName;
    } else {
      System.out.println("Unrecognised String Attribute" + this.getClass().getSimpleName());
      return null;
    }
  }

  /**
   * @param astToGet Shows which child to get from current node
   * @param counter Shows which child of child to get from current node
   * @return Returns the required child AST Node (determined by the astToGet parameter)
   */
  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter){
    if(astToGet.equals("paramList")){
      return paramList;
    } else if (astToGet.equals("statement")){
      return statement;
    } else if (astToGet.equals("ast_type")){
      return ast_type;
    }
    System.out.println("Unrecognised AST Node at class: " + this.getClass().getSimpleName());
    return null;
  }

  /**
   * @param astToSet Shows which child to set from current node
   * @param nodeToSet Shows which child of child to set from current node
   */
  @Override
  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet){
    if(astToSet.equals("paramList")){
      paramList = (AST_ParamList) nodeToSet;
    } else if (astToSet.equals("statement")){
      statement = (AST_Stat) nodeToSet;
    } else if (astToSet.equals("ast_type")){
      ast_type = (AST_Type) nodeToSet;
    } else {
      System.out.println("Unrecognised AST Node at class for " + astToSet + " : " + this.getClass().getSimpleName());
    }
  }

  /**
   * @return Returns the return type of the function
   */
  public String getReturnTypeName() {
    return ast_type.toString();
  }

  //Semantic Analysis and print error message if needed
  @Override
  protected boolean CheckSemantics(SymbolTable ST){
    //System.out.println(((FunctionObj) ST.lookupAll(funcName)).toString());
    if (ST.lookupAll(funcName) == null) {
      return true;
    } else {
      //System.out.println("Error on line " + ctx.getStart().getLine() + ". Function of same name already defined");
      new FunctionRedeclarationError(new FilePosition(ctx)).printAll();
      return false;
    }
  }

  @Override
  // Called from visitor
  public void Check(SymbolTable ST){
    //CheckSemantics(ST);
    if(CheckSemantics(ST)){
      //Add function to global scope i.e. program
      while(!ST.getScope().equals("global")){
        ST = ST.encSymTable;
      }
      System.out.println("Added " + funcName + " to the symbol tree: " + ST.getScope());
      ST.add(funcName, new FunctionObj(funcName, ast_type.getIdentifier(), this));
    }

      //System.out.println(ST.encSymTable.lookup(funcName)==null);
      //Create new symbol table   DONE
      //Add necessary contents specific to func to symbol table  DONE
      //set enclosing symbol table to curr symbol table   does this before check
      //Set curr symbol table to new symbol table

  }

  /**
   * Used for testing - Prints out contents of current AST node
   */
  @Override
  public void printContents(){
    System.out.println(this.getClass().getSimpleName() + ": ");
    System.out.println("Funcname: " + funcName);
    if(paramList == null){
      System.out.println("ParamList: null");
    } else {
      System.out.println("ParamList: has content");
    }
    if(statement == null){
      System.out.println("statement: null");
    } else {
      System.out.println("statement: has content");
    }
    if(ast_type == null){
      System.out.println("ast_type: null");
    } else {
      System.out.println("ast_type: has content");
    }
  }

}

