// Robert Denim Horton CS2022

package ADT;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Robert Denim Horton
 * @version 4.1
 * 
 *          This File represents the Symbol Table of the compiler
 */

public class SymbolTable {

    // Public static Stack stack;
    public SymbolEntryType[] smbol_table;
    public SymbolEntryType smbol_val_type_entry;
    public int smbol_table_index = -1;
    
    public SymbolTable(int maxSize) {
        /**
         * Initializes the 'SymbolTable' obj array to hold maxSize rows of
         * data which will be one of three different sub-classes of the
         * parent class 'SymbolEntryType' storing the symbol name, symbol
         * kind, symbol value (int, double, or string).
         * 
         * @param maxSize The specified size for the symbol table.
         */
        smbol_table = new SymbolEntryType[maxSize];
    }

    // Three overloaded methods to . . . .
    public int AddSymbol(String symbol, char kind, int value) {
        /**
         * Assign entry type object 'SymbolEntryType' symbol with given char
         * kind and string value to the end of the symbol table. We also use
         * a generic type to be able to initialize the object with what ever
         * data type the value ends up being.
         */
        smbol_val_type_entry = new SymbolEntryType<Integer>(symbol, kind, value);
        /**
         * If the symbol is already in the table according to a non-case
         * -sensitive comparison ['Total' matches total as well as 'ToTaL']
         * with all the existing strings in the table, no change or
         * verification is made, and this method returns the row index where
         * the symbol was found. This method only FAILS, and return -1, when
         * the table already contains maxSize rows, and adding a new row would
         * exceed this size limit. This should not happen.
         * 
         * @param symbol The string representation or 'name'.
         * @param kind   The kind of data.
         * @param value  The data type.
         */

        // If we look the symbol in the table and get a -1 then we know the symbol
        // does not yet exist in the table and as long at the next index is under
        // the length of the table.
        if (LookupSymbol(symbol) == -1 && smbol_table_index < smbol_table.length - 1) {
            // Iterate to next open space.
            smbol_table_index++;
            // Add symbol entry to smbol table.
            smbol_table[smbol_table_index] = smbol_val_type_entry;
            // Returns the index where the symbol was located.
            return smbol_table_index;
            // If the symbol does exsist in the symbol table already we return the index
            // of that already exsisting symbol entry.
        } else if (LookupSymbol(symbol) != -1 && smbol_table_index < smbol_table.length - 1) {
            return LookupSymbol(symbol);
            // The only possible option know it that the next entry would be out of
            // bounds for the length of the symbol table.
        } else {
            return -1;
        }
    }

    public int AddSymbol(String symbol, char kind, double value) {
        /**
         * Overloaded method just with different data types for value that handels
         * logic the same way
         */

        smbol_val_type_entry = new SymbolEntryType<Double>(symbol, kind, value);
        if (LookupSymbol(symbol) == -1 && smbol_table_index < smbol_table.length - 1) {
            // Iterate to next open space.
            smbol_table_index++;
            // Add symbol entry to smbol table.
            smbol_table[smbol_table_index] = smbol_val_type_entry;
            // Returns the index where the symbol was located.
            return smbol_table_index;
            // If the symbol does exsist in the symbol table already we return the index
            // of that already exsisting symbol entry.
        } else if (LookupSymbol(symbol) != -1 && smbol_table_index < smbol_table.length - 1) {
            return LookupSymbol(symbol);
            // The only possible option know it that the next entry would be out of
            // bounds for the length of the symbol table.
        } else {
            return -1;
        }
    }

    public int AddSymbol(String symbol, char kind, String value) {
        /**
         * Overloaded method just with different data types for value that handels
         * logic the same way
         */

        smbol_val_type_entry = new SymbolEntryType<String>(symbol, kind, value);
        if (LookupSymbol(symbol) == -1 && smbol_table_index < smbol_table.length - 1) {
            // Iterate to next open space.
            smbol_table_index++;
            // Add symbol entry to smbol table.
            smbol_table[smbol_table_index] = smbol_val_type_entry;
            // Returns the index where the symbol was located.
            return smbol_table_index;
            // If the symbol does exsist in the symbol table already we return the index
            // of that already exsisting symbol entry.
        } else if (LookupSymbol(symbol) != -1 && smbol_table_index < smbol_table.length - 1) {
            return LookupSymbol(symbol);
            // The only possible option know it that the next entry would be out of
            // bounds for the length of the symbol table.
        } else {
            return -1;
        }
    }

