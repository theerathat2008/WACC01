package ASTNodes.AST_Exprs;

import InstructionSet.Instruction;
import InstructionSet.InstructionAssignLit;
import Registers.RegisterARM;

import Registers.RegisterAllocation;
import Registers.RegisterUsage;
import org.antlr.v4.runtime.ParserRuleContext;
import ASTNodes.AST_Node;
import SymbolTable.SymbolTable;

import java.util.ArrayDeque;
import java.util.List;

import VisitorClass.AST_NodeVisitor;

import IdentifierObjects.*;
import static Registers.RegisterUsageBuilder.*;

/**
 * Class representing node in AST tree for LITERAL EXPRESSIONS
 */
public class AST_ExprLiter extends AST_Expr {

  //Syntactic attributes
  String constant;    //TODO change to content
  String literal;
  ParserRuleContext ctx;
  InstructionAssignLit instr;   //TODO put correct instruction type here.

  /**
   * Constructor for class - initialises class variables to NULL
   */
  public AST_ExprLiter(ParserRuleContext ctx) {
    this.constant = null;
    this.literal = null;
    this.ctx = ctx;

  }

  /**
   * Gets all children nodes of current node
   *
   * @return list of AST nodes that are the children of the current node
   */
  @Override
  public ArrayDeque<AST_Node> getNodes() {
    System.out.println("Terminal AST Node at: " + this.getClass().getSimpleName());
    return null;
  }

  /**
   * Sets syntactic attributes of class variables by assigning it a value
   *
   * @param value - Value to be assigned to class variable
   */
  @Override
  public void setSyntacticAttributes(String value) {
    System.out.println("VALUE IS: " + value);
    if (constant == null) {
      this.constant = value;
    } else if (literal == null) {
      this.literal = value;
      identifier = new BaseTypeObj(literal);
    } else {
      System.out.println("Unrecognised String Attribute" + this.getClass().getSimpleName());
    }
  }

  /**
   * Gets syntactic attributes of class variables
   *
   * @param strToGet - Value to be retrieved from class variable
   */
  @Override
  public String getSyntacticAttributes(String strToGet) {
    if (strToGet.equals("constant")) {
      return constant;
    } else if (strToGet.equals("literal")) {
      return literal;
    } else {
      System.out.println("Unrecognised String Attribute" + this.getClass().getSimpleName());
      return null;
    }
  }

  /**
   * Returns true if the embedded Nodes have value
   */
  @Override
  public boolean isEmbeddedNodesFull() {
    return true;
  }

  /**
   * @param astToGet Shows which child to get from current node
   * @param counter  Shows which child of child to get from current node
   * @return Returns the required child AST Node (determined by the astToGet parameter)
   */
  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter) {
    System.out.println("Terminal AST Node at: " + this.getClass().getSimpleName());
    return null;
  }

  /**
   * @param astToSet  Shows which child to set from current node
   * @param nodeToSet Shows which child of child to set from current node
   */
  @Override
  public void setEmbeddedAST(String astToSet, AST_Node nodeToSet) {
    System.out.println("Terminal AST Node at: " + this.getClass().getSimpleName());
  }

  /**
   * Semantic Analysis and print error message if needed
   */
  @Override
  public boolean CheckSemantics() {
    System.out.println("LITERAL: " + literal);
    setType(literal);
    setType(literal);

    //if it is int liter, check whether the number is inside the integer bounds
    //TODO reuntimeErr cases check
    if (literal.equals("int")) {
      if (Long.parseLong(constant) > Math.pow(2, 31) || Long.parseLong(constant) < -Math.pow(2, 31)) {
        System.out.println("Errors detected during compilation! Exit code 100 returned.");
        System.out.println("#syntax_error#");
        System.exit(100);
      } else {
        return true;
      }
    }
    //TODO implements error
    //check for only 'true' or 'false'
    if (literal.equals("bool")) {
      if (!(constant.equals("true") || constant.equals("false"))) {
        System.out.println("Boolean literal can only be 'true or 'false'.");
        return false;
      } else {
        return true;
      }
    }

    //check if it does not point to any pair
    if (literal.contains("pair")) {
      //TODO if it does not point to any pair
      if (constant != null) {
        System.out.println("The only pair literal is 'null.");
        return false;
      } else {
        return true;
      }
    }

    //TODO implement errors
    if (literal.equals("string")) {
      //check if the string literals are between two '"' symbols
      if (!(constant.charAt(0) == '"') && (constant.charAt(constant.length() - 1) == '"')) {
        System.out.println("String literals must be between two symbols");
        return false;
      }

      //check whether it is sequences of characters
      for (int i = 1; i < constant.length() - 1; i++) {
        if (!Character.isLetterOrDigit(constant.charAt(i))) {
          System.out.println("String literals must only consist of a sequence of character literals.");
          return false;
        }
      }
    }

    if (literal.equals("char")) {
      if (constant.length() == 1) {
        System.out.println("Character literals must be of length 1.");
        return false;
      }

      /*if (!Character.isLetterOrDigit(constant.charAt(0))) {
        System.out.println("Valid character literals must be ASCII character.");
        return false;
      }*/
    }
    return true;
  }

  /**
   * Called from visitor
   *
   * @param ST
   */
  @Override
  public void Check(SymbolTable ST) {

    if (CheckSemantics()) {
      setType(literal);
    }
  }

  /**
   * Used for testing - Prints out contents of current AST node
   */
  @Override
  public void printContents() {
    System.out.println(this.getClass().getSimpleName() + ": ");
    System.out.println("constant: " + constant);
    System.out.println("literal: " + literal);
  }

  @Override
  public void acceptPreProcess(RegisterAllocation regAlloc) {

  }

  public void accept(AST_NodeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void acceptInstr(List<String> assemblyCode) {
    assemblyCode.add(instr.block1);
  }


  /**
   *
   */

  @Override
  public RegisterARM acceptRegister(RegisterAllocation registerAllocation) throws Exception {


    RegisterUsage usage = aRegisterUsageBuilder()
        .withUsageType("exprType")
        .withSubType("resultType")
        .withScope(registerAllocation.getCurrentScope())
        .withContent(constant)
        .build();

    RegisterARM resultReg = registerAllocation.useRegister(usage);

    instr.registerAllocation(resultReg);

    return resultReg;
  }


  /**
   * Terminal class which holds the actual constant value
   * Generates Assembly code for InstructionMessage and InstructionAssignLit
   * INT_LITER: Holds the constant value for =Num when loading into a register
   * BOOL_LITER: Holds the contant value for #1 or #0 for moving into a register
   * CHAR_LITER: generate InstructionMessage
   * STR_LITER:  generate InstructionMessage
   * PAIR_LITER: Corresponds to =0 in the LDR instruction
   */


  public void genInstruction(List<Instruction> instructionList, RegisterAllocation registerAllocation) throws Exception {

    if (literal.equals("str")) {
      String string = constant;
      string = string.substring(1, constant.length() -1);
      registerAllocation.addString(string);
      InstructionAssignLit instructionAssignLit = new InstructionAssignLit(constant, literal);
      instructionAssignLit.setStringMsgNum(Integer.toString(registerAllocation.getStringID(string)));
      instr = instructionAssignLit;
      instructionList.add(instr);
    } else {
      InstructionAssignLit instructionAssignLit = new InstructionAssignLit(constant, literal);
      instr = instructionAssignLit;
      instructionList.add(instr);
    }

  }

  public String getLiteral() {
    return literal;
  }

  public String getConstant() {
    return constant;
  }
}
