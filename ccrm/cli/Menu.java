package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.util.CsvUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final StudentService studentService;
    private final CourseService courseService;
    private final Scanner scanner;

    public Menu(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Add Course");
            System.out.println("4. List Courses");
            System.out.println("5. Import Students from CSV");
            System.out.println("6. Export Students to CSV");
            System.out.println("7. Import Courses from CSV");
            System.out.println("8. Export Courses to CSV");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1": addStudent(); break;
                    case "2": listStudents(); break;
                    case "3": addCourse(); break;
                    case "4": listCourses(); break;
                    case "5": importStudents(); break;
                    case "6": exportStudents(); break;
                    case "7": importCourses(); break;
                    case "8": exportCourses(); break;
                    case "0": return;
                    default:  System.out.println("Invalid choice."); break;
                }
            } catch (IOException e) {
                System.out.println("I/O error: " + e.getMessage());
            }
        }
    }

    // --- Student Methods ---
    private void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter First Name: ");
        String first = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String last = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine();

        Student student = new Student(id, new Name(first, last), email, regNo);
        studentService.addStudent(student);
        System.out.println("Student added.");
    }

    private void listStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    // --- Course Methods ---
    private void addCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Instructor First Name: ");
        String instFirst = scanner.nextLine();
        System.out.print("Enter Instructor Last Name: ");
        String instLast = scanner.nextLine();
        Name instructorName = new Name(instFirst, instLast);
        Instructor instructor = new Instructor(
                UUID.randomUUID().toString(),
                instructorName,
                "",
                ""
        );
        System.out.print("Enter Semester (SPRING/SUMMER/FALL/WINTER): ");
        Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();

        Course course = new Course.Builder()
                .code(code)
                .title(title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(dept)
                .build();

        courseService.addCourse(course);
        System.out.println("Course added.");
    }

    private void listCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (Course c : courses) {
                System.out.println(c);
            }
        }
    }

    // --- CSV Import/Export ---
    private void importStudents() throws IOException {
        System.out.print("CSV file path: ");
        String path = scanner.nextLine();
        List<Student> imported = CsvUtils.importStudents(path);
        for (Student s : imported) {
            studentService.addStudent(s);
        }
        System.out.println("Imported " + imported.size() + " students.");
    }

    private void exportStudents() throws IOException {
        System.out.print("CSV file path to save: ");
        String path = scanner.nextLine();
        CsvUtils.exportStudents(path, studentService.getAllStudents());
        System.out.println("Students exported.");
    }

    private void importCourses() throws IOException {
        System.out.print("CSV file path: ");
        String path = scanner.nextLine();
        List<Course> imported = CsvUtils.importCourses(path);
        for (Course c : imported) {
            courseService.addCourse(c);
        }
        System.out.println("Imported " + imported.size() + " courses.");
    }

    private void exportCourses() throws IOException {
        System.out.print("CSV file path to save: ");
        String path = scanner.nextLine();
        CsvUtils.exportCourses(path, courseService.getAllCourses());
        System.out.println("Courses exported.");
    }
}
