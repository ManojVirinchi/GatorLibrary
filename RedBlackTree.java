import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;


// declaring red and black using an enum
enum Color {
    RED, BLACK;
}
class RedBlackTree {
    public static class Tnil {
    static RedBlackNode nil = new RedBlackNode(-1);
}

// Represents a node in the Red-Black Tree
    public static class RedBlackNode {
    int bookId;
    String bookName;
    String authorName;
    boolean isAvailable;
    int borrowedBy;
    RedBlackTree.RedBlackNode left, right, parent;
    Color color;
    MinHeap minHeap; // For managing reservations associated with this book

    public RedBlackNode() {
    }

    public RedBlackNode(int bookId, String bookName, String authorName, boolean isAvailable) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.left = (RedBlackTree.RedBlackNode)Tnil.nil;
        this.right = (RedBlackTree.RedBlackNode)Tnil.nil;
        this.parent = (RedBlackTree.RedBlackNode)Tnil.nil;
        this.color = Color.BLACK;
        this.isAvailable = isAvailable;
        this.borrowedBy = -1;
        minHeap = new MinHeap(20);
    }

    public RedBlackNode(int bookId) {
        this.bookId = bookId;
        this.color = Color.BLACK;
    }

    @Override
    public String toString() {
        return "BookID = " + bookId +
                "\nTitle = " + bookName +
                "\"\nAuthor = " + authorName +
                "\"\nAvailability = \"" + (isAvailable ? "Yes" : "No") +
                "\"\nBorrowedBy = " + (borrowedBy == -1 ? "None" : borrowedBy) +
                "\nReservations = [" + minHeap + "]\n";
    }
}
 // HashMaps to track color changes in nodes during operations
    public static Map<Integer, Color> hm1 = new HashMap<>();
    public static Map<Integer, Color> hm2 = new HashMap<>();
    public final RedBlackNode nil = Tnil.nil;
    public int flipCount;
    public RedBlackNode root;

    public static StringBuilder resultString = new StringBuilder(); //to store the output after each step

    public RedBlackTree() {
        this.root = nil;
        this.flipCount = 0;
    }
//insertion into a red black tree
    public void insertBook(int bookId, String bookName, String authorName, String isAvailable) {
        RedBlackNode book = new RedBlackNode(bookId, bookName, authorName, isAvailable.equals("Yes"));
        transferMap();
        if (root == nil) {
            hm1.put(bookId, Color.BLACK);
        } else {
            hm1.put(bookId, Color.RED);
        }
        insert(book);
        populateHm2();
        colorFlipCount();
    }

// logic to calculate flip count
    public void colorFlipCount() {
        for (Map.Entry<Integer, Color> entry : hm1.entrySet()) {
            if (entry.getValue() != hm2.get(entry.getKey())) {
                this.flipCount++;
            }
        }
    }
    public void getColorFlipCount() {
        resultString.append("Color Flip Count : " + this.flipCount + "\n");
    }


//helper method
    public void populateHm2() {
        inorderTraversal(root);
    }

    public void inorderTraversal(RedBlackNode root) {
        if (root == nil) {
            return;
        }
        inorderTraversal(root.left);
        hm2.put(root.bookId, root.color);
        inorderTraversal(root.right);
    }

    public void transferMap() {
        hm1.clear();
        hm1 = new HashMap<>(hm2);
        hm2.clear();
    }

    public void insert(RedBlackNode book) {

        RedBlackNode tempRoot = root;
        if (root == nil) {
            root = book;
            book.color = Color.BLACK;
            book.parent = nil;
        } else {
            book.color = Color.RED;
            while (true) {
                if (book.bookId < tempRoot.bookId) {
                    if (tempRoot.left == nil) {
                        tempRoot.left = book;
                        book.parent = tempRoot;
                        break;
                    } else {
                        tempRoot = tempRoot.left;
                    }
                } else if (book.bookId == tempRoot.bookId) {
                    return;
                } else {
                    if (tempRoot.right == nil) {
                        tempRoot.right = book;
                        book.parent = tempRoot;
                        break;
                    } else {
                        tempRoot = tempRoot.right;
                    }
                }
            }
            fixInsertViolation(book);
        }
    }

