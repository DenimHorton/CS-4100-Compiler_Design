// Robert Denim Horton CS2022

package ADT;

/**
 * @author Abrouill & Robert Denim Horton
 * @version 2.0
 * 
 *          TODO: This File represents . . .
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
     * NOTE: Given with provided Syntactic file.
     */
    private int ProgIdentifier() {
        int recur = 0;
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
     * NOTE: Given with provided Syntactic file.
     */
    private int Program() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
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
     * NOTE: Given with provided Syntactic file.
     */
    private int Block() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
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
     * <simple expression> --> <variable> $COLON-EQUALS <simple expression>
     *
     * NOTE: Given with provided Syntactic file.
     */
    private int Statement() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
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
     * NOTE: Given with provided Syntactic file.
     */
    private int handleAssignment() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("handleAssignment", true);
        // Have ident already in order to get to here, handle as Variable
        // Variable moves ahead, next token ready
        recur = Variable();
        if (token.code == lex.codeFor("ASIGN")) {
            token = lex.GetNextToken();
            recur = SimpleExpression();
        } else {
            error(lex.reserveFor("ASIGN"), token.lexeme);
        }
        trace("handleAssignment", false);
        return recur;
    }

    // #################################################################################################
    // BEGIN
    // Student Provided Terminal and Non-Terminal Token Identifiers
    // #################################################################################################

    /*
     * Non Terminal
     *
     * <addop> --> $PLUS | $MINUS
     * 
     * REVIEW: Needs to be checked.
     */
    private int Addop() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        // trace("Addop", true);
        if (token.code != lex.codeFor("PLUS_") || token.code != lex.codeFor("MINU_")) {
            token = lex.GetNextToken();
        }
        // trace("Addop", false);
        return recur;
    }

    /*
     * Recursive Terminal
     *
     * <variable> --> <identifier>
     *
     * REVIEW: Needs to be checked.
     */
    private int Variable() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("Variable", true);
        recur = ProgIdentifier();
        trace("Variable", false);
        return recur;
    }

    /*
     * Recursive Terminal
     * 
     * <simple expression> --> [<sign>] <term> {<addop> || <term>}*
     *
     * REVIEW: Needs to be checked.
     */
    private int SimpleExpression() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("SimpleExpression", true);
        if (token.code == lex.codeFor("PLUS_") || token.code == lex.codeFor("MINU_"))
            recur = Sign();
        if (token.code == Addop())
            recur = Addop();
        if (token.code == Term())
            recur = Term();
        trace("SimpleExpression", false);
        return recur;
    }

    /*
     * Non Terminal
     *
     * <sign> --> $PLUS | $MINUS
     * 
     * REVIEW: Needs to be checked.
     */
    private int Sign() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("Sign", true);

        if (token.code != lex.codeFor("PLUS_") || token.code != lex.codeFor("MINU_")) {
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
     * REVIEW: Needs to be checked.
     */
    private int Term() {
        int recur = 0;

        if (anyErrors) {
            return -1;
        }

        trace("Term", true);
        recur = Factor();

        while (token.code != lex.codeFor("MULT_")) {
            recur = Multop();
            recur = Factor();
        }
        trace("Term", false);

        return recur;
    }

    /*
     * Non Terminal
     *
     * <mulop> --> $MULTIPLY | $DIVIDE
     *
     * REVIEW: Needs to be checked.
     */
    private int Multop() {
        int recur = 0;

        if (anyErrors) {
            return -1;
        }
        // trace("Multop", true);
        if (token.code == lex.codeFor("MULT_") || token.code == lex.codeFor("DIVD_")) {
            token = lex.GetNextToken();
        }
        // trace("Multop", false);

        return recur;
    }

    /*
     * Non Terminals
     * 
     * <factor> --> <unsigned constant> | <variable> | $LPAR <simple expression>
     * $RPAR
     *
     * TODO: Needs to be checked.
     */
    private int Factor() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("Factor", true);

        if (token.code == UnsignedConstant()) {
            recur = UnsignedConstant();

        } else if ((token.code == Variable())) {
            recur = Variable();

        } else if (token.code == lex.codeFor("LTPAR")) {
            token = lex.GetNextToken();

            if (token.code == lex.codeFor("IDENT")) {
                // token = lex.GetNextToken();
                recur = Variable();

                if (token.code == lex.codeFor("RTPAR")) {
                    token = lex.GetNextToken();
                }
            }
        }
        trace("Factor", false);
        return recur;
    }

    /*
     * Not a Non-Terminal
     *
     * <unsigned constant> --> <unsigned number>
     *
     * REVIEW: Needs to be checked.
     */
    private int UnsignedConstant() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("UnsignedConst", true);
        recur = UnsignedNumber();
        trace("UnsignedConst", false);
        return recur;
    }

    /*
     * Non Terminal
     *
     * <unsigned number> --> $FLOAT | $INTEGER
     *
     * REVIEW: Needs to be checked.
     */
    private int UnsignedNumber() {
        int recur = 0;
        if (anyErrors) {
            return -1;
        }
        trace("UnsignedNumber", true);
        // This non-term is used to uniquely mark the Unsigned identifier
        if (token.code == lex.codeFor("FLT__") || token.code == lex.codeFor("INT__")) {
            token = lex.GetNextToken();
        }
        trace("UnsignedNumber", false);

        return recur;
    }
    // #################################################################################################
    // Student Provided Terminal and Non-Terminal Token Identifiers
    // END
    // #################################################################################################

    /**
     * *************************************************
     */
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

    /*
     * Template for all the non-terminal method bodies
     * private int exampleNonTerminal(){
     * int recur = 0;
     * if (anyErrors) {
     * return -1;
     * }
     * 
     * trace("NameOfThisMethod", true);
     * // unique non-terminal stuff
     * trace("NameOfThisMethod", false);
     * return recur;
     * 
     * }
     * 
     */
}
