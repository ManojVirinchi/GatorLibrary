import java.util.*;

public class MinHeap {
    private final int capacity;
    private int size;
    public ReservationNode[] heap;

    // Constructor to initialize MinHeap
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new ReservationNode[capacity];
        size = 0;
    }

    // Check if the MinHeap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the current size of the MinHeap
    public int size() {
        return size;
    }

    // Get the index of the left child of a parent node
    public int getLeftChildIdx(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    // Get the index of the right child of a parent node
    public int getRightChildIdx(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    // Get the index of the parent node of a child node
    public int getParentIdx(int childIndex) {
        return (childIndex - 1) / 2;
    }

    // Get the left child of a parent node
    public ReservationNode leftChild(int parentIndex) {
        return heap[getLeftChildIdx(parentIndex)];
    }

    // Get the right child of a parent node
    public ReservationNode rightChild(int parentIndex) {
        return heap[getRightChildIdx(parentIndex)];
    }

    // Get the parent node of a child node
    public ReservationNode parent(int childIndex) {
        return heap[getParentIdx(childIndex)];
    }

    // Get the minimum (root) element without removing it from the heap
    public ReservationNode peek() {
        if (size == 0) {
            System.out.println("MinHeap empty, invalid peek()");
            return null;
        }
        return heap[0];
    }

    // Remove and return the minimum (root) element from the heap
    public ReservationNode poll() {
        if (size == 0) {
            System.out.println("MinHeap empty, invalid poll()");
            return null;
        }
        ReservationNode minNode = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return minNode;
    }

    // Insert a new reservation node into the heap
    public void insertNode(ReservationNode reservation) {
        if (size == capacity) {
            System.out.println("The Reservation Heap is full and cannot accept any more reservations");
        } else {
            heap[size] = reservation;
            size++;
            heapifyUp();
        }
    }

    // Restore heap properties from a specific node to the root
    public void heapifyUp() {
        int idx = size - 1;
        while (getParentIdx(idx) >= 0 && heap[idx].compareTo(parent(idx)) < 0) {
            swap(getParentIdx(idx), idx);
            idx = getParentIdx(idx);
        }
    }

    // Restore heap properties from the root down to a specific node
    public void heapifyDown() {
        int idx = 0;
        while (getLeftChildIdx(idx) < size) {
            int smallestChild = getLeftChildIdx(idx);

            if (getRightChildIdx(idx) < size && rightChild(idx).compareTo(leftChild(idx)) < 0) {
                smallestChild = getRightChildIdx(idx);
            }

            if (heap[idx].compareTo(heap[smallestChild]) < 0) {
                break;
            } else {
                swap(idx, smallestChild);
            }
            idx = smallestChild;
        }
    }

    // Swap elements at two indices within the heap array
    public void swap(int index1, int index2) {
        ReservationNode temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    // Print the elements currently in the heap
    public void printHeap() {
        if (size == 0) {
            System.out.println("MinHeap is empty, nothing to print");
            return;
        }
        System.out.print("{ ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println(" }");
    }

    // Override toString method to return a string representation of the heap elements
    @Override
    public String toString() {
        String res = "";
        List<ReservationNode> tempList = new ArrayList<>();
        while (this.size > 1) {
            ReservationNode heapNode = poll();
            tempList.add(heapNode);
            res += heapNode.getPatronId() + ",";
        }
        if (!isEmpty()) {
            ReservationNode heapNode = poll();
            tempList.add(heapNode);
            res += heapNode.getPatronId();
        }
        for (ReservationNode heapNode : tempList)
            insertNode(heapNode);
        return res;
    }
}
