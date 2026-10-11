package foodstorage;

import java.util.Scanner;


public class FoodStorage {

    public static void main(String[] args) {
    
        Scanner input = new Scanner (System.in); // input = the keyboard 
    
        // Ask the chef how many trays the storage will have
        System.out.print("How many trays does the storage have? (max 10): ");
        int capacity = input.nextInt();
        
        // Create the queue with the number the chef chose 
        FoodQueue storage = new FoodQueue(capacity);
        System.out.println("Sotage created with: " + capacity + " Trays. ");
    }
}
