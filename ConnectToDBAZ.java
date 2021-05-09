import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToDBAZ {

    private Connection conn;
    private String DB_name;
    
    public ConnectToDBAZ(){

        this.DB_name = "AZ DataBase";

        try{
            System.out.printf("\nConnecting to %s...\n\n", DB_name);

            //Step 2 Load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            //Step 3 Create the connection object
            this.conn = DriverManager.getConnection("jdbc:oracle:thin:@dbsvcs.cs.uno.edu:1521:orcl", "scberger", "ddTF7znq");
            
            System.out.printf("Connected to %s!\n\n", DB_name);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConn(){
        return this.conn;
    }

    public void closeConnection(){
        try{
            conn.close();
            System.out.printf("\nConnection to %s Closed!\n", DB_name);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}