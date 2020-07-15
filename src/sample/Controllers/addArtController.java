package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DB;

public class addArtController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newArt_field;

    @FXML
    private TextArea intro_field;

    @FXML
    private TextArea text_field;

    @FXML
    private Button btn_add_2;

    @FXML
    private Button btn_exit;

    private DB db = new DB();

    @FXML
    void initialize() {
        btn_add_2.setOnAction(event -> {
            newArt_field.setStyle("-fx-border-color: #fafafa");
            text_field.setStyle("-fx-border-color: #fafafa");
            intro_field.setStyle("-fx-border-color: #fafafa");

            if(newArt_field.getCharacters().length() <=3 ) {
                newArt_field.setStyle("-fx-border-color: red");
                return;
            } else if(text_field.getText().length() <=5 ) {
                text_field.setStyle("-fx-border-color: red");
                return;
            } else if(intro_field.getText().length() <=5 ) {
                intro_field.setStyle("-fx-border-color: red");
                return;
            }


                try {
                    db.addArt(newArt_field.getCharacters().toString(), intro_field.getText(), text_field.getText());
                    Parent root = null;
                    root = FXMLLoader.load(getClass().getResource("/sample/scene/news.fxml"));
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setTitle("Какая-то программа");
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.show();
                    primaryStage.setResizable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
        });

        btn_exit.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/scene/news.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Какая-то программа");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
                primaryStage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
