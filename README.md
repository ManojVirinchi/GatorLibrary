# Gator Library Management System

## Author
**Manoj Virinchi Chitta**  
Email: mchitta@ufl.edu

---
## Introduction
The **Gator Library Management System** is a library management application designed to efficiently manage books, reservations, and patron interactions using advanced data structures. It incorporates Red-Black Trees and Min-Heaps to provide fast and balanced operations for book and reservation handling.

---

## Project Structure

The project consists of the following core files:

- **GatorLibrary.java**: Main entry point of the application.
- **RedBlackTree.java**: Implements a Red-Black Tree for book management.
- **MinHeap.java**: Implements a Min-Heap for reservation prioritization.
- **ReservationNode.java**: Represents individual reservations with patron ID, priority, and timestamp.
- **Makefile**: Automates project compilation and execution.
- **projectReport.pdf**: Detailed documentation of the project.

---

## Features

1. **Book Management**
   - Add, delete, and search books efficiently.
   - Borrow and return books with real-time updates.
   - Search for the closest book by ID.

2. **Reservation Handling**
   - Min-Heap-based reservation queue for prioritizing patrons.

3. **File Input/Output**
   - Read commands from an input file.
   - Generate an output file with the results of executed commands.

---

## Key Classes and Methods

### GatorLibrary.java
- **`main()`**: Reads commands from an input file and manages library operations.
- **`parse()`**: Interprets and processes commands.
- **`Output()`**: Generates the output file.

### RedBlackTree.java
- **`insertBook(int, String, String, String)`**: Adds a book to the Red-Black Tree.
- **`deleteBook(int)`**: Deletes a book and adjusts the tree structure.
- **`borrowBook(int, int, int)`**: Manages borrowing operations.
- **`returnBook(int, int)`**: Handles book returns and reassigns reservations.
- **`findClosestBook(int)`**: Finds the closest book to a given ID.
- **`printBooks(int, int)`**: Lists books within a range of IDs.

### MinHeap.java
- **`insertNode(ReservationNode)`**: Adds a reservation.
- **`poll()`**: Retrieves and removes the highest-priority reservation.
- **`heapifyUp()` / `heapifyDown()`**: Maintains heap properties.

### ReservationNode.java
- Stores patron details and reservation metadata.
- Implements comparison based on priority and timestamp.

---

## How to Compile and Run

### Prerequisites
- Java 8+ installed.
- Make tool available for compilation.

### Steps

1. **Compile the project**:
   ```bash
   make
   ```

2. **Run the program**:
   ```bash
   java GatorLibrary <input_file_name>
   ```

### Input File Format
Commands should be formatted as follows:
```plaintext
InsertBook(<BookID>, <BookName>, <AuthorName>, <AvailabilityStatus>)
BorrowBook(<BookID>, <PatronID>, <Priority>)
ReturnBook(<BookID>, <PatronID>)
PrintBook(<BookID>)
PrintBooks(<StartBookID>, <EndBookID>)
FindClosestBook(<BookID>)
DeleteBook(<BookID>)
Quit()
```

### Example Input File
```plaintext
InsertBook(1, "Java Programming", "John Doe", "Yes")
BorrowBook(1, 101, 2)
ReturnBook(1, 101)
Quit()
```

### Example Output File
```plaintext
Book 1 Borrowed by Patron 101
Book 1 Returned by Patron 101
Program Terminated!!
```

---


