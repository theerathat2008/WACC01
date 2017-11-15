package src.ASTNodes.AST_Stats;

import src.ASTNodes.AST_Exprs.AST_Expr;
import src.ASTNodes.AST_FuncDecl;
import src.ASTNodes.AST_Node;
import src.ASTNodes.AST_Program;
import src.SymbolTable.SymbolTable;
import src.ErrorMessages.TypeError;
import src.FilePosition;
import org.antlr.v4.runtime.ParserRuleContext;
import src.VisitorClass.AST_NodeVisitor;
import java.util.ArrayDeque;

public class AST_StatExpr extends AST_Stat {

  //Syntactic attributes
  AST_Expr expr;
  ParserRuleContext ctx;

  /**
   * Assign the class variables when called
   *
   * @param ctx
   */
  public AST_StatExpr(ParserRuleContext ctx) {
    this.expr = null;
    this.ctx = ctx;
  }

  /**
   * Gets all children nodes of current node
   *
   * @return list of AST nodes that are the children of the current node
   */
  @Override
  public ArrayDeque<AST_Node> getNodes() {
    ArrayDeque<AST_Node> returnList = new ArrayDeque<>();
    returnList.addLast(expr);
    return returnList;
  }

  /**
   * Returns true if the embedded Nodes have value
   */
  @Override
  public boolean isEmbeddedNodesFull() {
    return expr != null;
  }

  /**
   * @param astToGet Shows which child to get from current node
   * @param counter  Shows which child of child to get from current node
   * @return Returns the required child AST Node (determined by the astToGet parameter)
   */
  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter) {
    if (astToGet.equals("expr")) {
      return expr;
    }
    System.out.println("Unrecognised AST Node at class: " + this.getClass().getSimpleName());
    return null;
  }

  /**
   * @param astToSet  Shows which child to set from current node
   * @param nodeToSet Shows which child of child to set from current node
   */
  @Override
  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet) {
    if (astToSet.equals("expr")) {
      expr = (AST_Expr) nodeToSet;
    } else {
      System.out.println("Unrecognised AST Node at class: " + this.getClass().getSimpleName());
    }
  }

  /**
   * Semantic Analysis and print error message if needed
   *
   */
  @Override
  public boolean CheckSemantics() {
    if (statName.equals("free")) {
      return expr.getIdentifier().toString().contains("[]") || expr.getIdentifier().toString().startsWith("pair(");
    } else if (statName.equals("return")) {
      AST_Node parent = getParentNode();
      while (!(parent instanceof AST_FuncDecl)) {
        if (parent instanceof AST_Program) {
          System.out.println("Return statement not inside of a function.");
          new TypeError(new FilePosition(ctx)).printAll();
          return false;
        }
        parent = getParentNode();
        System.out.println("Going to AST parent, looking for function");
      }
      AST_FuncDecl temp = (AST_FuncDecl) parent;
      if (temp.ast_type.getIdentifier().equals(expr.getIdentifier())) {
        return true;
      }
    } else if (statName.equals("exit")) {
      return expr.getIdentifier().toString().equals("int");
    } else if (statName.equals("print")) {
      return true;
    } else if (statName.equals("println")) {
      return true;
    }
    new TypeError(new FilePosition(ctx)).printAll();
    return false;
  }

  /**
   * Called from visitor
   *
   * @param ST
   */
  @Override
  public void Check(SymbolTable ST) {
    //CheckSemantics(ST))
    //Do symbol table stuff
  }

  /**
   * Used for testing - Prints out contents of current AST node
   */
  @Override
  public void printContents() {
    System.out.println(this.getClass().getSimpleName() + ": ");
    super.printContents();
    if (expr == null) {
      System.out.println("expr: null");
    } else {
      System.out.println("expr: has content");
    }
  }

  public void accept(AST_NodeVisitor visitor) {
    visitor.visit(this);
    expr.accept(visitor);
  }
}
