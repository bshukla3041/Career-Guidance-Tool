package user;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserController implements Initializable {
    @FXML
    public JFXTextField userName;
    @FXML
    public JFXTextField userEmail;
    @FXML
    public JFXPasswordField userPassword;
    @FXML
    public JFXRadioButton studentBtn;
    @FXML
    public ToggleGroup toggleGroup1;
    @FXML
    public JFXRadioButton adminBtn;
    @FXML
    public Button registerButton;
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Button cancelButton;

    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
//        checkData();

    }

    private void checkData() {
        String qu = "SELECT name FROM users";
        ResultSet rs = databaseHandler.execQuery(qu);
        try{
            while(rs.next()){
                String qr = rs.getString("name");
                System.out.println(qr);
            }
        }catch (SQLException e){
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void handleRegistration(ActionEvent actionEvent) {
        String email = userEmail.getText();
        String password = userPassword.getText();
        String name = userName.getText();
        String userType;
        int aptScore=0;
        int artScore=0;
        int sciScore=0;
        int mathScore=0;
        if (studentBtn.isSelected()) {
            userType = studentBtn.getText();
        } else {
            userType = adminBtn.getText();
        }
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || userType.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO users VALUES (" +
                "\"" + email + "\"," +
                "\"" + password + "\"," +
                "\"" + name + "\"," +
                "\"" + userType + "\"," +
                "\"" + aptScore + "\"," +
                "\"" + artScore + "\"," +
                "\"" + sciScore + "\"," +
                "\"" + mathScore + "\"" +
                ");";
        System.out.println(qu);

        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Registration Successful!");
            alert.showAndWait();
            closeStage();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Registration Failed! Try Again.");
            alert.showAndWait();
        }
    }


    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void closeStage(){
        ((Stage)registerButton.getScene().getWindow()).close();
    }

}
