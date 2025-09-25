package edu.ccrm;

import edu.ccrm.cli.Menu;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();

        Menu menu = new Menu(studentService, courseService);
        menu.show();
    }
}
