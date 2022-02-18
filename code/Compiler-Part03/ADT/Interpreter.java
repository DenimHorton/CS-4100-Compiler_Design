package ADT;

import java.io.FileWriter;
import java.io.IOException;

public class Interpreter {
    public ReserveTable optable;
    public String trace_string;

    public Interpreter() {
        // TODO document why this constructor is empty
        this.optable = new ReserveTable(20);
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

    private String makeTraceString(int pc, int opcode, int op1, int op2, int op3) {
        String result = "";
        result = "PC = " + String.format("%04d", pc) + ": " + (optable.LookupCode(opcode) + "     ").substring(0, 6)
                + String.format("%02d", op1) + ", " + String.format("%02d", op2) + ", " + String.format("%02d", op3);
        return result;
    }

    public boolean initializeFactorialTest(SymbolTable stable, QuadTable qtable) {
        // TODO document why this method is empty
        return true;
    }

    public boolean initializeSummationTest(SymbolTable stable, QuadTable qtable) {
        // TODO document why this method is empty
        return true;
    }

    public void InterpretQuads(QuadTable Q, SymbolTable S, boolean TraceOn, String filename) {
        // TODO document why this method is empty
        // while(){
        for (int i = 0; i < optable.rsrv_table_index; i++) {
            if (TraceOn) {
                System.out.println("logging info . . .");
                trace_string = makeTraceString(i, i, 0, 0, 0);
                try {
                    // Build a tabulated representation of the reserve table and write it to
                    // a file.
                    FileWriter trace_writer = new FileWriter(filename, true);
                    trace_writer.write(trace_string + "\n");
                    trace_writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
