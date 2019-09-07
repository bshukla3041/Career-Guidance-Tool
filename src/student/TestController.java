package student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import user.UserController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static login.LoginController.currentUser;

public class TestController implements Initializable {

    DatabaseHandler databaseHandler;

    @FXML
    public Label pStatement;
    @FXML
    public Label qNumber;
    @FXML
    public JFXRadioButton optionA;
    @FXML
    public JFXRadioButton optionB;
    @FXML
    public JFXRadioButton optionC;
    @FXML
    public JFXRadioButton optionD;
    @FXML
    public JFXButton submitBtn;
    @FXML
    public JFXButton nextBtn;

    public static int aptScore = 0;
    public static int artScore = 0;
    public static int sciScore = 0;
    public static int mathScore = 0;

    private ResultSet resultSet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
        setProblem();
    }


    private void setProblem() {
        String qu = "SELECT * FROM questions";
        resultSet = databaseHandler.execQuery(qu);
        try {
            if (resultSet.next()) {
                String ps = resultSet.getString("prob_stmt");
                String oa = resultSet.getString("optionA");
                String ob = resultSet.getString("optionB");
                String oc = resultSet.getString("optionC");
                String od = resultSet.getString("optionD");

                pStatement.setText(ps);
                optionA.setText(oa);
                optionB.setText(ob);
                optionC.setText(oc);
                optionD.setText(od);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void handleProbSubmit(ActionEvent actionEvent) {
        try {
            String cat = resultSet.getString("category");
            String ans = resultSet.getString("answer");
            String answer = null;
            if (optionA.isSelected()) {
                answer = optionA.getText();
            } else if (optionB.isSelected()) {
                answer = optionB.getText();
            } else if (optionC.isSelected()) {
                answer = optionC.getText();
            } else if (optionD.isSelected()) {
                answer = optionD.getText();
            }
            if (ans.equals(answer)) {
                if (cat.equals("Aptitude")) {
                    aptScore = aptScore + 1;
                } else if (cat.equals("Arts")) {
                    artScore = artScore + 1;
                } else if (cat.equals("Science")) {
                    sciScore = sciScore + 1;
                } else if (cat.equals("Maths")) {
                    mathScore = mathScore + 1;
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Answer submitted successfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void handleNextBtn(ActionEvent actionEvent) {
        try {
            if (resultSet.next()) {
                String ps = resultSet.getString("prob_stmt");
                String oa = resultSet.getString("optionA");
                String ob = resultSet.getString("optionB");
                String oc = resultSet.getString("optionC");
                String od = resultSet.getString("optionD");

                pStatement.setText(ps);
                optionA.setText(oa);
                optionB.setText(ob);
                optionC.setText(oc);
                optionD.setText(od);
            }else{
                String qu = "UPDATE users SET " +
                        "apt_score=\"" + aptScore + "\"," +
                        "art_score=\"" + artScore + "\"," +
                        "sci_score=\"" + sciScore + "\"," +
                        "math_score=\"" + mathScore + "\"" +
                        "WHERE email=\""+currentUser+"\"";
                System.out.println(qu);

                if (databaseHandler.execAction(qu)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Test Finished! Please check your result analysis.");
                    alert.showAndWait();
//                    loadWindow("student.fxml", "Student Home | CGT");
                    closeStage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Test Submission Failed! Try Again.");
                    alert.showAndWait();
                    closeStage();
                }
//                loadWindow("student.fxml", "Student Home | CGT");
//                closeStage();
            }
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
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
        ((Stage)nextBtn.getScene().getWindow()).close();
    }
}
