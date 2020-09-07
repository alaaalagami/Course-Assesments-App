package classes;

import java.util.List;
import java.util.regex.Pattern;

import classes.customrowtypes.StrRow;
import classes.grades.Grades;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;


public class Utils{

    public String directConc(Integer passingCriteria, String value01, String value02){
        
        //Convert string to integer
        Integer val01 = Integer.parseInt(value01);
        Integer val02 = Integer.parseInt(value02);

        if ((val01 >= passingCriteria) && (val02 >= passingCriteria)){
            return "Satisfactory";

        }
        else if (((val01 < passingCriteria) && (val02 < passingCriteria))){
            return "Unsatisfactory";
        }
        return "Needs action";
    } 

    public String indirectConc(Integer passingCriteria, String value){
        
        //Convert string to integer
        Integer val = Integer.parseInt(value);

        if (val >= passingCriteria){
            return "Satisfactory";
        }
        else if (val < passingCriteria){
            return "Unsatisfactory";
        }
        return "Needs action";
    }  


    public static boolean isValidEmailAddress(String emailWannabe){
        // source https://www.geeksforgeeks.org/check-email-address-valid-not-java/
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
        "[a-zA-Z0-9_+&*-]+)*@" + 
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
        "A-Z]{2,7}$"; 
                
        Pattern pat = Pattern.compile(emailRegex); 
        if (emailWannabe == null) 
            return false; 
        return pat.matcher(emailWannabe).matches(); 
    }

    // public static void initTableColumnStrRow(TableColumn<StrRow, String> tCol, int colNum){
    //     tCol.setCellValueFactory(p -> {return p.getValue().getStr(colNum);});
    // }
    public static <S> void makeTableColumnEdittableTBox(TableColumn<S, String> tCol, int colNum) {
        //Set the second column to be editable as a TextField and to update our 
        // observable list
        tCol.setCellFactory(TextFieldTableCell.forTableColumn());
        // Define behavior on editting (inform about setter)
        tCol.setOnEditCommit(t -> {((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())
                                    ).setStr(colNum, t.getNewValue());
                                    // all we need to focus on is `.setStr(1, t.getNewValue())`
                                });
    }
    public static <S> void makeTableColumnEdittableTBox(TableColumn<S, String> tCol, int colNum,
                             boolean clearOnEdit) {
        //Set the second column to be editable as a TextField and to update our
        // observable list
        tCol.setCellFactory(TextFieldTableCell.forTableColumn());
        // Define behavior on editting (inform about setter)
        if (clearOnEdit){
            tCol.setOnEditStart(t ->
            {((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setStr(colNum, "");
            });

        }
        tCol.setOnEditCommit(t -> {((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())
                                    ).setStr(colNum, t.getNewValue());
                                    // all we need to focus on is `.setStr(1, t.getNewValue())`
                                });
    }

    public static void addColumns(TableView<StrRow> table, List<String> columnNames, String filler){
        int initialNumberColumns = table.getColumns().size();
        for (int i = 0; i< columnNames.size(); i++){
            var columnName  = columnNames.get(i);
            TableColumn<StrRow, String> newCol = new TableColumn<>(columnName);
            int index = i + initialNumberColumns;
            table.getColumns().add(newCol);
            table.getItems().forEach(t -> t.addCol(filler));
            newCol.setCellFactory(TextFieldTableCell.forTableColumn());
            newCol.setOnEditCommit(t -> {
                ((StrRow) t.getTableView().getItems().get( t.getTablePosition().getRow() )).setStr(0, t.getNewValue());
            });
        }
    }
      
    public static void addColumnsModified(TableView<StrRow> table, List<String> columnNames, String filler){
        int initialNumberColumns = table.getColumns().size();
        for (int i = 0; i< columnNames.size(); i++){
            var columnName  = columnNames.get(i);
            TableColumn<StrRow, String> newCol = new TableColumn<>(columnName);
            int index = i + initialNumberColumns;
            newCol.setCellValueFactory(p  ->{return p.getValue().getStr(index);});
            table.getItems().forEach(t -> t.addCol(filler));
            newCol.setCellFactory(TextFieldTableCell.forTableColumn());
            newCol.setOnEditCommit(t -> {
                ((StrRow) t.getTableView().getItems().get( t.getTablePosition().getRow() )).setStr(index, t.getNewValue());
            });
            table.getColumns().add(newCol);

        }
    }
    public  static void addRows(TableView<StrRow> table, Grades gradesEnt)
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

        String[] strArray1 = new String[numberOfCategories]; //declare with size
        for (int k = 0;k < numberOfCategories;k++)
        {
            strArray1[k] = "0";
        }

        for (int j = 0; j<numOfSubElements; j++)
        {
            StrRow newGradeTool = new StrRow( strArray1);
            table.getItems().add(newGradeTool);
        }

    }
    @SuppressWarnings("unchecked") 
    public static void removeColumns(TableView<StrRow> table, int index){
        int initialNumberColumns = table.getColumns().size();
        if (index >= initialNumberColumns)
            return;
        for (int i = index + 1 ; i < initialNumberColumns; i++){
            int new_index = i-1;
            System.out.println(new_index);
            ((TableColumn<StrRow, String>) table.getColumns().get(i)).setCellValueFactory(
            p -> {return  p.getValue().getStr(new_index);});
            ((TableColumn<StrRow, String>) table.getColumns().get(i)).setOnEditCommit(t -> {
                ((StrRow) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setStr(new_index, t.getNewValue());
            });
        }
        table.getItems().forEach(p -> p.rmCol(index));
        table.getColumns().remove(index);
        
    }
    /// taken from https://stackoverflow.com/questions/26793035/how-to-check-if-a-string-is-a-valid-int-or-double
    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }
}