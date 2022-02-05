package ADT;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Robert Denim Horton
 * Class CS 4100.001 'Compiler Design 1'
 * @version 2.0
 * 
 */
public class QuadTable {
    public static int[][] quad_table;
    private static int quad_table_index = -1;

    public QuadTable(int maxSize){
        /**
         * Constructor creates a new, empty QuadTable ready for data to
         * be added, with the specified maxumum number of rows (maxSize).
         * An array is suggested as the cleanest implementation of this,
         * along with a private int nextAvailable counter to keep track 
         * of which rows have been used so far.
         */
        int[][] quad_table = new int[maxSize][4];
    }

    public int NextQuad(){
        /**
         * Returns the int index of the next open slot in the QuadTable.  
         * Very important during code generation, this must be implemented 
         * exactly as described, and must be the index where the next 
         * AddQuad call will put its data.
         */
        return quad_table_index + 1;
    }

    public void AddQuad(int opcode, int op1, int op2, int op3){
        /**
         * Expands the active length of the quad table by adding a new row
         * at the NextQuad slot, with the parameters sent as the new contents,
         * and increments the NextQuad counter to the next available (empty)
         * index when done.
         */
        quad_table_index ++;
        quad_table[quad_table_index][0] = opcode;
        quad_table[quad_table_index][1] = op1;
        quad_table[quad_table_index][2] = op2;
        quad_table[quad_table_index][3] = op3;
    }

    public int GetQuad(int index, int column){
        /**
         * Returns the int data for the row and column specified at index, column.
        */
        return quad_table[index][column];
    }

    public void UpdateQuad(int index, int opcode, int op1, int op2, int op3){
        /**
         * Changes the contents of the existing quad at index.  Used only when 
         * backfilling jump addresses later, during code generation, and very 
         * important.
         */
        quad_table[index][0] = opcode;
        quad_table[index][2] = op1;
        quad_table[index][3] = op2;
        quad_table[index][4] = op3;
    }

    public void PrintQuadTable(String filename){
        /**
         * Prints to the named file only the currently used contents of the Quad
         * table in neat tabular format, one row per output text line.
         */
        try {
            // Build a tabulated representation of the reserve table and write it to 
            // a file.
            FileWriter rs_tbl_writer = new FileWriter(filename, false);
            rs_tbl_writer.write("||Index||operan||op1||op2||op3||\n");
            rs_tbl_writer.write("----------------------------\n");
            for (int i = 0; i <= quad_table_index; i++) {
                if(quad_table[i] == null){
                    break;
                } else { 
                    String entry = String.format("||%-5s||%-10s||%-5s||%-5s||\n", i, quad_table[i][0], quad_table[i][1], quad_table[i][2], quad_table[i][3]);
                    rs_tbl_writer.write(entry);
                }
            }
            rs_tbl_writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}