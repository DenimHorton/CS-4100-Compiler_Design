// Robert Denim Horton CS2022

package ADT;

import ADT.Lexical.token;

/**
 * @author Abrouill & Robert Denim Horton
 * @version 4.1
 * 
 *          This File represents the Syntactic part of the compiler.
 * 
 */

public class Syntactic {

    private String filein;              //The full file path to input file
    private SymbolTable symbolList;     //Symbol table storing ident/const
    private QuadTable quads;
    private Interpreter interp;
    private Lexical lex;                //Lexical analyzer
    private Lexical.token token;        //Next Token retrieved
    private boolean traceon;            //Controls tracing mode
    private int level = 0;              //Controls indent for trace mode
    private boolean anyErrors;          //Set TRUE if an error happens
    private final int symbolSize = 250;
    private final int quadSize = 1500;
    private int Minus1Index;
    private int Plus1Index;

    public Syntactic(String filename, boolean traceOn) {
        filein = filename;
        traceon = traceOn;
        symbolList = new SymbolTable(symbolSize);
        Minus1Index = symbolList.AddSymbol("-1", symbolList.constantkind, -1);
        Plus1Index = symbolList.AddSymbol("1", symbolList.constantkind, 1);
        quads = new QuadTable(quadSize);
        interp = new Interpreter();
        lex = new Lexical(filein, symbolList, true);
        lex.setPrintToken(traceOn);
        anyErrors = false;
        }

        public void parse() {
            // make filename pattern for symbol table and quad table output later
            String filenameBase = filein.substring(0, filein.length() - 4);
            System.out.println(filenameBase);
            int recur = 0;
            // prime the pump, get first token
            token = lex.GetNextToken();
            // call PROGRAM
            recur = Program();
            // done, so add the final STOP quad
            quads.AddQuad(interp.opcodeFor("STOP"), 0, 0, 0);
            //print ST, QUAD before execute
            symbolList.PrintSymbolTable(filenameBase + "ST-before.txt");
            quads.PrintQuadTable(filenameBase + "QUADS.txt");
            //interpret
            if (!anyErrors) {
            interp.InterpretQuads(quads, symbolList, false, filenameBase + 
            "TRACE.txt");
            } else {
            System.out.println("Errors, unable to run program.");
            }
            symbolList.PrintSymbolTable(filenameBase + "ST-after.txt");
            }

        private int handlePrintln() {
            int recur = 0;
            int toprint = 0;

            if (anyErrors) {
                return -1;
            }

            trace("handlePrintln", true);
            //got here from a PRINT token, move past it...
            token = lex.GetNextToken();
            //look for ( stringconst, ident, simpleexp )
            if (token.code == lex.codeFor("LTPAR")) {
                //move on
                token = lex.GetNextToken();
                if ((token.code == lex.codeFor("SCNS"))) { //|| (token.code == lex.codeFor("IDNT"))) {
                    // save index for string literal or identifier
                    toprint = symbolList.LookupSymbol(token.lexeme);
                    //move on
                    token = lex.GetNextToken();
                } else {
                    toprint = SimpleExpression();
                }
                quads.AddQuad(interp.opcodeFor("PRTLN"), toprint, 0, 0);
                //now need right ")"
                if (token.code == lex.codeFor("RTPAR")) {
                    //move on
                    token = lex.GetNextToken();
                } else {
                    error(lex.reserveFor("RTPAR"), token.lexeme);
                }
            } else {
                error(lex.reserveFor("LTPAR"), token.lexeme);
            }
            // end lpar group
            trace("handlePrintn", false);
            return recur;
        }

    private void error(String wanted, String got) {
        // error provides a simple way to print an error statement to standard output
        // and avoid reduncancy
        anyErrors = true;
        System.out.println("ERROR: Expected " + wanted + " but found " + got);
    }

