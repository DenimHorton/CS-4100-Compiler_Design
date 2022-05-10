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

    private String filein; // The full file path to input file
    private SymbolTable symbolList; // Symbol table storing ident/const
    private QuadTable quads;
    private Interpreter interp;
    private Lexical lex; // Lexical analyzer
    private Lexical.token token; // Next Token retrieved
    private boolean traceon; // Controls tracing mode
    private int level = 0; // Controls indent for trace mode
    private boolean anyErrors; // Set TRUE if an error happens
    private final int symbolSize = 250;
    private final int quadSize = 1500;
    private int Minus1Index;
    private int Plus1Index;

    public int genCount = 0;

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

    // The interface to the syntax analyzer, initiates parsing
    // Uses variable RECUR to get return values throughout the non-terminal method
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
        quads.AddQuad(interp.optable.LookupName("STOP"), 0, 0, 0);
        // print ST, QUAD before execute
        symbolList.PrintSymbolTable(".\\SP22HW6\\Outputs\\CodeGenBASICS-before-OUTPUT.txt");
        quads.PrintQuadTable(".\\SP22HW6\\Outputs\\CodeGenBASICQUADS-OUTPUT.txt");
        // interpret
        if (!anyErrors) {
            interp.InterpretQuads(quads, symbolList, false, ".\\SP22HW6\\Outputs\\CodeGenTrace-OUTPUT.txt");
        } else {
            System.out.println("Errors, unable to run program.");
        }
        symbolList.PrintSymbolTable(".\\SP22HW6\\Outputs\\CodeGenBASICS-after-OUTPUT.txt");
    }

    private int GenSymbol() {
        String temp = "@" + Integer.toString(genCount);
        genCount++;
        symbolList.AddSymbol(temp, 'v', 0);
        return symbolList.LookupSymbol(temp);
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

    private int relopToOpcode(int relop) {
        int result = 0;
        switch (lex.mnemonics.LookupCode(relop)) {
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

    private int doTypeCheck() {
        return 0;
    }

    // #################################################################################################
    // Terminals, handlers, and Non-Terminals
    // Start
    // #################################################################################################

    private int handleIf() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }

        int patchElse, branchQuad;
        // Set Trace to track recursion through console
        trace("handleIf", true);
        if (token.code == lex.codeFor("IF___")) {
            // move on
            token = lex.GetNextToken();
            branchQuad = Relexpression();
            if (token.code == lex.codeFor("THEN_")) {
                token = lex.GetNextToken();
                recur = Statement();
                if (token.code == lex.codeFor("ELSE_")) {
                    token = lex.GetNextToken();
                    patchElse = quads.NextQuad();
                    quads.AddQuad(interp.optable.LookupName("JMP"), 0, 0, 0);
                    quads.setQuadOp3(branchQuad, quads.NextQuad());
                    recur = Statement();
                    quads.setQuadOp3(patchElse, quads.NextQuad());
                } else {
                    quads.setQuadOp3(branchQuad, quads.NextQuad());
                }
            } else {
                error(lex.reserveFor("THEN_"), token.lexeme);
            }
        } else {
            error(lex.reserveFor("IF___"), token.lexeme);
        }
        trace("handleIf", false);
        return recur;
    }

    private int handleDoWhile() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }

        int branchQuad, saveTop;
        // Set Trace to track recursion through console
        trace("handleDoWhile", true);
        if (token.code == lex.codeFor("DOWHL")) {
            // move on
            token = lex.GetNextToken();
            saveTop = quads.NextQuad();
            branchQuad = Relexpression();
            recur = Statement();
            quads.AddQuad(interp.optable.LookupName("JMP"), 0, 0, saveTop);
            quads.setQuadOp3(branchQuad, quads.NextQuad());
        } else {
            error(lex.reserveFor("DOWHL"), token.lexeme);
        }
        trace("handleDoWhile", false);
        return recur;
    }

    private int handleRepeat() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }

        int branchTarget, branchQuad;
        // Set Trace to track recursion through console
        trace("handleRepeat", true);
        token = lex.GetNextToken();
        branchTarget = quads.NextQuad();
        recur = Statement();
        if (token.code == lex.codeFor("UNTIL")) {
            // move on
            branchQuad = Relexpression();
            quads.setQuadOp3(branchQuad, branchTarget);
        } else {
            error(lex.reserveFor("UNTIL"), token.lexeme);
        }
        trace("handleRepeat", false);
        return recur;
    }

    private int handleReadln() {
        // Reset recur to 0
        int recur = 0;
        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }
        // Set Trace to track recursion through console
        trace("handleReadln", true);
        int toRead;
        token = lex.GetNextToken();
        // look for ( stringconst, ident, simpleexp )
        if (token.code == lex.codeFor("LTPAR")) {
            // move on
            token = lex.GetNextToken();
            if (token.code == lex.codeFor("IDENT")) {
                toRead = Variable();
                quads.AddQuad(interp.optable.LookupName("READ"), toRead, 0, 0);
            } else {
                error(lex.reserveFor("IDENT"), token.lexeme);
            }
            // now need right ")"
            if (token.code == lex.codeFor("RTPAR")) {
                // move on
                token = lex.GetNextToken();
            } else {
                error(lex.reserveFor("RTPAR"), token.lexeme);
            }
        } else {
            error(lex.reserveFor("LTPAR"), token.lexeme);
        }
        trace("handleReadln", false);
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
        int left, right;
        right = Variable(); // Variable moves ahead, next token ready
        if (token.code == lex.codeFor("ASIGN")) {
            token = lex.GetNextToken();
            left = SimpleExpression();
            quads.AddQuad(interp.optable.LookupName("MOV"), left, 0, right);
        } else {
            token = lex.GetNextToken();
        }
        trace("handleAssignment", false);
        return recur;
    }

    private int handlePrintln() {
        int recur = 0;
        int toprint = 0;

        if (anyErrors) {
            return -1;
        }

        trace("handlePrintln", true);
        // got here from a PRINT token, move past it...
        token = lex.GetNextToken();
        // look for ( stringconst, ident, simpleexp )
        if (token.code == lex.codeFor("LTPAR")) {
            // move on
            token = lex.GetNextToken();
            if ((token.code == lex.codeFor("STR__"))) { // || (token.code == lex.codeFor("IDNT"))) {
                // save index for string literal or identifier
                toprint = symbolList.LookupSymbol(token.lexeme);
                // move on
                token = lex.GetNextToken();
            } else {
                toprint = SimpleExpression();
            }
            quads.AddQuad(interp.optable.LookupName("PRINT"), 0, 0, toprint);
            // now need right ")"
            if (token.code == lex.codeFor("RTPAR")) {
                // move on
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
        if (token.code == lex.codeFor("PRGRM")) { // must be an ASSUGNMENT
            token = lex.GetNextToken();
        } else if (token.code == lex.codeFor("IDENT")) { // must be an ASSUGNMENT
            recur = handleAssignment();
        } else if (token.code == lex.codeFor("IF___")) { // must be an
            recur = handleIf();
        } else if (token.code == lex.codeFor("BEGIN")) { // must be an
            recur = Block();
        } else if (token.code == lex.codeFor("DOWHL")) { // must be an
            recur = handleDoWhile();
        } else if (token.code == lex.codeFor("REPEAT")) { // must be an
            recur = handleRepeat();
        } else if (token.code == lex.codeFor("PRTLN")) { // must be an
            recur = handlePrintln();
        } else if (token.code == lex.codeFor("REDLN")) { // must be an
            recur = handleReadln();
        } else {
            error("Statement", token.lexeme);
        }
        trace("Statement", false);
        return recur;
    }

    /*
     * Non Terminal
     *
     * <addop> --> $PLUS | $MINUS
     * 
     */
    private int relop() {
        // Reset recur to 0
        int recur = 0;
        if (anyErrors) {
            return -1;
        }

        trace("Relop", true);
        if (token.code == lex.codeFor("BRCKT")) {
            recur = relopToOpcode(token.code);
        } else if (token.code == lex.codeFor("GRT__")) {
            recur = relopToOpcode(token.code);
        } else if (token.code == lex.codeFor("LES__")) {
            recur = relopToOpcode(token.code);
        } else if (token.code == lex.codeFor("GRTEQ")) {
            recur = relopToOpcode(token.code);
        } else if (token.code == lex.codeFor("LESEQ")) {
            recur = relopToOpcode(token.code);
        } else if (token.code == lex.codeFor("EQUAL")) {
            recur = relopToOpcode(token.code);
        }
        token = lex.GetNextToken();
        trace("Relop", false);

        return recur;
    }

    /*
     * Non Terminal
     *
     * <addop> --> $PLUS | $MINUS
     * 
     */
    private int Relexpression() {
        // Reset recur to 0
        int recur = 0;
        if (anyErrors) {
            return -1;
        }

        int left, right, saveRelop, temp;
        trace("Relexpression", true);
        left = SimpleExpression();
        saveRelop = relop();
        right = SimpleExpression();
        temp = GenSymbol();
        quads.AddQuad(interp.optable.LookupName("SUB"), left, right, temp);
        recur = quads.NextQuad();
        quads.AddQuad(relopToOpcode(saveRelop), temp, 0, 0);
        trace("Relexpression", false);
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
        if (token.code == lex.codeFor("PLUS_")) {
            recur = interp.optable.LookupName("ADD");
        } else {
            recur = interp.optable.LookupName("SUB");
        }
        token = lex.GetNextToken();
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
        int recur = -1;

        // If anyErrors is not equal to 0 then return -1
        if (anyErrors) {
            return -1;
        }

        // Set Trace to track recursion through console
        trace("Variable", true);

        if (token.code == lex.codeFor("IDENT")) {
            recur = Identifier();
        } else {
            error("Variable", token.lexeme);
        }
        // Set Trace to track recursion through console
        trace("Variable", false);
        return recur;
    }

    // Template for all the non-terminal method bodies
    private int Identifier() {
        // Reset recur to 0
        int recur = 0;
        if (anyErrors) {
            return -1;
        }

        trace("Identifier", true);
        if (token.code == lex.codeFor("IDENT")) {
            recur = symbolList.LookupSymbol(token.lexeme);
            token = lex.GetNextToken();
        }
        doTypeCheck();
        // System.out.println("SymbolList Look up for \'"+token.lexeme+"\'':\t "+recur);

        trace("Identifier", false);
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

        int left, right, signval, temp, opcode;

        // Set Trace to track recursion through console
        trace("SimpleExpression", true);
        // Check if the token is a plus or minus operator
        signval = Sign();
        left = Term();
        if (signval == -1) {
            quads.AddQuad(interp.optable.LookupName("MUL"), left, Minus1Index, left);
        }
        while (((token.code == lex.codeFor("PLUS_")) || (token.code == lex.codeFor("MINU_")))) {
            opcode = Addop();
            right = Term();
            temp = GenSymbol();
            quads.AddQuad(opcode, left, right, temp);
            left = temp;
        }
        trace("SimpleExpression", false);
        return left;
    }

    /*
     * Non Terminal
     *
     * <sign> --> $PLUS | $MINUS
     * 
     */
    private int Sign() {
        // Reset recur to 0
        int recur = 1;

        // Set Trace to track recursion through console
        trace("Sign", true);
        // Check if the token is a plus or minus operator
        if ((token.code == lex.codeFor("MINU_"))) {
            // If so, move to next token
            recur = -1;
            token = lex.GetNextToken();
        } else if (token.code == lex.codeFor("PLUS_")) {
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

        int left, right, multOp, temp;
        // Set Trace to track recursion through console
        trace("Term", true);
        // Call Factor to see if any terminating terminals exists
        left = Factor();
        // While the next operator is a multiply or divide operator keep rcurring and
        // moving to next token
        while ((token.code == lex.codeFor("MULT_")) || (token.code == lex.codeFor("DIVD_"))) {
            multOp = Multop(); // Call Multop to see move to next token
            right = Factor(); // Call Factor to see if any terminating terminals exists
            temp = GenSymbol();
            quads.AddQuad(multOp, left, right, temp);
            left = temp;
        }
        trace("Term", false);
        return left;
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
        if ((token.code == lex.codeFor("MULT_"))) {
            // If so, move to next token
            recur = interp.optable.LookupName("MUL");
        } else if (token.code == lex.codeFor("DIVD_")) {
            // If so, move to next token
            recur = interp.optable.LookupName("DIV");
        }
        token = lex.GetNextToken();

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
            recur = symbolList.LookupSymbol(token.lexeme);
            token = lex.GetNextToken();
        }
        trace("UnsignedNumber", false);
        return recur;
    }

    // #################################################################################################
    // Terminals and Non-Terminals
    // END
    // #################################################################################################

    /*
     * Template for all the non-terminal method bodies
     * private int exampleNonTerminal(){
     * //Reset recur to 0
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