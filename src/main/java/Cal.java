import gen.calLexer;
import gen.calParser;
import gen.calParser.ProgContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Cal {
    private static Path findFile(String[] provided) {
        if (provided.length < 2) return Path.of(provided[0].split("\\.")[0] + ".ir");
        if (!provided[1].contains(".")) return Path.of(provided[1] + ".ir");
        return Path.of(provided[1]);
    }

    public static void main(String[] args) {
        FileInputStream input;
        calParser parser;

        try {
            input = new FileInputStream(args[0]);
            parser = new calParser(new CommonTokenStream(new calLexer(CharStreams.fromStream(input))));
        } catch (IOException e) {
            System.out.println("Please provide a proper file as the program's first argument!");
            return;
        }

        ProgContext tree = parser.prog();
        if (parser.getNumberOfSyntaxErrors() < 1) {
            List<String> code = new CalVisitor().visitProg(tree).getValue();
            if (code == null) return;
            Path path = findFile(args);
            String fullCode = String.join("\n", code);
            try {
                Files.write(path, fullCode.getBytes());
            } catch (IOException e) {
                System.out.println("Something went wrong writing to your new file!");
            }
            System.out.println("\n" + args[0] + " has compiled to '" + path + "'");
        }
    }
}