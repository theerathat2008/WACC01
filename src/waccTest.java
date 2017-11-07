package src;

import ASTNodes.AST_Node;
import ASTNodes.AST_Program;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import antlr.*;

public class waccTest {


  public static void main(String[] args) throws Exception {
    CharStream input = CharStreams.fromStream(System.in);

    WaccLexer lexer = new WaccLexer(input);

    CommonTokenStream tokens = new CommonTokenStream(lexer);

    WaccParser parser = new WaccParser(tokens);

    ParseTree tree = parser.program();

    if (parser.getNumberOfSyntaxErrors() != 0) {
      System.out.println("#syntax_error#");
      System.exit(100);
    }

    //System.out.println("Printing out the tree: ");
    //System.out.println(tree.toStringTree(parser));

    System.out.println("===");
    waccVisitor visitor = new waccVisitor();
    visitor.visit(tree);
    System.out.println("===");



    System.out.println("---------TESTING------------");
    AST_Program root = visitor.getRootNode();
    visitor.printNodes(root);
    System.out.println("---------TESTING------------");



  }



}
