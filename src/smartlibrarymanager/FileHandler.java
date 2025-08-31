package smartlibrarymanager;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    
    private static final String USERS_FILE = "users.txt";
    private static final String BOOKS_FILE = "books.txt";
    private static final String MEMBERS_FILE = "members.txt";
    private static final String BORROWING_FILE = "borrowing.txt";
    
    // Date formatter for parsing and formatting dates
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    // Load all users from file
    public static List<User> loadUsers() {
        List<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    userList.add(new User(
                        Integer.parseInt(parts[0]),
                        parts[1], 
                        parts[2],
                        parts[3],
                        parts[4]
                    ));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's ok
            System.out.println("Users file not found. A new one will be created when needed.");
        }
        return userList;
    }
    
    // Save all users to file
    public static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.println(user.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    // Load all books from file
    public static List<Book> loadBooks() {
        List<Book> bookList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    bookList.add(new Book(
                        Integer.parseInt(parts[0]),
                        parts[1], 
                        parts[2],
                        parts[3]
                    ));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's ok
            System.out.println("Books file not found. A new one will be created when needed.");
        }
        return bookList;
    }
    
    // Save all books to file
    public static void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }
    
    // Load all members from file
    public static List<Member> loadMembers() {
        List<Member> memberList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    memberList.add(new Member(
                        Integer.parseInt(parts[0]),
                        parts[1], 
                        parts[2]
                    ));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's ok
            System.out.println("Members file not found. A new one will be created when needed.");
        }
        return memberList;
    }
    
    // Save all members to file
    public static void saveMembers(List<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                writer.println(member.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving members: " + e.getMessage());
        }
    }
    
    // Load all borrowings from file
    public static List<Borrowing> loadBorrowings() {
        List<Borrowing> borrowingList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BORROWING_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    LocalDate borrowDate = LocalDate.parse(parts[3], DATE_FORMATTER);
                    LocalDate returnDate = parts[4].equals("null") ? null : LocalDate.parse(parts[4], DATE_FORMATTER);
                    
                    borrowingList.add(new Borrowing(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        borrowDate,
                        returnDate
                    ));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's ok
            System.out.println("Borrowings file not found. A new one will be created when needed.");
        }
        return borrowingList;
    }
    
    // Save all borrowings to file
    public static void saveBorrowings(List<Borrowing> borrowings) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BORROWING_FILE))) {
            for (Borrowing borrowing : borrowings) {
                writer.println(borrowing.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving borrowings: " + e.getMessage());
        }
    }
    
    // Initialize default data if files don't exist
    public static void initializeDefaultData() {
        // Check if users file exists, if not create default admin
        if (!new File(USERS_FILE).exists()) {
            List<User> defaultUsers = new ArrayList<>();
            String encryptedPassword = encryptPassword("admin123");
            defaultUsers.add(new User(1, "Admin", "User", "admin@library.com", encryptedPassword));
            saveUsers(defaultUsers);
        }
        
        // Check if books file exists, if not create some sample books
        if (!new File(BOOKS_FILE).exists()) {
            List<Book> defaultBooks = new ArrayList<>();
            defaultBooks.add(new Book(1, "To Kill a Mockingbird", "Harper Lee", "Available"));
            defaultBooks.add(new Book(2, "1984", "George Orwell", "Available"));
            defaultBooks.add(new Book(3, "The Great Gatsby", "F. Scott Fitzgerald", "Borrowed"));
            defaultBooks.add(new Book(4, "Pride and Prejudice", "Jane Austen", "Available"));
            defaultBooks.add(new Book(5, "The Catcher in the Rye", "J.D. Salinger", "Borrowed"));
            saveBooks(defaultBooks);
        }
        
        // Check if members file exists, if not create some sample members
        if (!new File(MEMBERS_FILE).exists()) {
            List<Member> defaultMembers = new ArrayList<>();
            defaultMembers.add(new Member(1, "Sarah Johnson", "sarah.johnson@email.com"));
            defaultMembers.add(new Member(2, "Emily Rodriguez", "emily.rodriguez@email.com"));
            defaultMembers.add(new Member(3, "David Thompson", "david.thompson@email.com"));
            defaultMembers.add(new Member(4, "Lisa Wong", "lisa.wong@mail.com"));
            defaultMembers.add(new Member(5, "James Wilson", "james.wilson@email.com"));
            saveMembers(defaultMembers);
        }
        
        // Check if borrowings file exists, if not create empty list
        if (!new File(BORROWING_FILE).exists()) {
            saveBorrowings(new ArrayList<>());
        }
    }
    
    // MD5 encryption method
    public static String encryptPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Password encryption failed: " + e.getMessage());
            return null;
        }
    }
}