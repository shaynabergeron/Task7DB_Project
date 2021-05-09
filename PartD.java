import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PartD {

    public static void main(String [] args){

        //Connect to Databases
        ConnectToDBGV gv = new ConnectToDBGV();
        ConnectToDBAZ az = new ConnectToDBAZ();

        //Get DBs Connections
        Connection connGv = gv.getConn();
        Connection connAz = az.getConn();

        try{

            connGv.setAutoCommit(false);
            connAz.setAutoCommit(false);

            /** Get CIO information from GV Database */
            String sql              = "SELECT * FROM EXECUTIVE WHERE Ex_position = ?";
            PreparedStatement ps    = connGv.prepareStatement(sql);
            ps.setString(1, "CIO");
            ResultSet result        = ps.executeQuery();

            int gvCIOId         = -1;
            String gvCIOName    = "";

            while (result.next()){
                gvCIOId     = result.getInt(1);
                gvCIOName   = result.getString(2);
                System.out.println(
                    "Current CIO in GV: " + gvCIOId + ", " + gvCIOName
                );
            }

            /** Delete CIO from GV Database */
            sql     = "DELETE FROM EXECUTIVE WHERE Ex_id = ? AND Ex_name = ?";
            ps      = connGv.prepareStatement(sql);
            ps.setInt   (1, gvCIOId);
            ps.setString(2, gvCIOName);
            ps.execute();

            /** Get CIO information from AZ Database */
            sql     = "SELECT * FROM EXECUTIVE WHERE Ex_position = ?";
            ps      = connAz.prepareStatement(sql);
            ps.setString(1, "CIO");
            result  = ps.executeQuery();
            
            int azCIOId         = -1;
            String azCIOName    = "";

            while (result.next()){
                azCIOId     = result.getInt(1);
                azCIOName   = result.getString(2);
                System.out.println(
                    "Current CIO in AZ: " + azCIOId + ", " + azCIOName
                );
            }

            /** Delete CIO from AZ Database */
            sql     = "DELETE FROM EXECUTIVE WHERE Ex_id = ? AND Ex_name = ?";
            ps      = connAz.prepareStatement(sql);
            ps.setInt   (1, azCIOId);
            ps.setString(2, azCIOName);
            ps.execute();

            /** INSERT NEW CIO IN THE GV Database */
            sql     = "INSERT INTO EXECUTIVE VALUES (?, ?, ?)";
            ps      = connGv.prepareStatement(sql);
            ps.setInt   (1, azCIOId);
            ps.setString(2, azCIOName);
            ps.setString(3, "CIO");
            ps.execute();

            /** INSERT NEW CIO IN THE AZ Database */
            sql     = "INSERT INTO EXECUTIVE VALUES (?, ?, ?)";
            ps      = connAz.prepareStatement(sql);
            ps.setInt   (1, gvCIOId);
            ps.setString(2, gvCIOName);
            ps.setString(3, "CIO");
            ps.execute();

            /** GET UPDATE CIO IN THE GV DATABASE */
            sql = "SELECT * FROM EXECUTIVE WHERE Ex_position = ?";
            ps  = connGv.prepareStatement(sql);
            ps.setString(1, "CIO");
            result = ps.executeQuery();

            System.out.println("---");
            while (result.next()){
                System.out.println(
                    "New GV CIO: " + result.getInt(1) + ", " + result.getString(2) + ", " + result.getString(3)
                );
            }

            /** GET UPDATE CIO IN THE AZ DATABASE */
            sql = "SELECT * FROM EXECUTIVE WHERE Ex_position = ?";
            ps  = connAz.prepareStatement(sql);
            ps.setString(1, "CIO");
            result = ps.executeQuery();

            while (result.next()){
                System.out.println(
                    "New AZ CIO: " + result.getInt(1) + ", " + result.getString(2) + ", " + result.getString(3)
                );
            }

            connGv.commit();
            gv.closeConnection();
            connAz.commit();
            az.closeConnection();
        
        }catch(Exception e){

            //rollback if there was any issue
            try{
                connGv.rollback();
                connAz.rollback();
            }catch(Exception rbe){
                rbe.printStackTrace();
            }
            
            e.printStackTrace();
        }
    }
    
}