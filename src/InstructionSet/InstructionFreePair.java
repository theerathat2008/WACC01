package InstructionSet;

public class InstructionFreePair extends Instruction {
  String reg1;
  String reg2;
  String sp;
  String resultBlock = "";


  public void allocateSP(String sp) {
    this.sp = sp;
  }

  public void allocateRegisters(String reg1, String reg2) {
    this.reg1 = reg1;
    this.reg2 = reg2;
  }

  @Override
  public void genInstruction() {
    resultBlock.concat("\t\tLDR " +  reg2 + ", " + sp + "\n");
    resultBlock.concat("\t\tMOV " +  reg1 + ", " + reg2 +"\n");
    resultBlock.concat("\t\tBL p_free_pair\n");
  }

  @Override
  public int requiresRegisters() {
    return 2;
  }

  @Override
  public boolean crossOverRegister() {
    return false;
  }

}
