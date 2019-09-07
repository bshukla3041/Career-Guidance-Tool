package admin;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminController {

    @FXML
    public Label userLbl;
    @FXML
    public JFXButton makeTestBtn;
    @FXML
    public JFXButton publishBtn;
    @FXML
    public Button logoutBtn;

    public static boolean testPublished = false;

    public void handleMakeTest(ActionEvent actionEvent) {
        loadWindow("makeTest.fxml", "Add Question | CGT");
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

    public void handlePublishBtn(ActionEvent actionEvent) {
        testPublished = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Assessment Test Published Successfully!");
        alert.showAndWait();
    }

    public void handleLogout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Logout Successful! Please come back again.");
        alert.showAndWait();
        loadWindow("/login/login.fxml", "Login | CGT");
        closeStage();
    }

    private void closeStage(){
        ((Stage)logoutBtn.getScene().getWindow()).close();
    }

}
