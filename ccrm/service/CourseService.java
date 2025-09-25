package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.util.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public void loadCoursesFromCsv(String path) throws IOException {
        List<Course> imported = CsvUtils.importCourses(path);
        courses.addAll(imported);
    }

    public void saveCoursesToCsv(String path) throws IOException {
        CsvUtils.exportCourses(path, courses);
    }
}
