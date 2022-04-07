package SP22HW5;

import ADT.SymbolTable;
import ADT.Lexical;
import ADT.*;

/**
 *
 * @author abrouill & Robert Denim Horton
 */
public class test05 {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < args.length; i++) {
            String filePath = args[i];
            boolean traceon = true;
            Syntactic parser = new Syntactic(filePath, traceon);
            parser.parse();
            System.out.println("Done.");
        }
    }
}
