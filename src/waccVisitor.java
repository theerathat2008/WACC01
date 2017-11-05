package src;

import java.util.*;

import ASTNodes.AST_FuncDecl;
import ASTNodes.AST_Node;
import ASTNodes.AST_Param;
import ASTNodes.AST_Program;
import antlr.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Go thorugh all the nodes in parse tree
 * Generate AST tree
 */


public class waccVisitor extends WaccParserBaseVisitor<Void> {

  AST_Program progBase;
  AST_Node parentVisitorNode;

  /**
   * General layout of visitor functions
   * Set the current visitor Node
   * Assign new visitor Node to its embedded ast value through accessing its parent
   * Set the Curr Node
   * Semantic analysis on the currnode
   * Debug message
   * Iterate through the rest of the tree
   */

  @Override
  public Void visitProgram(WaccParser.ProgramContext ctx) {

    //Create the node for the current visitor function
    progBase = new AST_Program(ctx.getChildCount());

    //Set parentNode of AST class and global visitor class
    progBase.parentNode = null;
    parentVisitorNode = progBase;

    //Do semantic analysis
    progBase.Check();

    //Debug statement
    System.out.println("Prog");

    //Iterate through rest of the tree

    return visitChildren(ctx);
  }

  @Override
  public Void visitFunc(WaccParser.FuncContext ctx) {

    //Create the node for the current visitor function
    AST_FuncDecl funcNode = new AST_FuncDecl();

    //Set currNode to corresponding embedded AST in parent node
    parentVisitorNode.setEmbeddedAST("functionList", funcNode);

    //Set parentNode of AST class and global visitor class
    funcNode.parentNode = parentVisitorNode;
    parentVisitorNode = funcNode;

    //Do semantic analysis
    funcNode.Check();

    //Debug statement
    System.out.println("Func");

    //Iterate through rest of the tree
    return visitChildren(ctx);
  }

  //TERMINAL NODE
  @Override
  public Void visitParam(WaccParser.ParamContext ctx) {

    //Create the node for the current visitor function
    AST_Param paramNode = new AST_Param();

    //Set currNode to corresponding embedded AST in parent node
    parentVisitorNode.setEmbeddedAST("functionList", paramNode);

    //Set parentNode of AST class and global visitor class
    paramNode.parentNode = parentVisitorNode;
    parentVisitorNode = paramNode;

    //Do semantic analysis
    paramNode.Check();

    //Debug statement
    System.out.println("Param");

    //Set the parent node for terminal node
    if(){

    }


    //Iterate through rest of the tree
    return visitChildren(ctx);
  }

  @Override
  public Void visitMULT_STAT(WaccParser.MULT_STATContext ctx) {

    return visitChildren(ctx);
  }

}
/**

1. Override all the Base Visitor functions
   - Add the node to the AST node to the symbol table
   - Depending on the function do semantic analysis on it by calling check function of the AST Node class
2. Override all the Base Visitor functions
  - Create an AST Class which corresponds to the Visitor function

**/