// fixing the tree after insertion to satisfy the red-black tree properties
    public void fixInsertViolation(RedBlackNode book) {
        while (book.parent.color == Color.RED) {
            RedBlackNode uncle = nil;
            if (book.parent == book.parent.parent.left) {
                uncle = book.parent.parent.right;

                if (uncle != nil && uncle.color == Color.RED) {
                    book.parent.color = Color.BLACK;
                    if (book.parent.parent.color != Color.RED && book.parent.parent != root) {
                        book.parent.parent.color = Color.RED;
                    }
                    uncle.color = Color.BLACK;
                    book = book.parent.parent;
                    continue;
                }
                if (book == book.parent.right) {
                    book = book.parent;
                    rotateLeft(book);
                }
                book.parent.color = Color.BLACK;
                book.parent.parent.color = Color.RED;
                rotateRight(book.parent.parent);
            } else {
                uncle = book.parent.parent.left;
                if (uncle != nil && uncle.color == Color.RED) {
                    book.parent.color = Color.BLACK;
                    book.parent.parent.color = Color.RED;
                    uncle.color = Color.BLACK;
                    book = book.parent.parent;
                    continue;
                }
                if (book == book.parent.left) {
                    book = book.parent;
                    rotateRight(book);
                }
                book.parent.color = Color.BLACK;
                book.parent.parent.color = Color.RED;
                rotateLeft(book.parent.parent);
            }
        }
        root.color = Color.BLACK;
    }

