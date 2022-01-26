package ADT;

import java.io.FileWriter;
import java.io.IOException;


public class ReserveTable {

    public static Stack stack;
    public ReservedWord new_word;
    public String path = "C:\\Users\\denim\\Documents\\School\\CS-4100-Compiler_Design\\Code\\Compiler-Part01\\fa21hw1\\Reserve.txt";

    public ReserveTable(int maxSize) {
        // Constructor, initializes the internal storage to contain up to maxSize rows
        // of data. The maximum size of
        // the structure will not change, and the ADT should operate correctly with
        // fewer than maxSize entries; it
        // should not be required to add all the entries to fill the structure.
        stack = new Stack(maxSize);
    }

    public int Add(String name, int code) {
        int word_index;
        // Adds a new row to the storage with name and code as the values. Returns the
        // index of the row where the
        // data was placed; just adds to end of list, and does not check for duplicates,
        // and does not sort entries.
         new_word = new ReservedWord(name, code);

        // new_word.set_code(code);
        // new_word.set_name(name);
        word_index = stack.push(new_word);
        return word_index;
    }

    public int LookupName(String name) {
        // Returns the integer code associated with name if name is in the table, else
        // returns -1 to indicate a failed search. This must be a case insensitive search.
        int name_op_code = -1;

        for(int name_indx_iter = 0; name_indx_iter <= stack.stack_top; name_indx_iter++){
            if(stack.stack_array[name_indx_iter].reserved_word.compareToIgnoreCase(name) == 0){
                name_op_code = stack.stack_array[name_indx_iter].reserved_word_code; 
                return name_op_code;
            }
        }
        return name_op_code;
    }

    public String LookupCode(int code) {
        // Returns the associated name contained in the list if code is there, else an
        // empty string to indicate a failed
        // search for code.
        String str_name_code = "";

        for(int name_indx_iter = 0; name_indx_iter <= stack.stack_top; name_indx_iter++){
            if(stack.stack_array[name_indx_iter].reserved_word_code == code){
                str_name_code = stack.stack_array[name_indx_iter].reserved_word; 
                return str_name_code;
            }
        }
        return str_name_code;
    }

    public static void PrintReserveTable(String filename) {
        // Prints to the named plain ASCII text file the currently used contents of the
        // Reserve table in neat
        // tabular format, containing the index of the row, the name, and the code, one
        // row per output text line. Any
        // empty lines must be omitted from the list. (If the list was initialized to a
        // maxSize of 100, but only 5 rows
        // were added, only 5 rows will be printed out.)
        try {
            FileWriter rs_tbl_writer = new FileWriter(filename, false);
            rs_tbl_writer.write("||Index||Name\t   ||Code ||\n");
            rs_tbl_writer.write("----------------------------\n");
            for (int i = 0; i < stack.stack_array.length; i++) {
                if(stack.stack_array[i] == null){
                    break;
                } else { 
                    String entry = String.format("||%-5s||%-10s||%-5s||\n", i, stack.stack_array[i].reserved_word, stack.stack_array[i].reserved_word_code);
                    rs_tbl_writer.write(entry);
                }
            }
            rs_tbl_writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(filename);
    }
}
