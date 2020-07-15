package sample;

import java.sql.*;


public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3307";
    private final String DB_NAME = "db_app";
    private final String LOGIN = "mysql";
    private final String PASS = "mysql";

    private Connection dbConn;

    private Connection getDbConn() throws SQLException, ClassNotFoundException {
        String str = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(str, LOGIN, PASS);
        return dbConn;
    }

    public void isConnection() throws SQLException, ClassNotFoundException {
        dbConn = getDbConn();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean userRegistration(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES(?, ?, ?)";

        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login + "'LIMIT 1");
        if (res.next())
            return  false;

        PreparedStatement prSt = getDbConn().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, email);
        prSt.setString(3, password);
        prSt.executeUpdate();
        return true;
    }

    public static String id;

    public void getId(String login) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "'";
        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery(sql);
        while(res.next()) {
            id = res.getString("id");
        }
    }

    public boolean userAuth(String login, String password) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConn().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' AND `password` = '" + password + "'LIMIT 1";
        ResultSet res = statement.executeQuery(sql);
        return res.next();
    }

    public boolean updateUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE `users` SET `login` = ?, `email` = ?, `password` = ? WHERE `id` = ?";

        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1");
        if(res.next())
            return false;

        PreparedStatement prSt = getDbConn().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, email);
        prSt.setString(3, password);
        prSt.setString(4, id);
        prSt.executeUpdate();
        return true;
    }

    public ResultSet getAll() throws SQLException, ClassNotFoundException{
        String sql = "SELECT `title`, `intro` FROM `articles`";
        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;

    }

    public void addArt(String title, String intro, String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`, `views`) VALUES(?, ?, ?, ?)";
        PreparedStatement prSt = getDbConn().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.setString(2, intro);
        prSt.setString(3, text);
        prSt.setInt(4, 15);
        prSt.executeUpdate();

    }
}

