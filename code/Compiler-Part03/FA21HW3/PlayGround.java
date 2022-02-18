package FA21HW3;

import ADT.*;

/**
 *
 * @author abrouill
 */
public class PlayGround {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Admin\\Documents\\School\\CS-4100-Compiler_Design\\code\\Compiler-Part03\\FA21HW3";
        Interpreter interp = new Interpreter();
        SymbolTable st;
        QuadTable qt;

        // interpretation FACTORIAL
        st = new SymbolTable(20); // Create an empty SymbolTable
        qt = new QuadTable(20); // Create an empty QuadTable
        interp.initializeFactorialTest(st, qt); // Set up for FACTORIAL
        interp.InterpretQuads(qt, st, true, filePath + "traceFact.txt");
        st.PrintSymbolTable(filePath + "symbolTableFact.txt");
        qt.PrintQuadTable(filePath + "quadTableFact.txt");
        // interpretation SUMMATION
        st = new SymbolTable(20); // Create an empty SymbolTable
        qt = new QuadTable(20); // Create an empty QuadTable
        interp.initializeSummationTest(st, qt); // Set up for SUMMATION
        interp.InterpretQuads(qt, st, true, filePath + "traceSum.txt");
        st.PrintSymbolTable(filePath + "symbolTableSum.txt");
        qt.PrintQuadTable(filePath + "quadTableSum.txt");
    }
}
