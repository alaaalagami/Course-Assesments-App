package classes.map;

import java.util.List;

import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import classes.Utils;
import classes.customrowtypes.StrRow;
import classes.definitions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;

/**
 * TopicCloMapController
 */
public class MapController {

    public MapController()
    {}
    public TopicCLOMap initTopCloMap(){
        TopicCLOMap new_topCloMap = new TopicCLOMap();
        return new_topCloMap;
    }
    public CLOSOMap initCloSoMap(){
        CLOSOMap new_cloSoMap = new CLOSOMap();
        return new_cloSoMap;
    }
    public CLOInstMap initMethodsMap(){
        CLOInstMap new_methodsMap = new CLOInstMap();
        return new_methodsMap;
    }
    public boolean validateTableDataIsNumeric(TableView<StrRow> table, int nToBevalidatedColumns)
    {
        int nOfRows = table.getItems().size();
        int nOfColumns = table.getColumns().size();
        int startIndex = nOfColumns - nToBevalidatedColumns;
        for(int index = startIndex; index < nOfColumns; index++ )
        {
            for(int row = 0; row< nOfRows; row++)
            { String s = String.valueOf(table.getColumns().get(index).getCellObservableValue(row).getValue()); 
                if(!Utils.isNumber(s) && s != "")
                {   System.out.println("not valid input");
                    return false;
                }
            }
        }
        System.out.println("valid inputs");
        return true;
        
    }
    public void saveTopCloMapData(TableView<StrRow> table, Topic topics, CLO clos, TopicCLOMap map)
    {   
        int nOfClos = clos.getDefList().size();
        int nOfTopics = topics.getDefList().size();
        String[] topicsList = topics.getDefList().stream().toArray(String[]::new);
        String[] closList = clos.getDefList().stream().toArray(String[]::new);
        //////////
        System.out.print("ana gowa save top clo");
        Double[] weeksWeights = new Double[nOfTopics];
        String[][] mapString = new String[nOfTopics][nOfClos];
        Double[][] mapDouble = new Double[nOfTopics][nOfClos];

        //choose to save the topic and clo lists to the map or not

        //saving weeks weigths
        for(int i = 0; i < nOfTopics; i++)
        { //need to validate that none of the strings are null
            weeksWeights[i] = Double.valueOf(String.valueOf(table.getColumns().get(2).getCellObservableValue(i).getValue()));
        }
        ////// saving weights 
        for (int row = 0; row < nOfTopics; row++)
        {
            for(int col = 0 ; col < nOfClos; col++)
            {
                mapString[row][col] = String.valueOf(table.getColumns().get(col+3).getCellObservableValue(row).getValue());
                if(mapString[row][col] == "" || mapString[row][col] == null )
                {mapDouble[row][col] = 0.0;}
                else
                {mapDouble[row][col] = Double.valueOf(String.valueOf(table.getColumns().get(col+3).getCellObservableValue(row).getValue()));
                }
            }
        }
        System.out.println("ana 3mlt save");
        Double totalNOfWeeks = 0.0;
        for( int index = 0; index < nOfTopics;index++)
        {
            totalNOfWeeks = totalNOfWeeks + weeksWeights[index];
        }
        //set values at map objact
        map.setMapDouble(mapDouble);
        map.setMapString(mapString);
        map.setNOfweeksPerTopic(weeksWeights);
        map.setTotalnOfweeks(totalNOfWeeks);
        System.out.println(mapDouble);
        System.out.println(mapString);
        System.out.println(weeksWeights);
        

    }
    public void updateClosTimeWeights(TableView<StrRow> topCloMapCloOnlyTable, TopicCLOMap map, CLO clos, Topic topics )
    {
        int nOfClos = clos.getDefList().size();
        int nOfTopics = topics.getDefList().size();
       // Double[] nOfweeksPerTopic = map.getNOfWeeksPerClo();
        Double nOfweeks = map.getTotalnOfweeks();
        Double[] nOfWeeksPerClo = new Double[nOfClos];
        Double[][] mapDouble = map.getMapDouble();
        double sumOfWeights = 0;
        for (int col = 0; col< nOfClos;col++)
        {
            for(int row = 0; row < nOfTopics; row++)
            {
                sumOfWeights = sumOfWeights + mapDouble[row][col].doubleValue();
            }
        } 
        for (int col = 0; col<nOfClos;col++)
        {   double cloWeightsSum = 0;
            for (int row = 0; row<nOfTopics;row++)
            {
                cloWeightsSum = cloWeightsSum + mapDouble[row][col].doubleValue();
            }
            nOfWeeksPerClo[col] = nOfweeks*cloWeightsSum/sumOfWeights;
        }
        //topCloMapCloOnlyTable.getSelectionModel().setcell
        // UPDATING TABLE
        String[] nOFweeksPerCloString = new String[nOfClos];
        for (int index = 0; index < nOfClos; index++)
        { 
            nOFweeksPerCloString[index] = Double.toString(Math.round(nOfWeeksPerClo[index].doubleValue()*100.0)/(double)100);
        }
        String[] prefix = new String[]{"","",Double.toString(nOfweeks)+" Weeks"};
        StrRow updatedTimeRow = new StrRow(ArrayUtils.addAll(prefix,nOFweeksPerCloString));
        topCloMapCloOnlyTable.getItems().set(0, updatedTimeRow);
         //setting in map 
         map.setNOfWeeksPerClo(nOfWeeksPerClo); 
    }
    public void saveCloSoMapData(TableView<StrRow> CloSoMapTable,CLOSOMap map, SO sOs, CLO clos)
    {
        
        int nOfClos = clos.getDefList().size();
        int nOfSos = sOs.getDefList().size();
        List<Boolean> linked = sOs.getLinked();
        int nOfCheckedSos = 0;
        for( int i = 0; i<nOfSos; i++)
        {   if(linked.get(i) == true)
            {nOfCheckedSos = nOfCheckedSos +1;}
        }

        String[][] mapString = new String[nOfClos][nOfCheckedSos];
        Double[][] mapDouble = new Double[nOfClos][nOfCheckedSos];
        
        for (int row = 0; row <nOfClos; row++)
        {
            for(int col = 0; col< nOfCheckedSos;col++)
            {
              mapString[row][col] = (String)CloSoMapTable.getColumns().get(col+2).getCellObservableValue(row).getValue();
              if(mapString[row][col]== "")
              {
                  mapDouble[row][col] = 0.0;
              }
              else if(mapString[row][col]== "L")
              {
                  mapDouble[row][col] = 1.0;
              }
              if(mapString[row][col]== "M")
              {
                  mapDouble[row][col] = 2.0;
              }
              if(mapString[row][col]== "H")
              {
                  mapDouble[row][col] = 3.0;
              }
            System.out.println(mapDouble[row][col].toString());
             System.out.println(mapString[row][col]); 
            }
        
        }
        map.setMapDouble(mapDouble);
        map.setIsDataReady(true);
    }
    public void saveMethods(TableView<StrRow> methodsTable, CLOInstMap map, CLO clos)
    {
        int nOfRows = clos.getDefList().size();
        int nOfColumns = methodsTable.getColumns().size() -2;
        Double[][] mapDouble = new Double[nOfRows][nOfColumns];
        String[][] mapString = new String[nOfRows][nOfColumns];
        String[] methodsList = new String[nOfColumns];
        for(int col = 0; col< nOfColumns;col++)
        {   methodsList[col] = methodsTable.getColumns().get(col+2).getText();
            System.out.println(methodsList[col]);

            for(int row = 0; row < nOfRows; row++)
            {
                mapString[row][col] = (String)methodsTable.getColumns().get(col+2).getCellObservableValue(row).getValue();
                if(mapString[row][col]=="")
                {
                    mapDouble[row][col]=0.0;
                }
                else{
                    mapDouble[row][col] = Double.valueOf(mapString[row][col]);
                }
            }
        }
    }
    public void updateTopicsTabChart(BarChart<String, Double> topicsTabChart,TopicCLOMap map,CLO clos,CategoryAxis topicsChartX)
    {
        XYChart.Series<String,Double> set = new XYChart.Series<String, Double>();
        Double[] cloWeights = map.getNOfWeeksPerClo();
        double totalNOfWeeks = map.getTotalnOfweeks().doubleValue();
        ObservableList<String> x = FXCollections.observableArrayList();
        //loop over all the items in the Exit survey table
        for (int i = 0; i < clos.getDefList().size(); i++){
            set.getData().add(new XYChart.Data<String,Double>("CLO"+Integer.toString(i+1),cloWeights[i]*100.0/totalNOfWeeks));
            x.add("CLO"+Integer.toString(i+1));
        };
        topicsChartX.setCategories(x);
        topicsTabChart.getData().setAll(set);
    }
    public Optional<String> validateSosTabData(TableView<StrRow> CloSoMapTable)
    {
        Optional<String> result = Optional.empty();
        int nOfColumns = CloSoMapTable.getColumns().size() - 2;
        int nOfRows = CloSoMapTable.getItems().size();
        boolean errorFlag = false;
        for( int index = 0; index < nOfColumns; index ++)
        { boolean colLinkedFlag = false;
            for(int row = 0; row < nOfRows; row++)
            {
                if((String)CloSoMapTable.getColumns().get(index+2).getCellObservableValue(row).getValue() != "")
                {
                    colLinkedFlag = true;
                }
            }
            if(colLinkedFlag == false)
            {
                errorFlag = true;
            }
        }
        if( errorFlag == true)
        {
            result = Optional.of("ERROR: one or more SOs have no linked CLOs");
            return result;
        }
        else
        {
            return result;
        }

    }
    public void updateCloSoMapSoOnlyTable(TableView<StrRow> CloSoMapSoOnlyTable, TableView<StrRow> CloSoMapTable)
    {
        int nOfColumns = CloSoMapTable.getColumns().size() - 2;
        int nOfRows = CloSoMapTable.getItems().size();
        String[] newLinkedRow = new String[nOfColumns + 1];
        newLinkedRow[0] = "Links to SOs";
        for( int index = 0; index < nOfColumns; index ++)
        { boolean colLinkedFlag = false;
            for(int row = 0; row < nOfRows; row++)
            {
                if((String)CloSoMapTable.getColumns().get(index+2).getCellObservableValue(row).getValue() != "")
                {
                    colLinkedFlag = true;
                }
            }
            if(colLinkedFlag == false)
            { 
                newLinkedRow[index+1] = "";
            }
            else if(colLinkedFlag == true)
            {
                newLinkedRow[index+1] = "X";
            }
        }
        CloSoMapSoOnlyTable.getItems().set(0, new StrRow(newLinkedRow));
    }
}