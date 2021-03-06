package InstructionSet;

import Registers.RegisterAllocation;

public class InstructionBeginEnd extends Instruction {

  public String block1;
  public String block2;

  RegisterAllocation registerAllocation;

  /**
   * Class constructor
   * @param registerAllocation
   */
  InstructionBeginEnd(RegisterAllocation registerAllocation) {
    this.registerAllocation = registerAllocation;
  }

  /**
   * Generates the instruction block as a string for the current instruction
   */
  @Override
  public void genInstruction() {
    StringBuilder builder1 = new StringBuilder();
    if (registerAllocation.getStackSize() > 0) {
      builder1.append("\t\tSUB sp, sp, #");
      builder1.append(registerAllocation.getStackSize());
      builder1.append("\n");
    }
    block1 = builder1.toString();

    StringBuilder builder2 = new StringBuilder();
    if (registerAllocation.getStackSize() > 0) {
      builder1.append("\t\tADD sp, sp, #");
      builder1.append(registerAllocation.getStackSize());
      builder1.append("\n");
    }
    block1 = builder2.toString();
  }
}
