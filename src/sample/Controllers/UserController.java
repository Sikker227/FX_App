package sample.Controllers;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.DB;



public class UserController {

    @FXML
    private TextField user_log;

    @FXML
    private TextField user_email;

    @FXML
    private PasswordField user_pass;

    @FXML
    private Button newData_btn;

    @FXML
    private Button exit_btn;

    private DB db = new DB();

    @FXML
    void initialize() {
        newData_btn.setOnAction(event -> {
            user_log.setStyle("-fx-border-color: #fafafa");
            user_email.setStyle("-fx-border-color: #fafafa");
            user_pass.setStyle("-fx-border-color: #fafafa");
            newData_btn.setText("Изменить данные");

            if(user_log.getCharacters().length() <=3 ) {
                user_log.setStyle("-fx-border-color: red");
                return;
            } else if(user_email.getCharacters().length() <=5 ) {
                user_email.setStyle("-fx-border-color: red");
                return;
            } else if(user_pass.getCharacters().length() <=5 ) {
                user_pass.setStyle("-fx-border-color: red");
                return;
            }

            String pass = null;
            try {
                pass = md5String(user_pass.getCharacters().toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            try {
                boolean isReg = db.updateUser(user_log.getCharacters().toString(), user_email.getCharacters().toString(), pass);
                if (isReg) {
                    user_log.setText("");
                    user_email.setText("");
                    user_pass.setText("");
                    newData_btn.setText("Готово");
                } else {
                    newData_btn.setText("Введите другой логин");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
//        exit_btn.setOnAction(event -> {
//            exitWindow();
//        });
    }

    public static String md5String(String pass) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        messageDigest =  MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(pass.getBytes());
        digest = messageDigest.digest();
        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32)
            md5Hex = "0" + md5Hex;

        return md5Hex;
    }

//    public void exitWindow() {
//        exit_btn.getScene().getWindow().hide();
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/sample/scene/sample.fxml"));
//
//        try {
//            loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Parent root = loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.showAndWait();
//
//    }
}
