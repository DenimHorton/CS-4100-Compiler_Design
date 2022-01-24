package ADT;

public class ReservedWord {
    public String reserved_word;
    public int reserved_word_code;

    public ReservedWord() {
        this.reserved_word = "Default"; // word_to_reserve;
        this.reserved_word_code = 0; // word_to_reserve_code;
    }

    public void set_code(int word_to_reserve_code) {
        this.reserved_word_code = word_to_reserve_code;
    }

    public void set_name(String word_to_reserve) {
        this.reserved_word = word_to_reserve;
    }
}