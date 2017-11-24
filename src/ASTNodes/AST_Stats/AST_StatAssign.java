package ASTNodes.AST_Stats;

import ASTNodes.AST_Exprs.AST_Expr;
import ASTNodes.AST_Exprs.AST_ExprEnclosed;
import ASTNodes.AST_Exprs.AST_ExprIdent;
import ASTNodes.AST_Exprs.AST_ExprLiter;
import ASTNodes.AST_FuncDecl;
import ASTNodes.AST_Node;
import ASTNodes.AST_Program;
import ASTNodes.AST_Stats.AST_StatAssignLHSs.AST_StatArrayElemLHS;
import ASTNodes.AST_Stats.AST_StatAssignLHSs.AST_StatAssignLHS;
import ASTNodes.AST_Stats.AST_StatAssignLHSs.AST_StatIdentLHS;
import ASTNodes.AST_Stats.AST_StatAssignLHSs.AST_StatPairElemLHS;
import ASTNodes.AST_Stats.AST_StatAssignRHSs.AST_StatAssignRHS;
import ASTNodes.AST_Stats.AST_StatAssignRHSs.AST_StatCallRHS;
import ASTNodes.AST_Stats.AST_StatAssignRHSs.AST_StatExprRHS;
import ASTNodes.AST_Stats.AST_StatAssignRHSs.AST_StatPairElemRHS;
import IdentifierObjects.IDENTIFIER;
import InstructionSet.Instruction;
import InstructionSet.InstructionAssignLit;
import Registers.RegisterARM;
import Registers.RegisterAllocation;
import Registers.StackLocation;
import SymbolTable.SymbolTable;
import ErrorMessages.TypeMismatchError;
import src.FilePosition;
import org.antlr.v4.runtime.ParserRuleContext;
import VisitorClass.AST_NodeVisitor;
import java.util.ArrayDeque;
import java.util.List;

/**
 * Class representing node in AST tree for ASSIGNMENT STATEMENTS
 */
public class AST_StatAssign extends AST_Stat {
  //Syntactic attributes
  AST_StatAssignLHS ast_statAssignLHS;
  AST_StatAssignRHS ast_statAssignRHS;
  ParserRuleContext ctx;
  SymbolTable symbolTable;
  Instruction instr;  //TODO put correct instruction type here

  /**
   * Constructor for class - initialises class variables to NULL
   */
  public AST_StatAssign(ParserRuleContext ctx, SymbolTable symbolTable) {
    this.ast_statAssignLHS = null;
    this.ast_statAssignRHS = null;
    this.ctx = ctx;
    this.symbolTable = symbolTable;
  }

  /**
   * Gets all children nodes of current node
   *
   * @return list of AST nodes that are the children of the current node
   */
  @Override
  public ArrayDeque<AST_Node> getNodes() {
    ArrayDeque<AST_Node> returnList = new ArrayDeque<>();
    returnList.addLast(ast_statAssignLHS);
    returnList.addLast(ast_statAssignRHS);
    return returnList;
  }

  /**
   * Returns true if the embedded Nodes have value
   */
  @Override
  public boolean isEmbeddedNodesFull() {
    return ast_statAssignLHS != null && ast_statAssignRHS != null;
  }

  /**
   * Sets syntactic attributes of class variables by assigning it a value
   *
   * @param value - Value to be assigned to class variable
   */
  @Override
  public void setSyntacticAttributes(String value) {
    System.out.println("No String Syntactic Attributes in class: " + this.getClass().getSimpleName());
  }

  /**
   * Gets syntactic attributes of class variables
   *
   * @param strToGet - Value to be retrieved from class variable
   */
  @Override
  public String getSyntacticAttributes(String strToGet) {
    System.out.println("No String Syntactic Attributes in class: " + this.getClass().getSimpleName());
    return null;
  }

