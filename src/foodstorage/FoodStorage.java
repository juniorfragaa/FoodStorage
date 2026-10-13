package foodstorage;

import java.time.LocalDate;
import java.util.Scanner;


public class FoodStorage {

    public static void main(String[] args) {
    
        Scanner input = new Scanner (System.in); // input = the keyboard 
        
        // The 5 food types the restaurant has 
        String[] foods = {"Burger", "Pizza", "Fries", "Sandwich", "Hotdog"};
        
        //Ask the chef which data structure to use
        int type = 0;     //starts invalid, so the loop asks at least once
       
        do{
            System.out.println("===== CHOOSE STORAGE TYPE =====");
            System.out.println("1. Queue (FIFO) - add at one side, remove from the other side");
            System.out.println("2. Stack (LIFO) - add and remove from the same side");
            System.out.print("Choose: ");
            
            try{
                type = input.nextInt();
                if(type < 1 || type > 2){
                    System.out.println("Invalid option. Please choose 1 or 2.");
                } 
            }catch (Exception e) {
                System.out.println("That is not a number. Please type a number.");
                input.next(); // clear the letter, so the chef can type again
                        }
            }while (type < 1 || type > 2);
        
        
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
        
        
        // Create both structures with the size the chef chose
        // The manu will only use the one the chef picked (type 1 or 2)
        FoodQueue queue = new FoodQueue(capacity);
        FoodStack stack = new FoodStack(capacity);
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
            
            try{
                option = input.nextInt(); // Try to read the option
            } catch (Exception e) {
                input.next(); // Clear the letter, so the chef can type again
                option = -1;  // not a menu option, so the switch goes to default
            }
            
            
            //Do what the chef chose 
            switch (option) {

                // 0 = Exit: the do-while stops because option is 0
                case 0:
                    System.out.println("Okay, Goodbye!! ");
                    break;

                // 1 = Add food: ask the chef for the food data, then add it at the rear
                case 1:
                    // Show the food list
                    System.out.println("Choose the food:");
                    System.out.println("1. Burger   🍔");
                    System.out.println("2. Pizza    🍕");
                    System.out.println("3. Fries    🍟");
                    System.out.println("4. Sandwich 🥪");
                    System.out.println("5. Hotdog   🌭");
                   
                    int foodChoice = 0; // starts invalid, so the loop asks at least once

                    do {
                        System.out.print("Choose: ");
                        try {
                            foodChoice = input.nextInt();
                            if (foodChoice < 1 || foodChoice > 5) {
                                System.out.println("Invalid food. Please choose between 1 and 5.");
                            }
                        } catch (Exception e) {
                            System.out.println("That is not a number. Please type a number.");
                            input.next(); // clear the letters, so the chef can type again
                        }
                    } while (foodChoice < 1 || foodChoice > 5);
                    
                    
                    //The menu starts at 1, but the array stats at 0, so we use -1
                    String name = foods[foodChoice - 1];
                    
                    int weight = 0;
                    
                    do {
                        System.out.print("Weight in grams: ");
                    
                        try{
                            weight = input.nextInt();                           // reads a whole number
                            if(weight < 1){
                                System.out.println("Invalid weight. It must be more than 0 grams.");
                            }
                        }catch(Exception e){
                            System.out.println("That is not a number. Please type a number.");
                            input.next();
                        }
                    } while (weight < 1);
                    
                    System.out.print("Best-before date (yyyy-MM-dd): ");
                    LocalDate date = LocalDate.parse(input.next());         // turns the text into a date
                    
                    if(type == 1){
                        queue.enqueue(name, weight, date);          // add at the rear - O(1)
                    }else {
                        stack.push(name, weight, date);             // add on the top  - O(1)
                    }
                    break;
                    
                // 2 = Remove food: the oldest food (front) leaves first, Stack removes the newest
                case 2:
                    if(type == 1){
                        queue.dequeue();       // FIFO - O(1)
                    } else {
                        stack.pop();           // LIFO - O(1)
                    }
                    break;

                // 3 = Show the next food to leave, without removing it
                case 3:
                   if(type == 1){
                       queue.peek();          // O(1)
                   }else {
                       stack.peek();           // O(1)
                   }
                    break;

                // 4 = Show all food in the storage
                case 4:
                    if(type == 1){
                        queue.display();      // O(n)
                    }else {
                        stack.display();      // O(n)
                    }
                    break;

                // 5 = Search: ask the chef for an ID and look for it
                case 5:
                    System.out.print("Which ID would you like to search?: ");
                    int id = input.nextInt();
                    
                    if(type == 1){
                        queue.searchById(id); // O(n)
                    }else{
                        stack.searchById(id); // O(n)
                    }
                    break;

                // Any other number is not on the menu
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        } while (option != 0); //repeat while the option is NOT 0
    }
}
