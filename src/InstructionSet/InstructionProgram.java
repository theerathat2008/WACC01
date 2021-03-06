package InstructionSet;

import Registers.RegisterAllocation;

/**
 * Instruction class for main program. Contains start and end of assembly code
 */
public class InstructionProgram extends Instruction {

  public String block0;
  public String block1;
  public String block2;
  RegisterAllocation registerAllocation;

  /**
   * Class constructor
   * @param registerAllocation
   */
  public InstructionProgram(RegisterAllocation registerAllocation) {
    this.registerAllocation = registerAllocation;
  }

  /**
   * Uses special registers lr(link register) and pc(program counter)
   */

  /**
   * Generates the instruction block as a string for the current instruction
   */
  @Override
  public void genInstruction() {
    block0 = "\t.global main\n";

    StringBuilder builder = new StringBuilder("");
    builder.append("\tmain:\n");
    builder.append("\t\tPUSH {lr}\n");

    if (registerAllocation.getFinalStackSize() > 0) {
      builder.append("\t\tSUB sp, sp, #");
      builder.append(registerAllocation.getFinalStackSize());
      builder.append("\n");
    }

    block1 = builder.toString();

    StringBuilder builder2 = new StringBuilder();
    if (registerAllocation.getFinalStackSize() > 0) {
      builder2.append("\t\tADD sp, sp, #");
      builder2.append(registerAllocation.getFinalStackSize());
      builder2.append("\n");
    }

    builder2.append("\t\tLDR r0, =0\n");
    builder2.append("\t\tPOP {pc}\n");
    builder2.append("\t\t.ltorg\n\n");

    block2 = builder2.toString();
  }
}
