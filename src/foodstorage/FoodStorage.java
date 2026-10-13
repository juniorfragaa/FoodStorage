package foodstorage;

import java.time.LocalDate;
import java.util.Scanner;


public class FoodStorage {

    public static void main(String[] args) {
    
        Scanner input = new Scanner (System.in); // input = the keyboard 
        int capacity = 0; // starts invalid, so the loop keeps asking until it is valid
        
        do{
        // Ask the chef how many trays the storage will have
        System.out.print("How many trays does the storage have? (1-10): ");           
            
            try{
                capacity = input.nextInt(); //Try to read a number
                
                if (capacity < 1 || capacity > 10){
                System.out.println("Invalid number. Please choose between 1 and 10.");
                } 
            }catch (Exception e){
                // The chef typed letter ("abc", so nextInt() failed
                System.out.println("That is not a number. Please type a number");
                    input.next(); // clear the letter, so the chef can type again
                    }
        } while (capacity < 1 || capacity > 10); // Ask again while the number is invalid 
        
        
        // Create the queue with the number the chef chose 
        FoodQueue storage = new FoodQueue(capacity);
        System.out.println("Storage created with: " + capacity + " Trays. ");
        
        int option; //The option the chef chooses (declared here so the while can see it)
        do{
            System.out.println();
            System.out.println("===== FOOD STORAGE MENU ======");
            System.out.println("1. Add food");                  //enqueue
            System.out.println("2. Remove food");               //dequeue 
            System.out.println("3. Display food at the top");   //peek
            System.out.println("4. Display all food");          //display
            System.out.println("5. Search by ID");              //search by ID
            System.out.println("0. Exit");                      //exit
            System.out.print("Choose an option: ");
            option = input.nextInt();
            
            //Do what the chef chose 
            switch (option) {

                // 0 = Exit: the do-while stops because option is 0
                case 0:
                    System.out.println("Okay, Goodbye!! ");
                    break;

                // 1 = Add food: ask the chef for the food data, then add it at the rear
                case 1:
                    System.out.print("Food name: ");
                    String name = input.next();                       // reads one word
                    System.out.print("Weight in grams: ");
                    int weight = input.nextInt();                     // reads a whole number
                    System.out.print("Best-before date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(input.next());   // turns the text into a date
                    storage.enqueue(name, weight, date);              // O(1)
                    break;

                // 2 = Remove food: the oldest food (front) leaves first
                case 2:
                    storage.dequeue();      // O(1)
                    break;

                // 3 = Show the food at the top (front), without removing it
                case 3:
                    storage.peek();         // O(1)
                    break;

                // 4 = Show all food, from the oldest to the newest
                case 4:
                    storage.display();      // O(n)
                    break;

                // 5 = Search: ask the chef for an ID and look for it
                case 5:
                    System.out.print("Which ID would you like to search?: ");
                    int id = input.nextInt();
                    storage.searchById(id); // O(n)
                    break;

                // Any other number is not on the menu
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        } while (option != 0); //repeat while the option is NOT 0
    }
}
