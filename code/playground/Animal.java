package Animal;

public class Animal {
    int x = 5;
    String name = "Cat";
    String type = "Feline";

    public void makeNoise() {
        if (this.name == "Cat") {
            System.out.println("MEOW!!!!");
            System.out.println(this.type);

        }
    }
}
