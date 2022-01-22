// package fa21hw1;
import ADT.*;

  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abrouill
 */
public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Stack stack = new Stack(25);
        System.out.println("Capcity:\t"+stack.capacity);
        System.out.println("Stack top:\t"+stack.stack_top);
        System.out.println("Stack array:\t"+stack.stack_array);

        // Create the table
        ReserveTable reserve = new ReserveTable(25);
        // Add to the table
        reserve.Add("cat", 15);
        reserve.Add("APPLE", 11);
        reserve.Add("Dog", 5);
        reserve.Add("DOnE", 21);
        reserve.Add("Over", 8);
        // Search the table
        System.out.println("The Code for 'over' is "+reserve.LookupName("over"));
        System.out.println("The Code for 'DOG' is "+reserve.LookupName("DOG"));
        System.out.println("The Code for 'Cat' is "+reserve.LookupName("Cat"));
        System.out.println("The Code for 'gone' is "+reserve.LookupName("gone"));
        System.out.println();
        System.out.println("The Name for 11 is "+reserve.LookupCode(11));
        System.out.println("The Name for 5 is "+reserve.LookupCode(5));
        System.out.println("The Name for 8 is "+reserve.LookupCode(8));
        System.out.println("The Name for 28 is "+reserve.LookupCode(28));
        
        for(int i = 0; i < reserve.internal_storage.length; i++){
            System.out.print(i+1 + ":\t[" );        
            for(int j = 0; j < 3; j++){
                System.out.print(reserve.internal_storage[i][j]);
                System.out.print("], [");
            }
            System.out.println();
        }
        
        // Print table to file
        System.out.println("Saving Printed Table to d:\\res.txt");
        // reserve.PrintReserveTable("d:\\res.txt");
        
    }
    
}  