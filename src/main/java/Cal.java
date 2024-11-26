import gen.calLexer;
import gen.calParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;

public class Cal {
    public static void main(String[] args) {
        FileInputStream input;
        calParser parser;

        try {
            input = new FileInputStream(args[0]);
            parser = new calParser(new CommonTokenStream(new calLexer(CharStreams.fromStream(input))));
        } catch(IOException e) {
            System.out.println("Please provide a proper file as the program's first argument!");
            return;
        }

        ParseTree tree = parser.prog();
        if(parser.getNumberOfSyntaxErrors() > 0) {
            System.out.println(args[0] + " has not parsed");
            return;
        }
        System.out.println(args[0] + " parsed successfully");

        new CalVisitor().visit(tree);
    }
}