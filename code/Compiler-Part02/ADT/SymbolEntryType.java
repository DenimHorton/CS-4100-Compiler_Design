package ADT;
/**
 * @author Robert Denim Horton
 * Class CS 4100.001 'Compiler Design 1'
 * @version 2.0
 * 
 * This File represents the data array that will represent the specific symbols
 * and their coresponding type and value.  Will be represented as a inherrited
 * object 'SymbolEntryType' that has three specific values for entry. 
 */

public class SymbolEntryType <T> {
    /** This class represents the parent class of entries that will be put into a 
     *  symbol table that will store its three specific values. 
     */
    public String entry_name;
    public char entry_kind;
    public T entry_value;
    public SymbolEntryType(String string_entry, char char_entry, T type_entry){
        /**
         * Constructor for generic entries that is generated to be put into the 
         * 'SymbolEntryTabple'.
         * @param string_entry This parameter holds the string value stored at 
         * the same corresponding index in the 'quad table'. 
         * @param char_entry This parameter holds the char value stored at 
         * the same corresponding index in the 'quad table' that represnts 
         * whether the entry is a 'label', 'variable', and 'constant'.
         * @param type_entry This parameter holds the char value stored at 
         * the same corresponding index in the 'quad table' that represnts 
         * whether the entry is a 'label', 'variable', and 'constant'.
         */
        this.entry_name =  string_entry;        
        this.entry_kind = char_entry; 
        this.entry_value = type_entry; 
    }
}