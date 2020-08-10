import javafx.application.Application;
import javafx.scene.Scene;
// import javafx.scene.control.Label;
// import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
// import javafx.scene.control.Button;
// import javafx.event.EventHandler;
// import javafx.event.ActionEvent;
// import javafx

public class CASh extends Application {

    // Entry point of the program, starts the GUI
    public static void main(String[] args) {
        launch(args);
    }
    //Moemen added this line to test he can push.
    @Override
    // Gets called as the GUI starts
    public void start(Stage primaryStage) throws Exception {
        // load and start the GUI from the fxml file.
        // All other functionality originates from Controller.java
        // Parent root = FXMLLoader.load(getClass().getResource("views/cash.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml"));
        Parent root = (Parent) loader.load();
        // Boundary controller = (Boundary)loader.getController();
        LoginBoundary boundary = (LoginBoundary) loader.getController();
        // controller.setStage(primaryStage);
        boundary.setStage(primaryStage);
        primaryStage.setTitle("Course Assessment Sheet (CASh)");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}