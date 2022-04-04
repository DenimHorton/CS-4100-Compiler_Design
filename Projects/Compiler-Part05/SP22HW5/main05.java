package project3;

import ADT.SymbolTable;
import ADT.Lexical;
import ADT.*;

/**
 *
 * @author abrouill SPRING 2022
 */
public class main {

    public static void main(String[] args) {
        String filePath = "d:\\SyntaxTestSP22.txt";
        boolean traceon = true;
        Syntactic parser = new Syntactic(filePath, traceon);
        parser.parse();
        System.out.println("Done.");
    }

}
