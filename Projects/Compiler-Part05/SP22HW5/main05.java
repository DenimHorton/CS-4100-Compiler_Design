package SP22HW5;

import ADT.SymbolTable;
import ADT.Lexical;
import ADT.*;

/**
 *
 * @author abrouill SPRING 2022
 */
public class main05{
    public static void main(String[] args) {
        String filePath = "./SP22HW5/Inputs/SyntaxMinimumTestFA21.txt";
        boolean traceon = true;
        Syntactic parser = new Syntactic(filePath, traceon);
        parser.parse();
        System.out.println("Done.");
    }
}
