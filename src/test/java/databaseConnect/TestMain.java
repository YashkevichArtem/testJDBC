package databaseConnect;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class TestMain {

    @BeforeEach
    public void setUp(){
    }

    @AfterEach
    public void tearDown(){
        JDBCConnection.closeConnection();
    }
}