    private void trace(String proc, boolean enter) {
        // trace simply RETURNs if traceon is false; otherwise, it prints an
        // ENTERING or EXITING message using the proc string        
        String tabs = "";

        if (!traceon) {
            return;
        }

        if (enter) {
            tabs = repeatChar(" ", level);
            System.out.print(tabs);
            System.out.println("--> Entering " + proc);
            level++;
        } else {
            if (level > 0) {
                level--;
            }
            tabs = repeatChar(" ", level);
            System.out.print(tabs);
            System.out.println("<-- Exiting " + proc);
        }
    }

    private String repeatChar(String s, int x) {
        // repeatChar returns a string containing x repetitions of string s;
        // nice for making a varying indent format        
        int i;
        String result = "";
        for (i = 1; i <= x; i++) {
            result = result + s;
        }
        return result;
    }
   
    private int location(){
        return 0;
    }

    private int relopToOpcode(int relop){
        int result = 0;
        switch (lex.mnemonics.LookupCode(relop)){
            case "EQUAL":
                result = 12;
                break;
            case "BRCKT":
                result = 9;
                break;
            case "LES__":
                result = 14;
                break;
            case "GRT__":
                result = 13;
                break;
            case "LESEQ":
                result = 10;
                break;
            case "GRTEQ":
                result = 11;
                break;                  
            default:
                break;
        }
        return result;
    }
    //###################################################################################################
    //                              START   Terminals and Non-Terminal
    //###################################################################################################
    private int Variable() {
        int recur = 0;

        if (anyErrors) {
            return -1;
        }

        trace("Variable", true);

        if ((token.code == lex.codeFor("IDENT"))) {
            //return the location of this variable for Quad use
            recur = symbolList.LookupSymbol(token.lexeme);
            // bookkeeping and move on
            token = lex.GetNextToken();
        } else {
            error("Variable", token.lexeme);
        }
            trace("Variable", false);
        return recur;
    }

    private int Realexpression() {
        /*
        * Non Terminal
        *
        * <addop> --> $PLUS | $MINUS
        * 
        */        
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("Realexpression", true);
        int left, right, saveRelop, result, temp;
        left = SimpleExpression();
        saveRelop = Relop();
        right = SimpleExpression();
        // temp = SymbolTable;
        // temp = symbolList.AddSymbol(symbol, kind, value) ;
        temp = 0;
        QuadTable.AddQuad(lex.mnemonics.LookupName("MINU_"), left, right, temp);
        result = QuadTable.NextQuad();
        QuadTable.AddQuad(relopToOpcode(saveRelop), temp, 0, 0);
        trace("Realexpression", false);
        return result;
    }

    private int Relop() {
        /*
        * Non Terminal
        *
        * <addop> --> $PLUS | $MINUS
        * 
        */
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("Relop", true);
        // unique non-terminal stuff
        if (lex.mnemonics.LookupCode(token.code).equalsIgnoreCase("EQUAL")){
            token = lex.GetNextToken();
        } else if (lex.mnemonics.LookupCode(token.code).equalsIgnoreCase("LES__")){
            token = lex.GetNextToken();
        } else if (lex.mnemonics.LookupCode(token.code).equalsIgnoreCase("GRT__")){
            token = lex.GetNextToken();
        } else if (lex.mnemonics.LookupCode(token.code).equalsIgnoreCase("LES__")){
            token = lex.GetNextToken();
        } else if (lex.mnemonics.LookupCode(token.code).equalsIgnoreCase("GRTEQ")){
            token = lex.GetNextToken();
        } else if (lex.mnemonics.LookupCode(token.code).equalsIgnoreCase("LESEQ")){
            token = lex.GetNextToken();
        }
        trace("Relop", false);
        return recur;
    }

