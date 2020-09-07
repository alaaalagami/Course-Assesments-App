package classes.courses;

import java.util.List;

public class Courses {
    private String relativePath;
    private List<String> coursesList;

    public String getRelativePath() {
        return relativePath;
    }

    public List<String> getCourses() {
        return coursesList;
    }

    public void setCoursesList(List<String> coursesList) {
        this.coursesList = coursesList;
    }
}