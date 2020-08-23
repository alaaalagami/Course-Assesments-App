import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import classes.InitialValues;
import classes.Utils;
import classes.actions.Actions;
import classes.actions.ActionsController;
import classes.actions.CLOactions;
import classes.actions.SOactions;
import classes.courseInfo.CourseInfo;
import classes.courseInfo.CourseInfoController;
import classes.courseInfo.Required;
import classes.customrowtypes.StrRow;
import classes.customrowtypes.StrRowWithBool;
import classes.definitions.CLO;
import classes.definitions.DefinitionsController;
import classes.definitions.SO;
import classes.definitions.Topic;
import classes.grades.Grades;
import classes.grades.GradesController;
import classes.map.CLOInstMap;
import classes.map.CLOSOMap;
import classes.map.MapBoundary;
import classes.map.MapController;
import classes.map.TopicCLOMap;
import classes.survey.Survey;
import classes.survey.SurveyController;
import classes.syllabus.SyllabusController;
import classes.tools.Tools;
import classes.tools.ToolsController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class Boundary implements Initializable{
    CourseInfo cInfo;
    Grades gradesEnt;
    CLO clos;
    SO sOs;
    Topic topics;
    String program = "";
    Integer aYear = 0;
    String semester = "";
    Required required = Required.REQUIRED;
    Integer credit = 3;
    CLOSOMap cloSoMap;
    TopicCLOMap topCloMap;
    CLOInstMap methodsMap;
    Tools tools;
    
    WebEngine webEngine;
    Survey surveyEntity = new Survey();
    
    Actions cActionsEntity = new CLOactions();
    Actions sActionsEntity = new SOactions();

    @Override
    // Called as soon as the fxml is loaded
    public void initialize(URL u, ResourceBundle rb){
        // Initialize gradesEnt
        GradesController grCont = new GradesController();
        gradesEnt = grCont.initGrades();

        // Initialize CourseInfo
        CourseInfoController ciCont = new CourseInfoController();
        cInfo = ciCont.initCourseInfo();
        // Initialize Definitions
        DefinitionsController defCont = new DefinitionsController();
        clos = defCont.initClo();
        sOs = defCont.initSo();
        topics = defCont.initTopic();

        // make the objects (That are children of VBoxes)resize on resizing windows
        VBox.setVgrow(homeTitleVBox, Priority.ALWAYS);
        VBox.setVgrow(homeButtonsVBox, Priority.ALWAYS);
        VBox.setVgrow(courseInfoScrollPane, Priority.ALWAYS);
        VBox.setVgrow(definitionsScrollPane, Priority.ALWAYS);
        VBox.setVgrow(topicsTabVbox, Priority.ALWAYS);
        VBox.setVgrow(browser, Priority.ALWAYS);
        VBox.setVgrow(syllabusBPane, Priority.ALWAYS);
        
        // Define the getting behavior for the mandatory Table of the Entry column
        //Get the string of the first column see StrRow.java
        mandEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        mandValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});

        // Define the getting behavior for the mandatory Table of the Entry column
        mandEntry.setCellValueFactory((CellDataFeatures<StrRow, String> p) ->
        {return p.getValue().getStr(0);}); //Get the string of the first column see StrRow.java
        // Mandatory table, Value column inform getter
        mandValue.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(1);});
        // Initialize mandatory Table with an Observable list
        mantTableView.getItems().setAll(InitialValues.getInitialMandatoryList());
        Utils.makeTableColumnEdittableTBox(mandValue, 1, true);
        mandValue.setCellFactory(TextFieldTableCell.forTableColumn());

        optionalEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        optionalValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});

        optionalTableView.getItems().setAll(InitialValues.getInitialOptionalList());
        //Set the second column to be editable and to update our
        // observable list
        Utils.makeTableColumnEdittableTBox(optionalValue, 1, true);

        referencesEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        referencesValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});
        referencesTableView.getItems().setAll(InitialValues.getInitialReferencesList());
        Utils.makeTableColumnEdittableTBox(referencesValue, 1, true);


        textBookEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        textBookValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});
        textBookTableView.getItems().setAll(InitialValues.getInitialTextbookList());
        Utils.makeTableColumnEdittableTBox(textBookValue, 1, true);

        scheduleEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        scheduleValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});
        scheduleTableView.getItems().setAll(InitialValues.getInitialScheduleList());
        Utils.makeTableColumnEdittableTBox(scheduleValue, 1, true);

        notesEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        notesValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});
        Utils.makeTableColumnEdittableTBox(notesValue, 1, true);

        tpEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        tpValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});
        tpTableView.getItems().setAll(InitialValues.getInitialTPList());
        Utils.makeTableColumnEdittableTBox(tpValue, 1, true);


        cloEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        cloValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});
        cloTableView.getItems().setAll(InitialValues.getInitialCLOList());
        Utils.makeTableColumnEdittableTBox(cloValue, 1, true);

        soEntry.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        soValue.setCellValueFactory(p -> {return p.getValue().getStr(1);});
        soLinked.setCellValueFactory(p -> {return p.getValue().getBool();});
        soTableView.getItems().setAll(InitialValues.getInitialSOList());
        Utils.makeTableColumnEdittableTBox(soValue, 1, true);
        soLinked.setCellFactory(CheckBoxTableCell.forTableColumn(soLinked));

        // Grades tab
        categoryColumn.setCellValueFactory(p -> {return p.getValue().getStr(0);});
        abbreviationColumn.setCellValueFactory(p -> { return p.getValue().getStr(1);});
        gradeColumn.setCellValueFactory(p -> { return p.getValue().getStr(2 ); } );
        noOfElementsColumn.setCellValueFactory(p -> { return p.getValue().getStr(3 ); } );
        noOfSubElementsColumn.setCellValueFactory(p -> { return p.getValue().getStr(4 ); } );
        GradesTableView.setEditable(true);
        GradesTableView.getItems().setAll(InitialValues.getInitialGrades());
        Utils.makeTableColumnEdittableTBox(categoryColumn,0);
        Utils.makeTableColumnEdittableTBox(abbreviationColumn,1);
        Utils.makeTableColumnEdittableTBox(gradeColumn,2);
        Utils.makeTableColumnEdittableTBox(noOfElementsColumn,3);
        Utils.makeTableColumnEdittableTBox(noOfSubElementsColumn,4);

        categoryColumn.setOnEditCommit(t -> {
            ((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStr(0, t.getNewValue());

            if (grCont.validateUpdateInt(t.getNewValue()))
                classes.AlertBox.display("Data Error in Grades Tab", "Please enter a string");
        });

        abbreviationColumn.setOnEditCommit(t -> {
            ((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStr(1, t.getNewValue());
            if (grCont.validateUpdateInt(t.getNewValue()))
                classes.AlertBox.display("Data Error in Grades Tab", "Please enter a string");
        });

        gradeColumn.setOnEditCommit(t -> {
            ((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStr(2, t.getNewValue());
            if (!grCont.validateUpdateInt(t.getNewValue()))
            {
                classes.AlertBox.display("Data Error in Grades Tab", "Please enter a number");
            }
            else {
                var totalGrade= grCont.getTotalGrade(t.getTableView(),gradesEnt);
                totalGradeTextField.setText(Integer.toString(totalGrade)); // add total grade
            }
        });
        noOfElementsColumn.setOnEditCommit(t -> {
            ((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStr(3, t.getNewValue());
            if (!grCont.validateUpdateInt(t.getNewValue()))
                classes.AlertBox.display("Data Error in Grades Tab", "Please enter a number");

        });
        noOfSubElementsColumn.setOnEditCommit(t -> {
            ((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStr(4, t.getNewValue());
            if (!grCont.validateUpdateInt(t.getNewValue()))
                classes.AlertBox.display("Data Error in Grades Tab", "Please enter a number");
        });


        //Basic tab combo boxes
        programCB.getItems().addAll(InitialValues.getPrograms());
        // programCB.setOnAction(e -> program = programCB.getValue());
        programCB.valueProperty().addListener( (observable,  oldValue,  newValue) -> 
            {program  = newValue;});
        semesterCB.getItems().addAll("Fall", "Spring", "Summer");
        // semesterCB.setOnAction(e -> semester = semesterCB.getValue());
        semesterCB.valueProperty().addListener( (observable,  oldValue,  newValue) -> 
            {semester  = newValue;});
        aYearCB.getItems().addAll(IntStream.range(2013, 2101).boxed().map(String::valueOf).collect(Collectors.toList()));
        // aYearCB.setOnAction(e -> aYear = Integer.parseInt(aYearCB.getValue()));
        aYearCB.valueProperty().addListener( (o,  old,  newValue) -> 
            {aYear  = Integer.parseInt(newValue);});
        requiredCB.getItems().addAll("Required", "Elective");
        // requiredCB.setOnAction(e -> required =  (requiredCB.getValue() == "Elective") ? Required.ELECTIVE : Required.REQUIRED);
        requiredCB.valueProperty().addListener( (o,  old,  newValue) -> 
            {required  = (newValue == "Elective") ? Required.ELECTIVE : Required.REQUIRED;});
        creditSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 18, credit));
        creditSpinner.valueProperty().addListener((obs, oldValue, newValue) -> credit = newValue);

        //CLO EXIT SURVEY TAB
        survCLO.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(0);});
        survScore.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(1);});

        CLOExitSurveyTableView.getItems().addAll(InitialValues.getInitialsurvList());
        //CLOExitSurveyTableView.getItems().setAll(FXCollections.observableArrayList());

        // THE EXIT SURVEY TABLE LISTENS TO CHANGES IN THE CLO TABLE
        // IF ANY CHANGE OCCURS IN THEIR NUMBER
        // THE SURVEY TABLE IS UPDATED ACCORDINGLY
        cloTableView.getItems().addListener((Change<? extends StrRow> newValue)-> {
            ObservableList<StrRow> entriesSurv = FXCollections.observableArrayList();
            ObservableList<StrRow> entriesActions = FXCollections.observableArrayList();
            for (int i = 0; i < cloTableView.getItems().size(); i++){
                //get the ith row
                StrRow row = newValue.getList().get(i);
                String score = "0";
                //An error occurs here when it tries to access a row that isnt tehre
                // FIND A SOLUTION
                // once u find it, u r good to go.
                // if(!dataExists.isEmpty()){
                //     score = dataExists.get(i).getStr(1).get();
                //     System.out.println("data not empty." + score);
                // }

                String[] dataSurv = {row.getStr(0).getValue().toString(), score};
                String[] dataActions = {row.getStr(0).getValue().toString(), "<CLICK ME TO EDIT>",
                                            "<CLICK ME TO EDIT>","Unknown","<CLICK ME TO EDIT>","Unknown","<CLICK ME TO EDIT>","<CLICK ME TO EDIT>"};
                //get the ith row, get the string value from 0th col
                entriesSurv.add(new StrRow(dataSurv));
                entriesActions.add(new StrRow(dataActions));
            };
            CLOExitSurveyTableView.getItems().setAll(entriesSurv);
            cActionsTableView.getItems().setAll(entriesActions);}
            );
        CLOExitSurveyTableView.getItems().setAll(InitialValues.getInitialsurvList());
        Utils.makeTableColumnEdittableTBox(survScore, 1);

        //CLO ACTIONS TAB
        cActionsCLOs.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(0);});
        cActionsDirectPassing01.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(1);});
        cActionsDirectPassing02.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(2);});
        cActionsDirectConc.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(3);});
        cActionsIndirectPassing01.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(4);});
        cActionsIndirectConc.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(5);});
        cloPerceivedProblem.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(6);});
        cloActionPlan.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(7);});


        cActionsTableView.getItems().setAll(InitialValues.getInitialCActionsList());
        Utils.makeTableColumnEdittableTBox(cActionsDirectPassing01, 1);
        Utils.makeTableColumnEdittableTBox(cActionsDirectPassing02, 2);
        Utils.makeTableColumnEdittableTBox(cActionsIndirectPassing01, 4);
        Utils.makeTableColumnEdittableTBox(cloPerceivedProblem, 6);
        Utils.makeTableColumnEdittableTBox(cloActionPlan, 7);

        //SO ACTIONS TAB
        sActionsSOs.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(0);});
        sActionsDirectPassing01.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(1);});
        sActionsDirectPassing02.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(2);});
        sActionsDirectConc.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(3);});
        sActionsIndirectPassing01.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(4);});
        sActionsIndirectConc.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(5);});
        soPerceivedProblem.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(6);});
        soActionPlan.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(7);});
        //populate the so actions with an initial list
        sActionsTableView.getItems().setAll(InitialValues.getInitialSActionsList());
        //make columns of so actions tab editable
        Utils.makeTableColumnEdittableTBox(sActionsDirectPassing01, 1);
        Utils.makeTableColumnEdittableTBox(sActionsDirectPassing02, 2);
        Utils.makeTableColumnEdittableTBox(sActionsIndirectPassing01, 4);
        Utils.makeTableColumnEdittableTBox(soPerceivedProblem, 6);
        Utils.makeTableColumnEdittableTBox(soActionPlan, 7);

        // Tools tab
        ToolsElementsColumn.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {return p.getValue().getStr(0);});

        // Students tab
        StudentsGradesColumn.setCellValueFactory((CellDataFeatures<StrRow,String> p) -> {return p.getValue().getStr(0);});
        Utils.makeTableColumnEdittableTBox(StudentsGradesColumn, 0);

        // Syllabus tab
        webEngine = browser.getEngine();
        syllabusTab.selectedProperty().addListener((obs, old, newV) -> {
            if(newV)
            {
                SyllabusController scont = new SyllabusController();
                File htmlpath = scont.generateSyllabus(cInfo, clos, topics, sOs,
                 gradesEnt, System.getProperty("java.io.tmpdir"),
                 getClass().getResourceAsStream("im.png"));
                webEngine.load(htmlpath.toURI().toString());
            }});
       // System.out.println(System.getProperty("user.dir"));
        // System.out.println(System.getProperty("java.io.tmpdir"));
        // System.out.println(getClass().getResource("CASh-SWTeam_htm_92fa2ef7521805e9.png").getPath());
        loadIfPresent();
    }
    
    @FXML
    void toolsSubmitButtonclicked(ActionEvent event) {
        ToolsController cont = new ToolsController();
        tools = cont.initTools();
        if( cont.validateTableDataIsNumeric(ToolsCLOsTableView,ToolsCLOsTableView.getColumns().size()) &&
         cont.validateTableDataIsNumeric(ToolsElementsTableView,ToolsElementsTableView.getColumns().size()-1))
        {
            int eError = cont.validateElementsTable(ToolsElementsTableView);
            if( eError == -1)
            {
                int cError = cont.validateClosTable(ToolsCLOsTableView);
                if(cError == 0)
                {
                    cont.saveTables(ToolsElementsTableView,ToolsCLOsTableView,tools);
                    classes.AlertBox.display("", "Data Added Succesfully!");
                }
                else if( cError <0)
                {
                    classes.AlertBox.display("Data Error", "Clo" + Integer.toString(-cError)+ " is not linked to Any Assesment");

                }
                else if(cError>0)
                {
                    classes.AlertBox.display("Data Error", "SomeData is missing in CLOs Table Please check row " +Integer.toString(cError)); 

                }
            }
            else
            {
                classes.AlertBox.display("Data Error", "SomeData is missing in the Elements Table Please check row" +Integer.toString(eError+1)); 

            }
        }
       else{
           classes.AlertBox.display("Data Error", "Please, Enter Numeric Values!"); 

         }
    
    }
    @FXML
    void cloSoMapSubmitButtonClicked(ActionEvent event) {
        MapController mapCont = new MapController();
        cloSoMap = mapCont.initCloSoMap();
        Optional<String> result = mapCont.validateSosTabData(CloSoMapTable);
        if (result.isPresent()) {
            classes.AlertBox.display("Data Error", result.get());
        }
        else
        {
            mapCont.saveCloSoMapData(CloSoMapTable, cloSoMap, sOs, clos);
        }
        mapCont.updateCloSoMapSoOnlyTable(CloSoMapSoOnlyTable, CloSoMapTable);
        CLOExitSurveyTableView.getColumns().get(1).setEditable(true);
        survSubmitButton.setDisable(false);
        saveAsPng(SOsTab, "CLOSOMap");
    }
   
    @FXML
    void topCloMapSubmitButtonClicked(ActionEvent event) {
        //not working yet
        
        MapController mapCont = new MapController();
        topCloMap = mapCont.initTopCloMap();
        if(mapCont.validateTableDataIsNumeric(topCloMapTable, clos.getDefList().size()))
        {mapCont.saveTopCloMapData(topCloMapTable, topics, clos, topCloMap);
        mapCont.updateClosTimeWeights(topCloMapCloOnlyTable, topCloMap, clos, topics);
        mapCont.updateTopicsTabChart(topicsTabChart, topCloMap, clos,topicsChartX);
        saveAsPng(topicsTab, "TopCLOMap");}
        else
        {
            classes.AlertBox.display("Data Error", "Please Enter Numeric Values");
        }
        
    }

    @FXML void insertMethodButtonClicked(ActionEvent event) {

        String[] s = new String[]{newMethodTextField.getText()};
        System.out.println("s is" + s[0]+".");
        if ( s[0].equals("") || s[0].equals(""))
        {
            classes.AlertBox.display("", "Please, enter method name!");
        }
        else if (s[0] != "" || s[0] != " ")
        {  
            Utils.addColumnsModified(methodsTable, Arrays.asList(s) , "");
        }
        newMethodTextField.setText("");

    }

    @FXML void methodsSubmitButtonClicked(ActionEvent event) {
        MapController cont = new MapController();
        methodsMap = cont.initMethodsMap();
        int nOfColumns = methodsTable.getColumns().size();
        if( nOfColumns <= 2)
        {
            classes.AlertBox.display("Data Error", "Please add Methods");

        }
        else{
        if(cont.validateTableDataIsNumeric(methodsTable, nOfColumns-2))
        {       
             cont.saveMethods(methodsTable, methodsMap, clos);
             saveAsPng(methodsTab, "MethodsResults");
        }
        else
        {
            classes.AlertBox.display("Data Error", "Please Enter Numeric Values");
        }
    }
    }
    @FXML void deleteMethodButtonClicked(ActionEvent event) {
        if(methodsTable.getSelectionModel().getSelectedCells().size() > 0)
        {
            int index = methodsTable.getSelectionModel().getSelectedCells().get(0).getColumn();
            if (index > 1)
            {Utils.removeColumns(methodsTable, index);}
            else
            {
                classes.AlertBox.display("", "Please, Select Method!");
            }
        }
        else
        {
            classes.AlertBox.display("", "Please, Select Method!");
        }

    }
    @FXML void importButtonClicked(ActionEvent event) {
    }

    //Function for CLO actions, Indirect assessment -> passing criteria
    public void getIndirectList(double[] soSurvScore){
        // Populate the indirect assessment of the CLO ACTIONS tab
        Integer crowCount = CLOExitSurveyTableView.getItems().size();
        // Loop over all the rows in the Survey Exit table to populate the assessment
            for (int i = 0; i<crowCount; i++){
                    String data = CLOExitSurveyTableView.getItems().get(i).getStr(1).get();
                    cActionsTableView.getItems().get(i).setStr(4, data);
                    // Get the conclusion based on the threshold of the passing criteria
                    getIndirectConc(cActionsTableView.getItems(), i, data);
                }
        // Populate the indirect assessment of the SO ACTIONS tab
        Integer srowCount = sActionsTableView.getItems().size();
        System.out.println(srowCount);
            for (int i = 0; i<srowCount; i++){
                // Get the data from the survey result
                    Double data = soSurvScore[i];
                    sActionsTableView.getItems().get(i).setStr(4, data.toString());
                    //Get the conclusion based on the threshold of the passing criteria
                    getIndirectConc(sActionsTableView.getItems(), i, data.toString());
                }
    }

    //Conclusion for indirect assessment percentages
    public void getIndirectConc(ObservableList<StrRow> table, Integer i, String data){
        if(surveyEntity.getSatisfactoryCriteria()<Double.parseDouble(data)){
            table.get(i).setStr(5, "Satisfactory");
        }
        else{
            table.get(i).setStr(5, "Unsatisfactory");
        }
    }

    
    public ObservableList<StrRow> getSActionsData(){
        ObservableList<StrRow> entries = FXCollections.observableArrayList();
        System.out.println("IM DATA");
        if(sOs.isDataReady()){
            System.out.println("IM redy");
            Integer rowCount = soTableView.getItems().size();
            for (int i = 0; i<rowCount; i++){
                List<Boolean> linkedSOs = new ArrayList<Boolean>(sOs.getLinked());
                if(linkedSOs.get(i)){
                    System.out.println("IM LINKED");
                    String[] data = {soTableView.getItems().get(i).getStr(0).get(), "0", "0", "Unknown",
                                    "0", "Unknown",
                                    "<CLICK ME TO EDIT>", "<CLICK ME TO EDIT>"};
                    System.out.println(data);
                    entries.add(new StrRow(data));
                }
            }
         }
        return entries;
    };

    public static double[] multiply(Double[][] weights, double[] vector) {
        int rows = weights.length;
        int columns = weights[0].length;
        // Get the transpose of the matrix
        System.out.println("starting transpose of " + rows + " rows and "+ columns);
        double[][] transpose = new double[columns][rows];
        for (Integer i = 0; i < rows; i++) {
            for (Integer j = 0; j < columns; j++){
                transpose[j][i] = weights[i][j]; 
                System.out.println("row " + j + "col " + i + " has val " + weights[i][j]);
            }
        }
        double[] result = new double[columns];
        // Perform matrix multiplication

        for (int column = 0; column < columns; column++) {
            double sum = 0;
            for (int row = 0; row < rows; row++) {
                sum += (transpose[column][row]/rows)
                        * vector[row];
            }
            result[column] = sum;
            System.out.println("res " + sum );
        }
        return result;
    }
    

    public Series<String,Integer> getSOChartData(){
        XYChart.Series<String,Integer> set = new XYChart.Series<String, Integer>();
        //dummy data since this data is added manually outside program scope
        if(sOs.isDataReady()){
            Integer srowCount = soTableView.getItems().size();
            Integer crowCount = cloTableView.getItems().size();
            List<Boolean> linkedSOs = new ArrayList<Boolean>(sOs.getLinked());
            Integer counter = 0;
            //Generate the SO survey score by multiplying the closomap with the clo survey results
            System.out.println(" rows " + cloSoMap.getMapDouble().length + " cols "+ cloSoMap.getMapDouble()[0].length);
            
            double[] soSurvScore = multiply(cloSoMap.getMapDouble(),surveyEntity.getScores().stream().mapToDouble(Double::doubleValue).toArray());
            //Populate the SO actions table with the SO survey results as well.
            getIndirectList(soSurvScore);
            //loop over all the so rows to populate the chart
            for (int i = 0; i<srowCount; i++){
                if(linkedSOs.get(i)){
                    StrRow data = soTableView.getItems().get(i);
                    System.out.println((int)soSurvScore[counter]);
                    set.getData().add(new XYChart.Data<String,Integer>(data.getStr(0).get(),(int)soSurvScore[counter]));
                    //set.getData().add(new XYChart.Data<String,Integer>(data.getStr(0).get(),60));
                    counter +=1;
                }
            }
         }
         return set;
    }



    @FXML void addNoteButtonClicked(ActionEvent event) {
        // Create not StrRow object and add it to our TableView.
        StrRow new_note = new StrRow(new String[] {"Note " + Integer.toString((n_notes++) + 1),
                "<CLICK ME TO EDIT>"});
        notesTableView.getItems().add(new_note);
    }

    @FXML
    void rmLastNoteClicked(ActionEvent event) {
        if (n_notes == 0)
        {
            return;
        }
        int index2del = notesTableView.getItems().size() -1;
        notesTableView.getItems().remove(notesTableView.getItems().get(index2del));
        n_notes--;
    }

    @FXML
    void addCloButtonClicked(ActionEvent event) {
        StrRow new_clo = new StrRow(new String[] {"CLO " + Integer.toString((n_clos++) + 1),
                "<CLICK ME TO EDIT>"});
        cloTableView.getItems().add(new_clo);
    }
    @FXML
    void addSoButtonClicked(ActionEvent event) {
        StrRowWithBool new_so = new StrRowWithBool(new String[] {"SO " + Integer.toString((n_sos++) + 1),
                "<CLICK ME TO EDIT>"}, Boolean.valueOf(false));
        soTableView.getItems().add(new_so);
    }

    @FXML
    void addTpButtonClicked(ActionEvent event) {
        StrRow new_tp = new StrRow(new String[] {("Tp " + Integer.toString((n_tps++) + 1)),
                "<CLICK ME TO EDIT>"});
        tpTableView.getItems().add(new_tp);
    }

    @FXML void rmLastCloClicked(ActionEvent event){
        if (n_clos<= 1)
            return;
        int index2del = cloTableView.getItems().size() -1;
        cloTableView.getItems().remove(cloTableView.getItems().get(index2del));
        n_clos--;
    }

    @FXML
    void rmLastSoClicked(ActionEvent event) {
        if (n_sos <= 1)
            return;
        int index2del = soTableView.getItems().size() -1;
        soTableView.getItems().remove(soTableView.getItems().get(index2del));
        n_sos--;
    }

    @FXML
    void rmLastTpClicked(ActionEvent event) {
        if (n_tps <= 1)
            return;
        int index2del = tpTableView.getItems().size() -1;
        tpTableView.getItems().remove(tpTableView.getItems().get(index2del));
        n_tps--;
    }

    // Add grade button
    @FXML
    void addGrButtonClicked(ActionEvent actionEvent) {
        StrRow new_grade = new StrRow( new String[] { "<CLICK ME TO EDIT>","<CLICK ME TO EDIT>",
                "0","1","1" });
        GradesTableView.getItems().add(new_grade);

    }

    // remove grade button
    @FXML
    void removeGrButtonClicked(ActionEvent actionEvent) {
        GradesController grCont = new GradesController();
        GradesTableView.getItems().removeAll(GradesTableView.getSelectionModel().getSelectedItem());
        totalGradeTextField.setText(Integer.toString(grCont.getTotalGrade(gradeColumn.getTableView(),gradesEnt)));
    }


    // submit grade button
    @FXML
    void SubmitGrButtonClicked(ActionEvent actionEvent) {
        GradesController grCont = new GradesController();
        boolean totalIsCorrect;
        totalIsCorrect= grCont.validateSubmitGrade(gradeColumn.getTableView(), GradesTableView.getItems(),gradesEnt);
        if (totalIsCorrect)
        {
            //add columns for first Tools table
            Utils.addColumnsModified(ToolsElementsTableView, gradesEnt.getCategory(),"0");
            // add rows for Elements 
            grCont.addRows(ToolsElementsTableView,gradesEnt);
            //populate the first column ROWS in tools table
            grCont.setAllGradeElementsGUI(ToolsElementsTableView,gradesEnt,"Row");
            // add rows for second tools table
            grCont.addRows(ToolsCLOsTableView,gradesEnt);
            // Add columns for the students table
            grCont.setAllGradeElementsGUI(StudentsGradesTableView,gradesEnt,"Col");

        }
        else
            classes.AlertBox.display("Data Error in Basic Tab", "Grade should add up to 100");
    }

    @FXML
    void basicSubmitClicked(ActionEvent event) {
        CourseInfoController ciCont = new CourseInfoController();
        Optional<String> result = ciCont.validateUpdate(program, semester, aYear, mantTableView.getItems(),
                                                        required, credit, optionalTableView.getItems(),
                                                        scheduleTableView.getItems(), textBookTableView.getItems(),
                                                        referencesTableView.getItems(), notesTableView.getItems(),
                cInfo);
        if (result.isPresent()){
            cInfo.setDataReady(false);
            classes.AlertBox.display("Data Error in Basic Tab", result.get() + "\nMake sure you press enter after editting");
        }
        // System.out.println(cInfo);
    }

    @FXML
    void defsSubmitClicked(ActionEvent event) {
        DefinitionsController defCont = new DefinitionsController();
        Optional<String> result = defCont.validateData(tpTableView.getItems(), cloTableView.getItems(),
                soTableView.getItems(), topics, clos, sOs);
        if (result.isPresent()) {
            clos.setDataReady(false);
            topics.setDataReady(false);
            sOs.setDataReady(false);
            classes.AlertBox.display("Data Error in Definitions Tab", result.get() +
                    "\nMake sure you press enter after editting");
        }
        else
        {
            //add columns
            Utils.addColumnsModified(ToolsCLOsTableView,clos.getDefList(),"TEST");
             /// populate maps tables 
            MapBoundary.populateTopCloMapTable(topCloMapTable,clos,topics);
            MapBoundary.populateTopCloMapCloOnlyTable(topCloMapTable,topCloMapCloOnlyTable,clos,topics);
            MapBoundary.populateCloSoMapTable(CloSoMapTable, sOs, clos);
            MapBoundary.populateMethodsTable(methodsTable,clos);
            MapBoundary.populateCloSoMapSoOnlyTable(CloSoMapSoOnlyTable,sOs);
            newMethodTextField.setText("");
        }

        System.out.println(topics);
        System.out.println(clos);
        System.out.println(sOs);
        cloSoMapSubmitButton.setDisable(false);
        
        sActionsTableView.getItems().setAll(getSActionsData());
    }
    @FXML
    void rmLastTextbookClicked(ActionEvent event) {
        if (n_tbooks == 0)
        {
            return;
        }
        int index2del = textBookTableView.getItems().size() -1;
        textBookTableView.getItems().remove(textBookTableView.getItems().get(index2del));
        n_tbooks--;
    }
    @FXML
    void rmLastReferenceClicked(ActionEvent event) {
        if (n_refs== 0)
        {
            return;
        }
        int index2del = referencesTableView.getItems().size() -1;
        referencesTableView.getItems().remove(referencesTableView.getItems().get(index2del));
        n_refs--;
    }

    @FXML
    void addReferenceButtonClicked(ActionEvent event) {
        StrRow new_row = new StrRow(new String[] {("Reference " + Integer.toString(++n_refs)),
        "<CLICK ME TO EDIT>"});
        referencesTableView.getItems().add(new_row);
    }

    @FXML
    void addScheduleButtonClicked(ActionEvent event) {
        StrRow new_row = new StrRow(new String[] {("Schedule Line " + Integer.toString(++n_schedule)),
        "<CLICK ME TO EDIT>"});
        scheduleTableView.getItems().add(new_row);
    }
    @FXML
    void addTextbookButtonClicked(ActionEvent event) {
        StrRow new_row = new StrRow(new String[] {("Textbook " + Integer.toString((++n_tbooks))),
        "<CLICK ME TO EDIT>"});
        textBookTableView.getItems().add(new_row);
    }

    @FXML
    void rmLastScheduleClicked(ActionEvent event) {
        if (n_schedule == 0)
        {
            return;
        }
        int index2del = scheduleTableView.getItems().size() -1;
        scheduleTableView.getItems().remove(scheduleTableView.getItems().get(index2del));
        n_schedule--;
    }

    // void syllabusTabClicked() {
    //     System.out.println("ouchyy");
    //     System.out.println(getClass().getResource(""));
    //     SyllabusController scont = new SyllabusController();
    //     scont.generateSyllabus(cInfo, clo, tp, so, grades, getClass().getResource(""));
    //     // System.out.println(getClass().getResource("CASh.class"));
    // }

    // Add student button
    @FXML
    void addStButtonClicked(ActionEvent actionEvent) {
        String currentNumStudents = Integer.toString(StudentsGradesTableView.getItems().size()+1);
        StrRow new_id = new StrRow( new String[] { currentNumStudents });
        StudentsGradesTableView.getItems().add(new_id);
    }

    // remove student button
    @FXML
    void RemoveStButtonClicked(ActionEvent actionEvent) {
        StudentsGradesTableView.getItems().removeAll(StudentsGradesTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void save2PDF(ActionEvent event) {
        FileChooser myFile = new FileChooser();
        myFile.setInitialFileName("syllabus.pdf");
        File file = myFile.showSaveDialog(null);
        String path = "";
        try{
        path = new URI(webEngine.getDocument().getDocumentURI()).getPath();
        }
        catch (URISyntaxException e){}
        // try{
        // path = URLDecoder.decode(getClass().getResource("CASh-SWTeam.htm").getPath(), "UTF-8");
        // }
        // catch (UnsupportedEncodingException e){}
        ConverterProperties conv = new ConverterProperties();
        // String base_path = path.substring(0,path.lastIndexOf("/")+1);
        conv.setBaseUri(System.getProperty("java.io.tmpdir"));
        try{
        HtmlConverter.convertToPdf(
            new FileInputStream(path),
            new FileOutputStream(file),conv);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch (IOException e)
        {}
    }
    // Number of added notes in the Optonal Info.

    @FXML
    void survSubmitButtonClicked(ActionEvent event){
        SurveyController survCont = new SurveyController();
        // Call the controller
        Optional<String> result = survCont.validateData(CLOExitSurveyTableView.getItems(),
                                                        passingCriteria.getText(),
                                                        satisfactoryCriteria.getText(),
                                                        surveyEntity);
        if (result.isPresent()){
            surveyEntity.setDataReady(false);
            classes.AlertBox.display("Data Error in Survey Tab", result.get() +
                                    "\nMake sure you press enter after editting");
        }
        else if(!cloSoMap.getIsDataReady()){
           surveyEntity.setDataReady(false);
           classes.AlertBox.display("Error","Please fill the CLO-SO map first.");
        }
        else{
        surveyEntity.setDataReady(true);
        XYChart.Series<String,Integer> set = new XYChart.Series<String, Integer>();
        ObservableList<String> xCLO = FXCollections.observableArrayList();
        ObservableList<String> xSO = FXCollections.observableArrayList();
            //loop over all the items in the Exit survey table
            for (int i = 0; i < CLOExitSurveyTableView.getItems().size(); i++){
                //get the ith row
                StrRow data = CLOExitSurveyTableView.getItems().get(i);
                //set the data for the bar chart
                set.getData().add(new XYChart.Data<String,Integer>(data.getStr(0).get(),
                                                         Integer.parseInt(data.getStr(1).get())));
            };
            //Save a snapshot of the CLO data
            cloSurvChart.setAnimated(false);
            cloSurvChart.getData().setAll(set);
            // saveAsPng(cloSurvChart,"CLOChart");

            soSurvChart.setAnimated(false);
            soSurvChart.getData().setAll(getSOChartData());
            // saveAsPng(soSurvChart, "SOChart");
            System.out.println(surveyEntity);
            classes.AlertBox.display("Survey","Data added successfully!");
            cActionsText.setContentText("Indirect Assessment Passing Criteria: At least "+
                                        surveyEntity.getNumberToPass() +
                                        " % of students must give at least " +
                                        surveyEntity.getSatisfactoryCriteria() +
                                         " % in the CLO in the Exit survey.");
            sActionsText.setContentText("Indirect Assessment Passing Criteria: At least "+
                                        surveyEntity.getNumberToPass()  +
                                        " % of students must give at least " +
                                        surveyEntity.getSatisfactoryCriteria()+
                                         " % in the CLO in the Exit survey.");
            cActionsTableView.setEditable(true);
            sActionsTableView.setEditable(true);
            saveAsPng(surveyTab, "SurveyResults");
        }
    }
    @FXML
    void cActionsSubmitButtonClicked(ActionEvent event){
        ActionsController actCont = new ActionsController();
        // Call the controller
        Optional<String> result = actCont.validateData(cActionsTableView.getItems(),
                                                      Double.parseDouble(passingCriteria.getText()),
                                                       cActionsEntity);
        if (result.isPresent()){
            cActionsEntity.setDataReady(false);
            classes.AlertBox.display("Data Error in CLO actions Tab", result.get() +
                                    "\nMake sure you press enter after editting");
        }
        else{
            cActionsEntity.setDataReady(true);
            XYChart.Series<String,Integer> set = new XYChart.Series<String, Integer>();
            XYChart.Series<String,Integer> set1 = new XYChart.Series<String, Integer>();
            set.setName("passing criteria 1");
            set1.setName("passing criteria 2");
            for(Integer i = 0; i < cActionsTableView.getItems().size(); i ++){
            cActionsTableView.getItems().get(i).setStr(3, cActionsEntity.getAssessment().getConclusion().get(i));
            // TO populate the chart in tools table
            set.getData().add(new XYChart.Data<String,Integer>(cActionsTableView.getItems().get(i).getStr(0).get(),
                                                            Integer.parseInt(cActionsTableView.getItems().get(i).getStr(1).get())));
            set1.getData().add(new XYChart.Data<String,Integer>(cActionsTableView.getItems().get(i).getStr(0).get(),
                                                        Integer.parseInt(cActionsTableView.getItems().get(i).getStr(2).get())));};
            classes.AlertBox.display("", "Data added successfully!");
            saveAsPng(cActionsTab, "CLOActions");sActionsEntity.setDataReady(true);
            CLOPassingChart.getData().setAll(set);
            CLOPassingChart.getData().addAll(set1);
            saveAsPng(CLOPassingChart, "CLO passing criteria");
        }
    }
    @FXML
    void sActionsSubmitButtonClicked(ActionEvent event){
        ActionsController actCont = new ActionsController();
        // Call the controller
        Optional<String> result = actCont.validateData(sActionsTableView.getItems(),
                                                      Double.parseDouble(passingCriteria.getText()),
                                                       sActionsEntity);
        if (result.isPresent()){
            sActionsEntity.setDataReady(false);
            classes.AlertBox.display("Data Error in SO actions Tab", result.get() +
                                    "\nMake sure you press enter after editting");
        }
        else{
            sActionsEntity.setDataReady(true);
            XYChart.Series<String,Integer> set = new XYChart.Series<String, Integer>();
            XYChart.Series<String,Integer> set1 = new XYChart.Series<String, Integer>();
            set.setName("passing criteria 1");
            set1.setName("passing criteria 2");
            for(Integer i = 0; i < sActionsTableView.getItems().size(); i ++){
            sActionsTableView.getItems().get(i).setStr(3, sActionsEntity.getAssessment().getConclusion().get(i));
            set.getData().add(new XYChart.Data<String,Integer>(sActionsTableView.getItems().get(i).getStr(0).get(),
                                                                            Integer.parseInt(sActionsTableView.getItems().get(i).getStr(1).get())));
            set1.getData().add(new XYChart.Data<String,Integer>(sActionsTableView.getItems().get(i).getStr(0).get(),
                                                                            Integer.parseInt(sActionsTableView.getItems().get(i).getStr(2).get())));}
            
            classes.AlertBox.display("", "Data added successfully!");
            saveAsPng(sActionsTab, "SOActions");
            CLOPassingChart.getData().setAll(set);
            CLOPassingChart.getData().addAll(set1);
            saveAsPng(CLOPassingChart, "SO passing criteria");
        }
    }

    
    @FXML
    public void saveAsPng(Tab tab, String name) {
        WritableImage image = tab.getContent().snapshot(new SnapshotParameters(), null);
        
        // TODO: probably use a file chooser here
        File file = new File(name+".png");
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println("COULDNT SAVE CHART");
        }
    }
    public void saveAsPng(Chart chart, String name) {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        
        // TODO: probably use a file chooser here
        File file = new File(name+".png");
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println("COULDNT SAVE CHART");
        }
    }

    public void setStage(Stage mainwindow){
        mainwindow.setOnCloseRequest(e ->
        {
            e.consume();
            handleclose();
            mainwindow.close();
        });
        System.out.println(program);
    }
    
    public void handleclose() {
        if (cInfo.isDataReady()){
            try{
            System.out.println("saving in");
            File f = new File(System.getProperty("java.io.tmpdir"), "cinfo.ser");
            f.createNewFile();
            System.out.println(f.getPath());
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(cInfo);
            oos.close();
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
        if (clos.isDataReady()){
            try{
            System.out.println("saving in");
            File f = new File(System.getProperty("java.io.tmpdir"), "clo.ser");
            f.createNewFile();
            System.out.println(f.getPath());
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(clos);
            oos.close();
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
        if (topics.isDataReady()){
            try{
            System.out.println("saving in");
            File f = new File(System.getProperty("java.io.tmpdir"), "topics.ser");
            f.createNewFile();
            System.out.println(f.getPath());
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(topics);
            oos.close();
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
        if (sOs.isDataReady()){
            try{
            System.out.println("saving in");
            File f = new File(System.getProperty("java.io.tmpdir"), "sos.ser");
            f.createNewFile();
            System.out.println(f.getPath());
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(sOs);
            oos.close();
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
        if (gradesEnt.getDataReady()){
            try{
            System.out.println("saving in");
            File f = new File(System.getProperty("java.io.tmpdir"), "grades.ser");
            f.createNewFile();
            System.out.println(f.getPath());
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(gradesEnt);
            oos.close();
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
    }

    private void loadIfPresent() {
        File f = new File(System.getProperty("java.io.tmpdir"), "cinfo.ser");
        if (f.exists()){
            try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            cInfo = (CourseInfo) ois.readObject();
            ois.close();
            CourseInfoController ccont = new CourseInfoController();
            ccont.reviveGUI(programCB, semesterCB, aYearCB, mantTableView.getItems(),
                requiredCB, creditSpinner,
                optionalTableView.getItems(), scheduleTableView.getItems(),
                textBookTableView.getItems(), referencesTableView.getItems(),
                notesTableView.getItems(), cInfo);
            }
            catch (IOException e){
                System.out.println("ioexp");
                System.out.println(e.getMessage());
                
            }
            catch (ClassNotFoundException e){
                System.out.println("class not found");
            }
        }
        File fclo = new File(System.getProperty("java.io.tmpdir"), "clo.ser");
        File ftopic = new File(System.getProperty("java.io.tmpdir"), "topics.ser");
        File fso = new File(System.getProperty("java.io.tmpdir"), "sos.ser");
        if (fclo.exists() && ftopic.exists() && fso.exists()){
            try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fclo));
            clos = (CLO) ois.readObject();
            ois.close();
            ois = new ObjectInputStream(new FileInputStream(ftopic));
            topics = (Topic) ois.readObject();
            ois.close();
            ois = new ObjectInputStream(new FileInputStream(fso));
            sOs = (SO) ois.readObject();
            ois.close();
            DefinitionsController dcont = new DefinitionsController();
            dcont.reviveGUI(tpTableView.getItems(), cloTableView.getItems(),
            soTableView.getItems(), topics, clos, sOs);
            defsSubmitClicked(null);
            }
            catch (IOException e){
                System.out.println("ioexp");
                System.out.println(e.getMessage());
                
            }
            catch (ClassNotFoundException e){
                System.out.println("class not found");
            }
        }
        File fgrades = new File(System.getProperty("java.io.tmpdir"), "grades.ser");
        if (fgrades.exists()){
            try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fgrades));
            gradesEnt = (Grades) ois.readObject();
            ois.close();
            GradesController grCont = new GradesController();
            grCont.reviveGUI(GradesTableView.getItems(), gradesEnt);
            var totalGrade = grCont.getTotalGrade(GradesTableView,gradesEnt);
            totalGradeTextField.setText(Integer.toString(totalGrade)); 
            }
            catch (IOException e){
                System.out.println("ioexp");
                System.out.println(e.getMessage());
                
            }
            catch (ClassNotFoundException e){
                System.out.println("class not found");
            }
        }
    }
    @FXML
    void syllabusbuttonClicked(ActionEvent event) {
        mainTabPane.getSelectionModel().select(syllabusTab);
        }
    @FXML
    void reportsButtonClicked(ActionEvent event){

    }
    @FXML
    void onClearClicked(ActionEvent event){
        surveyEntity = new Survey();
        cActionsEntity = new CLOactions();
        sActionsEntity = new SOactions();
        CourseInfoController cc = new CourseInfoController(); 
        cInfo = cc.initCourseInfo();
        GradesController gc =  new GradesController();
        gradesEnt = gc.initGrades();
        DefinitionsController dc = new DefinitionsController();
        clos = dc.initClo();
        sOs = dc.initSo();
        topics = dc.initTopic();
        program = "";
        aYear = 0;
        semester = "";
        required = Required.REQUIRED;
        credit = 3;
        MapController mapCont = new MapController();
        cloSoMap = mapCont.initCloSoMap();
        mapCont = new MapController();
        topCloMap = mapCont.initTopCloMap();
        mapCont = new MapController();
        methodsMap = mapCont.initMethodsMap();
        mantTableView.getItems().setAll(InitialValues.getInitialMandatoryList());
        optionalTableView.getItems().setAll(InitialValues.getInitialOptionalList());
        referencesTableView.getItems().setAll(InitialValues.getInitialReferencesList());
        textBookTableView.getItems().setAll(InitialValues.getInitialTextbookList());
        scheduleTableView.getItems().setAll(InitialValues.getInitialScheduleList());
        tpTableView.getItems().setAll(InitialValues.getInitialTPList());
        cloTableView.getItems().setAll(InitialValues.getInitialCLOList());
        soTableView.getItems().setAll(InitialValues.getInitialSOList());
        notesTableView.getItems().clear();
        GradesTableView.getItems().setAll(InitialValues.getInitialGrades());
        CLOExitSurveyTableView.getItems().setAll(InitialValues.getInitialsurvList());
        cActionsTableView.getItems().setAll(InitialValues.getInitialCActionsList());
        sActionsTableView.getItems().setAll(InitialValues.getInitialSActionsList());
        handleclose();
      }
    // Number of added notes in the Optonal Info.
    int n_notes=0;
    int n_tps=2;
    int n_clos=2;
    int n_sos=2;
    int n_tbooks=3;
    int n_schedule=2;
    int n_refs=2;
    @FXML private VBox homeMainVBox;
    @FXML private VBox homeTitleVBox;
    @FXML private HBox titleImageHbox;
    @FXML private ImageView imageTitle;
    @FXML private Label titleLabel;
    @FXML private VBox homeButtonsVBox;
    @FXML private HBox clearVbox;
    @FXML private Button clearButton;
    @FXML private Label clearLabel;
    @FXML private HBox syllabusVbox;
    @FXML private Button syllabusButton;
    @FXML private Label syllabusLabel;
    @FXML private HBox reportsVbox;
    @FXML private Button reportsButton;
    @FXML private Label reportsLabel;
    @FXML private ScrollPane courseInfoScrollPane;
    @FXML private ScrollPane definitionsScrollPane;
    @FXML private VBox courseInfoVbox;
    // We need to set the TableView and Column template types
    // StrRow is enough if all columns are strings.
    @FXML private TableView<StrRow> mantTableView;
    @FXML private TableColumn<StrRow, String> mandEntry;
    @FXML private TableColumn<StrRow, String> mandValue;
    @FXML private TableView<StrRow> optionalTableView;
    @FXML private TableColumn<StrRow, String>  optionalEntry;
    @FXML private TableColumn<StrRow, String>  optionalValue;
    @FXML private Button addNoteButton;
    @FXML private Button rmLastNote;
    @FXML private TableView<StrRow> tpTableView;
    @FXML private TableColumn<StrRow, String> tpEntry;
    @FXML private TableColumn<StrRow, String> tpValue;
    @FXML private Button addTp;
    @FXML private Button rmLastTp;
    @FXML private TableView<StrRow> cloTableView;
    @FXML private TableColumn<StrRow, String> cloEntry;
    @FXML private TableColumn<StrRow, String> cloValue;
    @FXML private Button addClo;
    @FXML private Button rmLastClo;
    @FXML private TableView<StrRowWithBool> soTableView;
    @FXML private TableColumn<StrRowWithBool, String> soEntry;
    @FXML private TableColumn<StrRowWithBool, String> soValue;
    @FXML private TableColumn<StrRowWithBool, Boolean> soLinked;
    @FXML private Button addSo;
    @FXML private Button rmLastSo;
    @FXML private Button basicSubmit;

    // configure the grades table
    @FXML private TableView<StrRow> GradesTableView;
    @FXML private TableColumn<StrRow,String> categoryColumn;
    @FXML private TableColumn<StrRow,String> abbreviationColumn;
    @FXML private TableColumn<StrRow, String> gradeColumn;
    @FXML private TableColumn<StrRow, String> noOfElementsColumn;
    @FXML private TableColumn<StrRow, String> noOfSubElementsColumn;
    @FXML private TextField totalGradeTextField;


    @FXML private ComboBox<String> programCB;
    @FXML private ComboBox<String> semesterCB;
    @FXML private ComboBox<String> aYearCB;
    @FXML private ComboBox<String> requiredCB;
    @FXML private Spinner<Integer> creditSpinner;

    //Survey tab
    @FXML private TableView<StrRow> CLOExitSurveyTableView;
    @FXML private TextField passingCriteria;
    @FXML private TextField satisfactoryCriteria;
    @FXML private TableColumn<StrRow, String> survCLO;
    @FXML private TableColumn<StrRow, String> survScore;
    @FXML private Button survSubmitButton;
    @FXML private BarChart<String, Integer> cloSurvChart;
    @FXML private CategoryAxis cloX;
    @FXML private NumberAxis cloY;
    @FXML private BarChart<String, Integer> soSurvChart;
    @FXML private CategoryAxis soX;
    @FXML private NumberAxis soY;
    @FXML private Tab surveyTab;

    //cActions tab
    @FXML private DialogPane cActionsText;
    @FXML private TableView<StrRow> cActionsTableView;
    @FXML private Button cActionsSubmitButton;
    @FXML private TableColumn<StrRow, String> cActionsCLOs;
    @FXML private TableColumn<StrRow, String> cDirectAssessment;
    @FXML private TableColumn<StrRow, String> cActionsDirectPassing01;
    @FXML private TableColumn<StrRow, String> cActionsDirectPassing02;
    @FXML private TableColumn<StrRow, String> cActionsDirectConc;
    @FXML private TableColumn<StrRow, String> cIndirectAssessment;
    @FXML private TableColumn<StrRow, String> cActionsIndirectPassing01;
    @FXML private TableColumn<StrRow, String> cActionsIndirectConc;
    @FXML private TableColumn<StrRow, String> cloPerceivedProblem;
    @FXML private TableColumn<StrRow, String> cloActionPlan;
    @FXML private Tab cActionsTab;

    //sActions tab
    @FXML private DialogPane sActionsText;
    @FXML private TableView<StrRow> sActionsTableView;
    @FXML private Button sActionsSubmitButton;
    @FXML private TableColumn<StrRow, String> sActionsSOs;
    @FXML private TableColumn<StrRow, String> sDirectAssessment;
    @FXML private TableColumn<StrRow, String> sActionsDirectPassing01;
    @FXML private TableColumn<StrRow, String> sActionsDirectPassing02;
    @FXML private TableColumn<StrRow, String> sActionsDirectConc;
    @FXML private TableColumn<StrRow, String> sIndirectAssessment;
    @FXML private TableColumn<StrRow, String> sActionsIndirectPassing01;
    @FXML private TableColumn<StrRow, String> sActionsIndirectConc;
    @FXML private TableColumn<StrRow, String> soPerceivedProblem;
    @FXML private TableColumn<StrRow, String> soActionPlan;
    @FXML private Tab sActionsTab;

    // Basic tab Separated TableViews
    @FXML private Button addSchedule;
    @FXML private Button rmLastSchedule;
    @FXML private TableView<StrRow> textBookTableView;
    @FXML private TableColumn<StrRow, String> textBookEntry;
    @FXML private TableColumn<StrRow, String> textBookValue;
    @FXML private Button addTextbookButton;
    @FXML private Button rmLastTextbook;
    @FXML private TableView<StrRow> referencesTableView;
    @FXML private TableColumn<StrRow, String> referencesEntry;
    @FXML private TableColumn<StrRow, String> referencesValue;
    @FXML private Button addReferenceButton;
    @FXML private Button rmLastReference;
    @FXML private TableView<StrRow> notesTableView;
    @FXML private TableColumn<StrRow, String> notesEntry;
    @FXML private TableColumn<StrRow, String> notesValue;
    @FXML private TableView<StrRow> scheduleTableView;
    @FXML private TableColumn<StrRow, String> scheduleEntry;
    @FXML private TableColumn<StrRow, String> scheduleValue;
    // Topics tab 
    @FXML private VBox topicsTabVbox;
    @FXML private Button topCloMapSubmitButton;
    @FXML private TableView<StrRow> topCloMapTable;
    @FXML private Tab topicsTab;
    @FXML private TableView<StrRow> topCloMapCloOnlyTable;
    @FXML private BarChart<String, Double> topicsTabChart;
    @FXML private CategoryAxis topicsChartX;
    @FXML private NumberAxis topicsChartY;
    // SOs tab
    @FXML private TableView<StrRow> CloSoMapTable;
    @FXML private TableView<StrRow> CloSoMapSoOnlyTable;
    @FXML private Button cloSoMapSubmitButton;
    @FXML private Tab SOsTab;

    @FXML private WebView browser;
    @FXML private BorderPane syllabusBPane;
    @FXML private Tab syllabusTab;
    // Tools tab
    @FXML private  TableView<StrRow> ToolsElementsTableView;
    @FXML private TableColumn<StrRow,String> ToolsElementsColumn;
    @FXML private TableView<StrRow> ToolsCLOsTableView;
    @FXML private  TableColumn<StrRow,String> ToolsCloColumn;
    @FXML private Button ToolsSubmitButton;
    //Methods Tab
    @FXML private Button methodsSubmitButton;
    @FXML private Button deleteMethodButton;
    @FXML private TextField newMethodTextField;
    @FXML private Button insertMethodButton;
    @FXML private TableView<StrRow> methodsTable;
    @FXML private Tab methodsTab;

    // Students Tab
    @FXML private Button RemoveStudentButton;
    @FXML private Button AddStudentButton;
    @FXML private BarChart<String, Integer> CLOPassingChart;

    @FXML private TableView<StrRow> StudentsGradesTableView;
    @FXML private TableColumn<StrRow,String> StudentsGradesColumn;

    @FXML
    private TabPane mainTabPane;
}