    // private int SimpleExpression() {
    //     /*
    //     * Recursive Terminal
    //     * 
    //     * <simple expression> --> [<sign>] <term> {<addop> || <term>}*
    //     *
    //     */
    //     // Reset recur to 0
    //     int recur = 0;
    //     // If anyErrors is not equal to 0 then return -1
    //     if (anyErrors) {
    //         return -1;
    //     }
    //     int left, right, signval, temp, opcode;
    //     // Set Trace to track recursion through console
    //     trace("SimpleExpression", true);
    //     signval = Sign();
    //     left = Term();
    //     if (signval == -1){
    //         QuadTable.AddQuad(lex.mnemonics.LookupName("MULT_"), left, -1, left);
    //     } 
    //     while ((token.code == lex.codeFor("PLUS_")) || (token.code == lex.codeFor("MINU_"))){
    //         if (signval == -1){
    //             opcode = lex.mnemonics.LookupName("PLUS_");
    //         } else {
    //             opcode = lex.mnemonics.LookupName("MINU_");
    //         }
    //         token = lex.GetNextToken();
    //         right = Term();
    //         temp = SymbolEntryType.entry_data_type; //(lex.mnemonics.LookupCode(token.code), , ); 
    //         QuadTable.AddQuad(opcode, left, right, temp);
    //         left = temp;
    //     }
    //     return left; 
    // }

    private int SimpleExpression() {
        /*
        * Recursive Terminal
        * 
        * <simple expression> --> [<sign>] <term> {<addop> || <term>}*
        *
        */
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("SimpleExpression", true);
        // Check if the token is a plus or minus operator
        if ((token.code == lex.codeFor("PLUS_")) || (token.code == lex.codeFor("MINU_"))) {
            // If so call into Sign to move to next token
            recur = Sign();
        }

        // Call into Term since there must be at least one term recursion
        recur = Term();

        // While the next token after recursion through Term is a plus or minus operator
        while ((token.code == lex.codeFor("PLUS_")) || (token.code == lex.codeFor("MINU_"))) {
            recur = Addop(); // Enter into Addop move to next token
            recur = Term(); // Enter into Term ton enter into more recursion and move to next token
        }
        trace("SimpleExpression", false);
        return recur;
    }

    private int Addop() {
        /*
        * Non Terminal
        *
        * <addop> --> $PLUS | $MINUS
        * 
        */
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Addop", true);
        // Check if the token is a plus or minus operator
        if (token.code != lex.codeFor("PLUS_") || token.code != lex.codeFor("MINU_")) {
            // If so, move to next token
            token = lex.GetNextToken();
        }
        trace("Addop", false);
        return recur;
    }

    private int Sign() {
        /*
        * Non Terminal
        *
        * <sign> --> $PLUS | $MINUS
        * 
        */        
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Sign", true);
        // Check if the token is a plus or minus operator
        if (token.code == lex.codeFor("MINU_")) {
            // If so, move to next token
            recur = -1;
            token = lex.GetNextToken();
        } else if (token.code == lex.codeFor("PLUS_")){
            // If so, move to next token
            token = lex.GetNextToken();        
        }
        trace("Sign", false);
        return recur;
    }

    private int Term() {
        /*
        * Not a Non-Terminal
        *
        * <term> --> <factor> {<mulop> || <factor> }*
        *
        */        
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Term", true);
        // Call Factor to see if any terminating terminals exists
        recur = Factor();
        // While the next operator is a multiply or divide operator keep rcurring and
        // moving to next token
        while ((token.code == lex.codeFor("MULT_")) || (token.code == lex.codeFor("DIVD_"))) {
            recur = Multop(); // Call Multop to see move to next token
            recur = Factor(); // Call Factor to see if any terminating terminals exists
        }
        trace("Term", false);
        return recur;
    }

    private int Multop() {
        /*
        * Non Terminal
        *
        * <mulop> --> $MULTIPLY | $DIVIDE
        *
        */        
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Mulop", true);
        // Check if the token is a multiply or divide operator
        if ((token.code == lex.codeFor("MULT_")) || (token.code == lex.codeFor("DIVD_"))) {
            // If so, move to next token
            token = lex.GetNextToken();
        }
        trace("Mulop", false);
        return recur;
    }

