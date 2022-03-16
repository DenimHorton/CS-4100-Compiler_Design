package FA21HW2;
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
public class PlayGround {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create the table
        ReserveTable reserve = new ReserveTable(25);
        SymbolTable symbols = new SymbolTable(25);
        QuadTable quads = new QuadTable(50);
        int index, qvalue;
        System.out.println("Testing the Symbol Table\n");
        

        // // Add to the table
        // reserve.Add("cat", 15);
        // reserve.Add("APPLE", 11);
        // reserve.Add("Dog", 5);
        // reserve.Add("DOnE", 21);
        // reserve.Add("Over", 8);
        // // Search the table
        // System.out.println("The Code for 'over' is " + reserve.LookupName("over"));
        // System.out.println("The Code for 'DOG' is " + reserve.LookupName("DOG"));
        // System.out.println("The Code for 'Cat' is " + reserve.LookupName("Cat"));
        // System.out.println("The Code for 'gone' is " + reserve.LookupName("gone"));
        // System.out.println();
        // System.out.println("The Name for 11 is " + reserve.LookupCode(11));
        // System.out.println("The Name for 5 is " + reserve.LookupCode(5));
        // System.out.println("The Name for 8 is " + reserve.LookupCode(8));
        // System.out.println("The Name for 28 is " + reserve.LookupCode(28));

        // // Print table to file
        // System.out.println("Saving Printed Table to"+reserve.path);
        // ReserveTable.PrintReserveTable(reserve.path);

        // symbols.AddSymbol("TestInt", 'V', 27);
        // symbols.AddSymbol("TestDouble", 'V', 42.25);
        // symbols.AddSymbol("TestString", 'V', "These are the times...");
        // symbols.AddSymbol("135",'C', 135);
        // symbols.AddSymbol("3.1415",'C', 3.1415);
        // symbols.AddSymbol("Please Enter A Value",'C', "Please Enter A Value");
        symbols.AddSymbol("1",'C', 1);
        symbols.AddSymbol("2",'C', 2);
        symbols.AddSymbol("3",'C', 3);
        symbols.AddSymbol("4",'C', 1);
        symbols.AddSymbol("5",'C', 2);
        symbols.AddSymbol("6",'C', 3);
        symbols.AddSymbol("7",'C', 1);
        symbols.AddSymbol("8",'C', 2);
        symbols.AddSymbol("9",'C', 3);
        symbols.AddSymbol("10",'C', 1);
        symbols.AddSymbol("11",'C', 2);
        symbols.AddSymbol("12",'C', 3);
        symbols.AddSymbol("13",'C', 2);
        symbols.AddSymbol("14",'C', 3);
        symbols.AddSymbol("15",'C', 1);
        symbols.AddSymbol("16",'C', 2);
        symbols.AddSymbol("17",'C', 3);
        symbols.AddSymbol("18",'C', 1);
        symbols.AddSymbol("19",'C', 2);
        symbols.AddSymbol("20",'C', 3);
        symbols.AddSymbol("21",'C', 3);
        symbols.AddSymbol("22",'C', 1);
        symbols.AddSymbol("23",'C', 2);
        symbols.AddSymbol("24",'C', 3);        
        symbols.AddSymbol("25",'C', 1);
        symbols.AddSymbol("26",'C', 1);



        for (int entry_indx=0; entry_indx < symbols.smbol_table_index+1; entry_indx++){
            System.out.println(symbols.smbol_table[entry_indx].getClass().getSimpleName()+":");
            System.out.println("\t"+symbols.smbol_table[entry_indx].entry_kind);
            System.out.println("\t"+symbols.smbol_table[entry_indx].entry_name);
            System.out.println("\t"+symbols.smbol_table[entry_indx].entry_type);
        }

        // index = symbols.LookupSymbol("testint");
        // System.out.println("testint is located at "+index);
        // index = symbols.LookupSymbol("3.1415");
        // System.out.println("PI is located at "+index);
        // System.out.println("  the KIND for PI is "+symbols.GetKind(index));
        // System.out.println("The KIND for slot 5 is "+symbols.GetKind(5)+", data type is "
        // +symbols.GetDataType(5)+" and the value: "+symbols.GetString(5));
        // index = symbols.LookupSymbol("BadVal");
        // System.out.println("BadVal search returned "+index+'\n');
        // // symbols.PrintSymbolTable("symtable.txt");
        // System.out.println("Testing the Quad Table\n");

       
        // System.out.println("At the start, NextQuad is: "+ quads.NextQuad());
        // quads.AddQuad(4,3,2,1);
        // System.out.println("After one add, NextQuad is: "+ quads.NextQuad());
        // quads.AddQuad(1,2,3,4);
        // quads.AddQuad(2,2,2,2);
        // quads.AddQuad(0,0,0,0);
        // quads.AddQuad(1,3,5,9);
        
        // qvalue = quads.GetQuad(3, 2);
        // System.out.println("Quad value at (4,3) is: "+ quads.GetQuad(4,3));
        
        // quads.UpdateQuad(4,11,13,15,17);
        // System.out.println("Quad value at (4,3) is: "+ quads.GetQuad(4,3));
        
        // // quads.PrintQuadTable("quadtable.txt");
        // System.out.println("Finally NextQuad is: "+ quads.NextQuad());

    }

}