import java.io.*;

public class GatorLibrary {
    public static final String COMMA = ",";
    public static final String OPEN_PARENTHESIS = "(";
    public static final String CLOSED_PARENTHESIS = ")";

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            // Open the input file for reading
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
            RedBlackTree rbTree = new RedBlackTree();
            String row;

            // Read each line from the input file and process the GatorLibrary operations
            while ((row = bufferedReader.readLine()) != null) {
                parse(rbTree, row, fileName.substring(0, fileName.indexOf(".")));
            }
            Output(fileName); // Create an output file after processing all operations
        } catch (Exception e) {
            e.printStackTrace(); // Print any exception trace if encountered
        }
    }

    // Parse each line and perform respective operations based on the GatorLibrary command
public static void parse(RedBlackTree rbTree, String row, String fileName) throws IOException 
{
        row = row.replaceAll("\"", "");
        int start = row.indexOf(OPEN_PARENTHESIS);
        int end = row.indexOf(CLOSED_PARENTHESIS);
        String[] argArray = row.substring(start + 1, end).split(COMMA);
        String operation = row.substring(0, start);
        // Perform operations based on the parsed command
        if (operation.equals("InsertBook")) {
    // Insert a new book into the Red-Black Tree
    rbTree.insertBook(Integer.parseInt(argArray[0].trim()), argArray[1].trim(), argArray[2].trim(), argArray[3].trim());
} 
        else if (operation.equals("PrintBook")) {
    // Print details of a specific book or indicate if the book is not found
    RedBlackTree.RedBlackNode book = rbTree.findBook(Integer.parseInt(argArray[0].trim()));
    if (book == null) {
        RedBlackTree.resultString.append("Book " + Integer.parseInt(argArray[0].trim()) + " not found in the library\n");
    } else {
        RedBlackTree.resultString.append(book + "\n");
    }
} 
        else if (operation.equals("PrintBooks")) {
    // Print a range of books based on specified book IDs
    rbTree.printBooks(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()));
} 
        else if (operation.equals("BorrowBook")) {
    // Borrow a book by a patron, specifying book ID, patron ID, and days to borrow
        rbTree.borrowBook(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()),
            Integer.parseInt(argArray[2].trim()));
} 
        else if (operation.equals("ReturnBook")) {
    // Return a borrowed book by specifying book ID and patron ID
    rbTree.returnBook(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()));
} 
        else if (operation.equals("DeleteBook")) {
    // Delete a book from the library using its ID
    rbTree.deleteBook(Integer.parseInt(argArray[0].trim()));
} 
        else if (operation.equals("FindClosestBook")) {
    // Find the closest book to a specified ID in the library
    rbTree.findClosestBook(Integer.parseInt(argArray[0].trim()));
} 
        else if (operation.equals("ColorFlipCount")) {
    // Get the count of color flips performed in the Red-Black Tree
    rbTree.getColorFlipCount();
} 
        else if (operation.equals("Quit")) {
    // Quit the program, create an output file, and terminate
    rbTree.quit();
    Output(fileName);
    System.exit(0);
} 
        else {
    // If the parsed command is invalid, indicate an invalid operation
    RedBlackTree.resultString.append("Invalid GatorLibrary operation\n");
            }
}

    // Create an output file with the resultString content after all operations are performed
    public static void Output(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName + "_output_file.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(RedBlackTree.resultString));
        bufferedWriter.close();
        fileWriter.close();
    }
}



