import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import classes.AlertBox;
import classes.login.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginBoundary implements Initializable {
    private Stage prevStage;

    @Override
    // Called as soon as the fxml is loaded
    public void initialize(URL u, ResourceBundle rb) {

    };

    @FXML
    private TextField UsernameTextField;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private Button SignUpButton;

    @FXML
    private Button LoginButton;

    // Sign up
    @FXML
    void signUpButtonClicked(ActionEvent event) throws FileNotFoundException, IOException {
        LoginController cont = new LoginController();
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();
        boolean flag = cont.checkUsernameExistance(username);
        if (flag == true) {
            System.out.println("flag is true");
            AlertBox.display("Data Error", "Username Already Exists\n Please Enter a New one");
            
        } else {
            System.out.println("flag is false");
            Boolean isSuccessful = cont.addingNewUser(username, password);
            if (isSuccessful) {
                String path = cont.getUserPath();
                Stage stage = new Stage();
                stage.setTitle("User Courses");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("views/courses.fxml"));
                Parent root = loader.load();
                CoursesBoundary boundary = loader.getController();
                boundary.setCoursesPath(path);
                boundary.setStage(stage);
                stage.setScene(new Scene(root));
                prevStage.close();
                stage.show();
            }
        }

    }

    public void setStage(Stage prevStage) {
        this.prevStage = prevStage;
    }

    // Log in
    @FXML
    void LoginButtonClicked(ActionEvent event) throws FileNotFoundException, IOException {
        LoginController cont = new LoginController();
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();
        boolean flag = cont.checkUsernamePasswordExistance(username, password);
        if (!flag) {
        	System.out.println("flag is false");

            classes.AlertBox.display("Data Error", "Username or password is wrong.\nPlease make sure you are entering the correct credentials.");

        } else if (flag) {
        	System.out.println("User name is true");
            
        	String path = ".." + File.separator + "Course-Assessment-Sheets-ZC" + File.separator + "src" + File.separator
                    + "main" + File.separator + "Data" + File.separator + username;
            System.out.println(path);
            Stage stage = new Stage();
            stage.setTitle("User Courses");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/courses.fxml"));
            Parent root = loader.load();
            CoursesBoundary boundary = loader.getController();
            boundary.setCoursesPath(path);
            boundary.setStage(stage);
            stage.setScene(new Scene(root));
            prevStage.close();
            stage.show();
        }

    }
}