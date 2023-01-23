package model;


public class Board {
    public static void Init(){
        System.out.println("  1   2   3   4   5   6   7");
        for(int i=0; i<11; i++){
            if(i%2==0){
                System.out.println("|   |   |   |   |   |   |   |");
            }
            else{
                System.out.println("|---+---+---+---+---+---+---|");
            }
        }
        System.out.println("\\===========================/");
    }
}
