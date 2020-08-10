package classes;

import classes.customrowtypes.StrRow;
import classes.customrowtypes.StrRowWithBool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InitialValues {
    public static String[] getPrograms(){
        String[] programs = {"Communications and Information Engineering Program",
                            "Aerospace Engineering Program",
                            "Energy and Bioprocess Engineering Program",
                            "Environmental Engineering Program",
                            "Nanotechnology Engineering Program",
                            "Renewable Energy Engineering Program",
                            "Biomedical Sciences Program",
                            "Nano Science Program",
                            "Materials Science Program",
                            "Physics of Earth and Universe Program",
                            "Math",
                            "General Education",
                            "Foundation"};
        return programs;
    }

    public static ObservableList<StrRow> getInitialMandatoryList(){
        //Since any TableView has an inderlying ObservableList with type
        //of a class that we make. We Informed it about the setters and getters already        
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        // Create Array of Arrays each of Two strings
        String[][] data = {{"Course Code", "<CLICK ME TO EDIT>"},
                            {"Course Title", "<CLICK ME TO EDIT>"},
                            {"Instructor Name", "<CLICK ME TO EDIT>"},
                            {"Instructor Email", "<CLICK ME TO EDIT>"},
                            {"TA Name", "<CLICK ME TO EDIT>"},
                            {"TA Email", "<CLICK ME TO EDIT>"}};
        //loop of array and construct a row which is the classs StrRow that we made
        // and add it to the entries ObservableList
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }
    //This method will populate the grades table
    public static ObservableList<StrRow> getInitialGrades() {
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"Homework", "HW","5", "5", "1"}};
        for (String[] strArrTemp : data) {
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }



    // populate the toolsClo table
    public static ObservableList<StrRow> getInitialToolsClos() {
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"1.0"}};
        for (String[] strArrTemp : data) {
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }


    public static ObservableList<StrRow> getInitialOptionalList(){
        // Same Behavior as getInitialMandatoryList
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = { {"Description", "<CLICK ME TO EDIT>"},    
                            {"Prerequisites", "<CLICK ME TO EDIT>"},
                            {"Related Topics", "<CLICK ME TO EDIT>"},
                            {"Instructor Office", "<CLICK ME TO EDIT>"},
                            {"TA Office", "<CLICK ME TO EDIT>"},
                            {"Discussion Group", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }
    public static ObservableList<StrRow> getInitialTPList(){
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"Tp 0", "<CLICK ME TO EDIT>"},
                            {"Tp 1", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }
    public static ObservableList<StrRow> getInitialCLOList(){
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"CLO 1", "<CLICK ME TO EDIT>"},
                            {"CLO 2", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }

    public static ObservableList<StrRowWithBool> getInitialSOList(){
        ObservableList<StrRowWithBool> entries = FXCollections.observableArrayList();
        String[][] data = {{"SO 1", "<CLICK ME TO EDIT>"},
                            {"SO 2", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRowWithBool(strArrTemp, Boolean.valueOf(false)));
        }
        return entries;
    }

    public static ObservableList<StrRow> getInitialScheduleList(){
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"Schedule Line 1", "<CLICK ME TO EDIT>"},
                            {"Schedule Line 2", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }


    public static ObservableList<StrRow> getInitialTextbookList(){
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"Textbook 1", "<CLICK ME TO EDIT>"},
                            {"Textbook 2", "<CLICK ME TO EDIT>"},
                            {"Textbook 3", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }

    public static ObservableList<StrRow> getInitialReferencesList(){
            ObservableList<StrRow> entries = FXCollections.observableArrayList();
            String[][] data = {{"References 1", "<CLICK ME TO EDIT>"},
                                {"References 2", "<CLICK ME TO EDIT>"}};
            for (String[] strArrTemp : data){
                entries.add(new StrRow(strArrTemp));
            }
            return entries;
        }

        public static ObservableList<StrRow> getInitialsurvList(){
            ObservableList<StrRow> entries = FXCollections.observableArrayList();  
            String[][] data = {{"CLO 1", "0"},
                                {"CLO 2", "0"}};
            for (String[] strArrTemp : data){
                entries.add(new StrRow(strArrTemp));
            }
            return entries;
        }

    public static ObservableList<StrRow> getInitialCActionsList(){
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"CLO 1", "0", "0", "Unknown",
                                    "0", "Unknown",
                                    "<CLICK ME TO EDIT>", "<CLICK ME TO EDIT>"},
                            {"CLO 2", "0", "0", "Unknown",
                                    "0", "Unknown",
                                    "<CLICK ME TO EDIT>", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }

    public static ObservableList<StrRow> getInitialSActionsList(){
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[][] data = {{"SO 1", "0", "0", "Unknown",
                                    "0", "Unknown",
                                    "<CLICK ME TO EDIT>", "<CLICK ME TO EDIT>"},
                            {"SO 2","0", "0", "Unknown",
                                    "0", "Unknown",
                                    "<CLICK ME TO EDIT>", "<CLICK ME TO EDIT>"}};
        for (String[] strArrTemp : data){
            entries.add(new StrRow(strArrTemp));
        }
        return entries;
    }
}
 
