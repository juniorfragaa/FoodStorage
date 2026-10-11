package foodstorage;                            //  This class lives in the "foodstorage" package, together with the Main

import java.time.LocalDate;                     // Stores only a date
import java.time.LocalDateTime;                 // Stores date + time

public class FoodQueue {                   

    // The same index in every array = the same food item.
    private String[] names;                     // Food name                 
    private int[] weights;                      // Weight in grams
    private LocalDate[] bestBefore;             // best-before date
    private LocalDateTime[] timeAdded;          // date and time it was place into the storage
    private int[] ids;                          // ID number of each food item
    
    private int capacity; //How many trays the storage has
                          // This is the number the chef chooses. The last index is always capacity -1
    
    private int front;    // index of the oldest item (the next one to leave)
    private int rear;     // index of the newest item (the last one added)
    private int nextId;   // the ID next food will get (1, 2, 3)
    
    // CONSTRUCTOR = runs once when the queue is created.
    public FoodQueue(int capacity){
    
        this.capacity = capacity;                     // the number the chef chose (the parameter)
        
        this.names = new String[capacity];            // starts as null (empty)
        this.weights = new int[capacity];             // start as 0
        this.bestBefore = new LocalDate[capacity];    // start as null
        this.timeAdded = new LocalDateTime[capacity]; // start as null 
        this.ids = new int[capacity];                 // creates the array, same size as the others
    
        this.front = -1;  // -1 = not pointing to any tray
        this.rear = -1;   // so the queue starts empty
        this.nextId = 1;  // the first food will get ID 1
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
        ids[rear] = nextId;                    // this foods gets the next ID
        nextId++;                              // the next food will get the number after this one
            
        System.out.println(name + " added to tray " + rear + " with ID " + ids[rear] + ".");   
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
    // Shows the food at the top (front) wihtout removing it.
    public void peek (){
        //1. If the is no food, show the error and stop here
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty");
            return;
        }
        //2. Show the name of the food at the front 
        System.out.println("Item at the top: " + names[front]);
    }
    // Shows all foods items, from the oldest (front) to the newest (rear)
    public void display (){
        //1. If there is no food, show the error and stop here
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        System.out.println("Food in the storage (oldest to newest): ");
        //2. Start at the front
        int i = front;
        
        //3. Repeat: show the food, stop at the rear or move one step
        while (true) {
        // Show the food in tray i
            System.out.println("Tray " + i + ": " + names[i] );
            System.out.println("   - ID: " + ids[i]);
            System.out.println("   - Weight: " + weights[i] + "g");
            System.out.println("   - Best Before: " + bestBefore[i]);
            System.out.println("   - Added: " + timeAdded[i]);
            
            if(i == rear){ 
                break; // this is the rear (the last food), so stop!
            }
            
            i = (i + 1) % capacity; // move to the next tray (back to 0 after the last one)
        }
    }
    //  Looks for the food with the ID the chef typed.
    public void searchById(int id){
        //1. If there is no food, show the error and stop here
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        // 2. Start as the front
        int i = front;
        
        while(true){
            //3. Is this the ID the chef wants? Shot it and stop
            if (ids[i] == id){
                System.out.println("Found in tray " + i + ":" + names [i]);
                return;
            }
            //4. This is the rear (the last food), so stop looking.
            if (i == rear){
                break;
            }
            // 5. Move to the next tray (back to 0 after the last one)
            i = (i + 1) % capacity;
        }
            // 6. We checked every food and did not find it
            System.out.println("No food with ID " + id + "in the storage.");
    }
    
}

