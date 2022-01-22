package ADT;

public class Stack {  
    /* Intialize;
        'stack_array' for storing values as well,
        'stack_top' to the top of the stack, 
        'capacity' is the allocated space for the stack array.
    */
    public ReservedWord [] stack_array;
    public int stack_top;
    public int capacity;

    public Stack(int size){
        /* Stack constructor;
            Sets the size of the 'stack_array' with the given 
            argument 'size' to intialize the capacity of the  
        */
        this.stack_array = new ReservedWord[size];
        this.capacity = size;
        this.stack_top = -1;   
    }

    public int push(ReservedWord rsrvd_wrd){
        System.out.println("\tCalling Stack method push . . .\n-----------------------------------------\n");
        System.out.print("\tPushing "+rsrvd_wrd.reserved_word+" = "+rsrvd_wrd.reserved_word_code+" . . .");
        this.stack_top++;
        this.stack_array[stack_top] = rsrvd_wrd;
        return stack_top;
    }

    public ReservedWord pull(){
        System.out.println("\tCalling Stack method pull . . .\n-----------------------------------------\n");
        return stack_array[stack_top];
    }
}
