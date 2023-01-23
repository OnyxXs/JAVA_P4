public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        commetuveux();
    }

    public static void commetuveux(){
        System.out.println("  1   2   3   4   5   6   7");
        for (int ligne=0; ligne < 11; ligne++){
            if (ligne%2 == 0){
                System.out.println("|   |   |   |   |   |   |   |");
            }
            else{
                System.out.println("|---+---+---+---+---+---+---|");
            }
        }
        System.out.println("\\===========================/");
    }
}
