package classes.students;
import java.util.Optional;

public class StudentsController{
    //constructor
    public StudentsController(){

    }

    // The boundary class will call the controller class to create 
    //the entity object
    public Students initStudent(){
        Students new_SInfo = new Students();
        return new_SInfo;
    }
    
    public Optional<String> validateUpdate(){
        return Optional.of("Awesome");
    }

}