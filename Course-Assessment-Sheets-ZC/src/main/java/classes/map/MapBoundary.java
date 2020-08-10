package classes.map;
import classes.customrowtypes.StrRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.converter.DefaultStringConverter;
import classes.*;
import classes.definitions.*;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;


/**
 * MapsBoundry
 */
public class MapBoundary  {

    public static void populateCloSoMapSoOnlyTable(TableView<StrRow> CloSoMapSoOnlyTable, SO sOs)
    {   CloSoMapSoOnlyTable.getItems().clear();
        CloSoMapSoOnlyTable.getColumns().clear();

        int nOfSOs = sOs.getDefList().size();
        List<Boolean> linked = sOs.getLinked();
        int nOfCheckedSos = 0;
        for( int i = 0; i<nOfSOs; i++)
        {   if(linked.get(i) == true)
            {nOfCheckedSos = nOfCheckedSos +1;}
        }
        List<String> sOsList = sOs.getDefList();
        String[] colNames = new String[nOfCheckedSos+1];
        colNames[0] = "";
        int index =1; 
        for( int i = 0 ; i < nOfSOs; i++)
        {   if(linked.get(i) == true)
            {colNames[index] = "SO "+ Integer.toString(i+1);
          index++;
    }
        }
        Utils.addColumnsModified(CloSoMapSoOnlyTable, Arrays.asList(colNames), "");
        //adjusting columns width
        int nOfSections = nOfCheckedSos + 3;
        for (int i = 0; i < nOfCheckedSos+1 ; i ++)
        {
            if(i == 0 )
            {
                CloSoMapSoOnlyTable.getColumns().get(i).prefWidthProperty().bind(CloSoMapSoOnlyTable.widthProperty().multiply(3.0/nOfSections));
            }
            else
            {
                CloSoMapSoOnlyTable.getColumns().get(i).prefWidthProperty().bind(CloSoMapSoOnlyTable.widthProperty().multiply(1.0/nOfSections));
            }
        }
        //populateRows 
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        String[] firstRowFillers = new String[nOfCheckedSos+1];
        String[] secondRowFillers = new String[nOfCheckedSos+1];
        String[] sOsRow = new String[nOfCheckedSos+1];
        firstRowFillers[0] = "Links to SOs";
        secondRowFillers[0] = "Program target links for SOs";
        sOsRow[0] = "";
        int index1 =0;
        for(int i = 0; i<nOfSOs; i++)
        {   if(linked.get(i) == true){
            firstRowFillers[index1+1] = "";
            secondRowFillers[index1+1] = "X";
            sOsRow[index1+1] = sOsList.get(i);
            index1++;
        }
        }
        entries.add(new StrRow(firstRowFillers));
        entries.add(new StrRow(secondRowFillers));
        entries.add(new StrRow(sOsRow));

        CloSoMapSoOnlyTable.getItems().setAll(entries);

    }
    @SuppressWarnings("unchecked")  
    public static void populateCloSoMapTable(TableView<StrRow> CloSoMapTable,SO sOs, CLO clos )
    {   CloSoMapTable.getItems().clear();
        CloSoMapTable.getColumns().clear();

        //adding basic columns
        String[] names = {"Clos",""};
        Utils.addColumnsModified(CloSoMapTable, Arrays.asList(names) , " ");

        //adding columns
        int nOfSos = sOs.getDefList().size();
        List<Boolean> linked = sOs.getLinked();
        int nOfcheckedSos = 0;
        for(int i = 0; i<nOfSos; i++)
        {
            if(linked.get(i) == true)
            {
                nOfcheckedSos++;
            }
        }
        String[] SoColumnsNames = new String[nOfcheckedSos]; 
        String[] rowFillers = new String[nOfcheckedSos];
        int index = 0;  
        for(int i = 0 ; i < nOfSos; i++)
        {   if(linked.get(i) == true)
            { SoColumnsNames[index] = "SO " + Integer.toString(i + 1);
               rowFillers[index] = "";
            index++;
            }
         }
        Utils.addColumnsModified(CloSoMapTable,Arrays.asList(SoColumnsNames) , " ");
        //adjusting columns width 
        //review this
        int nOfSections = nOfcheckedSos + 3;
        int closWidth = (int)Math.floor(((double)nOfSections)/2);        
        for (int i = 0; i < nOfcheckedSos+2 ; i ++)
        {
            if(i == 0 )
            {
                CloSoMapTable.getColumns().get(i).prefWidthProperty().bind(CloSoMapTable.widthProperty().multiply(2.0/nOfSections));
            }
            else
            {
                CloSoMapTable.getColumns().get(i).prefWidthProperty().bind(CloSoMapTable.widthProperty().multiply(1.0/nOfSections));
            }
        }
        //making columns editable 
       CloSoMapTable.setEditable(true);
        for (int i = 0 ; i<2; i++)
        {
            CloSoMapTable.getColumns().get(i).setEditable(false);
        }
        for (int i = 2; i<nOfcheckedSos+2; i++)
        {   CloSoMapTable.getColumns().get(i).setEditable(true);
            Utils.makeTableColumnEdittableTBox((TableColumn<StrRow,String>)CloSoMapTable.getColumns().get(i),i);
        }

        //adding rows
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        int nOfClos = clos.getDefList().size();
        List<String> closList = clos.getDefList();
        for(int i = 0 ; i< nOfClos ; i++)
        {  
            String[] s ={closList.get(i)};
            String[] cloNum = {"CLO " + Integer.toString(i+1)};
            String[] closFillers = ArrayUtils.addAll(s,cloNum);

           entries.add(new StrRow(ArrayUtils.addAll(closFillers,rowFillers) ));
    
        }
        CloSoMapTable.getItems().setAll(entries);

        //populate with combobox
        ObservableList<String> levels = FXCollections.observableArrayList();
        levels.addAll("L","M","H","");
        for(int i = 0; i< nOfcheckedSos;i++)
        {
            ((TableColumn<StrRow,String>)CloSoMapTable.getColumns().get(i+2)).setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),levels));
        }


    }
    public static void populateTopCloMapCloOnlyTable(TableView<StrRow> topCloMapTable,TableView<StrRow> topCloMapCloOnlyTable,CLO clos, Topic topics)
    {
    ///populating CLO ONLY 
    topCloMapCloOnlyTable.getItems().clear();
    topCloMapCloOnlyTable.getColumns().clear();
    //adding basic columns
    String[] titles = {"","","Time"};
    Utils.addColumnsModified(topCloMapCloOnlyTable,Arrays.asList(titles) , " ");
    // adding clo columns
    int nOfCLOs = clos.getDefList().size();
    int nOfTopics = topics.getDefList().size();
    String[] cloColumnsNames = new String[nOfCLOs]; 
    String[] CLOList = clos.getDefList().stream().toArray(String[]::new);
    for(int i = 0 ; i < nOfCLOs; i++)
    {
        cloColumnsNames[i] = "CLO" + Integer.toString(i + 1);
    }
    Utils.addColumnsModified(topCloMapCloOnlyTable,Arrays.asList(cloColumnsNames) , " ");

    int sumWeeks = 0;
    for(int i = 0; i < nOfTopics; i++ )
    {
    sumWeeks = sumWeeks + Integer.valueOf(String.valueOf(topCloMapTable.getColumns().get(2).getCellObservableValue(i).getValue()));
    System.out.println("sha8al");
    }
    double placeHolder = 0.0;
    ObservableList<StrRow> entries2 = FXCollections.observableArrayList();

    //adding time row
    String[] timeRowFiller = new String[nOfCLOs];
    for (int i = 0 ; i< nOfCLOs;i++ )
    {
    timeRowFiller[i] = Double.toString(placeHolder);
    }
    String[] timeRow = {"","",Integer.toString(sumWeeks)+" Weeks"};
    
    entries2.add(new StrRow(ArrayUtils.addAll(timeRow,timeRowFiller)));
    //adding the clos definition
    String[] s2 = {"","",""};
    entries2.add(new StrRow(ArrayUtils.addAll(s2,CLOList)));
    System.out.println("ana hena");
    topCloMapCloOnlyTable.getItems().setAll(entries2);
    //adjusting columns width 
        int nOfSections = nOfCLOs + 4;
        System.out.println("nOfSections"+ String.valueOf(nOfSections));
        int topicsWidth = (int)Math.round(((double)nOfSections)/2);
        System.out.println("topics width"+ String.valueOf(topicsWidth));
        int weeksWidth = (int)Math.ceil((double)nOfSections*(4-((double)nOfSections/topicsWidth))/(double)2);
        System.out.println("weeksWidth"+ String.valueOf(weeksWidth));
        int topicsIndexWidth = (int)Math.floor((double)nOfSections*(4-((double)nOfSections/topicsWidth))/(double)2);
        System.out.println("topicsIndexWidth"+ String.valueOf(topicsIndexWidth));
        //from https://stackoverflow.com/questions/10152828/javafx-2-automatic-column-width
        for (int index = 0; index < nOfCLOs+3 ; index ++)
        {
            if(index == 0 )
            {
                topCloMapCloOnlyTable.getColumns().get(index).prefWidthProperty().bind(topCloMapCloOnlyTable.widthProperty().divide(topicsWidth));
            }
            else if(index== 1 )
            {
                topCloMapCloOnlyTable.getColumns().get(index).prefWidthProperty().bind(topCloMapCloOnlyTable.widthProperty().divide(topicsIndexWidth));

            }
            else if ( index == 2)
            {
                topCloMapCloOnlyTable.getColumns().get(index).prefWidthProperty().bind(topCloMapCloOnlyTable.widthProperty().divide(weeksWidth));
            }
            else 
            {
                topCloMapCloOnlyTable.getColumns().get(index).prefWidthProperty().bind(topCloMapCloOnlyTable.widthProperty().divide(nOfSections));
            }
        }
    }
    @SuppressWarnings("unchecked")  
    public static void populateTopCloMapTable(TableView<StrRow> topCloMapTable,CLO clos, Topic topics )
    {   topCloMapTable.getItems().clear();
        topCloMapTable.getColumns().clear();

        //adding basic columns
        String[] names = {"topics","","weeks"};
        Utils.addColumnsModified(topCloMapTable,Arrays.asList( names) , " ");

        //adding columns
        int nOfCLOs = clos.getDefList().size();
        System.out.println(nOfCLOs);
        String[] cloColumnsNames = new String[nOfCLOs]; 
        String[] rowFillers = new String[nOfCLOs+1];  
        rowFillers[0] = Integer.toString(3);
        String[] CLOList = clos.getDefList().stream().toArray(String[]::new);
        for(int i = 0 ; i < nOfCLOs; i++)
        {
            cloColumnsNames[i] = "CLO" + Integer.toString(i + 1);
            rowFillers[i+1] = "";
        }
        Utils.addColumnsModified(topCloMapTable, Arrays.asList(cloColumnsNames) , " ");
        //adjusting columns width 
        int nOfSections = nOfCLOs + 4;
        //System.out.println("nOfSections"+ String.valueOf(nOfSections));
        int topicsWidth = (int)Math.round(((double)nOfSections)/2);
        //System.out.println("topics width"+ String.valueOf(topicsWidth));
        int weeksWidth = (int)Math.ceil((double)nOfSections*(4-((double)nOfSections/topicsWidth))/(double)2);
       // System.out.println("weeksWidth"+ String.valueOf(weeksWidth));
        int topicsIndexWidth = (int)Math.floor((double)nOfSections*(4-((double)nOfSections/topicsWidth))/(double)2);
        //System.out.println("topicsIndexWidth"+ String.valueOf(topicsIndexWidth));

        for (int index = 0; index < nOfCLOs+3 ; index ++)
        {
            if(index == 0 )
            {
                topCloMapTable.getColumns().get(index).prefWidthProperty().bind(topCloMapTable.widthProperty().divide(topicsWidth));
            }
            else if(index== 1 )
            {
                topCloMapTable.getColumns().get(index).prefWidthProperty().bind(topCloMapTable.widthProperty().divide(topicsIndexWidth));

            }
            else if ( index == 2)
            {
                topCloMapTable.getColumns().get(index).prefWidthProperty().bind(topCloMapTable.widthProperty().divide(weeksWidth));
            }
            else 
            {
                topCloMapTable.getColumns().get(index).prefWidthProperty().bind(topCloMapTable.widthProperty().divide(nOfSections));
            }
        }
        //making columns editable 
        topCloMapTable.setEditable(true);
        for (int i = 0 ; i<2; i++)
        {
            topCloMapTable.getColumns().get(i).setEditable(false);
        }
        for (int i = 2; i<nOfCLOs+3; i++)
        {   topCloMapTable.getColumns().get(i).setEditable(true);
            Utils.makeTableColumnEdittableTBox((TableColumn<StrRow,String>)topCloMapTable.getColumns().get(i),i);
        }
        //adding row 
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        int nOfTopics = topics.getDefList().size();
        List<String> topicsList = topics.getDefList();
        for (int i =0; i< nOfTopics; i++)
        {
            String[] s = new String[]{topicsList.get(i)};
            String[] topNum = new String[]{"TP "+ Integer.toString(i+1)};
            String[] topicsFillers = ArrayUtils.addAll(s, topNum);
            entries.add(new StrRow(ArrayUtils.addAll(topicsFillers,rowFillers) ));
        }
        topCloMapTable.getItems().setAll(entries);

        //adding rows
        /*ObservableList<StrRow> entries = FXCollections.observableArrayList();
        int nOfTopics = topics.getDefList().size();
        List<String> topicsList = topics.getDefList();
        System.out.println("row gen");
        for(int i = 0 ; i< nOfTopics ; i++)
        {  
           String[] s ={topicsList.get(i)};
            String[] topNum = {"TP " + Integer.toString(i+1)};
            String[] topicsFillers = ArrayUtils.addAll(s,topNum);

           entries.add(new StrRow(ArrayUtils.addAll(topicsFillers,rowFillers) ));
            
        }
        System.out.println("row gen suc");
        topCloMapTable.getItems().setAll(entries);

*/
    }
    public static void populateMethodsTable(TableView<StrRow> methodsTable, CLO clos)
{
    methodsTable.getItems().clear();
    methodsTable.getColumns().clear();
    //adding columns
    String[] basicCols = new String[]{"CLOs","","Lecture","Projects","Problem Sessions / Clinics","Lab"};
    Utils.addColumnsModified(methodsTable,Arrays.asList(basicCols) , " ");
    //making columns editable 
    methodsTable.setEditable(true);
    for (int i = 0 ; i<2; i++)
    {
        methodsTable.getColumns().get(i).setEditable(false);
    }
    for (int i = 2; i<6; i++)
    {   methodsTable.getColumns().get(i).setEditable(true);
        Utils.makeTableColumnEdittableTBox((TableColumn<StrRow,String>)methodsTable.getColumns().get(i),i);
    }

    //populating rows
    ObservableList<StrRow> entries = FXCollections.observableArrayList();
        int nOfClos = clos.getDefList().size();
        List<String> closList = clos.getDefList();
        String[] rowFillers = new String[]{"","","",""};
        for(int i = 0 ; i< nOfClos ; i++)
        {  
            String[] s ={closList.get(i)};
            String[] cloNum = {"CLO " + Integer.toString(i+1)};
            String[] closFillers = ArrayUtils.addAll(s,cloNum);

           entries.add(new StrRow(ArrayUtils.addAll(closFillers,rowFillers) ));
    
        }
        methodsTable.getItems().setAll(entries);
    
}
}
