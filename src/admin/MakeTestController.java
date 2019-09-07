package admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MakeTestController implements Initializable {

    DatabaseHandler databaseHandler;

    @FXML
    public JFXButton addBtn;
    @FXML
    public JFXTextArea problemStatement;
    @FXML
    public JFXTextField optionA;
    @FXML
    public JFXTextField optionB;
    @FXML
    public JFXTextField optionC;
    @FXML
    public JFXTextField optionD;
    @FXML
    public JFXTextField answer;
    @FXML
    public JFXTextField category;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
    }


    public void handleAddQuestion(ActionEvent actionEvent) {
        String ps = problemStatement.getText();
        String oa = optionA.getText();
        String ob = optionB.getText();
        String oc = optionC.getText();
        String od = optionD.getText();
        String ans = answer.getText();
        String ct = category.getText();

        if (ps.isEmpty() || oa.isEmpty() || ob.isEmpty() || oc.isEmpty() || od.isEmpty() || ans.isEmpty() || ct.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO questions VALUES (" +
                "\"" + ps + "\"," +
                "\"" + oa + "\"," +
                "\"" + ob + "\"," +
                "\"" + oc + "\"," +
                "\"" + od + "\"," +
                "\"" + ans + "\"," +
                "\"" + ct + "\"" +
                ");";
        System.out.println(qu);

        if (databaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Problem Successfully Added!");
            alert.showAndWait();
            closeStage();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Try again. Problem addition failed!");
            alert.showAndWait();
            closeStage();
        }
    }

    private void closeStage() {
        ((Stage) addBtn.getScene().getWindow()).close();
    }

}
