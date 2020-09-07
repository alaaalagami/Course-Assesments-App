package classes.grades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import classes.Utils;
import classes.customrowtypes.StrRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class GradesController {
    //constructor

    public GradesController() {
    }


    public Grades initGrades() {
        Grades new_GInfo = new Grades(Arrays.asList("Homework"),Arrays.asList("HW"),Arrays.asList("5"),Arrays.asList("5")
                ,Arrays.asList("1"),0);
        return new_GInfo;
    }

    public int getTotalGrade(TableView<StrRow> gradeColumn, Grades gradesEnt) {
        int totalGrade = 0;
        for (int i = 0; i < gradeColumn.getItems().size(); i++) {
            totalGrade = totalGrade + Integer.parseInt(String.valueOf(gradeColumn.getColumns().get(2).getCellData(i)));
        }
        gradesEnt.setTotalGrade(totalGrade);
        return totalGrade;
    }

    public boolean validateUpdateInt(String currentValue) {
        try {
            Integer.parseInt(currentValue);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public void reviveGUI(ObservableList<StrRow> gradesTable,
                         Grades gradesEnt){
        gradesTable.clear();
        for (int i=0; i< gradesEnt.getAbbreviation().size(); i++){
            String abbr = gradesEnt.getAbbreviation().get(i);
            String catg = gradesEnt.getCategory().get(i);
            String grade = gradesEnt.getGrade().get(i);
            String elemnt = gradesEnt.getNoOfElements().get(i);
            String subelemnt = gradesEnt.getNoOfSubElements().get(i);
            String[] newRow = {catg, abbr, grade, elemnt, subelemnt};
            gradesTable.add(new StrRow(newRow));
        }
    }

    public boolean validateSubmitGrade(TableView<StrRow> gradeColumn, ObservableList<StrRow> gradesTable,Grades gradesEnt)
    {
        int totalGrade = 0;
        totalGrade = getTotalGrade(gradeColumn,gradesEnt);
        List<String> newCategoryList = new ArrayList<String>();
        List<String> newAbbreviationList = new ArrayList<String>();
        List<String> newGradeList = new ArrayList<String>();
        List<String> newElementsList = new ArrayList<String>();
        List<String> newSubElementsList = new ArrayList<String>();

        if (totalGrade != 100) {
            gradesEnt.setDataReady(false);
            return false;
        }

        else {
            gradesTable.forEach((StrRow row) -> {
                var new_sch = row.getStr(0).get();
                newCategoryList.add(new_sch);
            });
            gradesTable.forEach((StrRow row) -> {
                var new_sch = row.getStr(1).get();
                newAbbreviationList.add(new_sch);
            });

            gradesTable.forEach((StrRow row) -> {
                var new_sch = row.getStr(2).get();
                newGradeList.add(new_sch);
            });
            gradesTable.forEach((StrRow row) -> {
                var new_sch = row.getStr(3).get();
                newElementsList.add(new_sch);
            });
            gradesTable.forEach((StrRow row) -> {
                var new_sch = row.getStr(4).get();
                newSubElementsList.add(new_sch);
            });

            gradesEnt.setCategory(newCategoryList);
            gradesEnt.setTotalGrade(totalGrade);
            gradesEnt.setAbbreviation(newAbbreviationList);
            gradesEnt.setNoOfSubElements(newSubElementsList);
            System.out.println(newGradeList);
            gradesEnt.setGrade(newGradeList);
            gradesEnt.setNoOfElements(newElementsList);
            gradesEnt.setDataReady(true);
            return true;
        }
    }


    public void addRows(TableView<StrRow> table, Grades gradesEnt)
    {
        int numOfSubElements = 0;
        int currentElement = 0;
        int currentSubElement = 0;
        int numberOfCategories = gradesEnt.getCategory().size();
        for (int i = 0; i< gradesEnt.getCategory().size(); i++)
        {
            currentElement = Integer.parseInt(String.valueOf(gradesEnt.getNoOfElements().get(i)));
            currentSubElement = Integer.parseInt(String.valueOf(gradesEnt.getNoOfSubElements().get(i)));
            if (currentSubElement ==0)
            {
                numOfSubElements = numOfSubElements + currentElement;
            }
            else
            {
                numOfSubElements= numOfSubElements + (currentElement*currentSubElement);
            }
        }

        String[] strArray1 = new String[numberOfCategories+1]; //declare with size
        for (int k = 0;k < numberOfCategories+1;k++)
        {
            strArray1[k] = "0";
        }

        for (int j = 0; j<numOfSubElements; j++)
        {
            StrRow newGradeTool = new StrRow( strArray1);
            table.getItems().add(newGradeTool);
        }
    }
    
    public void setAllGradeElementsGUI(TableView<StrRow> table, Grades gradesEnt,String RowOrCol){
        // Row is true and Column is false

        List<String> category =gradesEnt.getCategory();
        List<String> elements = gradesEnt.getNoOfElements();
        List<String> subelements = gradesEnt.getNoOfSubElements();

        ObservableList<StrRow> entries = FXCollections.observableArrayList();

        int numOfSubElements = 0;
        int currentElement = 0;
        int currentSubElement = 0;
        int numberOfCategories = gradesEnt.getCategory().size();
        for (int i = 0; i< numberOfCategories; i++)
        {
            currentElement = Integer.parseInt(String.valueOf(gradesEnt.getNoOfElements().get(i)));
            currentSubElement = Integer.parseInt(String.valueOf(gradesEnt.getNoOfSubElements().get(i)));
            if (currentSubElement ==0)
            {
                numOfSubElements = numOfSubElements + currentElement;
            }
            else
            {
                numOfSubElements= numOfSubElements + (currentElement*currentSubElement);
            }
        }


        String delim = " , ";
        String[][] data = new String[1][numOfSubElements];
        for (int j = 0; j<numberOfCategories;j++)
        {
            currentElement = Integer.parseInt(String.valueOf(gradesEnt.getNoOfElements().get(j))); //5
            currentSubElement = Integer.parseInt(String.valueOf(gradesEnt.getNoOfSubElements().get(j)));
            int counter=0;

            for (int k=0;k<currentElement;k++)
            {
                for(int f=0; f<currentSubElement; f++)
                {
                    List<String> currentCategory = new ArrayList<>();

                    if (RowOrCol=="Row")
                    {   
                        currentCategory.add(String.valueOf(gradesEnt.getCategory().get(j)));
                        currentCategory.add(Integer.toString( k+1));
                        currentCategory.add(Integer.toString( f+1));
                        String currentRow = String.join(delim,currentCategory);
                        table.getItems().get(counter).setStr(0,currentRow);
                    }
                    else if(RowOrCol=="Col")
                    {
                        currentCategory.add(
                            String.valueOf(gradesEnt.getAbbreviation().get(j)) + " , " 
                            + (Integer.toString( k+1)) + " , "
                            + (Integer.toString( f+1)) + "\n" 
                            + (String.valueOf(gradesEnt.getGrade().get(f)))
                            );
                        Utils.addColumns(table, currentCategory, "0");
                    }
                    counter++;
                }
            }
        }
    }
}
