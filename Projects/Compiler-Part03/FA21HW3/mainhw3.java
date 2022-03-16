package FA21HW3;
/*
HERE is code so that all the trace outputs match the intended result 
    private String makeTraceString(int pc, int opcode,int op1,int op2,int op3 ){
        String result = "";
        result = "PC = "+String.format("%04d", pc)+": "+(optable.LookupCode(opcode)
+"     ").substring(0,6)+String.format("%02d",op1)+
                                ", "+String.format("%02d",op2)+", 
"+String.format("%02d",op3);
        return result;
    }
HERE is a free opcode table initialization for a created ReserveTable
  private void initReserve(ReserveTable optable){
      optable.Add("STOP", 0);
      optable.Add("DIV", 1);
      optable.Add("MUL", 2);
      optable.Add("SUB", 3);
      optable.Add("ADD", 4);
      optable.Add("MOV", 5);
      optable.Add("STI", 6);
      optable.Add("LDI", 7);
      optable.Add("BNZ", 8);
      optable.Add("BNP", 9);
      optable.Add("BNN", 10);
      optable.Add("BZ", 11);
      optable.Add("BP", 12);
      optable.Add("BN", 13);
      optable.Add("BR", 14);
      optable.Add("BINDR", 15);
      optable.Add("PRINT", 16);
 }
*/

// package project1a;

import ADT.*;

/**
 *
 * @author abrouill
 */
public class mainhw3 {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\denim\\Documents\\School\\CS-4100-Compiler_Design\\code\\Compiler-Part03\\FA21HW3\\";
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
