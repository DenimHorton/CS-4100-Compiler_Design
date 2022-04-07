package SP22HW5;

import ADT.SymbolTable;
import ADT.Lexical;
import ADT.*;

/**
 * @author abrouill SPRING 2022
 */
public class main05 {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Robert Denim Horton, 9803, CS4100, SPRING 2022, Compiler IDE used: VSCode");
        String filePath = "./SP22HW5/Inputs/GoodSyntax-1-ASP22.txt";

        boolean traceon = true;
        Syntactic parser = new Syntactic(filePath, traceon);
        parser.parse();
        System.out.println("Done.");

    }
}
