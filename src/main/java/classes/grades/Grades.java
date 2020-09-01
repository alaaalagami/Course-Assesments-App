package classes.grades;

import java.io.Serializable;

// import classes.Utils;
// import classes.customrowtypes.StrRow;
// import javafx.beans.property.SimpleIntegerProperty;
// import javafx.beans.property.SimpleStringProperty;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class Grades implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -2865461035785808153L;
    private List<String> category, grade, noOfElements, noOfSubElements;
    private int totalGrade;
    private List<String> abbreviation = new ArrayList<String>();

    private Boolean dataReady = false;

    public  Grades()
    {

    }
    public Grades( List<String> category, List<String> abbreviation, List<String> grade, List<String> noOfElements,
    List<String> noOfSubElements, int totalGrade)
    {
        this.category = category;
        this.abbreviation = abbreviation;
        this.grade = grade;
        this.noOfElements = noOfElements;
        this.noOfSubElements = noOfSubElements;
        this.totalGrade = totalGrade;
    }

    public List<String> getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(List<String> abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getGrade() {
        return grade;
    }

    public void setGrade(List<String> grade) {
        this.grade = grade;
    }

    public List<String> getNoOfElements() {
        return noOfElements;
    }

    public void setNoOfElements(List<String> noOfElements) {
        this.noOfElements = noOfElements;
    }

    public List<String> getNoOfSubElements() {
        return noOfSubElements;
    }

    public void setNoOfSubElements(List<String> noOfSubElements) {
        this.noOfSubElements = noOfSubElements;
    }

    public int getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(int totalGrade) {
        this.totalGrade = totalGrade;
    }

    public Boolean getDataReady() {
        return dataReady;
    }

    public void setDataReady(Boolean dataReady) {
        this.dataReady = dataReady;
    }

}
