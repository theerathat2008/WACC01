package InstructionSet.InstructionDeclOrAss.InstructionDeclAssArray;

import java.util.List;

public class InstructionDeclAssArrayInt extends InstructionDeclAssArray {
  List<Integer> arrayElems;

  public InstructionDeclAssArrayInt(List<Integer> arrayElems) {
    this.arrayElems = arrayElems;
    this.arraySize = arrayElems.size();
  }

  @Override
  public String getArrayElems() {
    String result = "";

    for (int i = 0; i < arraySize; i ++) {
      result.concat("\t\tLDR " +  reg3 + ", " + arrayElems.get(i) + "\n");
      result.concat("\t\tSTR " +  reg3 + ", " + "[" + reg2 + ", " + "#" + (4*(i+1))  + "]\n");
    }
    return result;
  }

  @Override
  public int requiresRegisters() {
    return 3;
  }

  @Override
  public boolean crossOverRegister() {
    return false;
  }

}