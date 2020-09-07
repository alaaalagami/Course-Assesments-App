package classes.customrowtypes;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
// import javafx.beans.value.*;
import javafx.collections.*;
public class StrRowWithBool extends StrRow{
    private ObservableList<SimpleStringProperty> strs;
    private SimpleBooleanProperty check;

    public StrRowWithBool() {
        // Init an empty observable array list.
        strs = FXCollections.observableArrayList();
        this.check = new SimpleBooleanProperty();
        this.check.set(false);
    }

    public StrRowWithBool(String[] d, Boolean check) {
        // Init an empty observable array list.
        strs = FXCollections.observableArrayList();
        this.check = new SimpleBooleanProperty();
        // loop over string Array
        for (String strTemp : d){
            //Create string property and add it to our observableList
            strs.add(new SimpleStringProperty(strTemp));            
        }
        this.check.set(check.booleanValue());
    }
    // Getter at index i
    public SimpleStringProperty getStr(int i){
        return strs.get(i);
    }
    public SimpleBooleanProperty getBool(){
        return check;
    }
    public void setBool(Boolean newCheck){
        this.check.set(newCheck.booleanValue());
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
}