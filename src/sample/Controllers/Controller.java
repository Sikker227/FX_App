package sample.Controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DB;

public class Controller {

    @FXML
    private TextField lodin_reg;

    @FXML
    private TextField email_reg;

    @FXML
    private PasswordField pass_reg;

    @FXML
    private CheckBox confid;

    @FXML
    private Button btn_reg;

    @FXML
    private TextField login_auth;

    @FXML
    private PasswordField pass_auth;

    @FXML
    private Button btn_auth;

    private DB db = new DB();


    @FXML
    void initialize() {
        btn_reg.setOnAction(event -> {
            lodin_reg.setStyle("-fx-border-color: #fafafa");
            email_reg.setStyle("-fx-border-color: #fafafa");
            pass_reg.setStyle("-fx-border-color: #fafafa");
            btn_reg.setText("Зарегистрироваться");

            if(lodin_reg.getCharacters().length() <=3 ) {
                lodin_reg.setStyle("-fx-border-color: red");
                return;
            } else if(email_reg.getCharacters().length() <=5 ) {
                email_reg.setStyle("-fx-border-color: red");
                return;
            } else if(pass_reg.getCharacters().length() <=5 ) {
                pass_reg.setStyle("-fx-border-color: red");
                return;
            } else if(!confid.isSelected()) {
                btn_reg.setText("Поставьте галочку");
                return;
            }

            String pass = null;
            try {
                pass = md5String(pass_reg.getCharacters().toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            try {
                boolean isReg = db.userRegistration(lodin_reg.getCharacters().toString(), email_reg.getCharacters().toString(), pass);
                if (isReg) {
                    db.getId(lodin_reg.getCharacters().toString());
                    lodin_reg.setText("");
                    email_reg.setText("");
                    pass_reg.setText("");
                    btn_reg.setText("Готово");
                    neWindow();
                } else {
                    btn_reg.setText("Введите другой логин");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        btn_auth.setOnAction(event -> {
            login_auth.setStyle("-fx-border-color: #fafafa");
            pass_auth.setStyle("-fx-border-color: #fafafa");

            if(login_auth.getCharacters().length() <=3 ) {
                login_auth.setStyle("-fx-border-color: red");
                return;
            } else if(pass_auth.getCharacters().length() <=5 ) {
                pass_auth.setStyle("-fx-border-color: red");
                return;
            }

            String pass = null;
            try {
                pass = md5String(pass_auth.getCharacters().toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            try {
                boolean isAuth = db.userAuth(login_auth.getCharacters().toString(), pass);
                if (isAuth) {
                    db.getId(login_auth.getCharacters().toString());
                    login_auth.setText("");
                    pass_auth.setText("");
                    btn_auth.setText("Готово");
                    neWindow();
                } else {
                    btn_auth.setText("Пользователь не найден");
                    login_auth.setText("");
                    pass_auth.setText("");
                    login_auth.setStyle("-fx-border-color: red");
                    pass_auth.setStyle("-fx-border-color: red");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });



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

    public void neWindow() {
        btn_reg.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/scene/user_cab.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }
}
