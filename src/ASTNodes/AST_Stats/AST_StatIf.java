package ASTNodes.AST_Stats;

import ASTNodes.AST_Exprs.*;
import ASTNodes.AST_Node;
import ASTNodes.AST_Stats.AST_StatIfs.AST_StatIfElse;
import ASTNodes.AST_Stats.AST_StatIfs.AST_StatIfThen;
import IdentifierObjects.IDENTIFIER;
import InstructionSet.Instruction;
import InstructionSet.InstructionIf;
import Registers.RegisterARM;
import Registers.RegisterAllocation;
import SymbolTable.SymbolTable;
import ErrorMessages.*;
import ErrorMessages.FilePosition;
import VisitorClass.AST_NodeVisitor;
import java.util.ArrayDeque;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class AST_StatIf extends AST_Stat {

  //Syntactic attributes
  AST_Expr expr;
  AST_StatIfThen thenStat;
  AST_StatIfElse elseStat;
  //Semantic attribute
  ParserRuleContext ctx;
  InstructionIf instr;
  SymbolTable symbolTable;

  /**
   * Assign the class variables when called
   */
  public AST_StatIf(ParserRuleContext ctx, SymbolTable symbolTable) {
    this.expr = null;
    this.thenStat = null;
    this.elseStat = null;
    this.ctx = ctx;
    this.symbolTable = symbolTable;
  }

  /**
   * Gets all children nodes of current node
   * @return list of AST nodes that are the children of the current node
   */
  @Override
  public ArrayDeque<AST_Node> getNodes() {
    ArrayDeque<AST_Node> returnList = new ArrayDeque<>();
    returnList.addLast(expr);
    returnList.addLast(thenStat);
    returnList.addLast(elseStat);
    return returnList;
  }

  /**
   * Sets syntactic attributes of class variables by assigning it a value
   * @param value - Value to be assigned to class variable
   */
  @Override
  public void setSyntacticAttributes(String value) {
    System.out.println("No String Syntactic Attributes in class: " + this.getClass().getSimpleName());
  }

  /**
   * Gets syntactic attributes of class variables
   * @param strToGet - Value to be retrieved from class variable
   */
  @Override
  public String getSyntacticAttributes(String strToGet) {
    System.out.println("No String Syntactic Attributes in class: " + this.getClass().getSimpleName());
    return null;
  }

  /**
   * Returns true if the embedded Nodes have value
   */
  @Override
  public boolean isEmbeddedNodesFull() {
    return expr != null && thenStat != null && elseStat != null;
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
    } else if (astToGet.equals("thenStat")) {
      return thenStat;
    } else if (astToGet.equals("elseStat")) {
      return elseStat;
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
    } else if (astToSet.equals("statement")) {

      if (thenStat == null) {
        thenStat = (AST_StatIfThen) nodeToSet;
      } else if (elseStat == null) {
        elseStat = (AST_StatIfElse) nodeToSet;
      } else {
        System.out.println("If and then in AST_StatIf have already been assigned.");
      }

    } else {
      System.out.println("Unrecognised AST Node at class: " + this.getClass().getSimpleName());
    }
  }

  /**
   * Semantic Analysis and print error message if needed
   */
  @Override
  public boolean CheckSemantics() {

    //get type of the expr of the context to see whether it is equal to type bool
    SymbolTable ST = this.symbolTable;
    IDENTIFIER typeExpr = expr.getIdentifier();

    if (expr instanceof AST_ExprIdent) {
      String varName = ((AST_ExprIdent) expr).getVarName();
      IDENTIFIER typeName = ST.lookup(varName);

      AST_Node tempNode = this.getParentNode();

      while (typeName == null) {
        ST = ST.encSymTable;
        typeName = ST.lookup(varName);
      }

      if (typeName.toString().contains("bool")) {
        return true;
      } else {
        new TypeError(new FilePosition(ctx)).printAll();
        return false;
      }
    }

    if (typeExpr != null) {
      if (typeExpr.toString().contains("bool")) {
        return true;
      } else {
        new TypeError(new FilePosition(ctx)).printAll();
        return false;
      }
    } else {
      return true;
    }
  }

  /**
   * Used for testing - Prints out contents of current AST node
   */
  @Override
  public void printContents() {
    System.out.println(this.getClass().getSimpleName() + ": ");
    if (expr == null) {
      System.out.println("expr: null");
    } else {
      System.out.println("expr: has content");
    }
    if (thenStat == null) {
      System.out.println("thenStat: null");
    } else {
      System.out.println("thenStat: has content");
    }
    if (elseStat == null) {
      System.out.println("elseStat: null");
    } else {
      System.out.println("elseStat: has content");
    }
  }

  /**
   * Used to flag special cases where the register needs a stack implementation before the backend parse
   * @param regAlloc
   */
  @Override
  public void acceptPreProcess(RegisterAllocation regAlloc) {
    expr.acceptPreProcess(regAlloc);
    thenStat.acceptPreProcess(regAlloc);
    elseStat.acceptPreProcess(regAlloc);
  }

  /**
   * Part of the visitor code gen pattern, used to generate the instruction classes
   * which are added to the instruction list
   * @param visitor
   */
  public void accept(AST_NodeVisitor visitor) {
    visitor.visit(this);
    expr.accept(visitor);
    thenStat.accept(visitor);
    elseStat.accept(visitor);
  }

  /**
   * General case to call acceptNode
   * @param visitor
   */
  public int acceptRootNode(AST_NodeVisitor visitor) {
    visitor.visit(this);

    int result = 0;

    if (expr instanceof AST_ExprLiter) {
      result = ((AST_ExprLiter) expr).acceptNode(visitor);
    } else if (expr instanceof AST_ExprBinary) {
      result = ((AST_ExprBinary) expr).acceptNode(visitor);
    } else if (expr instanceof AST_ExprUnary) {
      result = ((AST_ExprUnary) expr).acceptNode(visitor);
    }

    return result;
  }

  /**
   * Function that is iterates through the ast_nodes and adds the instruction blocks
   * in the right order to the assembly code list
   * @param assemblyList
   */
  public void acceptInstr(List<String> assemblyList) {
    expr.acceptInstr(assemblyList);
    assemblyList.add(instr.blockIf);
    thenStat.acceptInstr(assemblyList);
    assemblyList.add(instr.blockElse);
    elseStat.acceptInstr(assemblyList);
    assemblyList.add(instr.blockContinue);
  }

  /**
   * Stat if needs result of the expr reg for instruction block
   * returns null Reg as if doesn't evaluate to anything
   * Set the scopes here for then stat and else stat
   */
  @Override
  public RegisterARM acceptRegister(RegisterAllocation registerAllocation) throws Exception {

    RegisterARM exprEvalReg = expr.acceptRegister(registerAllocation);
    instr.allocateRegisters(exprEvalReg);
    registerAllocation.freeRegister(exprEvalReg);

    String oldScope = registerAllocation.getCurrentScope();

    registerAllocation.setCurrentScope("IfThen");
    thenStat.acceptRegister(registerAllocation);

    registerAllocation.setCurrentScope("IfElse");
    elseStat.acceptRegister(registerAllocation);

    registerAllocation.setCurrentScope(oldScope);

    return RegisterARM.NULL_REG;
  }

  /**
   * Creates an InstructionIf
   * Uses registers one of which is taken from InstructionExpr
   */

  /**
   * takes the embeded information corresponding to the specific instruction class and generates blocks
   * of assembly code for that instruction class
   * The embeded information is mainly the registers which is allocated using registerAllocation.
   * @param instructionList
   * @param registerAllocation
   * @throws Exception
   */
  @Override
  public void genInstruction(List<Instruction> instructionList, RegisterAllocation registerAllocation) throws Exception {
    InstructionIf instructionIf = new InstructionIf();

    //Allocate registers for exprReg

    instructionIf.setLabels(registerAllocation.generateLabel(), registerAllocation.generateLabel());

    instructionList.add(instructionIf);
    instr = instructionIf;
  }
}
