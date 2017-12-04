package InstructionSet;

import InstructionSet.InstructionBlocks.InstructionError.InstructionDivByZero;
import InstructionSet.InstructionBlocks.InstructionError.InstructionErrorOverflow;
import InstructionSet.InstructionBlocks.InstructionError.InstructionErrorRuntime;
import InstructionSet.InstructionBlocks.InstructionPrintBlocks.InstructionPrintBlocksString;
import Registers.RegisterAllocation;

import java.util.List;

public class InstructionLibraryFunction extends Instruction {

  private String name;
  private String block1;
  List<Instruction> instructionList;
  RegisterAllocation registerAllocation;

  public InstructionLibraryFunction(String name, List<Instruction> instructionList, RegisterAllocation registerAllocation) {
    this.name = name;
    this.instructionList = instructionList;
    this.registerAllocation = registerAllocation;
  }

  public String getInstruction() {
    return block1;
  }

  @Override
  public void genInstruction() {
    StringBuilder builder = new StringBuilder("\tf_");
    builder.append(name);
    builder.append("\n\t\t");

    if (name.equals("max")) {
      builder.append("PUSH {lr}\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tLDR r5, [sp, #8]\n");
      builder.append("\t\tCMP r4, r5\n");
      builder.append("\t\tMOVGT r4, #1\n");
      builder.append("\t\tMOVLE r4, #0\n");
      builder.append("\t\tCMP r4, #0\n");
      builder.append("\t\tBEQ L0\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\tB L1\n");
      builder.append("\tL0:\n");
      builder.append("\t\tLDR r4, [sp, #8]\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\tL1:\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\t.ltorg");
      
    } else if (name.equals("min")) {
      builder.append("PUSH {lr}\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tLDR r5, [sp, #8]\n");
      builder.append("\t\tCMP r4, r5\n");
      builder.append("\t\tMOVLT r4, #1\n");
      builder.append("\t\tMOVGE r4, #0\n");
      builder.append("\t\tCMP r4, #0\n");
      builder.append("\t\tBEQ L0\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\tB L1\n");
      builder.append("\tL0:\n");
      builder.append("\t\tLDR r4, [sp, #8]\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\tL1:\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\t.ltorg");

    } else if (name.equals("factorial")) {
      builder.append("PUSH {lr}\n");
      builder.append("\t\tSUB sp, sp, #8\n");
      builder.append("\t\tLDR r4, =0\n");
      builder.append("\t\tSTR r4, [sp, #4]\n");
      builder.append("\t\tLDR r4, =1\n");
      builder.append("\t\tSTR r4, [sp]\n");
      builder.append("\t\tLDR r4, [sp, #12]\n");
      builder.append("\t\tLDR r5, =0\n");
      builder.append("\t\tCMP r4, r5\n");
      builder.append("\t\tMOVLT r4, #1\n");
      builder.append("\t\tMOVGE r4, #0\n");
      builder.append("\t\tCMP r4, #0\n");
      builder.append("\t\tBEQ L0\n");
      builder.append("\t\tLDR r4, =1\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tADD sp, sp, #8\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\tB L1\n");
      builder.append("\tL0:\n");
      builder.append("\t\tB L2\n");
      builder.append("\tL3:\n");
      builder.append("\t\tLDR r4, [sp]\n");
      builder.append("\t\tLDR r5, [sp, #4]\n");
      builder.append("\t\tSMULL r4, r5, r4, r5\n");
      builder.append("\t\tCMP r5, r4, ASR #31\n");
      builder.append("\t\tBLNE p_throw_overflow_error\n");
      builder.append("\t\tSTR r4, [sp]\n");
      builder.append("\t\tLR r4, [sp, #4]\n");
      builder.append("\t\tLDR r5, =1\n");
      builder.append("\t\tADDS r4, r4, r5\n");
      builder.append("\t\tBLVS p_throw_overflow_error\n");
      builder.append("\t\tSTR r4, [sp, #4]\n");
      builder.append("\tL2:\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tLDR r5, [sp, #12]\n");
      builder.append("\t\tCMP r4, r5\n");
      builder.append("\t\tMOVLT r4, #1\n");
      builder.append("\t\tMOVGE r4, #0\n");
      builder.append("\t\tCMP r4, #1\n");
      builder.append("\t\tBEQ L3\n");
      builder.append("\t\tLDR r4, [sp]\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tADD sp, sp, #8\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\tL1:\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\t.ltorg\n");
      builder.append("\tmain:\n");
      builder.append("\t\tPUSH {lr}\n");
      builder.append("\t\tLDR r0, =0\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\t.ltorg");

      addOverflow(instructionList, registerAllocation);

    } else if (name.equals("avg")) {   //needs to add overflow error and divide by zero error maybe (wont ever be called though)
      builder.append("PUSH {lr}\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tLDR r5, [sp, #8]\n");
      builder.append("\t\tADDS r4, r4, r5\n");
      builder.append("\t\tBLVS p_throw_overflow_error\n");
      builder.append("\t\tLDR r5, =2\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tMOV r1, r5\n");
//      builder.append("\t\tBL p_check_divide_by_zero\n");  //wont need this since will probably never be useful
      builder.append("\t\tBL __aeabi_idiv\n");
      builder.append("\t\tMOV r4, r0\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\t.ltorg");

      addOverflow(instructionList, registerAllocation);
   //   addDivByZero(instructionList, registerAllocation);


    } else if (name.equals("pow")) {
      builder.append("PUSH {lr}\n");
      builder.append("\t\tSUB sp, sp, #8\n");
      builder.append("\t\tLDR r4, =0\n");
      builder.append("\t\tSTR r4, [sp, #4]\n");
      builder.append("\t\tLDR r4, =1\n");
      builder.append("\t\tSTR r4, [sp]\n");
      builder.append("\t\tLDR r4, [sp, #16]\n");
      builder.append("\t\tLDR r5, =0\n");
      builder.append("\t\tCMP r4, r5\n");
      builder.append("\t\tMOVLT r4, #1\n");
      builder.append("\t\tMOVGE r4, #0\n");
      builder.append("\t\tCMP r4, #0\n");
      builder.append("\t\tBEQ L2\n");
      builder.append("\t\tLDR r4, =1\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tADD sp, sp, #8\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\tB L1\n");
      builder.append("\tL0:\n");
      builder.append("\t\tB L2\n");
      builder.append("\tL3:\n");
      builder.append("\t\tLDR r4, [sp]\n");
      builder.append("\t\tLDR r5, [sp, #12]\n");
      builder.append("\t\tSMULL r4, r5, r4, r5\n");
      builder.append("\t\tCMP r5, r4, ASR #31\n");
      builder.append("\t\tBLNE p_throw_overflow_error\n");
      builder.append("\t\tSTR r4, [sp]\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tLDR r5, =1\n");
      builder.append("\t\tADDS r4, r4, r5\n");
      builder.append("\t\tBLVS p_throw_overflow_error\n");
      builder.append("\t\tSTR r4, [sp, #4]\n");
      builder.append("\tL2:\n");
      builder.append("\t\tLDR r4, [sp, #4]\n");
      builder.append("\t\tLDR r5, [sp, #16]\n");
      builder.append("\t\tCMP r4, r5\n");
      builder.append("\t\tMOVLT r4, #1\n");
      builder.append("\t\tMOVGE r4, #0\n");
      builder.append("\t\tCMP r4, #1\n");
      builder.append("\t\tBEQ L3\n");
      builder.append("\t\tLDR r4, [sp]\n");
      builder.append("\t\tMOV r0, r4\n");
      builder.append("\t\tADD sp, sp, #8\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\tL1:\n");
      builder.append("\t\tPOP {pc}\n");
      builder.append("\t\t.ltorg");

      addOverflow(instructionList, registerAllocation);


    }

    block1 = builder.toString();
  }

