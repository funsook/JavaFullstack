import java.util.*;

// ===== Book Class =====
class Book {
    String id;
    String title;
    String author;
    boolean isBorrowed;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public void display() {
        System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author + ", Borrowed: " + isBorrowed);
    }
}

// ===== User Class =====
class User {
    String userId;
    String name;
    ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() >= 3) {
            System.out.println("Cannot borrow more than 3 books.");
            return false;
        }
        if (book.isBorrowed) {
            System.out.println("Book is already borrowed.");
            return false;
        }
        borrowedBooks.add(book);
        book.isBorrowed = true;
        System.out.println("Book borrowed successfully.");
        return true;
    }

    public boolean returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            System.out.println("This book was not borrowed by the user.");
            return false;
        }
        borrowedBooks.remove(book);
        book.isBorrowed = false;
        System.out.println("Book returned successfully.");
        return true;
    }

    public void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
        } else {
            for (Book book : borrowedBooks) {
                book.display();
            }
        }
    }
}

// ===== Library Class =====
class Library {
    HashMap<String, Book> books = new HashMap<>();
    HashMap<String, User> users = new HashMap<>();

    // Admin functionalities
    public void addBook(Book book) {
        books.put(book.id, book);
        System.out.println("Book added.");
    }

    public void removeBook(String bookId) {
        if (!books.containsKey(bookId)) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        books.remove(bookId);
        System.out.println("Book removed.");
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book book : books.values()) {
            book.display();
        }
    }

    // User functionalities
    public void registerUser(String userId, String name) {
        if (users.containsKey(userId)) {
            System.out.println("User already registered.");
            return;
        }
        users.put(userId, new User(userId, name));
        System.out.println("User registered.");
    }

    public void borrowBook(String userId, String bookId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not registered.");
            return;
        }

        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        user.borrowBook(book);
    }

    public void returnBook(String userId, String bookId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not registered.");
            return;
        }

        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        user.returnBook(book);
    }

    public void showUserBorrowedBooks(String userId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        user.viewBorrowedBooks();
    }
}

// ===== Main Application Class =====
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Admin - Add Book");
            System.out.println("2. Admin - Remove Book");
            System.out.println("3. Admin - View All Books");
            System.out.println("4. User - Register");
            System.out.println("5. User - Borrow Book");
            System.out.println("6. User - Return Book");
            System.out.println("7. User - View Borrowed Books");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());  // handles newline cleanly
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(id, title, author));
                    break;

                case 2:
                    System.out.print("Enter Book ID to remove: ");
                    String removeId = sc.nextLine();
                    library.removeBook(removeId);
                    break;

                case 3:
                    library.viewBooks();
                    break;

                case 4:
                    System.out.print("Enter User ID: ");
                    String userId = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    library.registerUser(userId, name);
                    break;

                case 5:
                    System.out.print("Enter User ID: ");
                    String uId = sc.nextLine();
                    System.out.print("Enter Book ID to borrow: ");
                    String bId = sc.nextLine();
                    library.borrowBook(uId, bId);
                    break;

                case 6:
                    System.out.print("Enter User ID: ");
                    String uid = sc.nextLine();
                    System.out.print("Enter Book ID to return: ");
                    String bid = sc.nextLine();
                    library.returnBook(uid, bid);
                    break;

                case 7:
                    System.out.print("Enter User ID: ");
                    String uid2 = sc.nextLine();
                    library.showUserBorrowedBooks(uid2);
                    break;

                case 8:
                    System.out.println("Exiting... Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1 to 8.");
            }
        }
    }
}
