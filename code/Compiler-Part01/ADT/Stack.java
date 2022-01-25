package ADT;

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
        stack_top++;
        stack_array[stack_top] = new ReservedWord();
        stack_array[stack_top].set_name(rsrvd_wrd.reserved_word);
        stack_array[stack_top].set_code(rsrvd_wrd.reserved_word_code);
        return stack_top;
    }

    public ReservedWord pop() {
        return stack_array[stack_top];
    }
}
