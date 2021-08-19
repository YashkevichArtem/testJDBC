package databaseConnect;

import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestDB extends TestMain {

    @Test
    @Order(1)
    public void testCreateTable(){
        String query = "CREATE TABLE customers ("
                + "ID int(3) NOT NULL,"
                + "FIRST_NAME VARCHAR(45) NOT NULL,"
                + "LAST_NAME VARCHAR(45) NOT NULL,"
                + "COUNTRY VARCHAR(45) NOT NULL,"
                + "TOWN VARCHAR(45))";
        JDBCConnection.createTable(query);
    }

    @Test
    @Order(2)
    public void testInsertRequest(){
        String query = "INSERT INTO customers (ID, FIRST_NAME, LAST_NAME, COUNTRY, TOWN) VALUES (123, 'Stepan',"
                + " 'Petrov', 'Belarus', 'Gomel')";
        JDBCConnection.insertIntoTable(query);

        String selectQuery = "SELECT * FROM customers";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        assertAll("Should return inserted data",
                () ->assertEquals("123", rs.getString("ID")),
                () ->assertEquals("Stepan", rs.getString("FIRST_NAME")),
                () ->assertEquals("Petrov", rs.getString("LAST_NAME")),
                () ->assertEquals("Belarus", rs.getString("COUNTRY")),
                () ->assertEquals("Gomel", rs.getString("TOWN")));
    }

    @Test
    @Order(3)
    public void testSearchCustomerName(){
        String query = "SELECT * FROM customers WHERE FIRST_NAME LIKE 's%'";
        ResultSet rs = JDBCConnection.selectFromTable(query);
        assertAll("Should return inserted data",
                () ->assertEquals("Stepan", rs.getString("FIRST_NAME")));

    }

    @Test
    @Order(4)
    public void testUpdateRequest() throws SQLException {
        String query = "UPDATE customers SET TOWN = 'Gomel' WHERE ID='123'";
        JDBCConnection.updateInTable(query);
        String selectQuery = "SELECT TOWN FROM customers WHERE ID=123";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String expectedTown = "Gomel";
        String actualTown = rs.getString("TOWN");
        assertEquals(expectedTown, actualTown, "Actual town is '" + actualTown + "'. Expected - '" + expectedTown + "'.");
    }

    @Test
    @Order(5)
    public void testFoundCountry(){
        String query = "SELECT * FROM customers WHERE COUNTRY='Belarus'";
        ResultSet rs = JDBCConnection.selectFromTable(query);
        assertAll("Should return inserted data",
                () ->assertEquals("Belarus", rs.getString("COUNTRY")));
    }

    @Test
    @Order(6)
    public void testDeleteRequest() {
        String query = "DELETE FROM customers WHERE ID=123";
        JDBCConnection.deleteFromTable(query);
    }

    @Test
    @Order(7)
    public void testDeleteTable() {
        JDBCConnection.dropTable("customers");
    }

}