package SP22HW4;

//import ADT.SymbolTable;
//import ADT.Lexical;
import ADT.*;

/**
 *
 * @author abrouill SPRING 2021
 */
public class main {

    public static void main(String[] args) {
        String fileAndPath = ".\\SP22HW4\\Inputs\\LexicalTestPlayground.txt";
        System.out.println("Lexical for " + fileAndPath);
        boolean traceOn = true;
        // Create a symbol table to store appropriate3 symbols found
        SymbolTable symbolList;
        symbolList = new SymbolTable(150);
        Lexical myLexer = new Lexical(fileAndPath, symbolList, traceOn);
        Lexical.token currToken;
        currToken = myLexer.GetNextToken();
        while (currToken != null) {
            System.out.println("\t" + currToken.mnemonic + " | \t" + String.format("%04d", currToken.code)
                    + " | \t" + currToken.lexeme);
            currToken = myLexer.GetNextToken();
        }
        symbolList.PrintSymbolTable(".\\SP22HW4\\Outputs\\symbolTableLex.txt");
        System.out.println("Done.");
    }

}