// Rotate Left operation on the red balck tree
    public void rotateLeft(RedBlackNode book) {
        if (book.parent != nil) {
            if (book == book.parent.left) {
                book.parent.left = book.right;
            } else {
                book.parent.right = book.right;
            }
            book.right.parent = book.parent;
            book.parent = book.right;
            if (book.right.left != nil) {
                book.right.left.parent = book;
            }
            book.right = book.right.left;
            book.parent.left = book;
        } else {
            RedBlackNode right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

// Rotate right operation on the red black tree
    public void rotateRight(RedBlackNode book) {
        if (book.parent != nil) {
            if (book == book.parent.left) {
                book.parent.left = book.left;
            } else {
                book.parent.right = book.left;
            }

            book.left.parent = book.parent;
            book.parent = book.left;
            if (book.left.right != nil) {
                book.left.right.parent = book;
            }
            book.left = book.left.right;
            book.parent.right = book;
        } else {
            RedBlackNode left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    public RedBlackNode findBook(int bookId) {
        RedBlackNode temp = root;
        if (root.bookId == -1)
            return null;
        while (true) {
            if (bookId < temp.bookId) {
                if (temp.left == nil) {
                    return null;
                } else {
                    temp = temp.left;
                }
            } else if (bookId == temp.bookId) {
                return temp;
            } else {
                if (temp.right == nil) {
                    return null;
                } else {
                    temp = temp.right;
                }
            }
        }
    }

// deletion of the book
    public void deleteBook(int bookId) {
        RedBlackNode book = findBook(bookId);
        if (book == null) {
            resultString.append("RedBlackNode " + bookId + " is no longer available.\n");
            return;
        }
        transferMap();
        hm1.remove(bookId);
        RedBlackNode y = book;
        Color y_original_color = y.color;
        RedBlackNode x;
        if (book.left == nil) {
            x = book.right;
            transplant(book, book.right);
        } else if (book.right == nil) {
            x = book.left;
            transplant(book, book.left);
        } else {
            y = treeMaximum(book.left);
            y_original_color = y.color;
            x = y.left;
            if (y.parent == book)
                x.parent = y;
            else {
                transplant(y, y.left);
                y.left = book.left;
                y.left.parent = y;
            }
            transplant(book, y);
            y.right = book.right;
            y.right.parent = y;
            y.color = book.color;
        }
        if (y_original_color == Color.BLACK) {
            fixDeleteViolation(x);
        }
        populateHm2();
        colorFlipCount();
        if (book.minHeap.isEmpty()) {
            resultString.append("RedBlackNode " + bookId + " is no longer available.\n");
        } else {
            resultString.append("RedBlackNode " + bookId + " is no longer available. Reservations made by Patrons "
                    + book.minHeap + " have been cancelled!\n");
        }
    }

    private void transplant(RedBlackNode u, RedBlackNode v) {
        if (u.parent == nil) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else
            u.parent.right = v;
        v.parent = u.parent;
    }

    private RedBlackNode treeMinimum(RedBlackNode z) {
        while (z.left != nil) {
            z = z.left;
        }
        return z;
    }

    private RedBlackNode treeMaximum(RedBlackNode z) {
        while (z.right != nil) {
            z = z.right;
        }
        return z;
    }

// fixing the tree after deletion
    public void fixDeleteViolation(RedBlackNode x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                RedBlackNode w = x.parent.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                    continue;
                } else if (w.right.color == Color.BLACK) {
                    w.left.color = Color.BLACK;
                    w.color = Color.RED;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if (w.right.color == Color.RED) {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                RedBlackNode w = x.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                    continue;
                } else if (w.left.color == Color.BLACK) {
                    w.right.color = Color.BLACK;
                    w.color = Color.RED;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if (w.left.color == Color.RED) {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

// printing books which are between the given two bookID's
    public void printBooks(int bookId1, int bookId2) {
        List<RedBlackNode> listOfBooks = new ArrayList<>();
        inorder(this.root, bookId1, bookId2, listOfBooks, true);
        for (RedBlackNode book : listOfBooks)
            resultString.append(book + "\n");
    }

    public void findClosestBook(int targetId) {
        int minDiff = Integer.MAX_VALUE;
        List<RedBlackNode> listOfBooks = new ArrayList<>();
        inorder(this.root, -1, -1, listOfBooks, false);
        List<RedBlackNode> res = new ArrayList<>();
        for (RedBlackNode book : listOfBooks) {
            int diff = Math.abs(targetId - book.bookId);
            if (minDiff > diff) {
                minDiff = diff;
                res = new ArrayList<>();
                res.add(book);
            } else if (minDiff == diff)
                res.add(book);
        }
        Collections.sort(res, (x, y) -> {
            return x.bookId - y.bookId;
        });
        for (RedBlackNode book : res)
            resultString.append(book + "\n");
    }

    public void inorder(RedBlackNode book, int lower, int upper, List<RedBlackNode> listOfBooks, boolean flag) {
        if (book == nil)
            return;
        inorder(book.left, lower, upper, listOfBooks, flag);
        if (flag) {
            if (book.bookId >= lower && book.bookId <= upper)
                listOfBooks.add(book);
        } else {
            listOfBooks.add(book);
        }
        inorder(book.right, lower, upper, listOfBooks, flag);
    }

//This method will help you find out if the book is available or if it is borrowed
    public void borrowBook(int patronId, int bookId, int patronPriority) {
        RedBlackNode book = findBook(bookId);
        if (book == null)
            return;
        if (book.isAvailable) {
            book.borrowedBy = patronId;
            book.isAvailable = false;
            resultString.append("Book " + bookId + " Borrowed by Patron " + patronId + "\n");
        } else if (alreadyReservedByPatron(patronId, book)) {
            resultString.append("Book " + bookId + " Already Reserved by Patron " + patronId + "\n");
        } else {
            resultString.append("Book " + bookId + " Reserved by Patron " + patronId + "\n");
            book.minHeap.insertNode(new ReservationNode(patronId, patronPriority, new Date()));
        }
    }

    public boolean alreadyReservedByPatron(int patronId, RedBlackNode book) {
        for (ReservationNode reservation : book.minHeap.heap) {
            if (reservation != null && reservation.getPatronId() == patronId) {
                return true;
            }
        }
        return false;
    }
// method to modify the red black tree when a book is returned
    public void returnBook(int patronId, int bookId) {
        RedBlackNode book = findBook(bookId);
        if (book == null)
            return;
        if (book.borrowedBy != patronId)
            return;
        if (book.isAvailable)
            return;
        book.borrowedBy = -1;
        book.isAvailable = true;
        resultString.append("Book " + bookId + " Returned by Patron " + patronId + "\n");
        if (!book.minHeap.isEmpty()) {
            ReservationNode latestReservation = book.minHeap.poll();
            if (latestReservation.getPatronId() == -1)
                return;
            book.borrowedBy = latestReservation.getPatronId();
            book.isAvailable = false;
            resultString.append("Book " + bookId + " Allotted to Patron " + latestReservation.getPatronId() + "\n");
        }
    }

    public void quit() {
        resultString.append("Program Terminated!!\n");
        this.root = null;
    }

}