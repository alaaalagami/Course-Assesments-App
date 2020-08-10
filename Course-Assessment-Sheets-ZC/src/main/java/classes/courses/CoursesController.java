package classes.courses;

import java.io.File;
import java.util.Optional;

//import org.apache.commons.io.FileUtils;

public class CoursesController {
    public void remCourse(String coursePath){
        // FileUtils.deleteDirectory(new File(coursePath));
    }

    public Optional<String> addCourse(String path){
    File file = new File(path);
    // Creating the directory
    boolean bool = file.mkdirs();
    if (bool) {
        System.out.println("Directory created successfully" + file.getAbsolutePath());
        return Optional.of(file.getAbsolutePath());
    } else {
        System.out.println("Sorry couldnt create specified directory");
        return Optional.empty();
    }
    }
    public void getCoursePath(){

    }
}