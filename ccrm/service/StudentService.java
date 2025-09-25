package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.util.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // === CSV Import/Export ===
    public void loadStudentsFromCsv(String path) throws IOException {
        List<Student> imported = CsvUtils.importStudents(path);
        for (Student s : imported) {
            addStudent(s);
        }
    }

    public void saveStudentsToCsv(String path) throws IOException {
        CsvUtils.exportStudents(path, students);
    }
}
