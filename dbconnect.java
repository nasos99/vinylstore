package vinylstore;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbconnect {
    
    
    
    String msAccDB = "vinylstore.accdb"; // path to the DB file
    String dbURL = "jdbc:ucanaccess://" + msAccDB;
    
    public void init(){
        // Step 1: Loading or registering JDBC driver class
        try {
           // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {
            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
        
//        try{
//            connection = DriverManager.getConnection(dbURL);
//        }
//        catch(SQLException sqlex){
//            System.err.println(sqlex.getMessage());
//        }
    
    }
    
    public String getURL(){
        return dbURL;
    }
    
    
//    public getConnection(){
//        return connection;
//    }
    
}
