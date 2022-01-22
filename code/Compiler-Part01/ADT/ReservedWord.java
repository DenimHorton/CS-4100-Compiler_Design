package ADT;

public class ReservedWord {
    String reserved_word;
    int reserved_word_code;
     
    public ReservedWord(String word_to_reserve, int word_to_reserve_code){
        this.reserved_word = word_to_reserve;
        this.reserved_word_code = word_to_reserve_code; 
    }

    public void set_code(int word_to_reserve_code){
        this.reserved_word_code = word_to_reserve_code;     
    }

    public void set_name(String word_to_reserve){
        this.reserved_word = word_to_reserve;
    }
}