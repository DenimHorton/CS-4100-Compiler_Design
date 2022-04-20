// Robert Denim Horton CS2022
package ADT;

/**
 * @author Robert Denim Horton
 * @version 4.0
 * 
 *          This File represents Reserve Word Entries for the Reserve table in
 *          the compiler.
 */

public class ReservedWordEntry {
    /**
     * This class represents each individual entry that is put into an abstract
     * data structure. For the first part we are using this table in the context
     * of an table that holds certain words called 'name(s)' that represents a
     * certain int value or called 'OP code' that is unique to the word.
     * 
     * @param reserved_word      This parameter represents the words string value
     *                           or 'name' represented as a String.
     * @param reserved_word_code This parameter represents the 'op code' value
     *                           for the word being reserved represented as an
     *                           integer.
     */

    public String reserved_word;
    public int reserved_word_code;

    ReservedWordEntry(String word_to_reserve, int word_to_reserve_code) {
        /**
         * Constructor for the instance of the obejct that sets values
         * at instanciation.
         */
        this.reserved_word = word_to_reserve;
        this.reserved_word_code = word_to_reserve_code;
    }

    public void set_code(int word_to_reserve_code) {
        /**
         * Enables the instance of the caode to set its OP value to a differnt
         * value.
         * 
         * @param word_to_reserve_code This parameter represents the op code
         *                             integer value that is specific to the word's
         *                             String representation
         *                             or 'name'.
         */
        this.reserved_word_code = word_to_reserve_code;
    }

    public void set_name(String word_to_reserve) {
        /**
         * Enables the instance of the caode to set its string value to a differnt
         * string value.
         * 
         * @param word_to_reserve This parameter represents the string that will be
         *                        set as the 'names's new String representation.
         */
        this.reserved_word = word_to_reserve;
    }
}