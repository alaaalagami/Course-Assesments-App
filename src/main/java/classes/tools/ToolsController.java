package classes.tools;

import java.util.Optional;

import classes.Utils;
import classes.customrowtypes.StrRow;
import javafx.scene.control.TableView;


public class ToolsController {
    //constructor
    public ToolsController(){

    }

    public Tools initTools(){
        Tools new_TInfo = new Tools();
        return new_TInfo;
    }
    
    public Optional<String> validateUpdate(){
        return Optional.of("Awesome");
    }
    public void saveTables(TableView<StrRow> ToolsElementsTableView, TableView<StrRow> ToolsCLOsTableView, Tools tools)
    {
        //saving Elements table
        int nColsE = ToolsElementsTableView.getColumns().size()-1;
        int nRowsE = ToolsElementsTableView.getItems().size();
        String[][] eStrings = new String[nRowsE][nColsE];
        Double[][] eDoubles = new Double[nRowsE][nColsE];
        for( int row = 0; row<nRowsE ; row++)
        {
            for(int col = 0; col <nColsE; col++)
            {
                eStrings[row][col] = (String)ToolsElementsTableView.getColumns().get(col+1).getCellObservableValue(row).getValue();
                if(eStrings[row][col]=="")
                {
                    eDoubles[row][col]=0.0;
                }
                else{
                    eDoubles[row][col] = Double.valueOf(eStrings[row][col]);
                }
            }
        }
        int nColsC = ToolsCLOsTableView.getColumns().size();
        int nRowsC = ToolsCLOsTableView.getItems().size();
        String[][] cStrings = new String[nRowsC][nColsC];
        Double[][] cDoubles = new Double[nRowsC][nColsC];
        for( int row = 0; row<nRowsC; row++)
        {
            for(int col = 0; col <nColsC; col++)
            {
                cStrings[row][col] = (String)ToolsCLOsTableView.getColumns().get(col).getCellObservableValue(row).getValue();
                if(cStrings[row][col]=="")
                {
                    cDoubles[row][col]=0.0;
                }
                else{
                    cDoubles[row][col] = Double.valueOf(cStrings[row][col]);
                }
            }
        }
        tools.setClosDouble(cDoubles);
        tools.setClosString(cStrings);
        tools.setElementsDouble(eDoubles);
        tools.setElementsString(eStrings);
        tools.setIsDataReady(true);
    }
    public int validateElementsTable(TableView<StrRow> table)
    {
        int nOfRows = table.getItems().size();
        int nOfColumns = table.getColumns().size()-1;
        int nOfElementsPerRow;
        for( int row = 0; row <nOfRows; row ++ )
        {   nOfElementsPerRow =0;
            for(int col = 0; col < nOfColumns; col++)
            { String s = String.valueOf(table.getColumns().get(col+1).getCellObservableValue(row).getValue());
                if(!s.equals(""))
                {
                    nOfElementsPerRow++;
                }
            }
            if(nOfElementsPerRow != 1)
            {
                return row;
            }
          
        }
        return -1;  
    }
    public int validateClosTable(TableView<StrRow> table)
    {   
        //return positive index+1 for row problem 
        //return negative index +1 for col problem
        //return 0 for valid
        int nOfRows = table.getItems().size();
        int nOfColumns = table.getColumns().size();
        //checking colmuns
        boolean breakFlag;
        for(int col = 0; col < nOfColumns; col++)
        {   breakFlag = false;
            for( int row = 0; row <nOfRows; row ++ )
            {
                String s = String.valueOf(table.getColumns().get(col).getCellObservableValue(row).getValue());
                if(!s.equals(""))
                {
                    breakFlag = true;
                    break;
                }
            }
            if(!breakFlag)
            {
                return -(col+1);
            }
        }

        //cheking rows 
        int nOfElementsPerRow;
        for( int row = 0; row <nOfRows; row ++ )
        {   nOfElementsPerRow =0;
            for(int col = 0; col < nOfColumns; col++)
            { String s = String.valueOf(table.getColumns().get(col).getCellObservableValue(row).getValue());
                if(!s.equals(""))
                {
                    nOfElementsPerRow++;
                }
            }
            if(nOfElementsPerRow != 1)
            {
                return row+1;
            }
          
        }
        return 0;
    }
    public boolean validateTableDataIsNumeric(TableView<StrRow> table, int nToBevalidatedColumns)
    {
        int nOfRows = table.getItems().size();
        int nOfColumns = table.getColumns().size();
        int startIndex = nOfColumns - nToBevalidatedColumns;
        for(int index = startIndex; index < nOfColumns; index++ )
        {
            for(int row = 0; row< nOfRows; row++)
            {  //if(table.getColumns().get(index).getCellObservableValue(row).getValue().getClass() != null)
               //{ //String s = String.valueOf(table.getColumns().get(index).getCellObservableValue(row).getValue()); 
                String s = table.getItems().get(row).getStr(index).get();
                if(!Utils.isNumber(s) && s != "")
                {   System.out.println("not valid input");
                    return false;
                }
            }
            
        }
        System.out.println("valid inputs");
        return true;
        
    }
}