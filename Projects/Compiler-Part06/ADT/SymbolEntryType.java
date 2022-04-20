// Robert Denim Horton CS2022
package ADT;

/**
 * @author Robert Denim Horton
 * @version 4.0
 * 
 *          This File represents the data array that will represent the specific
 *          symbols and their corresponding type and value. Will be represented
 *          as a inherited object 'SymbolEntryType' that has three specific
 *          values
 *          for entry.
 */

public class SymbolEntryType<T> {
    /**
     * This class represents the the class of entries that will be put into a
     * symbol table that will store its three specific data types string,
     * character and a generic data type (of either string, integer, or double).
     */
    public String entry_name;
    public char entry_kind;
    public char entry_data_type;
    public T entry_type;

    public SymbolEntryType(String string_entry, char char_entry, T type_entry) {
        /**
         * Constructor for generic entries that is generated to be put into the
         * 'SymbolEntryTabple'.
         * 
         * @param string_entry This parameter holds the string value stored at
         *                     stored into the symbol table.
         * @param char_entry   This parameter holds the char value stored at
         *                     the same corresponding index in the symbol table that
         *                     represents
         *                     whether the entry is a 'label', 'variable', or
         *                     'constant'.
         * @param type_entry   This parameter holds the char value stored at
         *                     the same corresponding index in the 'quad table' that
         *                     represents
         *                     whether the entry is a 'label', 'variable', and
         *                     'constant'.
         */
        this.entry_name = string_entry;
        this.entry_kind = char_entry;
        this.entry_type = type_entry;
        // Setting data type for value.
        if (type_entry instanceof String) {
            this.entry_data_type = 's';
        } else if (type_entry instanceof Double) {
            this.entry_data_type = 'f';
        } else {
            this.entry_data_type = 'i';
        }
    }
}