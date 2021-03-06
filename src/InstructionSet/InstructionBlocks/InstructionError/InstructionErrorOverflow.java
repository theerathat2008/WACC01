package InstructionSet.InstructionBlocks.InstructionError;

import Registers.RegisterARM;

public class InstructionErrorOverflow extends InstructionError {
  String reg1;
  int msgNum;

  /**
   * Class constructor
   */
  public InstructionErrorOverflow(int msgNum) {
    this.reg1 = "r0";
    this.blockType = "throw_overflow_error";
  }

  /**
   * Assigned string value indicating name of register
   * @param reg1 - first register
   */
  public void allocateRegisters(RegisterARM reg1) {
    this.reg1 = reg1.name();
  }

  /**
   * Generates the instruction block as a string for the current instruction
   */
  public void genInstruction() {
    StringBuilder builder = new StringBuilder();
    builder.append("\tp_throw_overflow_error:\n\t\tLDR ");
    builder.append(reg1);
    builder.append(", =msg_");
    builder.append(msgNum);
    builder.append("\n\t\tBL p_throw_runtime_error\n");

    this.resultBlock = builder.toString();
  }

}
