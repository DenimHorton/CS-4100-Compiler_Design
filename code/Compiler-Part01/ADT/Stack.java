package ADT;
/**
 * @author Robert Denim Horton
 * @version 0.1
 * This is PART ONE of the orverall compiler project.  
 * 
 * This File represents the data structure that will represent where the table
 * values will be stored which part one is reserved words with op code values.
 */

public class Stack {
    /*
     * Intialize;
     * 'stack_array' for storing values as well,
     * 'stack_top' to the top of the stack,
     * 'capacity' is the allocated space for the stack array.
     */
    public ReservedWord[] stack_array;
    public int stack_top;
    public int capacity;

    public Stack(int size) {
        /*
         * Stack constructor;
         * Sets the size of the 'stack_array' with the given
         * argument 'size' to intialize the capacity of the
         */
        stack_array = new ReservedWord[size];
        this.capacity = size;
        stack_top = -1;
    }

    public int push(ReservedWord rsrvd_wrd) {
        /**
         * This method will add and instance of a reserved word onto 
         * the top of the objectArray.
         * @param rsrvd_wrd This parameter is an object that is passed
         * into this method to be pushed ontop of an objectArray that 
         * is implemented as a stack.
         * 
         */
        stack_top++;
        stack_array[stack_top] = rsrvd_wrd;
        stack_array[stack_top].set_name(rsrvd_wrd.reserved_word);
        stack_array[stack_top].set_code(rsrvd_wrd.reserved_word_code);
        return stack_top;
    }
}
