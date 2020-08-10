package classes.customrowtypes;
import javafx.beans.property.SimpleStringProperty;
// import javafx.beans.value.*;
import javafx.collections.*;
public class StrRow{
    private ObservableList<SimpleStringProperty> strs;
    

    public StrRow() {
        // Init an empty observable array list.
        strs = FXCollections.observableArrayList();
    }
    
    public StrRow(String d){
        strs = FXCollections.observableArrayList();
        strs.add(new SimpleStringProperty(d));
    }

    public StrRow(String[] d) {
        // Init an empty observable array list.
        strs = FXCollections.observableArrayList();
        // loop over string Array
        for (String strTemp : d){
            //Create string property and add it to our observableList
            strs.add(new SimpleStringProperty(strTemp));            
        }
    }
    // Getter at index i
    public SimpleStringProperty getStr(int i){
        return strs.get(i);
    }
    // Add another column(entry) to the row with empty string
    public void addCol(){
        strs.add(new SimpleStringProperty(""));
    }
    // Add another column(entry) to the row with empty newColStr
    public void addCol(String newColStr){
        strs.add(new SimpleStringProperty(newColStr));
    }
    // Setter at index i
    public void setStr(int i, String aStr) {
        this.strs.get(i).set(aStr);
    }
    public void rmCol(int index)
    {
        strs.remove(index);
    }
}