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
    public SymbolEntryType[] smbol_table;
    public SymbolEntryType smbol_val_type_entry;
    public int smbol_table_index = -1;
    public String smbol_path = "C:\\Users\\denim\\Documents\\School\\CS-4100-Compiler_Design\\Code\\Compiler-Part02\\FA21HW1\\";


    public SymbolTable(int maxSize){
        /**
         * Initializes the 'SymbolTable' obj array to hold maxSize rows of 
         * data which will be one of three different sub-classes of the 
         * parent class 'SymbolEntryType' storing the symbol name, smybol
         * kind, symbol value (int, double, or string).
         * @param maxSize
         */
        smbol_table = new SymbolEntryType[maxSize];
        
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

        smbol_val_type_entry = new SymbolEntryType<Integer>(symbol, kind, value);
        smbol_table_index ++;           
        smbol_table[smbol_table_index] = smbol_val_type_entry;        
        return smbol_table_index;
    }
    
    public int AddSymbol(String symbol, char kind, double value){
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

        smbol_val_type_entry = new SymbolEntryType<Double>(symbol, kind, value);
        smbol_table_index ++;           
        smbol_table[smbol_table_index] = smbol_val_type_entry;        
        return smbol_table_index;
    }

    public int AddSymbol(String symbol, char kind, String value){
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

        smbol_val_type_entry = new SymbolEntryType<String>(symbol, kind, value);
        smbol_table_index ++;           
        smbol_table[smbol_table_index] = smbol_val_type_entry;        
        return smbol_table_index;
    }

    public int LookupSymbol(String symbol){
        /**
         * Returns the index where symbol is found, or -1 if not in the table.
         * Uses a non-case-sensitive comparison.
         */
        for(int symbl_indx = 0; symbl_indx < smbol_table_index; symbl_indx++){
            if (smbol_table[symbl_indx].entry_name.equalsIgnoreCase(symbol)){
                System.out.println("Found:"+smbol_table[symbl_indx].entry_name);
                return symbl_indx;
            } 
        }
        return -1;
    }

    public String GetSymbol(int index){
        /**
         * Return the various values currently stored at index.
         */
        return smbol_table[index].entry_name;
    }

    public char GetKind(int index){
        /**
         * Return the various values currently stored at index.
         */
        return smbol_table[index].entry_kind;
    }

    public char GetDataType(int index){
        /**
         * Return the various values currently stored at index.
         */
        if (smbol_table[index].entry_value instanceof String){
            return 'S';
        } else if (smbol_table[index].entry_value instanceof Double){
            return 'F';
        } 
        return 'I';
    }

    public String GetString(int index){
        /**
         * Return the various values currently stored at index.
         */
        return smbol_table[index].entry_value.toString();
    }

    public int GetInteger(int index){
        /**
         * Return the various values currently stored at index.
         */
        return (int) (smbol_table[index].entry_value);
    }
    
    public double GetFloat(int index){
        /**
         * Return the various values currently stored at index.
         */
        return (double) (smbol_table[index].entry_value);
    }

    //Overloaded methods . . . 
    public void UpdateSymbol(int index, char kind, int value){
        /**
         *  these set the kind and value fields at the slot indicated by index.
         */
        smbol_table[index].entry_kind = kind;
        smbol_table[index].entry_value = value;
    }
     
    public void UpdateSymbol(int index, char kind, double value){
        /**
         *  these set the kind and value fields at the slot indicated by index.
         */
        smbol_table[index].entry_kind = kind;
        smbol_table[index].entry_value = value;        
    }
    public void UpdateSymbol(int index, char kind, String value){
        /**
         *  these set the kind and value fields at the slot indicated by index.
         */
        smbol_table[index].entry_kind = kind;
        smbol_table[index].entry_value = value;        
    }
    
    public void PrintSymbolTable(String filename){
        /**
         * Prints to a plain text file all the data in only the occupied rows of 
         * the symbol table.  Must be in neat tabular format, 1 text line per row,
         * selectively showing only the used value field (stringValue, integerValue,
         * or floatValue) which is active for that row based on the dataType for 
         * that row.
         */
        try {
            // Build a tabulated representation of the reserve table and write it to 
            // a file.
            FileWriter rs_tbl_writer = new FileWriter(smbol_path += filename, false);
            rs_tbl_writer.write("||Index||Entry Kind||Entry Name||Entry Value||\n");
            rs_tbl_writer.write("----------------------------\n");
            for (int i = 0; i <= smbol_table_index; i++) {
                if(smbol_table[i] == null){
                    break;
                } else { 
                    String entry = String.format("||%-5s||%-5s||%-5s|| %-5s||\n", i, smbol_table[i].entry_kind, smbol_table[i].entry_name, smbol_table[i].entry_value);
                    rs_tbl_writer.write(entry);
                }
            }
            rs_tbl_writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}