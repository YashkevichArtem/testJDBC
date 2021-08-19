package databaseConnect;

import java.sql.*;

public class JDBCConnection {

    private static final String url = "jdbc:mysql://localhost:3306/sakila";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static Connection connectToDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connection to DB succesful!");
        } catch (ClassNotFoundException e) {

        } catch (SQLException sqlEx){
            System.err.println("Connection to DB failed!\n" + sqlEx.getMessage());
        }

        return conn;
    }

    public static void closeConnection(){
        if (conn != null){
                try {
                    conn.close();
                    System.out.println("Connection to DB closed successfully");
                } catch (SQLException se) {
                    System.err.println("Connection to DB was not closed. Reason:\n" + se.getMessage());
                }
        }

        if (stmt != null){
                try {
                    stmt.close();
                    System.out.println("Statement closed succesfully");
                } catch (SQLException se) {
                    se.printStackTrace();
                    System.err.println("Statement was not closed. Reason:\n" + se.getMessage());
                }
            }

        if (rs != null){
            try {
                rs.close();
                System.out.println("ResultSet closed succesfully!");
            } catch (SQLException se) {
                System.err.println("ResultSet was not closed. Reason:\n" + se.getMessage());
            }
        }
    }

    public static void createTable(String query){
        try {
            stmt = connectToDB().prepareStatement(query);
            System.out.println("Send request to DB: " + query);
            stmt.executeUpdate(query);
            System.out.println("Table was created successfully");
        } catch (SQLException se) {
            System.err.println("Table was not created. Reason: \n" + se.getMessage());
        }
    }

    public static void dropTable(String tableName){
        String query = "DROP TABLE " + tableName;
        try {
            stmt = connectToDB().prepareStatement(query);
            System.out.println("Send request to DB: " + query);
            stmt.executeUpdate(query);
            System.out.println("Table was deleted succesfully!");
        } catch (SQLException se) {
            System.err.println("Table was not deleted. Reason:\n" + se.getMessage());
        }
    }

    public static ResultSet selectFromTable(String query){
        try {
            stmt = connectToDB().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Send request to DB: " + query);
            rs = stmt.executeQuery(query);
            //перемещение курсора к следующей строке значений, удовлетворяющих запросу
            rs.next();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return rs;
    }

    public static void insertIntoTable(String query) {
        try {
            stmt = connectToDB().createStatement();
            System.out.println("Send request to DB: " + query);
            stmt.executeUpdate(query);
            System.out.println("New data was inserted into table successfully");
        } catch (SQLException se) {
            System.err.println("New data was not inserted into table. Reason:\n" + se.getMessage());
        }
    }

    public static void updateInTable(String query) {
        try {
            stmt = connectToDB().createStatement();
            System.out.println("Send request to DB: " + query);
            stmt.executeUpdate(query);
            System.out.println("Data in table was updated successfully");
        } catch (SQLException se) {
            System.err.println("Data in table was not updated. Reason:\n" + se.getMessage());
        }
    }

    public static void deleteFromTable(String query) {
        try {
            System.out.println("Send request to DB: " + query);
            stmt = connectToDB().createStatement();
            stmt.executeUpdate(query);
            System.out.println("Data from table was deleted successfully");
        } catch (SQLException se) {
            System.err.println("Data from table was not deleted. Reason:\n" + se.getMessage());
        }
    }

}
