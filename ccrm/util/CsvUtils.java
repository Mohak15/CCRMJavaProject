package edu.ccrm.util;

import edu.ccrm.domain.*;

import java.io.*;
import java.util.*;
import java.util.UUID;

public class CsvUtils {

    // --- Students ---
    public static List<Student> importStudents(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                String id = parts[0];
                String first = parts[1];
                String last = parts[2];
                String email = parts[3];
                String regNo = parts[4];
                students.add(new Student(id, new Name(first, last), email, regNo));
            }
        }
        return students;
    }

    public static void exportStudents(String filePath, List<Student> students) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("id,first,last,email,regNo");
            for (Student s : students) {
                pw.printf("%s,%s,%s,%s,%s%n",
                        s.getId(),
                        s.getFullName().getFirst(),
                        s.getFullName().getLast(),
                        s.getEmail(),
                        s.getRegNo());
            }
        }
    }

    // --- Courses ---
    public static List<Course> importCourses(String filePath) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String code = parts[0];
                String title = parts[1];
                int credits = Integer.parseInt(parts[2]);
                Name instructorName = new Name(parts[3], parts[4]);
                
                
                Instructor instructor = new Instructor(
                        UUID.randomUUID().toString(),
                        instructorName,
                        "",
                        ""
                );
                Semester semester = Semester.valueOf(parts[5].toUpperCase());
                String department = parts.length > 6 ? parts[6] : "UNKNOWN";

                courses.add(new Course.Builder()
                        .code(code)
                        .title(title)
                        .credits(credits)
                        .instructor(instructor)
                        .semester(semester)
                        .department(department)
                        .build());
            }
        }
        return courses;
    }

    public static void exportCourses(String filePath, List<Course> courses) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("code,title,credits,instructorFirst,instructorLast,semester,department");
            for (Course c : courses) {
                pw.printf("%s,%s,%d,%s,%s,%s,%s%n",
                        c.getCode(),
                        c.getTitle(),
                        c.getCredits(),
                        c.getInstructor().getFullName().getFirst(),
                        c.getInstructor().getFullName().getLast(),
                        c.getSemester(),
                        c.getDepartment());
            }
        }
    }
}
