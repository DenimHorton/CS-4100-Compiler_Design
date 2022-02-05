package ADT;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Robert Denim Horton
 * Class CS 4100.001 'Compiler Design 1'
 * @version 2.0
 * 
 */

public class SymbolTable {

    // Public static Stack stack;
    public static SymbolEntry[] smbol_table;
    public SymbolEntry smbol_entry;
    private static int smbol_table_index = -1;

    public SymbolTable(int maxSize){
        /**
         * Initializes the SymbolTable to hold maxSize rows of data.
         * @param maxSize
         */
        smbol_table = new SymbolEntry[maxSize];
    }

    // Three overloaded methods to . . . .
    public int AddSymbol(String symbol, char kind, int value){
        /**
         * add symbol with given kind and value to the end of the symbol 
         * table, automatically setting the correct data_type, and returns 
         * the index where the symbol was located.  If the symbol is 
         * already in the table according to a non-case-sensitive comparison
         * [“Total” matches “total” as well as “ToTaL”]with all the existing
         * strings in the table, no change or verification is made, and this
         * just returns the row index where the symbol was found. These 
         * methods only FAIL, and return -1, when the table already contains 
         * maxSize rows, and adding a new row would exceed this size limit.
         * This should not happen.
         */
        return 0;
    }
    
    public int AddSymbol(String symbol, char kind, double value){
        return 0;
    }

    public int AddSymbol(String symbol, char kind, String value){
        return 0;
    }

    public int LookupSymbol(String symbol){
        /**
         * Returns the index where symbol is found, or -1 if not in the table. Uses a non-
         *case-sensitive comparison.
         */
        return 0;
    }

    public String GetSymbol(int index){
        /**
         * Return the various values currently stored at index.
         */
        return " ";
    }

    public char GetKind(int index){
        /**
         * Return the various values currently stored at index.
         */
        return 'a';
    }

    public char GetDataType(int index){
        /**
         * Return the various values currently stored at index.
         */
        return 'a';
    }

    public String GetString(int index){
        /**
         * Return the various values currently stored at index.
         */
        return " ";
    }

    public int GetInteger(int index){
        /**
         * Return the various values currently stored at index.
         */
        return 0;
    }
    
    public double GetFloat(int index){
        /**
         * Return the various values currently stored at index.
         */
        return 0.0;
    }

    //Overloaded methods . . . 
    public void UpdateSymbol(int index, char kind, int value){
        /**
         *  these set the kind and value fields at the slot indicated by index.
         */
    }
     
    public void UpdateSymbol(int index, char kind, double value){
        /**
         *  these set the kind and value fields at the slot indicated by index.
         */
    }
    public void UpdateSymbol(int index, char kind, String value){
        /**
         *  these set the kind and value fields at the slot indicated by index.
         */
    }
    
    public void PrintSymbolTable(String filename){
        /**
         * Prints to a plain text file all the data in only the occupied rows of 
         * the symbol table.  Must be in neat tabular format, 1 text line per row,
         * selectively showing only the used value field (stringValue, integerValue,
         * or floatValue) which is active for that row based on the dataType for 
         * that row.
         */
    }

}