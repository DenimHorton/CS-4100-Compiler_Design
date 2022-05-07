// Robert Denim Horton CS2022
package ADT;

/**
 * @author Robert Denim Horton
 * @version 4.1
 * 
 *          This File represents the interpreter part our of compiler 
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Interpreter {
    // Declare reserve table.
    public ReserveTable optable;
    // Declare string to build loging info.
    public String trace_string;
    // Declare integer to store value after operations are made.
    public int result;
    // Declare and intialize value to hold up to 1,000 instructions
    static final int MAXQUAD = 1000;

    public Interpreter() {
        /*
         * Constructor intializes and calls intilization function to
         * popullate reserve table with opcode values.
         */
        this.optable = new ReserveTable(20);
        initReserve(optable);
    }

    private void initReserve(ReserveTable optable) {
        /*
         * Intilialization function to hold opcodes and there
         * corresponding index values.
         * 
         * @param optable Instance of table that was intialized with the values
         * proivded below.
         */
        optable.Add("STOP", 0);
        optable.Add("DIV", 1);
        optable.Add("MUL", 2);
        optable.Add("SUB", 3);
        optable.Add("ADD", 4);
        optable.Add("MOV", 5);
        optable.Add("PRINT", 6);
        optable.Add("READ", 7);
        optable.Add("JMP", 8);
        optable.Add("JZ", 9);
        optable.Add("JP", 10);
        optable.Add("JN", 11);
        optable.Add("JNZ", 12);
        optable.Add("JNP", 13);
        optable.Add("JNN", 14);
        optable.Add("JINDR", 15);
    }

    private String makeTraceString(int pc, int opcode,
            int op1, int op2, int op3) {
        /*
         * This method builds a string to log inforamtion.
         * 
         * @param pc Progaram counter to keep track of what instruction
         * to execute next.
         * 
         * @param opcode The retrived symbol/opcode from the ReserveTable
         * intialized with this instance, 'opcode'.
         * 
         * @param op1 The retrived symbol/opcode corresponding opcode
         * value stored in the 1 index from the ReserveTable intialized
         * with this instance, 'opcode'.
         * 
         * @param op2 The retrived symbol/opcode corresponding opcode
         * value stored in the 2 index from the ReserveTable intialized
         * with this instance, 'opcode'.
         * 
         * @param op3 The retrived symbol/opcode corresponding opcode
         * value stored in the 3 index from the ReserveTable intialized
         * with this instance, 'opcode'.
         */
        String result = "";
        result = "PC = " + String.format("%04d", pc) + ": "
                + (optable.LookupCode(opcode) + "     ").substring(0, 6)
                + String.format("%02d", op1) + ", " + String.format("%02d", op2)
                + ", " + String.format("%02d", op3);
        return result;
    }

    public void logger(String logger_str, String logger_filename) {
        /*
         * This method logs inforamtion to a .txt file.
         * 
         * @param logger_str
         * 
         * @param logger_filename
         */
        try {
            // Build a tabulated representation of the reserve table and
            // write it to a file.
            FileWriter trace_writer = new FileWriter(logger_filename, true);
            trace_writer.write(logger_str + "\n");
            trace_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean initializeFactorialTest(SymbolTable stable, QuadTable qtable) {
        /*
         * Test method to see is factorial 'assembly language' type instructions
         * exectue properly.
         * 
         * @param stable Intialized empty 'SymbolTable.smbol_table' instances.
         * 
         * @param qtable Intialized empty 'QuadTable.quad_table' instances.
         */
        stable.AddSymbol("n", 'v', 10);
        stable.AddSymbol("i", 'v', 0);
        stable.AddSymbol("product", 'v', 0);
        stable.AddSymbol("1", 'c', 1);
        stable.AddSymbol("$temp", 'v', 0);
        qtable.AddQuad(5, 3, 0, 2); // MOV
        qtable.AddQuad(5, 3, 0, 1); // MOV
        qtable.AddQuad(3, 1, 0, 4); // SUB
        qtable.AddQuad(10, 4, 0, 7); // JP
        qtable.AddQuad(2, 2, 1, 2); // MUL
        qtable.AddQuad(4, 1, 3, 1); // ADD
        qtable.AddQuad(8, 0, 0, 2); // JMP
        qtable.AddQuad(6, 2, 0, 0); // PRINT
        qtable.AddQuad(0, 0, 0, 0); // STOP

        // Professor said no need for checking, simple return of true works.
        return true;
    }

    public boolean initializeSummationTest(SymbolTable stable, QuadTable qtable) {
        /*
         * Test method to see is summation 'assembly language' type instructions
         * exectue properly.
         * 
         * @param stable Intialized empty 'SymbolTable.smbol_table' instances.
         * 
         * @param qtable Intialized empty 'QuadTable.quad_table' instances.
         */
        stable.AddSymbol("n", 'v', 10);
        stable.AddSymbol("i", 'v', 0);
        stable.AddSymbol("product", 'v', 0);
        stable.AddSymbol("1", 'c', 1);
        stable.AddSymbol("$temp", 'v', 0);
        stable.AddSymbol("0", 'c', 0);
        qtable.AddQuad(5, 5, 0, 2); // MOV
        qtable.AddQuad(5, 3, 0, 1); // MOV
        qtable.AddQuad(3, 1, 0, 4); // SUB
        qtable.AddQuad(10, 4, 0, 7); // JP
        qtable.AddQuad(4, 2, 1, 2); // ADD
        qtable.AddQuad(4, 1, 3, 1); // ADD
        qtable.AddQuad(8, 0, 0, 2); // JMP
        qtable.AddQuad(6, 2, 0, 0); // PRINT

        // Professor said no need for checking, simple return of true works.
        return true;
    }

    public void InterpretQuads(QuadTable Q, SymbolTable S,
                               boolean TraceOn, String filename) {
        /*
         * This function interpets values that are stored in side both
         * tables passed into the method along iwth using correspong
         * reservetable values stored inside the 'opcode' ReserveTable
         * intialize with this object.
         * 
         * @param S Intialized and information populatted 'SymbolTable'
         * instances.
         * 
         * @param Q Intialized and information populatted 'QuadTable'
         * instances.
         * 
         * @param TraceOn Boolean values for if we track information
         * to a .txt log file.
         * 
         * @param filename Name of the .txt file to save all the
         * logging information to.
         */
        // Program counter to keep track of what symbol is being
        // executed next (intialized to 0 at method call).
        int program_counter = 0;
        // Integer value to represent where the specific sysmbol is
        // stored.
        int retrived_symbol;
        // Integer value for user input.
        int input_value;
        // Integer values for the opperands values.
        int retrived_op1;
        int retrived_op2;
        int retrived_op3;

        // Overwrite new file by deleting it if it already exsits.
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }

        // Prints log once so you know it ran and replaced new info.
        if (TraceOn) {
            System.out.println("\nlogging info to:\n\t" + filename);
        }

        this.logger("run:", filename);
        while (program_counter < MAXQUAD) {
            // Retrive all the opcodes located as the indexing
            // program counter, 'program_counter'.
            retrived_op1 = Q.GetQuad(program_counter, 1);
            retrived_op2 = Q.GetQuad(program_counter, 2);
            retrived_op3 = Q.GetQuad(program_counter, 3);
            retrived_symbol = Q.GetQuad(program_counter, 0);

            // If trace is true then we log all the information to a
            // .txt file named what ever file name was passed to it,
            // 'filename'.
            if (TraceOn) {
                // Builds trace string.
                trace_string = makeTraceString(program_counter,
                                               retrived_symbol,
                                               retrived_op1,
                                               retrived_op2,
                                               retrived_op3);
                // Logs built information trace string, 'trace_string'.
                this.logger(trace_string, filename);
            }
            // Symbol Table Insturction STOP.
            // Terminate program.
            if (retrived_symbol == 0) {
                program_counter = MAXQUAD;
                this.logger("Execution terminated by program STOP.",
                        filename);
                // Symbol Table Insturction DIV.
                // Compute op1 / op2, place result into op3 (assume integer
                // divide now).
            } else if (retrived_symbol == 1) {
                result = S.GetInteger(retrived_op1)
                        / S.GetInteger(retrived_op2);
                S.UpdateSymbol(retrived_op3, 'v', result);
                program_counter++;
                // Symbol Table Insturction MUL.
                // Compute op1 * op2, place result into op3.
            } else if (retrived_symbol == 2) {
                result = S.GetInteger(retrived_op1)
                        * S.GetInteger(retrived_op2);
                S.UpdateSymbol(retrived_op3, 'v', result);
                program_counter++;
                // Symbol Table Insturction SUB.
                // Compute op1 - op2, place result into op3.
            } else if (retrived_symbol == 3) {
                result = S.GetInteger(retrived_op1)
                        - S.GetInteger(retrived_op2);
                S.UpdateSymbol(retrived_op3, 'v', result);
                program_counter++;
                // Symbol Table Insturction ADD.
                // Compute op1 + op2, place result into op3.
            } else if (retrived_symbol == 4) {
                result = S.GetInteger(retrived_op1)
                        + S.GetInteger(retrived_op2);
                S.UpdateSymbol(retrived_op3, 'v', result);
                program_counter++;
                // Symbol Table Insturction MOV.
                // Assign the value in op1 into op3 (op2 is ignored here).
            } else if (retrived_symbol == 5) {
                S.UpdateSymbol(retrived_op3, 'i',
                        S.GetInteger(retrived_op1));
                program_counter++;
                // Symbol Table Insturction PRINT.
                // Write symbol table name and value of op3 to
                // StandardOutput (console).
            } else if (retrived_symbol == 6) {
                logger(S.GetSymbol(retrived_op1) + " = "
                        + S.GetInteger(retrived_op1), filename);
                if (S.GetDataType(retrived_op1) == 's') {
                    System.out.println(S.GetSymbol(retrived_op1) + " = "
                            + S.GetInteger(retrived_op1));
                } else {
                    System.out.println(S.GetInteger(retrived_op1));
                }
                program_counter++;
                // Symbol Table Insturction READ.
                // Read the next integer from StandardInput (keyboard)
                // as value of op3.
            } else if (retrived_symbol == 7) {
                Scanner keyboard = new Scanner(System.in);
                do {
                    System.out.println("Enter an integer:");
                    input_value = keyboard.nextInt();
                } while (input_value < 0 && input_value > this.optable.rsrv_table_index);
                keyboard.close();
                program_counter = input_value;
                // Symbol Table Insturction JMP.
                // Branch (unconditional); set program counter to op3
                // (QuadTable index, not S.T.).
            } else if (retrived_symbol == 8) {
                program_counter = retrived_op3;
                // Symbol Table Insturction JZ.
                // Branch Zero; if op1 = 0, set program counter to op3.
            } else if (retrived_symbol == 9) {
                if (S.GetInteger(retrived_op1) == 0) {
                    program_counter = retrived_op3;
                } else {
                    program_counter++;
                }
                // Symbol Table Insturction JP.
                // Branch Positive; if op1 > 0, set program counter to
                // op3.
            } else if (retrived_symbol == 10) {
                if (S.GetInteger(retrived_op1) > 0) {
                    program_counter = retrived_op3;
                } else {
                    program_counter++;
                }
                // Symbol Table Insturction JN.
                // Branch Negative; if op1 < 0, set program counter to
                // op3.
            } else if (retrived_symbol == 11) {
                if (S.GetInteger(retrived_op1) < 0) {
                    program_counter = retrived_op3;
                } else {
                    program_counter++;
                }
                // Symbol Table Insturction JNZ.
                // Branch Not Zero; if op1 <> 0, set program counter to op3
            } else if (retrived_symbol == 12) {
                if (S.GetInteger(retrived_op1) != 0) {
                    program_counter = retrived_op3;
                } else {
                    program_counter++;
                }
                // Symbol Table Insturction JNP.
                // Branch Not Positive; if op1 <= 0, set program counter to
                // op3.
            } else if (retrived_symbol == 13) {
                if (S.GetInteger(retrived_op1) <= 0) {
                    program_counter = retrived_op3;
                } else {
                    program_counter++;
                }
                // Symbol Table Insturction JNN.
                // Branch Not Negative; if op1 >= 0, set program counter to
                // op3.
            } else if (retrived_symbol == 14) {
                if (S.GetInteger(retrived_op1) >= 0) {
                    program_counter = retrived_op3;
                } else {
                    program_counter++;
                }
                // Symbol Table Insturction JINDR.
                // Branch (unconditional); set program counter to op3 Symbol
                // Table value contents (indirect).
            } else if (retrived_symbol == 15) {
                program_counter = S.GetInteger(retrived_op3);
                // Checks out each instance that exsists in the opcode table
                // (0-15) any thing that is not in the opcode will through
                // the console printout. We will also double check this for
                // sake of using the method built to do this.
            } else if (retrived_symbol == 7){
                // Make a scanner to read from CONSOLE
                Scanner sc = new Scanner(System.in);
                // Put out a prompt to the user
                System.out.print('>');
                // Read one integer only
                int readval = sc.nextInt();
                // Op3 has the ST index we need, update it
                S.UpdateSymbol(retrived_op3,'i',readval);
                // Deallocate the scanner
                sc = null;
                program_counter++;
                break;
            } else if (optable.LookupCode(retrived_symbol) != "") {
                System.out.println("Not a valid 'Reserved Symbol'."
                        + " Reffer to intialized 'Reserve Table' list 'optable'.");
            }
        }
    }
}
