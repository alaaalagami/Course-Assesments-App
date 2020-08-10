package classes.students;
import java.util.*;

public class Students {

  private List<String> grades = new ArrayList<String>();

  private Integer studentID;

  //empty constructor
  public Students(){

  }

  //constructors with args grades and student ID
  public Students(List<String> grades, Integer studentID){
    this.grades = grades;
    this.studentID = studentID;
  }
  
  public List<String> getgrades() {
    return grades;
  }

  public void setGrades(List<String> grades) {
    this.grades = grades;
  }

  public Integer getStudentID() {
    return studentID;
  }

  public void setStudentID(Integer studentID) {
    this.studentID = studentID;
  }
}