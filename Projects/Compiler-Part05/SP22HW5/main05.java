package SP22HW5;

import ADT.*;

/**
 *
 * @author abrouill SPRING 2022
 */
public class main05{
    public static void main(String[] args) {
        String filePath = "./SP22HW5/Inputs/SyntaxMinimumTestSP22.txt";
        // String filePath = "./SP22HW5/Inputs/BadSyntax-1-ASP22.txt";
        // String filePath = "./SP22HW5/Inputs/BadSyntax-2-ASP22.txt";

        boolean traceon = true;
        Syntactic parser = new Syntactic(filePath, traceon);
        parser.parse();
        System.out.println("Done.");
    }
}
