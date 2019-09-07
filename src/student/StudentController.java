package student;

import admin.AdminController;
import com.jfoenix.controls.JFXButton;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static login.LoginController.currentUser;
import static student.TestController.*;

public class StudentController implements Initializable {

    @FXML
    public Label userLbl;
    @FXML
    public JFXButton testBtn;
    @FXML
    public JFXButton resultBtn;
    @FXML
    public Button logoutBtn;

    private DatabaseHandler databaseHandler;

    public void handleTest(ActionEvent actionEvent) {
        if(AdminController.testPublished){
            loadWindow("test.fxml", "Test | CGT");

//            String qu = "UPDATE users SET " +
//                    "apt_score=\"" + aptScore + "\"," +
//                    "art_score=\"" + artScore + "\"," +
//                    "sci_score=\"" + sciScore + "\"," +
//                    "math_score=\"" + mathScore + "\"" +
//                    "WHERE email=\""+currentUser+"\"";
//            System.out.println(qu);
//
//            if (databaseHandler.execAction(qu)) {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setHeaderText(null);
//                alert.setContentText("Test Finished! Please check your result analysis.");
//                alert.showAndWait();
//                loadWindow("student.fxml", "Student Home | CGT");
//                closeStage();
//            } else {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setHeaderText(null);
//                alert.setContentText("Test Submission Failed! Try Again.");
//                alert.showAndWait();
//            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Test is unavailable at this moment! Try Later");
            alert.showAndWait();
        }

    }

    public void handleResult(ActionEvent actionEvent) {
        if(AdminController.testPublished){
            loadWindow("result.fxml", "Result | CGT");
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Logout Successful! Please come back again.");
        alert.showAndWait();
        loadWindow("/login/login.fxml", "Login  | CGT");
        closeStage();
    }

    private void loadWindow(String loc, String title){
        try {
            Parent parent  = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeStage(){
        ((Stage)logoutBtn.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
        userLbl.setText("Welcome "+currentUser);
    }
}