  /**
   * @param astToGet Shows which child to get from current node
   * @param counter  Shows which child of child to get from current node
   * @return Returns the required child AST Node (determined by the astToGet parameter)
   */
  @Override
  public AST_Node getEmbeddedAST(String astToGet, int counter) {
    if (astToGet.equals("ast_statAssignLHS")) {
      return ast_statAssignLHS;
    } else if (astToGet.equals("ast_statAssignRHS")) {
      return ast_statAssignRHS;
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
    if (astToSet.equals("ast_statAssignLHS")) {
      ast_statAssignLHS = (AST_StatAssignLHS) nodeToSet;
    } else if (astToSet.equals("statAssignRHS")) {
      ast_statAssignRHS = (AST_StatAssignRHS) nodeToSet;
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

    SymbolTable ST = this.symbolTable;

    System.out.println("checking semantic of assign statement");
    System.out.println(ast_statAssignLHS.toString());
    System.out.println(ast_statAssignRHS.toString());
    System.out.println("LHS identifier is: " + ast_statAssignLHS.getIdentifier());
    System.out.println("RHS identifier is: " + ast_statAssignRHS.getIdentifier());

    IDENTIFIER typeLHS = ast_statAssignLHS.getIdentifier();
    IDENTIFIER typeRHS = ast_statAssignRHS.getIdentifier();

    if (ast_statAssignLHS instanceof AST_StatArrayElemLHS) {
      System.out.println("I'm instance of AST_StatArrayElemLHS");
      //check the size of the array if it contains any element inside
      String identName = ((AST_StatArrayElemLHS) ast_statAssignLHS).getIdentName();
      SymbolTable temporaryST = this.symbolTable;
      typeLHS = temporaryST.lookup(identName);

      while (typeLHS == null) {
        System.out.println("typeLHS is null");
        temporaryST = temporaryST.encSymTable;
        typeLHS = temporaryST.lookup(identName);
      }

      //TODO need to make more check before go inside this
      if (((AST_StatArrayElemLHS) ast_statAssignLHS).getAst_exprList().size() != 0) {
        System.out.println("I'm inside if statement");
        System.out.println(((AST_StatArrayElemLHS) ast_statAssignLHS).getAst_exprList().get(0));
        AST_Expr firstElem = ((AST_StatArrayElemLHS) ast_statAssignLHS).getAst_exprList().get(0);

        System.out.println("firstElem is: " + firstElem);
        //check if it is instace of AST_ExprIdent if it is an ident
        if (firstElem instanceof AST_ExprEnclosed) {
          return true;
        } else if (firstElem instanceof AST_ExprIdent) {
          String varName = ((AST_ExprIdent) firstElem).getVarName();
          System.out.println(varName);

          SymbolTable tempST = this.symbolTable;
          typeLHS = tempST.lookup(varName);

          while (typeLHS == null) {
            System.out.println("typeLHS is null");
            tempST = tempST.encSymTable;
            typeLHS = tempST.lookup(varName);
          }
          System.out.println(typeLHS);
        } if (firstElem instanceof AST_ExprLiter) {
          System.out.println("first elem is instance of AST_ExprLiter");
          String literal = ((AST_ExprLiter) firstElem).getLiteral();
          String constant = ((AST_ExprLiter) firstElem).getConstant();
          System.out.println("literal is: " + literal);
          System.out.println("constant is: " + constant);

          //meaning it's accessing an array
          if (literal.equals("int")) {
            if (typeLHS.toString().contains("str")) {
              //make typeLHs = char || check the type of the first elem
              System.out.println("reaches here where converting array of string to char");
              //TODO check the type of the first elem easier
              //want to use if RHS has type char to return true

              System.out.println("stat exprList is: ");
              System.out.println(((AST_StatArrayElemLHS) ast_statAssignLHS).getAst_exprList());
              return true;

            }
          }
        }
        else {
          typeLHS = ast_statAssignLHS.getIdentifier();
        }
      } else {
        typeLHS = ast_statAssignLHS.getIdentifier();
      }


      System.out.println("ast_statAssignRHS");
      if (ast_statAssignRHS instanceof AST_StatExprRHS) {
        AST_Expr ast_expr = ((AST_StatExprRHS) ast_statAssignRHS).getAst_expr();

        if (ast_expr instanceof AST_ExprIdent) {
          System.out.println("RHS is instance of AST_ExprIdent");
          String varName = ((AST_ExprIdent) ast_expr).getVarName();
          System.out.println(varName);
          typeRHS = ST.encSymTable.lookup(varName);
          System.out.println(typeRHS);
        } else {
          typeRHS = ast_statAssignRHS.getIdentifier();
        }
      } else {
        typeRHS = ast_statAssignRHS.getIdentifier();
      }

      //TODO implement general cases for typeLHS and typeRHS

    }

    System.out.println("ast_statAssignRHS");
    if (ast_statAssignRHS instanceof AST_StatExprRHS) {
      AST_Expr ast_expr = ((AST_StatExprRHS) ast_statAssignRHS).getAst_expr();

      if (ast_expr instanceof AST_ExprIdent) {
        System.out.println("RHS is instance of AST_ExprIdent");
        String varName = ((AST_ExprIdent) ast_expr).getVarName();
        System.out.println(varName);
        SymbolTable tempST = ST;
        typeRHS = tempST.lookup(varName);


        AST_Node tempNodeRHS = this.getParentNode();

        while(typeRHS == null) {
          System.out.println("typeRHS is null");
          tempST = tempST.encSymTable;
          typeRHS = tempST.lookup(varName);
        }
        System.out.println(typeRHS);
      } else {
        typeRHS = ast_statAssignRHS.getIdentifier();
      }
    } else if (ast_statAssignRHS instanceof AST_StatPairElemRHS) {
      AST_Expr ast_expr = ((AST_StatPairElemRHS) ast_statAssignRHS).getAst_expr();

      if (ast_expr instanceof AST_ExprIdent) {
        System.out.println("RHS is instance of AST_ExprIdent");
        String varName = ((AST_ExprIdent) ast_expr).getVarName();
        System.out.println(varName);
        SymbolTable tempST = ST;
        typeRHS = tempST.lookup(varName);

        AST_Node tempNodeRHS = this.getParentNode();

        while (typeRHS == null) {
          System.out.println("typeRHS is null");
          tempST = tempST.encSymTable;
          typeRHS = tempST.lookup(varName);
        }
        System.out.println(typeRHS);

        //TODO implement for fst and snd
        String exprType = ((AST_StatPairElemRHS) ast_statAssignRHS).getTypeName();
        System.out.println("exprType name is: " + exprType);

        if (exprType.equals("fst")) {
          //TODO add implementation to check type
          return true;
        } else if (exprType.equals("snd")) {
          //TODO add implementation to check type
          return true;
        }

      } else {
        typeRHS = ast_statAssignRHS.getIdentifier();
      }

    } else if (ast_statAssignRHS instanceof AST_StatCallRHS) {
      //TODO right now the type is not to supposed to be typeRHS
      System.out.println("Hey, I'm instance of AST_StatCallRHS");
      List<AST_Expr> exprList = ((AST_StatCallRHS) ast_statAssignRHS).getAst_exprList();
      String funcName = ((AST_StatCallRHS) ast_statAssignRHS).getFuncName();
      System.out.println("funcName is: " + funcName);
      System.out.println("exprList is: " + exprList);

      SymbolTable tempST = ST;
      typeRHS = tempST.lookup(funcName);

      while (typeRHS == null) {
        System.out.println("typeRHS is null");
        tempST = tempST.encSymTable;
        typeRHS = tempST.lookup(funcName);
      }

      System.out.println("Recent typeRHS is: " + typeRHS);

      /*if (exprList.size() == 1) {
        AST_Expr firstElem = exprList.get(0);
        System.out.println("firstElem is: " + firstElem);

        if (firstElem instanceof AST_ExprIdent) {
          System.out.println("firstElem is instance of AST_ExprIdent");
          String varName = ((AST_ExprIdent) firstElem).getVarName();
          System.out.println("varName is: " + varName);
          SymbolTable tempST = ST;
          typeRHS = tempST.lookup(varName);

          while (typeRHS == null) {
            System.out.println("typeRHs is null");
            tempST = tempST.encSymTable;
            typeRHS = tempST.lookup(varName);
          }
          System.out.println("typeRHs is: " + typeRHS);
        }
      }*/
    } else {
      typeRHS = ast_statAssignRHS.getIdentifier();
    }

    if (ast_statAssignLHS instanceof AST_StatPairElemLHS) {
      System.out.println("Hey, I'm instance of AST_StatPairElemLHS");

      AST_Expr expr = ((AST_StatPairElemLHS) ast_statAssignLHS).getAst_expr();
      System.out.println(expr);

      if (expr instanceof AST_ExprIdent) {
        String varName = ((AST_ExprIdent) expr).getVarName();
        System.out.println(varName);
        SymbolTable tempST = ST;
        typeLHS = tempST.lookup(varName);
        System.out.println(typeLHS);

        AST_Node tempNode = this.getParentNode();

        while (typeLHS == null) {
          System.out.println("typeLHS is null");
          tempST = tempST.encSymTable;
          typeLHS = tempST.lookup(varName);
        }

        //TODO check type for fst and snd
        if (typeLHS.toString().contains("PAIR(")) {
          System.out.println("Hey, typeLHS is of type pair!");
          System.out.println("The type for expression is");
          String typeExpr = ((AST_StatPairElemLHS) ast_statAssignLHS).getTypeName();
          System.out.println(typeExpr);

          if (typeExpr.equals("fst")) {
            //TODO add
            return true;
          } else if (typeExpr.equals("snd")) {
            //TODO add
            return true;
          }
        }

      } else {
        typeLHS = ast_statAssignLHS.getIdentifier();
      }
    }

    System.out.println("Hey, I reach the general cases");
    System.out.println("typeLHS is: " + typeLHS);
    System.out.println(ast_statAssignLHS.getIdentifier());
    System.out.println("typeRHS is: " + typeRHS);
    System.out.println(ast_statAssignRHS.getIdentifier());
    if (typeLHS.toString().contains("PAIR(") && typeRHS.toString().contains("PAIR(")) {
      String stringLHS = typeLHS.toString();
      String pairLHS = stringLHS.substring(stringLHS.indexOf("P"), stringLHS.indexOf(")"));
      String stringRHS = typeRHS.toString();
      String pairRHS  =stringRHS.substring(stringRHS.indexOf("P"), stringRHS.indexOf(")"));
      if (pairLHS.contains(pairRHS) || pairRHS.contains(pairLHS)) {
        return true;
      } else {
        new TypeMismatchError(new FilePosition(ctx)).printAll();
        return false;
      }
    }
    if (typeLHS.toString().contains(typeRHS.toString()) || typeRHS.toString().contains(typeLHS.toString())) {
      return true;
    } else {
      new TypeMismatchError(new FilePosition(ctx)).printAll();
      return false;
    }
    /*String identifierLHS = ast_statAssignLHS.getIdentifier().toString();
    String identifierRHS = ast_statAssignRHS.getIdentifier().toString();

    if (identifierLHS.contains(identifierRHS) || identifierRHS.contains(identifierLHS)) {
      return true;
    } else {
      new TypeMismatchError(new FilePosition(ctx)).printAll();
      return false;
    }*/
  }

  /**
   * Called from visitor
   *
   * @param ST
   */
  @Override
  public void Check(SymbolTable ST) {
    CheckSemantics();
  }

  /**
   * Used for testing - Prints out contents of current AST node
   */
  @Override
  public void printContents() {
    System.out.println(this.getClass().getSimpleName() + ": ");
    if (ast_statAssignLHS == null) {
      System.out.println("ast_statAssignLHS: null");
    } else {
      System.out.println("ast_statAssignLHS: has content");
    }
    if (ast_statAssignRHS == null) {
      System.out.println("ast_statAssignRHS: null");
    } else {
      System.out.println("ast_statAssignRHS: has content");
      ast_statAssignRHS.printContents();
    }
  }

  public void accept(AST_NodeVisitor visitor) {
    visitor.visit(this);
    ast_statAssignLHS.accept(visitor);
    ast_statAssignRHS.accept(visitor);
  }

  @Override
  public void acceptInstr(List<String> assemblyCode) {
    assemblyCode.add(instr.toString());
  }

  @Override
  public void acceptRegister(RegisterAllocation registerAllocation) throws Exception {

    registerAllocation.useRegister("expr");
    ast_statAssignRHS.acceptRegister(registerAllocation);
    RegisterARM reg1 = registerAllocation.searchByValue("expr");
    registerAllocation.freeRegister(reg1);

    ast_statAssignLHS.acceptRegister(registerAllocation);

    if(ast_statAssignLHS instanceof AST_StatIdentLHS){
      String result;
      if(registerAllocation.getStackSize() > 0){

        StringBuilder builder = new StringBuilder();

        builder.append("[sp, #");
        int displacement = registerAllocation.getStackSize();
        int memSize = registerAllocation.getMemSize(ast_statAssignLHS.getIdentifier().toString());
        builder.append(displacement + memSize);
        result = builder.toString();

        String identName = ((AST_StatIdentLHS) ast_statAssignLHS).getIdentName();
        String scope = registerAllocation.getCurrentScope();
        int location = (displacement + memSize);
        registerAllocation.addToStack(identName, new StackLocation(location ,scope));
      }

    }


    RegisterARM interReg = registerAllocation.useRegister("intermediate");
    //instructionAssign.allocateRegisters(interReg, result);
    registerAllocation.freeRegister(interReg);

  }


  /**
   * Needs two register corresponding to the destination and source in two lines
   * and an intermediate register to hold the value
   *   MOV r4, #'Z'    ->CHAR
   *	 STRB r4, [sp]
   *	 or
   *	 LDR r4, =20     ->INT
   *	 STR r4, [sp]
   *	 or
   *	 LDR r4, =msg_1   ->STRING
   *   STR r4, [sp]
   *
   *   STRB r4, [sp]    ->BOOL
   *   LDRSB r4, [sp]
   *
   */


  public void genInstruction(List<Instruction> instructionList, RegisterAllocation registerAllocation) throws Exception {

    String type = ast_statAssignRHS.getIdentifier().toString();
    //InstructionNewVar instructionAssign = new InstructionNewVar(type);


    //instructionList.add(instructionAssign);
  }
}
