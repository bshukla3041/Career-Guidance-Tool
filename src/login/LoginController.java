package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public JFXButton registerButton;
    @FXML
    public JFXButton loginButton;
    @FXML
    public JFXTextField email;
    @FXML
    public JFXPasswordField password;
    @FXML
    public Label headLabel;
    @FXML
    public JFXRadioButton studentBtn;
    @FXML
    public JFXRadioButton adminBtn;
    @FXML
    public ToggleGroup toggleGroup1;

    public static String currentUser;

    DatabaseHandler databaseHandler;

    public void handleLogin(ActionEvent actionEvent) {
        String user = email.getText();
        String pass = password.getText();
        if (isLogin(user, pass)) {
            headLabel.setText("Successful Login!");
            if(studentBtn.isSelected()){
                closeStage();
                loadWindow("/student/student.fxml", "Student Home | CGT");
            }else{
                closeStage();
                loadWindow("/admin/admin.fxml", "Admin Home | CGT");
            }
        } else {
            headLabel.setText("Wrong Credentials");
        }
    }

    private boolean isLogin(String user, String pass) {
        ResultSet rs;
        String query = "SELECT * FROM users WHERE email =\"" + user + "\" and password =\"" + pass+"\"";
        try {
            rs = databaseHandler.execQuery(query);
            if(rs.next()) {
                currentUser = rs.getString("email");
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
    private void loadRegistration(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/user/user.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Registration | CGT");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        ((Stage)email.getScene().getWindow()).close();
    }
}
