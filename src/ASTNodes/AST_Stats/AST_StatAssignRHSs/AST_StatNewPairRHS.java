package ASTNodes.AST_Stats.AST_StatAssignRHSs;

import ASTNodes.AST_Exprs.AST_Expr;
import ASTNodes.AST_Exprs.AST_ExprBinary;
import ASTNodes.AST_Exprs.AST_ExprLiter;
import ASTNodes.AST_Exprs.AST_ExprUnary;
import ASTNodes.AST_Node;
import InstructionSet.Instruction;
import InstructionSet.InstructionDeclOrAss.InstructionDeclAssPair;
import Registers.RegisterARM;
import Registers.RegisterAllocation;
import Registers.RegisterUsage;
import SymbolTable.SymbolTable;
import VisitorClass.AST_NodeVisitor;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import static Registers.RegisterUsageBuilder.aRegisterUsageBuilder;

/**
 * Class representing node in AST tree for NEW PAIR ASSIGNMENT
 */
public class AST_StatNewPairRHS extends AST_StatAssignRHS {

  //Syntactic attributes
  AST_Expr ast_expr_first;
  AST_Expr ast_expr_second;
  InstructionDeclAssPair instructionDeclAssPair;

  /**
   * Constructor for class - initialises class variables to NULL
   */
  public AST_StatNewPairRHS() {
    this.ast_expr_first = null;
    this.ast_expr_second = null;
  }

  /**
   * Gets all children nodes of current node
   * @return list of AST nodes that are the children of the current node
   */
  @Override
  public ArrayDeque<AST_Node> getNodes() {
    ArrayDeque<AST_Node> returnList = new ArrayDeque<>();
    returnList.addLast(ast_expr_first);
    returnList.addLast(ast_expr_second);
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
    return ast_expr_first != null && ast_expr_second != null;
  }

