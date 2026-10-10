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
    
        this.front = -1;  // -1 = not poiting to any tray
        this.rear = -1;   // so the queue starts empty
    
    }
}

