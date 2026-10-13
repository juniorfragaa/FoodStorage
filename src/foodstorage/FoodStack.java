package foodstorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * FoodStack - Stack (LIFO: Last In, First Out)
 * The chef adds and removes food from the SAME side (the top).
 * The last food added is the first one to leave, like a pile of plates.
 * Simpler than the queue: top only goes up and down, it never goes around.
 */
public class FoodStack {

    // Parallel arrays, organised the same way as in FoodQueue
    private String [] names;
    private int [] weights;
    private LocalDate[] bestBefore;
    private LocalDateTime[] timeAdded;
    private int[] ids;

    private int capacity;
    private int top;                        // index of the food on top (the last one added)
    private int nextId;

    // CONSTRUCTOR = runs once when the stack is created with new FoodStack(capacity)
    public FoodStack(int capacity){
        this.capacity = capacity;
        this.names = new String [capacity];
        this.weights = new int [capacity];
        this.bestBefore = new LocalDate[capacity];
        this.timeAdded = new LocalDateTime [capacity];
        this.ids = new int [capacity];

        this.top = -1;       // no plate yet: the stack starts empty
        this.nextId = 1;
    }

    // O(1) - top -1 means there is no food on the pile
    public boolean isEmpty(){
        return top == -1;
    }

    // O(1) - full when top reaches the last tray (no % needed, top never goes around)
    public boolean isFull(){
        return top == capacity - 1;         // e.g. capacity 5: trays 0..4, full when top = 4
    }

    // Puts a food item on the top of the pile
    // O(1) - only top moves, no other food is moved
    public void push (String name, int weight, LocalDate date){
        if (isFull()){
            System.out.println("Error: the storage is full. Remove an item first");
            return;
        }

        // ++ works here: on the first push top goes from -1 to 0
        top++;

        // The new plate goes on top: every array uses the same index (top)
        names[top] = name;
        weights[top] = weight;
        bestBefore[top] = date;
        timeAdded[top] = LocalDateTime.now();
        ids[top] = nextId;
        nextId++;

        System.out.println(name + " added to tray " + top + " with ID " + ids[top] + ".");
    }

    // Takes the food from the top (the last one added)
    // O(1) - only top moves
    public void pop(){
        // Checked first in every reading operation, so an empty stack never causes an error
        if (isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        System.out.println(names[top] + " removed from tray " + top + ".");

        // No special case for the last food: from 0, top goes to -1 (empty) by itself
        top--;
    }

    // Shows the food on the top of the pile, without taking it
    // O(1) - goes straight to the top index
    public void peek() {
        if (isEmpty()) {
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        System.out.println("Item at the top: " + names[top]);  // the food pop() would remove next
    }

    // Shows all food items, from the top (newest) to the bottom (oldest)
    // O(n) - it visits every item once
    public void display(){
        if (isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        System.out.println("Food in the storage (top to bottom):");

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // >= 0 so that the bottom plate (tray 0) is also shown
        int i = top;
        while (i >= 0){
            System.out.println("Tray " + i + ": " + names[i]);
            System.out.println("    - ID: " + ids[i]);
            System.out.println("    - Weight: " + weights [i] + " g");
            System.out.println("    - Best Before: " + bestBefore[i].format(dateFormat));
            System.out.println("    - Added: " + timeAdded[i].format(dateTimeFormat));
            i--;                            // one plate down
        }
    }

    // Looks for the food with the ID the chef typed, from the top down (linear search)
    // O(n) - in the worst case the food is at the bottom, or does not exist
    public void searchById(int id){
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty");
            return;
        }
        int i = top;
        while (i >= 0){
            if (ids[i] == id){
                System.out.println("Found in tray " + i + ": " + names[i]);
                return;                     // stops at the first match
            }
            i--;
        }
        System.out.println("No food with ID " + id + " in the storage.");  // reached the bottom without a match
    }
}
