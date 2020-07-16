package sample.Controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DB;
import sample.User;

public class NewsController {

    @FXML
    private Button user_cab, btn_add;

    @FXML
    private Button btn_exit;

    @FXML
    private VBox paneVbox;

    private DB db = new DB();


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        ResultSet res = db.getAll();

        while (res.next()){
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/sample/scene/article.fxml"));

                Label title = (Label) node.lookup("#title");
                title.setText(res.getString("title"));

                Label intro = (Label) node.lookup("#intro");
                intro.setText(res.getString("intro"));

                final Node nodeSet = node;

                node.setOnMouseEntered(event -> {
                    nodeSet.setStyle("-fx-background-color: #F8F8C4");
                });

                node.setOnMouseClicked(event -> {
                    String text = title.getText();
                    try {
                        db.getPostId(text);
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/sample/scene/readPost.fxml"));
                            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            primaryStage.setTitle("Какая-то программа");
                            primaryStage.setScene(new Scene(root, 600, 400));
                            primaryStage.show();
                            primaryStage.setResizable(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                node.setOnMouseExited(event -> {
                    nodeSet.setStyle("-fx-background-color: #FFFFE0");
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            HBox hbox = new HBox();
            hbox.getChildren().add(node);
            hbox.setAlignment(Pos.BASELINE_CENTER);
            paneVbox.getChildren().add(hbox);
            paneVbox.setSpacing(10);

        }
        btn_exit.setOnAction(event -> {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("User.settings");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new User(""));
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/scene/sample.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Какая-то программа");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
                primaryStage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        user_cab.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/scene/user_cab.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Какая-то программа");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
                primaryStage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_add.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/scene/addArt.fxml"));
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

