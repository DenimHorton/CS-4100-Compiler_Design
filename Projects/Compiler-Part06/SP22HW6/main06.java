package SP22HW6;

//import ADT.SymbolTable;
//import ADT.Lexical;
import ADT.*;

/**
 *
 * @author abrouill FALL 2021
 */
public class main06 {

    public static void main(String[] args) {
        String filePath = ".\\SP22HW6\\Inputs\\CodeGenFULL-CSP22.txt";
        // String filePath = ".\\SP22HW6\\Inputs\\CodeGenBASIC-BSP22.txt";
        System.out.println("Parsing " + filePath);
        boolean traceon = false; // true; //false;
        Syntactic parser = new Syntactic(filePath, traceon);
        parser.parse();

        System.out.println("Done.");
    }
}
