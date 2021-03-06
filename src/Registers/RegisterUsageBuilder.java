package Registers;

/**
 * Using the builder and factory pattern to create RegisterUsage with a
 * fluent interface
 */

public class RegisterUsageBuilder {

  String scope = "Global";
  String usageType = "General";
  String subType = "null";
  String content = "null";
  String funcName = "null";
  String varName = "null";
  String operationType = "null";
  int paramCallPos = 0;

  private RegisterUsageBuilder(){}

  public static RegisterUsageBuilder aRegisterUsageBuilder(){
    return new RegisterUsageBuilder();
  }

  public RegisterUsage build(){
    RegisterUsage registerUsage = new RegisterUsage();
    registerUsage.setScope(scope);
    registerUsage.setUsageType(usageType);
    registerUsage.setSubType(subType);
    registerUsage.setContent(content);
    registerUsage.setFuncName(funcName);
    registerUsage.setVarName(varName);
    registerUsage.setOperationType(operationType);
    registerUsage.setParamCallPos(paramCallPos);
    return registerUsage;
  }

  public RegisterUsageBuilder withParamCallPos(int paramCallPos){
    this.paramCallPos = paramCallPos;
    return this;
  }

  public RegisterUsageBuilder withScope(String scope){
    this.scope = scope;
    return this;
  }
  public RegisterUsageBuilder withUsageType(String usageType){
    this.usageType = usageType;
    return this;
  }
  public RegisterUsageBuilder withContent(String content){
    this.content = content;
    return this;
  }
  public RegisterUsageBuilder withFuncName(String funcName){
    this.funcName = funcName;
    return this;
  }
  public RegisterUsageBuilder withOperationType(String operationType){
    this.operationType = operationType;
    return this;
  }
  public RegisterUsageBuilder withSubType(String subType){
    this.subType = subType;
    return this;
  }
  public RegisterUsageBuilder withVarName(String varName){
    this.varName = varName;
    return this;
  }
}
