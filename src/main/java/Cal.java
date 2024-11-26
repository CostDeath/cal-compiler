import gen.calLexer;
import gen.calParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;

public class Cal {
    public static void main(String[] args) throws Exception {
        FileInputStream input;
        try {
            input = new FileInputStream(args[0]);
        } catch(Exception e) {
            System.out.println("Please provide a proper file as the program's first argument!");
            return;
        }

        calParser parser = new calParser(new CommonTokenStream(new calLexer(CharStreams.fromStream(input))));
        parser.prog();
        if(parser.getNumberOfSyntaxErrors() < 1) {
            System.out.println(args[0] + " parsed successfully");
            return;
        }
        System.out.println(args[0] + " has not parsed");
    }
}