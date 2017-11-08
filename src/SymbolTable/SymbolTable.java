package SymbolTable;


import IdentifierObjects.*;

import java.util.*;

public class SymbolTable {

  public SymbolTable encSymTable;
  public Map<String, IDENTIFIER> symMap;
  String scope;

  public SymbolTable(String scope){
    System.out.println("Created new SYMBOL TABLE!!!!!");
    symMap = new HashMap<String, IDENTIFIER>();
    this.scope = scope;
  }

  public String getScope(){
    return this.scope;
  }

  public SymbolTable() {
    System.out.println("made top level symbol table");
    this.scope = "top_level";
    symMap = new HashMap<String, IDENTIFIER>();
    encSymTable = null;
    add("int", new KeywordObj());
    add("bool", new KeywordObj());
    add("char", new KeywordObj());
    add("string", new KeywordObj());
    add("skip", new KeywordObj());
    add("read", new KeywordObj());
    add("free", new KeywordObj());
    add("return", new KeywordObj());
    add("exit", new KeywordObj());
    add("print", new KeywordObj());
    add("println", new KeywordObj());
    add("if", new KeywordObj());
    add("then", new KeywordObj());
    add("else", new KeywordObj());
    add("fi", new KeywordObj());
    add("while", new KeywordObj());
    add("do", new KeywordObj());
    add("done", new KeywordObj());
    add("begin", new KeywordObj());
    add("end", new KeywordObj());
    add("fst", new KeywordObj());
    add("snd", new KeywordObj());
    add("int", new KeywordObj());
    add("pair", new KeywordObj());
    add("len", new KeywordObj());
    add("ord", new KeywordObj());
    add("chr", new KeywordObj());
    add("true", new KeywordObj());
    add("false", new KeywordObj());
    add("null", new KeywordObj());
  }

  public void add(String name, IDENTIFIER obj){
    symMap.put(name, obj);
  }

  public IDENTIFIER lookup(String name){
    return symMap.get(name);
  }

  public IDENTIFIER lookupAll(String name){
    SymbolTable S = this;
    while(S != null){
      IDENTIFIER obj = S.lookup(name);
      if(obj != null){
        return obj;
      }
      S = S.encSymTable;
    }
    return null;
  }

  public void setEncSymTable(SymbolTable toSet){
    this.encSymTable = toSet;
  }

  public void printAllTables(){
    System.out.println("Printing all symbol tables: ");
    SymbolTable S = this;
    while(S != null){
      if(S.encSymTable == null){
        break;
      }
      printKeysTable(S);
      S = S.encSymTable;
    }
  }

  public void printKeysTable(SymbolTable T){
    System.out.println("The symbol table contents for: " + T.scope);
    Iterator<String> it = T.symMap.keySet().iterator();
    while (it.hasNext()) {
      String code = (String)it.next();
      System.out.println(code + ": " + T.symMap.get(code).getClass().getSimpleName());
    }
  }

  public IDENTIFIER stringToIdent(String name, String type) {
    if (type.equals("int")) {
      return new BaseTypeObj(name, "int");
    } else if (type.equals("bool")) {
      return new BaseTypeObj(name, "bool");
    } else if (type.equals("char")) {
      return new BaseTypeObj(name, "char");
    } else if (type.equals("string")) {
      return new BaseTypeObj(name, "string");
    } else if (type.endsWith("[]")) {
      return new ArrayObj(name, stringToIdent(name, type.substring(0, type.length()-3)));
    } else if (type.startsWith("PAIR(")) {

    } else if (type.endsWith(" FUNCTION")) {
      return new FunctionObj(name, stringToIdent(name, type.substring(0, type.length()-10)), null);
    } else if (type.equals("param_list")) {

    } else if (type.equals("keyword")) {

    }
    return null;
  }
}


