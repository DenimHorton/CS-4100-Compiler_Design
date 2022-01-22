package ADT;

public class Stack {  
    /* Intialize;
        'stack_array' for storing values as well,
        'stack_top' to the top of the stack, 
        'capacity' is the allocated space for the stack array.
    */
    public int stack_array[];
    public int stack_top;
    public int capacity;

    public Stack(int size){
        /* Stack constructor;
            Sets the size of the 'stack_array' with the given 
            argument 'size' to intialize the capacity of the  
        */
        stack_array = new int[size];
        capacity = size;
        stack_top = -1;   
    }

    public void push(string ReservedWord){
        

    }
}