  /**
   * @param astToGet Shows which child to get from current node
   * @param counter  Shows which child of child to get from current node
   * @return Returns the required child AST Node (determined by the astToGet parameter)
   */
  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter) {
    if (astToGet.equals("ast_expr_first")) {
      return ast_expr_first;
    } else if (astToGet.equals("ast_expr_second")) {
      return ast_expr_second;
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
      if (ast_expr_first == null) {
        ast_expr_first = (AST_Expr) nodeToSet;
      } else if (ast_expr_second == null) {
        ast_expr_second = (AST_Expr) nodeToSet;
      } else {
        System.out.println("If and then in AST_StatIf have already been assigned.");
      }
    } else if (astToSet.equals("ast_expr_second")) {
      ast_expr_second = (AST_Expr) nodeToSet;
    } else {
      System.out.println("Unrecognised AST Node at class: " + this.getClass().getSimpleName());
    }
  }

  /**
   * @param ST - Parameter of current symbol table in scope
   * @return - Returns the type of the identifier variable
   */
  public String getType(SymbolTable ST) {
    return "PAIR(" + ast_expr_first.toString() + "," + ast_expr_second + ")";
  }

  /**
   * Semantic Analysis and print error message if needed
   */
  @Override
  public boolean CheckSemantics() {
    return true;
  }

  /**
   * Used for testing - Prints out contents of current AST node
   */
  @Override
  public void printContents() {
    System.out.println(this.getClass().getSimpleName() + ": ");
    if (ast_expr_first == null) {
      System.out.println("ast_expr_first: null");
    } else {
      System.out.println("ast_expr_first: has content");
    }
    if (ast_expr_second == null) {
      System.out.println("ast_expr_second: null");
    } else {
      System.out.println("ast_expr_second: has content");
    }
  }

  /**
   * Used to flag special cases where the register needs a stack implementation before the backend parse
   * @param regAlloc
   */
  @Override
  public void acceptPreProcess(RegisterAllocation regAlloc) {
    ast_expr_first.acceptPreProcess(regAlloc);
    ast_expr_second.acceptPreProcess(regAlloc);
  }

  /**
   * Part of the visitor code gen pattern, used to generate the instruction classes
   * which are added to the instruction list
   * @param visitor
   */
  public void accept(AST_NodeVisitor visitor) {
    visitor.visit(this);
    ast_expr_first.accept(visitor);
    ast_expr_second.accept(visitor);
  }

  /**
   * General case to call acceptNode
   * @param visitor
   */
  public List<Integer> acceptRootNode(AST_NodeVisitor visitor) {
    visitor.visit(this);

    int result1 = 0;

    if (ast_expr_first instanceof AST_ExprLiter) {
      result1 = ((AST_ExprLiter) ast_expr_first).acceptNode(visitor);
    } else if (ast_expr_first instanceof AST_ExprBinary) {
      result1 = ((AST_ExprBinary) ast_expr_first).acceptNode(visitor);
    } else if (ast_expr_first instanceof AST_ExprUnary) {
      result1 = ((AST_ExprUnary) ast_expr_first).acceptNode(visitor);
    }

    int result2 = 0;
    if (ast_expr_second instanceof AST_ExprLiter) {
      result2 = ((AST_ExprLiter) ast_expr_second).acceptNode(visitor);
    } else if (ast_expr_second instanceof AST_ExprBinary) {
      result2 = ((AST_ExprBinary) ast_expr_second).acceptNode(visitor);
    } else if (ast_expr_second instanceof AST_ExprUnary) {
      result2 = ((AST_ExprUnary) ast_expr_second).acceptNode(visitor);
    }

    List<Integer> newPair = new ArrayList<>();
    newPair.add(result1);
    newPair.add(result2);

    return newPair;
  }

  /**
   * Function that is iterates through the ast_nodes and adds the instruction blocks
   * in the right order to the assembly code list
   * @param assemblyCode
   */
  @Override
  public void acceptInstr(List<String> assemblyCode) {
    assemblyCode.add(instructionDeclAssPair.getBlock1());
    ast_expr_first.acceptInstr(assemblyCode);
    assemblyCode.add(instructionDeclAssPair.getBlock2());
    ast_expr_second.acceptInstr(assemblyCode);
    assemblyCode.add(instructionDeclAssPair.getBlock3());
  }

  /**
   * allocateRegisters(RegisterARM regR0, RegisterARM tempReg, RegisterARM tempPairAddressReg, String finalPairAddressReg)
   */
  @Override
  public RegisterARM acceptRegister(RegisterAllocation registerAllocation) throws Exception {

    RegisterUsage usage = aRegisterUsageBuilder()
        .withScope(registerAllocation.getCurrentScope())
        .withUsageType("tempType")
        .build();

    RegisterARM tempReg = registerAllocation.useRegister(usage);


    RegisterARM tempPairAddressReg = ast_expr_first.acceptRegister(registerAllocation);
    registerAllocation.freeRegister(tempPairAddressReg);
    tempPairAddressReg = ast_expr_second.acceptRegister(registerAllocation);


    instructionDeclAssPair.allocateRegisters(RegisterARM.r0, tempReg, tempPairAddressReg);
    registerAllocation.freeRegister(tempPairAddressReg);


    return tempReg;
  }

  /**
   * takes the embeded information corresponding to the specific instruction class and generates blocks
   * of assembly code for that instruction class
   * The embeded information is mainly the registers which is allocated using registerAllocation.
   * @param instructionList
   * @param registerAllocation
   * @throws Exception
   */
  public void genInstruction(List<Instruction> instructionList, RegisterAllocation registerAllocation) throws Exception {
    instructionDeclAssPair
        = new InstructionDeclAssPair(ast_expr_first.getType(), ast_expr_second.getType()
        , ast_expr_first.getIdentifier().toString(), ast_expr_second.getIdentifier().toString());
    instructionList.add(instructionDeclAssPair);
  }
}
