// Robert Denim Horton CS2022
package ADT;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * @author Robert Denim Horton
 * @version 4.1
 * 
 *          This File represents the lexical analyzer of our compiler that 
 *          works by essentially breaking each line into token and parsing 
 *          through each token to decide what kind of token it is.  The 
 *          tokens are evaluated with its starting character and it is
 *          parsed through until either an error occurs, i.e. non-
 *          terminating string quotation marks (" " ") or comments (" (* "
 *          or  " *) ") or untill a space of the end of the line is hit. 
 */
import java.io.*;

public class Lexical {
    // File to be read for input
    private File file;
    // Reader, Java reqd
    private FileReader filereader;
    // Buffered, Java reqd
    private BufferedReader bufferedreader;
    // Current line of input from file
    private String line;
    // Current character position in the current line
    private int linePos;
    // SymbolTable used in Lexical sent as parameter to construct
    private SymbolTable saveSymbols;
    // End Of File indicator
    private boolean EOF;
    // true means echo each input line
    private boolean echo;
    // true to print found tokens here
    private boolean printToken;
    // line #in file, for echo-ing
    private int lineCount;
    // track when to read a new line
    private boolean needLine;
    // Tables to hold the reserve words and the mnemonics for token codes
    // a few more than # reserves
    private ReserveTable reserveWords = new ReserveTable(50);
    // a few more than # reserve
    ReserveTable mnemonics = new ReserveTable(50);
    // global char
    char currCh;

    // constructor
    public Lexical(String filename, SymbolTable symbols, boolean echoOn) {
        // map the initialized parameter to the local ST
        saveSymbols = symbols;
        // store echo status
        echo = echoOn;
        // start the line number count
        lineCount = 0;
        // line starts empty
        line = "";
        // need to read a line
        needLine = true;
        // default OFF, do not print tokesn here
        printToken = false;
        // within GetNextToken; call setPrintToken to change it publicly.
        // no chars read yet
        linePos = -1;
        // call initializations of tables
        initReserveWords(reserveWords);
        initMnemonics(mnemonics);

        // set up the file access, get first character, line retrieved 1st time
        try {
            // creates a new file instance
            file = new File(filename);
            // reads the file
            filereader = new FileReader(file);
            // creates a buffering character input stream
            bufferedreader = new BufferedReader(filereader);
            EOF = false;
            currCh = GetNextChar();
        } catch (IOException e) {
            EOF = true;
            e.printStackTrace();
        }
    }

    // token class is declared here, no accessors needed
    public class token {

        public String lexeme;
        public int code;
        public String mnemonic;

        token() {
            lexeme = "";
            code = 0;
            mnemonic = "";
        }
    }

    private final int UNKNOWN_CHAR = 99;
    public final int _GRTR = 38;
    public final int _LESS = 39;
    public final int _GREQ = 40;
    public final int _LEEQ = 41;
    public final int _EQLS = 42;
    public final int _NEQL = 43;

    private final int IDENT_ID = 50;
    private final int INTEGER_ID = 51;
    private final int FLOAT_ID = 52;
    private final int STRING_ID = 53;

    final char comment_start1 = '{';
    final char comment_end1 = '}';
    final char comment_start2 = '(';
    final char comment_startend = '*';
    final char comment_end2 = ')';

    private final int MAX_IDENT_LENGTH = 20;
    private final int MAX_INT_LENGTH = 6;
    private final int MAX_FLOAT_LENGTH = 12;
        

    // Public access to the current End Of File status
    public boolean EOF() {
        return EOF;
    }

    // DEBUG enabler, turns on token printing inside of GetNextToken
    public void setPrintToken(boolean on) {
        printToken = on;
    }

    public int codeFor(String mnemonic) {
        return mnemonics.LookupName(mnemonic);
    }

    public String reserveFor(String mnemonic) {
        return reserveWords.LookupCode(mnemonics.LookupName(mnemonic));
    }

    /* @@@ */
    private void initReserveWords(ReserveTable reserveWords) {
        reserveWords.Add("GO_TO", 0);
        reserveWords.Add("INTEGER", 1);
        // add the rest of the reserve words here
        reserveWords.Add("TO", 2);
        reserveWords.Add("DO", 3);
        reserveWords.Add("IF", 4);
        reserveWords.Add("THEN", 5);
        reserveWords.Add("ELSE", 6);
        reserveWords.Add("FOR", 7);
        reserveWords.Add("OF", 8);
        reserveWords.Add("PRINTLN", 9);
        reserveWords.Add("READLN", 10);
        reserveWords.Add("BEGIN", 11);
        reserveWords.Add("END", 12);
        reserveWords.Add("VAR", 13);
        reserveWords.Add("DOWHILE", 14);
        reserveWords.Add("PROGRAM", 15);
        reserveWords.Add("LABEL", 16);
        reserveWords.Add("REPEAT", 17);
        reserveWords.Add("UNTIL", 18);
        reserveWords.Add("PROCEDURE", 19);
        reserveWords.Add("DOWNTO", 20);
        reserveWords.Add("FUNCITON", 21);
        reserveWords.Add("RETURN", 22);
        reserveWords.Add("FLOAT", 23);
        reserveWords.Add("STRING", 24);
        reserveWords.Add("ARRAY", 25);

        // add 1 and 2-char tokens here
        reserveWords.Add("/", 30);
        reserveWords.Add("*", 31);
        reserveWords.Add("+", 32);
        reserveWords.Add("-", 33);
        reserveWords.Add("(", 34);
        reserveWords.Add(")", 35);
        reserveWords.Add(";", 36);
        reserveWords.Add(":=", 37);
        reserveWords.Add(">", 38);
        reserveWords.Add("<", 39);
        reserveWords.Add(">=", 40);
        reserveWords.Add("<=", 41);
        reserveWords.Add("=", 42);
        reserveWords.Add("<>", 43);
        reserveWords.Add(",", 44);
        reserveWords.Add("[", 45);
        reserveWords.Add("]", 46);
        reserveWords.Add(":", 47);
        reserveWords.Add(".", 48);
        reserveWords.Add("UK", UNKNOWN_CHAR);
    }

    /* @@@ */
    private void initMnemonics(ReserveTable mnemonics) {
        // add 5-character student created mnemonics corresponding to reserve values
        mnemonics.Add("GOTO_", 0);
        mnemonics.Add("INTGE", 1);
        mnemonics.Add("TO___", 2);
        mnemonics.Add("DO___", 3);
        mnemonics.Add("IF___", 4);
        mnemonics.Add("THEN_", 5);
        mnemonics.Add("ELSE_", 6);
        mnemonics.Add("FOR__", 7);
        mnemonics.Add("OF___", 8);
        mnemonics.Add("PRTLN", 9);
        mnemonics.Add("REDLN", 10);
        mnemonics.Add("BEGIN", 11);
        mnemonics.Add("END__", 12);
        mnemonics.Add("VAR__", 13);
        mnemonics.Add("DOWHL", 14);
        mnemonics.Add("PRGRM", 15);
        mnemonics.Add("LABEL", 16);
        mnemonics.Add("REPEAT", 17);
        mnemonics.Add("UNTIL", 18);
        mnemonics.Add("PROCD", 19);
        mnemonics.Add("DWNTO", 20);
        mnemonics.Add("FUNCT", 21);
        mnemonics.Add("RTURN", 22);
        mnemonics.Add("FLOAT", 23);
        mnemonics.Add("STRING", 24);
        mnemonics.Add("ARRAY", 25);
        mnemonics.Add("DIVD_", 30);
        mnemonics.Add("MULT_", 31);
        mnemonics.Add("PLUS_", 32);
        mnemonics.Add("MINU_", 33);
        mnemonics.Add("LTPAR", 34);
        mnemonics.Add("RTPAR", 35);
        mnemonics.Add("SMICL", 36);
        mnemonics.Add("ASIGN", 37);
        mnemonics.Add("GRT__", 38);
        mnemonics.Add("LES__", 39);
        mnemonics.Add("GRTEQ", 40);
        mnemonics.Add("LESEQ", 41);
        mnemonics.Add("EQUAL", 42);
        mnemonics.Add("BRCKT", 43);
        mnemonics.Add("COMMA", 44);
        mnemonics.Add("RBRKT", 45);
        mnemonics.Add("LBRKT", 46);
        mnemonics.Add("COLON", 47);
        mnemonics.Add("PER__", 48);
        mnemonics.Add("IDENT", IDENT_ID);
        mnemonics.Add("INT__", INTEGER_ID);
        mnemonics.Add("FLT__", FLOAT_ID);
        mnemonics.Add("STR__", STRING_ID);
        mnemonics.Add("UNKWN", 99);
    }

    private boolean isLetter(char ch) {
        // Character category for alphabetic chars, upper and lower case
        return (((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z')));
    }

    private boolean isDigit(char ch) {
        // Character category for 0..9
        return ((ch >= '0') && (ch <= '9'));
    }

    private boolean isWhitespace(char ch) {
        // Category for any whitespace to be skipped over
        // space, tab, and newline        
        return ((ch == ' ') || (ch == '\t') || (ch == '\n'));
    }

    private char PeekNextChar() {
        // Returns the VALUE of the next character without removing it from the
        // input line. Useful for checking 2-character tokens that start with
        // a 1-character token.        
        char result = ' ';
        if ((needLine) || (EOF)) {
            // at end of line, so nothing
            result = ' ';
        } else //
        {
            // have a char to peek
            if ((linePos + 1) < line.length()) {
                result = line.charAt(linePos + 1);
            }
        }
        return result;
    }

    private void GetNextLine() {
        // Called by GetNextChar when the characters in the current line
        // buffer string (line) are used up.
        try {
            // returns a null string when EOF
            line = bufferedreader.readLine();
            if ((line != null) && (echo)) {
                lineCount++;
                System.out.println(String.format("%04d", lineCount) + " " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // The readLine returns null at EOF, set flag
        if (line == null) {
            EOF = true;
        }
        // reset vars for new line if we have one
        linePos = -1;
        // we have one, no need
        needLine = false;
        // the line is ready for the next call to get a char with GetNextChar
    }

    public char GetNextChar() {
        // Returns the next character in the input file, returning a
        // /n newline character at the end of each input line or at EOF        
        char result;
        // ran out last time we got a char, so get a new line
        if (needLine) {
            GetNextLine();
        }
        // try to get char from line buff
        // if EOF there is no new char, just return endofline, DONE
        if (EOF) {
            result = '\n';
            needLine = false;
        } else {
            // if there are more characters left in the input buffer
            // have a character available
            if ((linePos < line.length() - 1)) {
                linePos++;
                result = line.charAt(linePos);
            } else {
                // need a new line, but want to return eoln on this call first
                result = '\n';
                // will read a new line on next GetNextChar call
                needLine = true;
            }
        }
        return result;
    }

    public char skipComment(char curr) {
        // skips over any comment as if it were whitespace, so it is ignored
        // if this is the start of a comment...
        if (curr == comment_start1) {
            curr = GetNextChar();
            // loop until the end of comment or EOF is reached
            while ((curr != comment_end1) && (!EOF)) {
                curr = GetNextChar();
            }
            // if the file ended before the comment terminated
            if (EOF) {
                System.out.println("Comment not terminated before End Of File");
            } else {
                // keep getting the next char
                curr = GetNextChar();
            }
        } else {
            // this is for the 2-character comment start, different start/end
            if ((curr == comment_start2) && (PeekNextChar() == comment_startend)) {
                // get the second
                curr = GetNextChar();
                // into comment or end of comment
                curr = GetNextChar();
                // while comment end is not reached
                while ((!((curr == comment_startend) && (PeekNextChar() == comment_end2))) && (!EOF)) {
                    curr = GetNextChar();
                }
                // EOF before comment end
                if (EOF) {
                    System.out.println("Comment not terminated before End Of File");
                } else {
                    // must move past close
                    curr = GetNextChar();
                    // must get following
                    curr = GetNextChar();
                }
            }

        }
        return (curr);
    }

    public char skipWhiteSpace() {
        // reads past any white space, blank lines, comments
        do {
            while ((isWhitespace(currCh)) && (!EOF)) {
                currCh = GetNextChar();
            }
            currCh = skipComment(currCh);
        } while (isWhitespace(currCh) && (!EOF));
        return currCh;
    }

    private boolean isPrefix(char ch) {
        // returns TRUE if ch is a prefix to a 2-character token like := or <=

        return ((ch == ':') || (ch == '<') || (ch == '>'));
    }

    private boolean isStringStart(char ch) {
        // returns TRUE if ch is the string delmiter        
        return ch == '"';
    }

    private token getIdent() {
        /*
         * This function is called from the GetNextToken() when the character being
         * evaluated is a upper or lower case alphabetic char. The token is then
         * parsed and added to the tokens lexeme and then used to check if the
         * token lexeme is a 'reserve' word of not and return the result as such.
         * 
         * 'identifier' tokens can be classified within the set of characters,
         * - 'A-Z' & 'a-z'
         * - '_'
         * - '|'
         * 
         * @return token An object with attributes that allow the token to be
         * associated with a 'code' look up and 'name' look up
         * related to the tokens set lexeme.
         */
        // Instantiate new token, 'result', to return.
        token result = new token();
        // Adds the first char to the token's lexeme.
        result.lexeme = "" + currCh;
        // Get the next char to evaluate.
        currCh = GetNextChar();
        // Enter while loop to evaluate chars until a char not in the set is hit.
        while (isLetter(currCh) || isDigit(currCh) ||
                (currCh == '|') || (currCh == '_')) {
            // Add char to token lexeme.
            result.lexeme = result.lexeme + currCh;
            // Retrieve next char.
            currCh = GetNextChar();
        }
        // Broken out of loop from invalid char, including end of line.
        // Look up token lexeme to see if word exists (In upper case).
        result.code = reserveWords.LookupName(result.lexeme.toUpperCase());
        // If result returns '-1' then no reserve word was found and the token
        // is identified as an 'identifier'
        if (result.code == -1) {
            result.code = IDENT_ID;
        }
        // Else we know that the toekn lexeme was found in the reserve word
        // table and set its nemonic to which reserve word it is that.
        result.mnemonic = mnemonics.LookupCode(result.code);
        // Return token as 'identifier' or a specific 'reserve' word.
        return result;
    }

    private token getNumber() {
        /*
         * This function is called from the GetNextToken() when the character being
         * evaluated is a digit. The token is then parsed and added to the tokens
         * lexeme and then returned.
         * 
         * 'identifier' tokens can be classified as the regex example provided below,
         * 
         * <digit>+[.<digit>*[E[+|-]<digit>+]]
         * 
         * @return token An object with attributes that allow the token to be
         * associated with a 'code' look up and 'name' look up
         * related to the tokens set lexeme.
         */
        // Instantiate new token, 'result', to return.
        token result = new token();
        // Adds the first char to the token's lexeme.
        result.lexeme = "" + currCh;
        // Get the next char to evaluate.
        currCh = GetNextChar();
        // Boolean to keep track of if a 'E', 'e', or a decimal has been parsed for this
        // token.
        boolean isFloat = false;
        boolean isScientific = false;
        // Enter while loop to evaluate chars until a condition is hit.
        while
        // char is a digit or decimal, and char decimal has not been parsed yet.
        (isDigit(currCh) || ((currCh == '.') && !isFloat)
        // char is a 'E' or a 'e', and char 'e' or 'E' has not been parsed yet, and
        // char decimal has been parsed.
                || (((currCh == 'E') || (currCh == 'e')) && isFloat && !isScientific)
                // char is a '+' or '-', and decimal has been parsed. And a 'E' or 'e' has
                // been parsed.
                || (((currCh == '+') || (currCh == '-')) && isFloat/* && isScientific */)) {
            // Add char to token lexeme
            result.lexeme = result.lexeme + currCh;
            // Check and set boolean for if decimal has been parsed yet.
            if ((currCh == '.')) {
                isFloat = true;
            }
            // Check and set boolean for if 'E' or 'e' has been parsed yet.
            if ((currCh == 'E') || (currCh == 'e')) {
                isScientific = true;
            }
            // Get next char.
            currCh = GetNextChar();
        }
        // Set token code to be a float.
        if (isFloat) {
            result.code = FLOAT_ID;
            // Set token code to be a integer.
        } else {
            result.code = INTEGER_ID;
        }
        // Look up code to set 'mnemonics' for token.
        result.mnemonic = mnemonics.LookupCode(result.code);
        // Return token as 'integer' or 'float'.
        return result;
    }

    private token getString() {
        /*
         * This function is called from the GetNextToken() when the character being
         * evaluated is a double quote ("). The token is then parsed and added to the
         * tokens lexeme and then returned as long as the har being evaluated is either
         * double quotation mark ('"') or a end of line terminator, ('\n'). If the new
         * line is hit we break out of loop and return token result and print out error.
         * else continue iter through token chars until double quotation mark is hit.
         * 
         * @return token An object with attributes that allow the token to be
         * associated with a 'code' look up and 'name' look up
         * related to the tokens set lexeme.
         */
        // Instantiate new token, 'result', to return.
        token result = new token();
        // Get nex t char.
        currCh = GetNextChar();
        // Enter Do-While loop to evaluate each char one line or until " is parsed.
        do {
            if (currCh == '\n') { // If currCh is newline string error occurred.
                // Print String Error.
                System.out.println("Unterminated String");
                // Move the currCh to the next char on the next line.
                currCh = GetNextChar();
                // Return token which is a BAD string token.
                return result;
            }
            // Add char onto the token lexeme.
            result.lexeme = result.lexeme + currCh;
            // Get next char.
            currCh = GetNextChar();
        } while (!isStringStart(currCh)); // Check to break loop or repeat.
        // Get next char to move onto either the next char after string.
        currCh = GetNextChar();
        // Since no return happened string must be good and is set to such with code.
        result.code = 53;
        // Code used to find mnemonic for strings.
        result.mnemonic = mnemonics.LookupCode(result.code);
        // Return token which is a GOOD string token.
        return result;
    }

    private token getOneTwoChar() {
        /*
         * This function is called from the GetNextToken() when the character being
         * evaluated is neither a number, letter, or a double quotation mark ("").
         * The token is then parsed and added to the tokens lexeme at beginning and
         * user to set the variable 'lstCh' to keep track of the char that was first
         * evaluated and resulted in calling this function. The lstCh, which is the
         * first out of the two chars being evaluated on this function call, if checked
         * against valid chars in the first If statement.
         * 
         * The nested, second if statement then checks to see if the lstCh is a prefix
         * to one of the double operators ('<>', ':=', '<=', or '>='), if so then the
         * currCh, second char, is evaluated to see if it is one of the second chars
         * from
         * choices above ('<', '>', '='). If so we then return the result, after moving
         * the curCh to the next char to be evaluated on the next call to
         * GetNextToken(). If the first char is not in the valid option in the first
         * statement then the else statement for the first If condition is entered and
         * the char is given the unknown code and mnemonic and returned. else we
         * assume that the first char (lstChr) was valid but the second char (currCh)
         * and return the single char with its matching mnemonic and code with the
         * currCh already being iterated for the next GetNextToken() call.
         * 
         * @return token An object with attributes that allow the token to be
         * associated with a 'code' look up and 'name' look up
         * related to the tokens set lexeme.
         */
        // Instantiate new token, 'result', to return.
        token result = new token();
        // Get current char being evaluated.
        char lstCh = currCh;
        // Move to next char to evaluate in tandem.
        currCh = GetNextChar();
        // Add char onto the token lexeme.
        result.lexeme = "" + lstCh; // have the first char
        // First If to check is first char is even a valid char.
        if ((lstCh == '/')
                || (lstCh == '*')
                || (lstCh == '+')
                || (lstCh == '-')
                || (lstCh == '(')
                || (lstCh == ')')
                || (lstCh == ';')
                || (lstCh == '>')
                || (lstCh == '<')
                || (lstCh == '=')
                || (lstCh == ',')
                || (lstCh == '[')
                || (lstCh == ']')
                || (lstCh == ':')
                || (lstCh == '.')) {
            // Second, nested If to check if the second char is a valid second
            // for char combination with the first char.
            if (((isPrefix(lstCh)) && (currCh == '=')) // Check if 1st char ':', '<', '>' and 2nd is '='.
                    || (((lstCh == '<')) && (currCh == '>'))) {// Check if 1st char is a '<' and 2nd is '>'
                // Add on second char
                result.lexeme = result.lexeme + currCh;
                // Iterate to next char for next call to GetNextToken()
                currCh = GetNextChar();
            }
            // Set token code to the associated char code, Should exist.
            result.code = reserveWords.LookupName(result.lexeme);
            // Else statement to first If statement
        } else {
            // Set token code to the Unknown char code
            result.code = UNKNOWN_CHAR;
        }
        // Retrvie mnemnoic for valid or invalid char or double char.
        result.mnemonic = mnemonics.LookupCode(result.code);
        // Return token as 'invalid char', 'single char', or 'double char'
        // operator/chars
        return result;
    }

    public token checkTruncate(token result) {
        /*
         * This function is called from GetNextToken() which checks for valid lexeme
         * of certain token types or token's code.
         */
        // Int value to see if token is a valid integer token.
        int ival = 0;
        // Double value to see if token is a valid double token.
        double dval = 0.0;
        // Integer to get tokens lexeme's length.
        int len = result.lexeme.length();
        // String of the tokens lexeme to be truncated.
        String lexemetrunc = result.lexeme;
        // Start switch statements.
        switch (result.code) {
            // Token code is set to ident.
            case IDENT_ID:
                // Checks if lexeme exceeds the length for identifiers.
                if (len > MAX_IDENT_LENGTH) {
                    // Slice index lexeme string to get truncated string.
                    lexemetrunc = result.lexeme.substring(0, MAX_IDENT_LENGTH);
                    // Print out error of token lexeme being to long.
                    System.out.println("Identifier length > " + MAX_IDENT_LENGTH + ", truncated " + result.lexeme
                            + " to " + lexemetrunc);
                }
                // Adds identifier to symbol table.
                saveSymbols.AddSymbol(lexemetrunc, 'v', 0);
                // Break case statement.
                break;
            // Token code is set to integer.
            case INTEGER_ID:
                // Checks if lexeme exceeds the length for integers.
                if (len > MAX_INT_LENGTH) {
                    // Slice index lexeme string to get truncated string.
                    lexemetrunc = result.lexeme.substring(0, MAX_INT_LENGTH);
                    // Print out error of token lexeme being to long.
                    System.out.println("Integer length > " + MAX_INT_LENGTH + ", truncated " + result.lexeme + " to "
                            + lexemetrunc);
                    // Set integer value set to 0 due to invalid value given.
                    ival = 0;
                } else {
                    if (integerOK(result.lexeme)) {
                        // Change string value to integer.
                        ival = Integer.valueOf(lexemetrunc);
                    } else {
                        // Prints out error message of invalid float value.
                        System.out.println("Invalid integer value");
                    }
                }
                // Adds integer to symbol table.
                saveSymbols.AddSymbol(lexemetrunc, 'c', ival);
                // Break case statement.
                break;
            // Token code is set to float.
            case FLOAT_ID:
                // Checks if lexeme exceeds the length for floats.
                if (len > MAX_FLOAT_LENGTH) {
                    // Slice index lexeme string to get truncated string.
                    lexemetrunc = result.lexeme.substring(0, MAX_FLOAT_LENGTH);
                    // Print out error of token lexeme being to long.
                    System.out.println("Float length > " + MAX_FLOAT_LENGTH + ", truncated " + result.lexeme + " to "
                            + lexemetrunc);
                    // Set double value set to 0 due to invalid value given.
                    dval = 0;
                } else {
                    if (doubleOK(result.lexeme)) {
                        // Changes string to a double value
                        dval = Double.valueOf(lexemetrunc);
                    } else {
                        // Prints out error message of invalid float value.
                        System.out.println("Invalid float value");
                    }
                }
                // Adds double to symbol table.
                // saveSymbols.AddSymbol(lexemetrunc, 'c', dval);
                // Break case statement.
                break;
            // Token code is set to string.
            case STRING_ID:
                // Add string to symbol table.
                saveSymbols.AddSymbol(result.lexeme, 'c', result.lexeme);
                // Break case statement.
                break;
            // Token code is set to nothing.
            default:
                // Break case statement.
                break; // don't add
        }
        return result;
    }

    public boolean doubleOK(String stin) {
        // Checks to see if a string contains a valid DOUBLE
        boolean result;
        try {
            Double.parseDouble(stin);
            result = true;
        } catch (NumberFormatException ex) {
            result = false;
        }
        return result;
    }

    public boolean integerOK(String stin) {
        // Checks the input string for a valid INTEGER
        boolean result;
        int x;
        try {
            x = Integer.parseInt(stin);
            result = true;
        } catch (NumberFormatException ex) {
            result = false;
        }
        return result;
    }

    public token GetNextToken() {
        // Main method of Lexical

        token result = new token();

        currCh = skipWhiteSpace();
        if (isLetter(currCh)) { // is ident
            result = getIdent();
        } else if (isDigit(currCh)) { // is numeric
            result = getNumber();
        } else if (isStringStart(currCh)) { // string literal
            result = getString();
        } else // default char checks
        {
            result = getOneTwoChar();
        }

        if ((result.lexeme.equals("")) || (EOF)) {
            result = null;
        }
        // set the mnemonic
        if (result != null) {
            // THIS LINE REMOVED-- PUT BACK IN TO USE LOOKUP result.mnemonic =
            // mnemonics.LookupCode(result.code);
            result = checkTruncate(result);
            if (printToken) {
                System.out.println("\t" + result.mnemonic + " | \t" + String.format("%04d", result.code) + " | \t"
                        + result.lexeme);
            }
        }
        return result;

    }
}
