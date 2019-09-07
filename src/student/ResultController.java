package student;

import database.DatabaseHandler;
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
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static login.LoginController.currentUser;

public class ResultController implements Initializable {

    DatabaseHandler databaseHandler;

    @FXML
    public Button aptResult;
    @FXML
    public Button artResult;
    @FXML
    public Button sciResult;
    @FXML
    public Button mathResult;

    public Label resultText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
        showResult();
    }

    private void showResult() {
        ResultSet rs;
        String query = "SELECT * FROM users WHERE email =\"" + currentUser+"\"";
        try {
            rs = databaseHandler.execQuery(query);
            if (rs.next()) {
                aptResult.setText(rs.getString("apt_score"));
                artResult.setText(rs.getString("art_score"));
                sciResult.setText(rs.getString("sci_score"));
                mathResult.setText(rs.getString("math_score"));

                int apts = Integer.parseInt(aptResult.getText());
                int arts = Integer.parseInt(artResult.getText());
                int scis = Integer.parseInt(sciResult.getText());
                int maths = Integer.parseInt(mathResult.getText());

                if (arts >= scis && arts >= maths) {
                    resultText.setText("You scored best in Arts. You should choose Arts as you field of study for your career.");
                } else if (scis >= arts && scis >= maths) {
                    resultText.setText("You scored best in Science. You should choose Science as you field of study for your career.");
                } else {
                    resultText.setText("You scored best in Mathematics. You should choose Mathematics as you field of study for your career.");
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("No result found!");
                alert.showAndWait();
                loadWindow("/student.fxml", "Home  | CGT");
                closeStage();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No result found!");
            alert.showAndWait();
            loadWindow("student.fxml", "Home  | CGT");
            closeStage();
        }
    }

    private void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeStage() {
        ((Stage) resultText.getScene().getWindow()).close();
    }


}
