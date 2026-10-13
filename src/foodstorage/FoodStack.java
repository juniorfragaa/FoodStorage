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
    
    public void push (String name, int weight, LocalDate date){
        //1. If there is no free tray, stop here
        if (isFull()){
            System.out.println("Error: the storage is full. Remove an item first");
            return;
        }
        
        //2. Move top one step up
        top++;
        
        //3. Save the food data ub the same index of every array
        names[top] = name;
        weights[top] = weight;
        bestBefore[top] = date;
        timeAdded[top] = LocalDateTime.now();
        ids[top] = nextId;
        nextId++;
        
        System.out.println(name + " added to tray " + top + " with ID " + ids[top] + ".");
    }
    
    //Removes the food on the top (the last one added)
    //O(1) - only top moves, no other food is moved
    public void pop(){
        //1. If there is no food, show the error and stop here
        if (isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        //2. Show which food is leaving 
        System.out.println(names[top] + " removed from tray " + ".");
        
        //3. Move top one step down (from 0 it goes to -1 = empty 
        top--;
    }
    
}