    public int LookupSymbol(String symbol) {
        /**
         * Returns the index where symbol is found, or -1 if not in the table.
         * Uses a non-case-sensitive comparison.
         * 
         * @param symbol Entry name to look up in the symbol table.
         */
        // We iterate through the exsisting entry values in the table based on our
        // top ointer for symbol table.
        for (int symbl_indx = 0; symbl_indx <= smbol_table_index; symbl_indx++) {
            // Check if each netry matches untill all of been checked and returns
            // -1 of the index of where that symbol entry is.
            if (smbol_table[symbl_indx].entry_name.equalsIgnoreCase(symbol)) {
                return symbl_indx;
            }
        }
        return -1;
    }

    public String GetSymbol(int index) {
        /**
         * Return the string value currently stored for the obj at given index.
         * 
         * @param index Where we would like to extract our symbol table entry
         *              string name value.
         */
        return smbol_table[index].entry_name;
    }

    public char GetKind(int index) {
        /**
         * Return the char value currently stored for the obj at given index.
         * 
         * @param index Where we would like to extract our symbol table entry
         *              char kind value.
         */
        return smbol_table[index].entry_kind;
    }

    public char GetDataType(int index) {
        /**
         * Return the char value currently stored for the obj at given index.
         * 
         * @param index Where we would like to extract our symbol table entry
         *              char data type value.
         */
        return smbol_table[index].entry_data_type;
    }

    public String GetString(int index) {
        /**
         * Return the various values currently stored at index.
         * 
         * @param index Where we would like to extract our symbol table entry
         *              String data type value.
         */
        return smbol_table[index].entry_type.toString();
    }

    public int GetInteger(int index) {
        /**
         * Return the various values currently stored at index.
         * 
         * @param index Where we would like to extract our symbol table entry
         *              int data type value.
         */
        return (int) (smbol_table[index].entry_type);
    }

    public double GetFloat(int index) {
        /**
         * Return the various values currently stored at index.
         * 
         * @param index Where we would like to extract our symbol table entry
         *              double data type value.
         */
        return (double) (smbol_table[index].entry_type);
    }

    public void UpdateSymbol(int index, char kind, int value) {
        /**
         * These set the kind and value fields at the slot indicated by index.
         */
        smbol_table[index].entry_kind = kind;
        smbol_table[index].entry_type = value;
    }

    public void UpdateSymbol(int index, char kind, double value) {
        /**
         * Overloaded methoded with different value data type.
         */
        smbol_table[index].entry_kind = kind;
        smbol_table[index].entry_type = value;
    }

    public void UpdateSymbol(int index, char kind, String value) {
        /**
         * Overloaded methoded with different value data type.
         */
        smbol_table[index].entry_kind = kind;
        smbol_table[index].entry_type = value;
    }

    public void PrintSymbolTable(String filename) {
        /**
         * Prints to a plain text file all the data in only the occupied rows of
         * the symbol table. Must be in neat tabular format, 1 text line per row,
         * selectively showing only the used value field (stringValue, integerValue,
         * or floatValue) which is active for that row based on the dataType for
         * that row.
         */
        try {
            // Build a tabulated representation of the reserve table and write it to
            // a file.
            FileWriter rs_tbl_writer = new FileWriter(filename, false);
            for (int i = 0; i <= smbol_table_index; i++) {
                if (smbol_table[i] == null) {
                    break;
                } else {
                    String entry = String.format("|%s|%s|%s|%s|\n", smbol_table[i].entry_name,
                            smbol_table[i].entry_kind, smbol_table[i].entry_data_type, smbol_table[i].entry_type);
                    rs_tbl_writer.write(entry);
                }
            }
            rs_tbl_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char constantkind(){
        
        return 'v';
    }


}