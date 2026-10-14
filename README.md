# Fast-Food Storage

A simple Java console application, which stores data about fast-food (Burger, Pizza, Fries, Sandwich, Hotdog) in storage with a capacity of up to 10 trays. Each item has a name, weight in grams, best-before date (with a maximum of 2 weeks) and time of addition to storage.

The chef needs to choose one of two options:

| Scenario | Data structure |
|---|---|
| Add/remove from the same side | Stack (LIFO) |
| Add on one side, remove from the opposite side | Circular Queue (FIFO) |

## Time complexity

| Operation | Queue | Stack |
|---|---|---|
| Add / Remove / Peek | O(1) | O(1) |
| Display / Search by ID | O(n) | O(n) |

## How to run

Open the project in Apache NetBeans and press Run (F6).
