package foodstorage;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * FoodStack - Stack (LIFO: Last In, First Out)
 * The chef adds and removes food from the SAME side (the top).
 * The last food added is the first one to leave.
 */

public class FoodStack {
    // The same index in every array = the same food item (same as FoodQueue)

    private String [] names;                // food name (Burger, Pizza...)
    private int [] weights;                 // weight in grams
    private LocalDate[] bestBefore;         // best-before date
    private LocalDateTime[] timeAdded;      // date and time it was placed into the storage
    private int[] ids;                      // ID number of each food item

    private int capacity;                   //how many trays the storage has
    private int top;                        // index of the food on top (the last one added)
    private int nextId;                     // the ID the next food will get

    // CONSTRUCTOR = runs once when the stack is created
    public FoodStack(int capacity){
        this.capacity = capacity;
        this.names = new String [capacity];
        this.weights = new int [capacity];
        this.bestBefore = new LocalDate[capacity];
        this.timeAdded = new LocalDateTime [capacity];
        this.ids = new int [capacity];

        this.top = -1;       // the stack starts empty
        this.nextId = 1;     // the first food will get ID 1
    }
    // O(1) - the stack is empty when top is -1
    public boolean isEmpty(){
        return top == -1;
    }
    // O(1) - the stack is full when top is at the last tray
    public boolean isFull(){
        return top == capacity - 1;
    }
}
