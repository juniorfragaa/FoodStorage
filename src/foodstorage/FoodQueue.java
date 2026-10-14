package foodstorage;

import java.time.LocalDate;                     // stores only a date
import java.time.LocalDateTime;                 // stores date + time
import java.time.format.DateTimeFormatter;      // shows dates as dd/MM/yyyy


 //FoodQueue - Circular Queue (FIFO: First In, First Out)
 //The chef adds food at one side (rear) and removes it from the other side (front).
 //The oldest food leaves first, so it does not pass its best-before date.
 //It is circular: when an index reaches the last tray, it goes back to 0,
 //so the free trays are reused and no food ever needs to be shifted.
 
public class FoodQueue {

    // Parallel arrays: the same index in every array = the same food item
    private String[] names;                     // Burger, Pizza...
    private int[] weights;                      // in grams
    private LocalDate[] bestBefore;             // expiry date typed by the chef
    private LocalDateTime[] timeAdded;          // when it was placed into the storage
    private int[] ids;                          // unique number of each food

    private int capacity; // number of trays the chef chose; the last index is always capacity - 1

    private int front;    // index of the oldest item (the next one to leave)
    private int rear;     // index of the newest item (the last one added)
    private int nextId;   // IDs only go up (1, 2, 3...), they are never reused

    // CONSTRUCTOR = runs once when the queue is created with new FoodQueue(capacity)
    public FoodQueue(int capacity){

        this.capacity = capacity;                     // this. = the class variable, capacity = the parameter

        this.names = new String[capacity];            // new creates the arrays with the chosen size
        this.weights = new int[capacity];
        this.bestBefore = new LocalDate[capacity];
        this.timeAdded = new LocalDateTime[capacity];
        this.ids = new int[capacity];

        this.front = -1;  // -1 = not pointing to any tray, so the queue starts empty
        this.rear = -1;
        this.nextId = 1;  // IDs are for people, so they start at 1 (indexes start at 0)
    }

    // O(1) - only one comparison
    public boolean isEmpty(){
        return front == -1;
    }

    // O(1) - full when the next step of rear would land on front
    public boolean isFull(){
        return (rear + 1) % capacity == front;  // e.g. capacity 5, rear 4, front 0: (4 + 1) % 5 = 0 -> full
    }

    // Adds a food item at the rear
    // O(1) - only rear moves, no other food is moved
    public void enqueue (String name, int weight, LocalDate date){

        if(isFull()){
            System.out.println("Error: The storage is Full. Remove an item first");
            return;                             // return stops the method here
        }
        // The first item makes front leave -1 and point to the new food
        if(isEmpty()){
            front = 0;
        }
        // % makes rear go back to 0 after the last tray (this is what makes it circular)
        rear = (rear + 1) % capacity;

        // All the data goes to the same tray (rear), keeping the arrays aligned
        names[rear] = name;
        weights [rear] = weight;
        bestBefore[rear] = date;
        timeAdded[rear] = LocalDateTime.now();  // now() = the current date and time
        ids[rear] = nextId;
        nextId++;                               // ++ is fine here: IDs never go around

        System.out.println(name + " added to tray " + rear + " with ID " + ids[rear] + ".");
    }

    // Removes the oldest food item (the one at the front)
    // O(1) - only front moves, the other foods are not shifted
    public void dequeue (){
        // Every operation that reads the queue checks this first, so an empty storage never causes an error
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty");
            return;
        }

        // The message is shown before front moves, otherwise it would show the next food
        System.out.println(names[front] + " removed from tray " + front + ".");

        if (front == rear) {
            // It was the last food, so the queue is empty again
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;     // the next oldest food becomes the front
        }
        // The old data stays in the array, but it is outside front..rear, so it is ignored and later overwritten
    }

    // Shows the next food to leave (front) without removing it
    // O(1) - goes straight to the front index
    public void peek (){
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty");
            return;
        }
        System.out.println("Item at the top: " + names[front]);  // in a queue the "top" is the front
    }

    // Shows all food items, from the oldest (front) to the newest (rear)
    // O(n) - it visits every item once
    public void display (){
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        System.out.println("Food in the storage (oldest to newest): ");

        // Formats to show the dates in a friendly way (e.g. 27/10/2026 and 13/10/2026 22:50)
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");  // HH = hours, mm = minutes

        // i walks through the trays; front itself is not moved, because display only reads the queue
        int i = front;

        while (true) {                          // repeats until the break below
            System.out.println("Tray " + i + ": " + names[i] );
            System.out.println("   - ID: " + ids[i]);
            System.out.println("   - Weight: " + weights[i] + " g");
            System.out.println("   - Best Before: " + bestBefore[i].format(dateFormat));
            System.out.println("   - Added: " + timeAdded[i].format(dateTimeFormat));

            if(i == rear){
                break; // the newest food was shown, so stop
            }

            i = (i + 1) % capacity;             
        }
    }

    // Looks for the food with the ID the chef typed (linear search)
    // O(n) - in the worst case it checks every food
    public void searchById(int id){
        if(isEmpty()){
            System.out.println("Error: no data in the system. The storage is empty.");
            return;
        }
        int i = front;                          // same path as display: front -> rear

        while(true){
            if (ids[i] == id){                  // ids = the array, id = the number the chef typed
                System.out.println("Found in tray " + i + ": " + names [i]);
                return; // found it, so there is no need to check the rest
            }
            if (i == rear){
                break;
            }
            i = (i + 1) % capacity;
        }
        // Every food was checked and none had this ID
        System.out.println("No food with ID " + id + " in the storage.");
    }
}