  @Override
  public int requiresRegisters() {
    return 0;
  }

  @Override
  public boolean crossOverRegister() {
    return false;
  }

  private void addOverflow(List<Instruction> instructionList, RegisterAllocation registerAllocation) {
    registerAllocation.addString("OverflowError: the result is too small/large to store in a 4-byte signed-integer.\\n");
    InstructionErrorOverflow errorOverflow = new InstructionErrorOverflow(registerAllocation.
            getStringID("OverflowError: the result is too small/large to store in a 4-byte signed-integer.\\n"));
    instructionList.add(errorOverflow);

    registerAllocation.addString("%.*s\\0");
    InstructionPrintBlocksString instructionPrintString = new InstructionPrintBlocksString(registerAllocation.getStringID("%.*s\\0"));
    instructionList.add(instructionPrintString);

    instructionList.add(new InstructionErrorRuntime());
  }

  private void addDivByZero(List<Instruction> instructionList, RegisterAllocation registerAllocation) {
    registerAllocation.addString("DivideByZeroError: divide or modulo by zero\\n\\0");
    InstructionDivByZero divByZero = new InstructionDivByZero();
    divByZero.setOutputMessageNumber(registerAllocation.
            getStringID("DivideByZeroError: divide or modulo by zero\\n\\0"));
    instructionList.add(divByZero);

    registerAllocation.addString("%.*s\\0");
    InstructionPrintBlocksString instructionPrintString = new InstructionPrintBlocksString(registerAllocation.getStringID("%.*s\\0"));
    instructionList.add(instructionPrintString);

    instructionList.add(new InstructionErrorRuntime());
  }


}
