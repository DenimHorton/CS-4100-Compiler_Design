// Robert Denim Horton CS2022

package ADT;

import ADT.Lexical.token;

/**
 * @author Abrouill & Robert Denim Horton
 * @version 4.0
 * 
 *          This File represents the Syntactic part of the compiler.
 * 
 */

public class Syntactic {

    private String filein; // The full file path to input file
    private SymbolTable symbolList; // Symbol table storing ident/const
    private Lexical lex; // Lexical analyzer
    private Lexical.token token; // Next Token retrieved
    private boolean traceon; // Controls tracing mode
    private int level = 0; // Controls indent for trace mode
    private boolean anyErrors; // Set TRUE if an error happens

    private final int symbolSize = 250;

    public Syntactic(String filename, boolean traceOn) {
        filein = filename;
        traceon = traceOn;
        symbolList = new SymbolTable(symbolSize);
        lex = new Lexical(filein, symbolList, true);
        lex.setPrintToken(traceOn);
        anyErrors = false;
    }

    // The interface to the syntax analyzer, initiates parsing
    // Uses variable RECUR to get return values throughout the non-terminal methods
    public void parse() {
        // Reset recur to 0
        int recur = 0;
        // prime the pump
        token = lex.GetNextToken();
        // call PROGRAM
        recur = Program();
    }

    /*
     * Non Terminal
     *
     * <prog-identifier> --> <identifier>
     *
     */
    private int ProgIdentifier() {
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

    /*
     * Non Terminals
     * 
     * <program> --> $PROGRAM <prog-identifier> $SEMICOLON <block> $PERIOD
     * 
     */
    private int Program() {
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

    /*
     * Non Terminal
     * 
     * <block> --> BEGIN <statement> {$SEMICOLON <statement>} * $END
     * 
     */
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

    /*
     * Non Terminal
     *
     * <statment> --> <variable> $COLON-EQUALS <simple expression>
     *
     */
    private int Statement() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Statement", true);
        if (token.code == lex.codeFor("IDENT")) { // must be an ASSUGNMENT
            recur = handleAssignment();
        } else {
            if (token.code == lex.codeFor("IF___")) { // must be an ASSUGNMENT
                // this would handle the rest of the IF statment IN PART B
            } else // if/elses should look for the other possible statement starts...
            // but not until PART B
            {
                error("Statement start", token.lexeme);
            }
        }
        trace("Statement", false);
        return recur;
    }

    /*
     * Not a Non-Terminal
     *
     * <handle Assignment> -- > <variable> $COLON-EQUALS <simple expression>
     *
     */
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

    /*
     * Non Terminal
     *
     * <addop> --> $PLUS | $MINUS
     * 
     */
    private int Addop() {
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

    /*
     * Recursive Terminal
     *
     * <variable> --> <identifier>
     *
     */
    private int Variable() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Variable", true);
        // Check if the token is an identifier
        if (token.code == lex.codeFor("IDENT")) {
            // If so, move to next token
            token = lex.GetNextToken();
        }
        trace("Variable", false);
        return recur;
    }

    /*
     * Recursive Terminal
     * 
     * <simple expression> --> [<sign>] <term> {<addop> || <term>}*
     *
     */
    private int SimpleExpression() {
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

    /*
     * Non Terminal
     *
     * <sign> --> $PLUS | $MINUS
     * 
     */
    private int Sign() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("Sign", true);
        // Check if the token is a plus or minus operator
        if ((token.code == lex.codeFor("PLUS_")) || (token.code == lex.codeFor("MINU_"))) {
            // If so, move to next token
            token = lex.GetNextToken();
        }
        trace("Sign", false);
        return recur;
    }

    /*
     * Not a Non-Terminal
     *
     * <term> --> <factor> {<mulop> || <factor> }*
     *
     */
    private int Term() {
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

    /*
     * Non Terminal
     *
     * <mulop> --> $MULTIPLY | $DIVIDE
     *
     */
    private int Multop() {
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

    /*
     * Non Terminals
     * 
     * <factor> --> <unsigned constant> | <variable> | $LPAR <simple expression>
     * $RPAR
     *
     */
    private int Factor() {
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

    /*
     * Not a Non-Terminal
     *
     * <unsigned constant> --> <unsigned number>
     *
     */
    private int UnsignedConstant() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("UnsignedConstant", true);
        // Call Unsigned for recursion
        recur = UnsignedNumber();
        trace("UnsignedConstant", false);
        return recur;
    }

    /*
     * Non Terminal
     *
     * <unsigned number> --> $FLOAT | $INTEGER
     *
     */
    private int UnsignedNumber() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("UnsignedNumber", true);
        // If token is a float or integer identifier
        if ((token.code == lex.codeFor("FLT__")) || (token.code == lex.codeFor("INT__"))) {
            // If so, get next token
            token = lex.GetNextToken();
        }
        trace("UnsignedNumber", false);
        return recur;
    }

    /* UTILITY FUNCTIONS USED THROUGHOUT THIS CLASS */
    // error provides a simple way to print an error statement to standard output
    // and avoid reduncancy
    private void error(String wanted, String got) {
        anyErrors = true;
        System.out.println("ERROR: Expected " + wanted + " but found " + got);
    }

    // trace simply RETURNs if traceon is false; otherwise, it prints an
    // ENTERING or EXITING message using the proc string
    private void trace(String proc, boolean enter) {
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

    // repeatChar returns a string containing x repetitions of string s;
    // nice for making a varying indent format
    private String repeatChar(String s, int x) {
        int i;
        String result = "";
        for (i = 1; i <= x; i++) {
            result = result + s;
        }
        return result;
    }
}