
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Utils;
import classes.courses.Courses;
import classes.courses.CoursesController;
import classes.customrowtypes.StrRow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class CoursesBoundary implements Initializable {
	
    private String mainCoursePath;
    
    private Stage prevStage;
    
    @FXML
    private Button addCourseButton;
    @FXML
    private Button remCourseButton;
    @FXML 
    private Button nextButton;
    @FXML
    private TableView<StrRow> courseListTableView;
    @FXML
    private TableColumn<StrRow, String> courseTableColumn;
    

    @Override
    // Called as soon as the fxml is loaded
    public void initialize(URL u, ResourceBundle rb) {
        // Students tab
        courseListTableView.setEditable(true);
        courseTableColumn.setCellValueFactory((CellDataFeatures<StrRow, String> p) -> {
            return p.getValue().getStr(0);
        });
        Utils.makeTableColumnEdittableTBox(courseTableColumn, 0);
        courseTableColumn.setOnEditCommit(t -> {
            ((StrRow) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStr(0, t.getNewValue());
            String path = mainCoursePath + File.separator + courseListTableView.getSelectionModel().getSelectedItem().getStr(0).get();
            System.out.println("Path passed :" + path);
            CoursesController cont = new CoursesController();
            cont.addCourse(path);
        });
    }

    @FXML
    void addCourseButtonClicked(ActionEvent actionEvent) {
        StrRow new_course = new StrRow(new String[] { "COURSEID COURSENAME" });
        courseListTableView.getItems().add(new_course);
    }

    // remove grade button
    @FXML
    void remCourseButtonClicked(ActionEvent actionEvent) {
        courseListTableView.getItems().removeAll(courseListTableView.getSelectionModel().getSelectedItem());
        
        String path = ".." + File.separator + "Course-Assessment-Sheets-ZC" + File.separator + "src" + File.separator
                + "main" + File.separator + "Data" + File.separator + mainCoursePath;
        
        CoursesController cont = new CoursesController();
        cont.remCourse(path);
    }

    @FXML
    void nextButtonClicked(ActionEvent actionEvent) throws IOException{
        //update the course list with all the removes and adds to the filesystem
        //CoursesController.updateCourseList(userCourseList);

        // get the path to the saved course data
        //TO DO

        //open it in the cash app.
        //TO DO
        // String path = userPath.get();
    	
        Stage stage = new Stage();
        stage.setTitle("Le CASh");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/cash.fxml"));
        Parent root = loader.load();
        Boundary boundary = loader.getController();
        boundary.setStage(stage);
        stage.setScene(new Scene(root));
        prevStage.close();
        stage.show();
    }

    public void setCoursesPath(String path) {
        System.out.println("Path passed :" + path);
        this.mainCoursePath = path;
    }

    public void setStage(Stage prevStage) {
        this.prevStage = prevStage;
    }

}
