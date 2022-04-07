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

        String filePath_mini = "./SP22HW5/Inputs/MinimumSyntax-1-ASP22.txt";
        String filePath_bad1 = "./SP22HW5/Inputs/BadSyntax-1-ASP22.txt";
        String filePath_bad2 = "./SP22HW5/Inputs/BadSyntax-2-ASP22.txt";
        String filePath_good = "./SP22HW5/Inputs/GoodSyntax-1-ASP22.txt";
        String[] strArray = { filePath_bad1, filePath_bad2, filePath_good };
        // String[] strArray = { filePath_mini };
        // String[] strArray = { filePath_bad1 };
        // String[] strArray = { filePath_bad2 };
        // String[] strArray = { filePath_good };

        for (int flPath = 0; flPath < strArray.length; flPath++) {
            String filePath = strArray[flPath];

            boolean traceon = true;
            Syntactic parser = new Syntactic(filePath, traceon);
            System.out.print("Running test from text file at location:");
            System.out.println(strArray[flPath]);
            parser.parse();
            System.out.println("Done.\n\n");
        }
    }
}