    private int Factor() {
        /*
        * Non Terminals
        * 
        * <factor> --> <unsigned constant> | <variable> | $LPAR <simple expression>
        * $RPAR
        *
        */        
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Factor", true);
        // Check if the token is a " ( "
        if (token.code == lex.codeFor("LTPAR")) {
            // If so, move to next token
            token = lex.GetNextToken();
            // Call simple expression for recursion
            recur = SimpleExpression();
            // Check if the token is a " ) "
            if (token.code == lex.codeFor("RTPAR")) {
                // If so, move to next token
                token = lex.GetNextToken();
            }
            // Else If, not a " ( " then check if token is a identifier
        } else if (token.code == lex.codeFor("IDENT")) {
            // If so, call Variable for recursion
            recur = Variable();
            // Else, call Unsigned for recursion
        } else {
            recur = UnsignedConstant();
        }
        trace("Factor", false);
        return recur;
    }

    private int ProgIdentifier() {
        /*
        * Non Terminal
        *
        * <prog-identifier> --> <identifier>
        *
        */
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // This non-term is used to uniquely mark the program identifier
        if (token.code == lex.codeFor("IDENT")) {
            // Because this is the progIdentifier, it will get a 'p' type to prevent re-use
            // as a var
            symbolList.UpdateSymbol(symbolList.LookupSymbol(token.lexeme), 'p', 0);
            // move on
            token = lex.GetNextToken();
        }
        return recur;
    }

    private int Program() {
        /*
        * Non Terminals
        * 
        * <program> --> $PROGRAM <prog-identifier> $SEMICOLON <block> $PERIOD
        * 
        */
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Program", true);
        if (token.code == lex.codeFor("PRGRM")) {
            token = lex.GetNextToken();
            recur = ProgIdentifier();
            if (token.code == lex.codeFor("SMICL")) {
                token = lex.GetNextToken();
                recur = Block();
                if (token.code == lex.codeFor("PER__")) {
                    if (!anyErrors) {
                        System.out.println("Success.");
                    } else {
                        System.out.println("Compilation failed.");
                    }
                } else {
                    error(lex.reserveFor("PER__"), token.lexeme);
                }
            } else {
                error(lex.reserveFor("SMICL"), token.lexeme);
            }
        } else {
            error(lex.reserveFor("PRGRM"), token.lexeme);
        }
        trace("Program", false);
        return recur;
    }

