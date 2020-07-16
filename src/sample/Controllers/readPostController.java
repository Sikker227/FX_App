package sample.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.DB;

public class readPostController {

    @FXML
    private Label title;

    @FXML
    private Label text;

    private DB db = new DB();

    @FXML
    private Button exit_btn;

    public static String title_n;
    public static String text_n;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        exit_btn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/sample/scene/news.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Какая-то программа");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
                primaryStage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        title.setText(db.getTitle(title_n));
        text.setText(db.getText(text_n));
    }
}
