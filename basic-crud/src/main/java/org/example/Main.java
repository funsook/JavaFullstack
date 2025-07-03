package org.example;

import org.example.dao.StudentDao;
import org.example.model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDao studentDAO = new StudentDao();

    public static void main(String[] args) {
        System.out.println("--- Simple Student Management (CRUD) ---");
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = getUserInputInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 0:
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Student By ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("0. Exit");
    }

    private static int getUserInputInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void addStudent() {
        scanner.nextLine(); // consume leftover newline
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dobStr = scanner.nextLine();

        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(dobStr);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use YYYY-MM-DD. Student not added.");
            return;
        }

        Student student = new Student(firstName, lastName, dateOfBirth);
        studentDAO.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void viewAllStudents() {
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private static void viewStudentById() {
        System.out.print("Enter student ID: ");
        int id = getUserInputInt();
        Student student = studentDAO.getStudentById(id);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n --- Update Student ---");
        System.out.print("Enter student ID to update: ");
        int id = getUserInputInt();
        Student existingStudent = studentDAO.getStudentById(id);

        if (existingStudent == null) {
            System.out.println("Student not found.");
            return;
        }

        scanner.nextLine(); // consume leftover newline
        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter new date of birth (YYYY-MM-DD): ");
        String dobStr = scanner.nextLine();

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use YYYY-MM-DD. Update canceled.");
            return;
        }

        existingStudent.setFirstName(firstName);
        existingStudent.setLastName(lastName);
        existingStudent.setDateOfBirth(dob);

        studentDAO.updateStudent(existingStudent);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        int id = getUserInputInt();
        studentDAO.deleteStudent(id);
        System.out.println("Student deleted successfully (if existed).");
    }
}