    private int Block() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Block", true);
        if (token.code == lex.codeFor("BEGIN")) {
            token = lex.GetNextToken();
            recur = Statement();
            while ((token.code == lex.codeFor("SMICL")) && (!lex.EOF()) && (!anyErrors)) {
                token = lex.GetNextToken();
                recur = Statement();
            }
            if (token.code == lex.codeFor("END__")) {
                token = lex.GetNextToken();
            } else {
                error(lex.reserveFor("END__"), token.lexeme);
            }
        } else {
            error(lex.reserveFor("BEGIN"), token.lexeme);
        }
        trace("Block", false);
        return recur;
    }

    private int Statement() {
        /*
        * Non Terminal
        *
        * <statment> --> <variable> $COLON-EQUALS <simple expression>
        *
        */        
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Statement", true);
        int left, right;
        if (token.code == lex.codeFor("IDENT")) { // must be an ASSUGNMENT
            left = Variable();
            if (token.code == lex.codeFor("ASIGN")){
                token = lex.GetNextToken();
                right = SimpleExpression();
                QuadTable.AddQuad(5, right, 0, left);
            } else {
                error("Statement start", token.lexeme);
            }   
        } else {
            // If statement
            if (token.code == lex.codeFor("IF___")) { // must be an ASSUGNMENT
                token = lex.GetNextToken();
                int branchQuad = Realexpression();
                if (token.code == lex.codeFor("THEN_")){
                    token = lex.GetNextToken();
                    Statement();
                    if (token.code == lex.codeFor("ELSE_")){
                        token = lex.GetNextToken();
                        int patchElse = QuadTable.NextQuad();
                        QuadTable.AddQuad(10, 0, 0, 0);
                        QuadTable.setQuadOp3(branchQuad, QuadTable.NextQuad());
                        Statement();
                        QuadTable.setQuadOp3(patchElse, QuadTable.NextQuad());
                    } else {
                        QuadTable.setQuadOp3(branchQuad, QuadTable.NextQuad());
                    }
                } else {
                    error("THEN", token.lexeme);
                }
            }
            // Do while statement 
            if (token.code == lex.codeFor("WHILE")){
                // Declare above int saveTop, branchQuad
                token = lex.GetNextToken();
                // Before generating code, save top of loop
                int saveTop = QuadTable.NextQuad(); 
                // Where unconditional branch will jump 
                // Tells where branchTarget to be set 
                int branchQuad = Realexpression();
                if (token.code == lex.codeFor("DO___")){
                    token = lex.GetNextToken();
                    // The loop body is processed
                    Statement(); 
                    // Jump to top of loop /backfill the forward branch
                    QuadTable.AddQuad(10, 0, 0, saveTop);
                    //Quad function for ease- set 3rd op
                    //NOTE: Do i just pass the token.code here or next token.
                    QuadTable.setQuadOp3(branchQuad, token.code);//conditional jumps nextQuad 
                } else {
                    error("DO", token.lexeme);
                }
            }         
            if (token.code == lex.codeFor("PRTLN")){
                token = lex.GetNextToken();
                Statement();
            } else {
                error("(", token.lexeme);
            }
        }
        trace("Statement", false);
        return recur;
    }            
        
    private int UnsignedConstant() {
        int recur = 0;

        if (anyErrors) {
            return -1;
        }

        trace("UnsignedConstant", true);

        // only accepting a number
        recur = UnsignedNumber();

        trace("UnsignedConstant", false);
        
        return recur;
    }
    
    private int UnsignedNumber() {
        int recur = 0;

        if (anyErrors) {
            return -1;
        }

        trace("UnsignedNumber", true);
        // float or int or ERROR
        // unsigned constant starts with integer or float number
        if ((token.code == lex.codeFor("INT__")|| (token.code == lex.codeFor("FLT__")))) {
            // return the s.t. index
            recur = symbolList.LookupSymbol(token.lexeme);
            token = lex.GetNextToken();
        } else {
            error("Integer or Floating Point Number", token.lexeme);
        }
            trace("UnsignedNumber", false);
        return recur;
    }
   
    private int Identifier(){
        //Non Terminal
        //
        //<non-terminal> --> <NON-TERMINAL>
        // 
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("Identifier", true);
        if (token.code == lex.codeFor("IDENT")) {
            // If so, move to next token
            token = lex.GetNextToken();
        }        
        trace("Identifier", false);
        return recur;
    }
   
    private int StringConstant(){
        //Non Terminal
        //
        //<non-terminal> --> <NON-TERMINAL>
        // 
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("StringConstant", true);
        // unique non-terminal stuff
        System.out.println("String!!!!");
        if (token.lexeme.equals("\"")){
            System.out.println("String!!!!");
        }
        trace("StringConstant", false);
        return recur;
    }

    private int handleAssignment() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("handleAssignment", true);
        // have ident already in order to get to here, handle as Variable
        recur = Variable(); // Variable moves ahead, next token ready
        if (token.code == lex.codeFor("ASIGN")) {
            token = lex.GetNextToken();
            recur = SimpleExpression();
        } else {
            error(lex.reserveFor("ASIGN"), token.lexeme);
        }
        trace("handleAssignment", false);
        return recur;
    }
    //###################################################################################################
    //                              END   Terminals and Non-Terminal
    //###################################################################################################
}

    //Template for all the non-terminal method bodies

    /*
    private int exampleNonTerminal(){
    //Non Terminal
    //
    //<non-terminal> --> <NON-TERMINAL>
    // 
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("NameOfThisMethod", true);
        // unique non-terminal stuff
        recur = exampleTerminal();
        trace("NameOfThisMethod", false);
        return recur;
    }
    /*

    //Template for all the terminal method bodies
    /* Terminal
     *
     * <Non-Terminal> --> $TERMINAL 
     */
    /*
    private int exampleTerminal(){
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("NameOfThisMethod", true);
        token = lex.GetNextToken();
        trace("NameOfThisMethod", false);
        return recur;
    }*/