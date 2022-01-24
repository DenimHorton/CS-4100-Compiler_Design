import ADT.Stack;
import ADT.*;
import java.util.*;

public class ReserveTable {

    public Stack stack;
    public ReservedWord new_word;

    ReserveTable(int maxSize) {
        // Constructor, initializes the internal storage to contain up to maxSize rows
        // of data. The maximum size of
        // the structure will not change, and the ADT should operate correctly with
        // fewer than maxSize entries; it
        // should not be required to add all the entries to fill the structure.
        System.out.println("\n-----------------------------------------\n"
                + "Calling ReserveTable constructor . . .\n-----------------------------------------");

        stack = new Stack(maxSize);
    }

    public int Add(String name, int code) {
        int word_index = 0;
        // Adds a new row to the storage with name and code as the values. Returns the
        // index of the row where the
        // data was placed; just adds to end of list, and does not check for duplicates,
        // and does not sort entries.
        System.out.println("\n-----------------------------------------\nCalling ReserveTable Add . . ."
                + "\n-----------------------------------------");
        new_word = new ReservedWord();
        System.out.println(new_word.reserved_word);
        System.out.println(new_word.reserved_word_code);

        new_word.set_code(code);
        new_word.set_name(name);
        System.out.println(new_word.reserved_word);
        System.out.println(new_word.reserved_word_code);
        word_index = this.stack.push(new_word);
        return word_index;
    }

    public int LookupName(String name) {
        // Returns the integer code associated with name if name is in the table, else
        // returns -1 to indicate a failed
        // search. This must be a case insensitive search.
        System.out.println("\n-----------------------------------------\n"
                + "Calling ReserveTable LookupName . . .\n-----------------------------------------");

        int int_name_code = 0;
        return int_name_code;
    }

    public String LookupCode(int code) {
        // Returns the associated name contained in the list if code is there, else an
        // empty string to indicate a failed
        // search for code.
        System.out.println("\n-----------------------------------------\n"
                + "Calling ReserveTable LookupCode . . .\n-----------------------------------------\n");

        String str_name_code = " ";
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
        System.out.println("\n-----------------------------------------\n"
                + "Calling ReserveTable PrintReserveTable . . .\n-----------------------------------------\n");

        System.out.println(filename);
    }

    public void reservedWordExsists(String wrd) {
        for (int tbl_wrd_indx = 0; tbl_wrd_indx < stack.stack_array.length; tbl_wrd_indx++) {
            System.out.println(stack.stack_array[tbl_wrd_indx]);
            if (stack.stack_array[tbl_wrd_indx].CompareToIgnoreCase(wrd) == 0) {
                System.out.print("Found word . . .");
                return True;
            }
        }
        return False;
    }
}
