package foodstorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


 // FoodStorage - the main class (the menu the chef uses).
 // It reads and validates what the chef types, and calls the chosen
 // data structure: FoodQueue (FIFO) or FoodStack (LIFO).
public class FoodStorage {

    public static void main(String[] args) {                 // the program starts here

        Scanner input = new Scanner (System.in); // reads what the chef types on the keyboard

        // The 5 food types the restaurant has (index 0 to 4)
        String[] foods = {"Burger", "Pizza", "Fries", "Sandwich", "Hotdog"};

        // Ask the chef which data structure to use.
        // Every question below follows the same pattern: the variable starts with an
        // invalid value, so the do-while asks at least once and repeats until it is valid.
        int type = 0;

        do{
            System.out.println("===== CHOOSE STORAGE TYPE =====");
            System.out.println("1. Queue (FIFO) - add at one side, remove from the other side");
            System.out.println("2. Stack (LIFO) - add and remove from the same side");
            System.out.print("Choose: ");                     // print (no ln) keeps the answer on the same line

            try{
                type = input.nextInt();                       // try: this line fails if the chef types letters
                if(type < 1 || type > 2){                     // || = OR
                    System.out.println("Invalid option. Please choose 1 or 2.");
                }
            }catch (Exception e) {
                // nextInt() fails when the chef types letters (e.g. "abc")
                System.out.println("That is not a number. Please type a number.");
                input.next(); // removes the wrong text, otherwise nextInt() would read it again forever
            }
        }while (type < 1 || type > 2);                       

        // Ask how many trays the storage will have (maximum 10)
        int capacity = 0;                                    
        do{
            System.out.print("How many trays does the storage have? (1-10): ");

            try{
                capacity = input.nextInt();
                if (capacity < 1 || capacity > 10){
                    System.out.println("Invalid number. Please choose between 1 and 10.");
                }
            }catch (Exception e){
                System.out.println("That is not a number. Please type a number.");
                input.next();
            }
        } while (capacity < 1 || capacity > 10);

        // Both structures are created with the chosen size,
        // but the menu only uses the one the chef picked (type 1 or 2)
        FoodQueue queue = new FoodQueue(capacity);            // calls the FoodQueue constructor
        FoodStack stack = new FoodStack(capacity);
        System.out.println("Storage created with: " + capacity + " Trays. ");

        int option; // declared outside the do-while so the while condition can see it
        do{
            System.out.println();                             // empty line, so the menu is not stuck to the text above
            System.out.println("===== FOOD STORAGE MENU ======");
            System.out.println("1. Add food");                  // enqueue / push
            System.out.println("2. Remove food");               // dequeue / pop
            System.out.println("3. Display food at the top");   // peek
            System.out.println("4. Display all food");          // display
            System.out.println("5. Search by ID");              // searchById
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            try{
                option = input.nextInt();
            } catch (Exception e) {
                input.next();
                option = -1;  // -1 is not on the menu, so the switch goes to default (0 would exit)
            }

            switch (option) {                                 // each case is one menu option

                // 0 = Exit: the do-while stops because option is 0
                case 0:
                    System.out.println("Okay, Goodbye!! ");
                    break;                                    // break ends the case (without it, the next case would run too)

                // 1 = Add food: ask for the food type, its name, weight and best-before date
                case 1:
                    System.out.println("Choose the food:");
                    System.out.println("1. Burger   🍔");
                    System.out.println("2. Pizza    🍕");
                    System.out.println("3. Fries    🍟");
                    System.out.println("4. Sandwich 🥪");
                    System.out.println("5. Hotdog   🌭");

                    int foodChoice = 0;

                    do {
                        System.out.print("Choose: ");
                        try {
                            foodChoice = input.nextInt();
                            if (foodChoice < 1 || foodChoice > 5) {
                                System.out.println("Invalid food. Please choose between 1 and 5.");
                            }
                        } catch (Exception e) {
                            System.out.println("That is not a number. Please type a number.");
                            input.next();
                        }
                    } while (foodChoice < 1 || foodChoice > 5);

                    // nextInt() leaves the Enter in the input, so nextLine() would read an empty line.
                    // This clears it before asking for the name.
                    input.nextLine();

                    // The chef types a name so two foods of the same type can be told apart (e.g. "Cheese Burger")
                    String foodName = "";
                    while (foodName.isEmpty()) {
                        System.out.print("Food name (e.g. Cheese Burger): ");
                        foodName = input.nextLine().trim();   // nextLine() reads the whole line, spaces included
                        if (foodName.isEmpty()) {
                            System.out.println("The name cannot be empty. Please type a name.");
                        }
                    }

                    // The menu starts at 1, but the array starts at 0, so we use - 1
                    // e.g. choice 1 + "Cheese Burger" -> "Burger - Cheese Burger"
                    String name = foods[foodChoice - 1] + " - " + foodName;

                    // The weight has no maximum, it only needs to be more than 0 grams
                    int weight = 0;

                    do {
                        System.out.print("Weight in grams: ");
                        try{
                            weight = input.nextInt();
                            if(weight < 1){
                                System.out.println("Invalid weight. It must be more than 0 grams.");
                            }
                        }catch(Exception e){
                            System.out.println("That is not a number. Please type a number.");
                            input.next();
                        }
                    } while (weight < 1);

                    // Best-before date: format dd/MM/yyyy, from today up to 2 weeks
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // MM = month (mm would be minutes)
                    LocalDate today = LocalDate.now();
                    LocalDate maxDate = today.plusDays(14);
                    LocalDate date = null;                    // null = no valid date yet

                    do {
                        System.out.print("Best-before date (dd/MM/yyyy): ");
                        try {
                            date = LocalDate.parse(input.next(), dateFormat);  // parse = turns the text into a real date
                            if (date.isBefore(today)) {
                                System.out.println("Invalid date. This food is already expired.");
                                date = null;
                            } else if (date.isAfter(maxDate)) {
                                System.out.println("Invalid date. The maximum is 2 weeks from today.");
                                date = null;
                            }
                        } catch (Exception e) {
                            // parse() fails when the text is not a real date in this format (e.g. "abc", "32/10/2026")
                            System.out.println("Invalid date. Please use the format dd/MM/yyyy.");
                        }
                    } while (date == null);

                    if(type == 1){
                        queue.enqueue(name, weight, date);          // added at the rear - O(1)
                    }else {                                         // type 2 = Stack (the only other option)
                        stack.push(name, weight, date);             // added on the top - O(1)
                    }
                    break;

                // 2 = Remove food: the Queue removes the oldest, the Stack removes the newest
                case 2:
                    if(type == 1){
                        queue.dequeue();       // FIFO - O(1)
                    } else {
                        stack.pop();           // LIFO - O(1)
                    }
                    break;

                // 3 = Show the next food to leave, without removing it - O(1)
                case 3:
                    if(type == 1){
                        queue.peek();
                    }else {
                        stack.peek();
                    }
                    break;

                // 4 = Show all food in the storage - O(n)
                case 4:
                    if(type == 1){
                        queue.display();
                    }else {
                        stack.display();
                    }
                    break;

                // 5 = Search: the question is the same for both structures, so it is asked only once - O(n)
                case 5:
                    System.out.print("Which ID would you like to search?: ");
                    try {
                        int id = input.nextInt();
                        if(type == 1){
                            queue.searchById(id);
                        }else{
                            stack.searchById(id);
                        }
                    } catch (Exception e) {
                        System.out.println("That is not a number. Please type a number.");
                        input.next();
                    }
                    break;

                default:                                      // any number that is not a case above
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        } while (option != 0);                                // != means "is different from"
    }
}
