package InstructionSet.InstructionPrintBlocks;

import InstructionSet.Instruction;

/**
 * Base class of all print instructions.
 */
public class InstructionPrintBlocks extends Instruction {
  String reg1;
  protected String resultBlock;
  int msgNum1;

  /**
   * Class constructor - all subclasses use this
   *
   * @param msgNum1 - Indicates the ID of the message to be output
   */
  public InstructionPrintBlocks(int msgNum1) {
    this.msgNum1 = msgNum1;
    this.resultBlock = "";
    reg1 = "reg1";
  }

  public String getResultBlock() {
    return resultBlock;
  }

  @Override
  public void genInstruction(){

  }

  @Override
  public int requiresRegisters() {
    return 1;
  }

  @Override
  public boolean crossOverRegister() {
    return false;
  }

}
