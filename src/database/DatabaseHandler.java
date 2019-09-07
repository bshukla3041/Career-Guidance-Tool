package database;

import javax.swing.*;
import java.sql.*;

public final class DatabaseHandler {

    private static DatabaseHandler handler=null;

    private static final String DB_URL = "jdbc:sqlite:cgt.db";
    private static Connection conn = null;
    private static Statement stmt = null;

    private DatabaseHandler() {
        createConnection();
//        setupUserTable();
//        setupQuestionTable();
    }

    public static DatabaseHandler getInstance(){
        if(handler==null){
            handler=new DatabaseHandler();
        }
        return handler;
    }

    private void createConnection() {
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Database Connection Successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupUserTable() {
        String TABLE_NAME = "users";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                stmt.execute("CREATE TABLE "+TABLE_NAME+"(" +
                        "email varchar(50) primary key,\n" +
                        "password varchar(30),\n" +
                        "name varchar(200),\n" +
                        "user_type varchar(10)" +
                        "apt_score int,\n" +
                        "art_score int,\n" +
                        "sci_score int,\n" +
                        "math_score int,\n" +
                        ")");
            }
        } catch (SQLException e) {
            System.out.println("Database Creation failed.");
        }

    }

    private void setupQuestionTable() {
        String TABLE_NAME = "questions";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                stmt.execute("CREATE TABLE "+TABLE_NAME+"(" +
                        "prob_stmt varchar(200) primary key,\n" +
                        "optionA varchar(100),\n" +
                        "optionB varchar(100),\n" +
                        "optionC varchar(100),\n" +
                        "optionD varchar(100),\n" +
                        "answer varchar(100),\n" +
                        "category varchar(100)" +
                        ")");
            }
        } catch (SQLException e) {
            System.out.println("Database Creation failed.");
        }

    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }

}




