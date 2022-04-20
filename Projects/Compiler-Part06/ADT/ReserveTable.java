// Robert Denim Horton CS2022
package ADT;

/**
 * @author Robert Denim Horton
 * @version 4.0
 * 
 *          This File represents the data structure that will store the objects 
 *          in an array and be what ever data corresponds for each entry via
 *          their list index. This version has a different approach of 
 *          simply building the object array inside the ReserveTable in stead 
 *          of using a stack.  The table stills keeps track of the last index of 
 *          the last entered obj 'ReservedWordEntry'.  For this part we also want to 
 *          implement more requirements which is integrate a second table that 
 *          holds symbols that have corresponding list values for specific entries.
 */

import java.io.FileWriter;
import java.io.IOException;

public class ReserveTable {

    // public static Stack stack;
    public ReservedWordEntry rsrv_word;
    public ReservedWordEntry[] rsrv_table;
    public int rsrv_table_index = -1;
    // public static int notFound;

    public ReserveTable(int maxSize) {
        /**
         * Constructor, initializes the internal storage to contain up to maxSize rows
         * of data. The maximum size of the structure will not change, and the ADT
         * should
         * operate correctly with fewer than maxSize entries; it should not be required
         * to add all the entries to fill the structure.
         * The stack represents a stack implemented from scratch the stored the
         * different
         * reserve words along with their names, op values, size of entries along with
         * size of
         * abstract data structure.
         * 
         * @param maxSize The maxsize is passed at instantiation to set the size of the
         *                objArray
         *                used to store the different table values.
         */
        rsrv_table = new ReservedWordEntry[maxSize];
    }

    public int Add(String name, int code) {
        /**
         * Adds a new entry "ReserveWord" at the top of the storage with unique values.
         * Returns the
         * index of the row where the data was placed; just adds to end of list, and
         * does not check for duplicates, and does not sort entries. The word_index will
         * be used to pass back what ever index (the top of the stack) was used for
         * storing the newly entered reserved word.
         * 
         * @param name This is the string representation of the 'name' to be stored
         *             along
         *             with passed in op code.
         * @param code This is the integer code value that will represent the op code
         *             that
         *             is specific to what ever name is being stored in the value.
         */

        rsrv_word = new ReservedWordEntry(name, code);
        rsrv_table_index++;
        rsrv_table[rsrv_table_index] = rsrv_word;
        return rsrv_table_index;
    }

    public int LookupName(String name) {
        /**
         * Returns the integer code associated with name if name is in the table, else
         * returns -1 to indicate a failed search. This must be a case insensitive
         * search.
         * 
         * @param name This parameter represent the string that we want to find in the
         *             instance of this table.
         */

        int name_op_code = -1;

        // Iterate throughout the list of entries in the table and use
        // compareToIgnoreCase
        // method to search for the same exact string ignoring case and just letter
        // comparrison. If it is not found exit out of for loop and return -1.
        for (int name_indx_iter = 0; name_indx_iter <= rsrv_table_index; name_indx_iter++) {
            // If name matches an element in the data structure return the op code for name.
            if (rsrv_table[name_indx_iter].reserved_word.compareToIgnoreCase(name) == 0) {
                name_op_code = rsrv_table[name_indx_iter].reserved_word_code;
                return name_op_code;
            }
        }
        return name_op_code;
    }

    public String LookupCode(int code) {
        /**
         * Returns the associated name contained in the list if code is there, else an
         * empty string to indicate a failed search for code.
         * 
         * @param code This is the integer value that we will look for in the data
         *             structure
         *             to find it's string representation.
         */

        String str_name = "";
        // Iterate throughout the list of entries in the table and search for the same
        // exact integer value. If it is not found exit out of for loop and return
        // an emtpy string.
        for (int name_indx_iter = 0; name_indx_iter <= rsrv_table_index; name_indx_iter++) {
            // If it finds a match it then returns the string representation that it
            // is matched with e.
            if (rsrv_table[name_indx_iter].reserved_word_code == code) {
                str_name = rsrv_table[name_indx_iter].reserved_word;
                return str_name;
            }
        }
        return str_name;
    }

    public void PrintReserveTable(String filename) {
        /**
         * Prints to the named plain ASCII text file the currently used contents of the
         * Reserve table in neat tabular format, containing the index of the row, the
         * name, and the code, one row per output text line. Any empty lines must be
         * omitted from the list. (If the list was initialized to a maxSize of 100, but
         * only 5 rows were added, only 5 rows will be printed out).
         * 
         * @param filename The file name of .txt file where the 'ReserveTable'
         *                 'rsrv_table' will be outputted to.
         */
        try {
            // Build a tabulated representation of the reserve table and write it to
            // a file.
            FileWriter rs_tbl_writer = new FileWriter(filename, false);
            rs_tbl_writer.write("||Index||Name\t   ||Code ||\n");
            rs_tbl_writer.write("----------------------------\n");
            for (int i = 0; i <= rsrv_table_index; i++) {
                if (rsrv_table[i] == null) {
                    break;
                } else {
                    String entry = String.format("||%-5s||%-10s||%-5s||%n", i, rsrv_table[i].reserved_word,
                            rsrv_table[i].reserved_word_code);
                    rs_tbl_writer.write(entry);
                }
            }
            rs_tbl_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
