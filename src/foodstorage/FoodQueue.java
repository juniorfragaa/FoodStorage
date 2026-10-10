package foodstorage;                            //  This class lives in the "foodstorage" package, together with the Main

import java.time.LocalDate;                     // Stores only a date
import java.time.LocalDateTime;                 // Stores date + time

public class FoodQueue {                   

    // The same index in every array = the same food item.
    private String[] names;                     // Food name                 
    private int[] weights;                      // Weight in grams
    private LocalDate[] bestBefore;             // best-before date
    private LocalDateTime[] timeAdded;          // date and time it was place into the storage
    
    
    private int capacity; //How many trys the storage has
                          // This is the number the chef chooses. The last index is always capacity -1
    
    private int front;    // index of the oldest item (the next one to leave)
    private int rear;     // index of the newest item (the last one added)
    
    // CONSTRUCTOR = runs once when the queue is created.
    public FoodQueue(int capacity){
    
        this.capacity = capacity;                     // the number the chef chose (the parameter)
        
        this.names = new String[capacity];            // starts as null (empty)
        this.weights = new int[capacity];             // start as 0
        this.bestBefore = new LocalDate[capacity];    // start as null
        this.timeAdded = new LocalDateTime[capacity]; // start as null     
    
        this.front = -1;  // -1 = not pointing to any tray
        this.rear = -1;   // so the queue starts empty
    }   
    
    public boolean isEmpty(){
        return front == -1; // Return true if the storage has no food
    }
    
    public boolean isFull(){
        return (rear + 1) % capacity == front; // returns true if there is no free tray left
    }
    // Adds a food item at the rear of the queue
    public void enqueue (String name, int weight, LocalDate date){
    
        // 1. If there is no free tray, stop here
        if(isFull()){
            System.out.println("Error: The storage is Full. Remove an item first");
            return;
        }
        // 2. If it is the first item, front leaves -1 and goes to tray 0
        if(isEmpty()){
            front = 0;
        }
        // 3. Move rear one step, After the last tray, it goes back to 0.
        rear = (rear + 1) % capacity;
        // 4. Save the food data in the same index of every array
        names[rear] = name;
        weights [rear] = weight;
        bestBefore[rear] = date;
        timeAdded[rear] = LocalDateTime.now(); //   The current date and time
            
        System.out.println(name + " added to tray " + rear + ".");   
    }  
    // Removes the oldest food item (the one at the front)
    public void dequeue (){
        //1. If there is no food, show the error and stop here.
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty");
            return;
        }
        
        //2. Show which food is leaving
        System.out.println(names[front] + " removed from tray " + front);
        
        //3. If it was the last food, the queue becomes empty again
        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
        // 4. Otherwise, front moves to the next tray (back to 0 after the last one)
            front = (front + 1) % capacity;
        }
    }
}

