import Animal;

class palyground {
    public static void main(String[] args) {
        Animal myObj = new Animal();
        System.out.println(myObj.x);
        myObj.x = 40;
        System.out.println(myObj.x);
        myObj.makeNoise();
    }
}