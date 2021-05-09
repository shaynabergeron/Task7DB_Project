import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PartC {
    
    public static void main(String[] args){
        
        //Connect to Databse
        ConnectToDBGV gv = new ConnectToDBGV();

        //Get DBs Connections
        Connection connGv = gv.getConn();

        try{
            connGv.setAutoCommit(false);

            /** EM BUYS $100 million worth of products from GV */

            int contractId          = getContractId(connGv);
            String sql              = "INSERT INTO CONTRACT VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps    = connGv.prepareStatement(sql);

            ps.setInt       (1, contractId);
            ps.setInt    (2, 19292);
            ps.setString    (3, "19/06/2020");
            ps.setString       (4, "100000000");
           // ps.setString    (5, "once");
           // ps.setString    (6, "00004");
            ps.setString    (5, "Weekly");
            ps.execute();
            
            System.out.println("\n*********ACTION*********\n");
            System.out.println("EM HAS BOUGHT 100,000,000 WORTH OF PRODUCTS FROM GV");
            System.out.println("Contract Code: " + contractId);
            System.out.print("RECORD: ");
            sql = "SELECT * FROM CONTRACT WHERE ContractID = ?";
            ps  = connGv.prepareStatement(sql);
            ps.setInt(1, contractId);
            ResultSet result = ps.executeQuery();

            while (result.next()){
                System.out.println(
                    result.getInt(1) + ", " + 
                    result.getString(2) + ", " + 
                    result.getString(3) + ", " +
                    result.getInt(4)    + ", " + 
                    result.getString(5) 
                  //  result.getString(6) + ", " +
                  //  result.getInt(7)
                );
            }
            System.out.println();

            /** GV BUYS $75 Million products worth from EM */

            sql                     = "INSERT INTO PURCHASE VALUES (?, ?, ?, ?, ?, ?, ?)";
            int purchaseNum         = getPurchaseNum(connGv);
            int supplierOrderNum    = getSupplierOrderNum(connGv);
            ps                      = connGv.prepareStatement(sql);
            
            ps.setInt    (1, purchaseNum);
            ps.setInt (2, 839);
            ps.setString    (3, "EM purchase made");
            ps.setString (4, "05/2021");
            ps.setString (5, "05/2021");
           // ps.setString (6, "Purchase Made");
            ps.setInt (6, 787);
            ps.setInt    (7, 409);
            ps.execute();
            
            System.out.println("\n*********ACTION*********\n");
            System.out.println("GV HAS BOUGHT 75,000,000 WORTH OF PRODUCTS FROM GV");
            System.out.println("Purchase Number: " + purchaseNum);
            System.out.print("RECORD: ");
            sql = "SELECT * FROM PURCHASE WHERE PurchaseNum = ?";
            ps  = connGv.prepareStatement(sql);
            ps.setInt(1, purchaseNum);
            result = ps.executeQuery();

            while (result.next()){
                System.out.println(
                    result.getInt(1)    + ", " + 
                    result.getString(2) + ", " + 
                    result.getInt(3)    + ", " +
                    result.getString(4) + ", " + 
                    result.getString(5) + ", " + 
                    result.getString(6) + ", " +
                    result.getString(7) 
                );
            }
            System.out.println();

            //Close DBs Connections
            connGv.commit();
            gv.closeConnection();
        
        }catch (Exception e){
            //rollback if there was any issue
            try{
                connGv.rollback();
            }catch(Exception rbe){
                rbe.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    public static int getSupplierOrderNum(Connection conn){
        int maxSupplierOrderNum = 0;
        try{
            Statement state     = conn.createStatement();
            ResultSet result    = state.executeQuery("SELECT count(*) from PURCHASE");
            while(result.next()){
                maxSupplierOrderNum = (result.getInt(1));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return maxSupplierOrderNum += 1;
    }

    public static int getPurchaseNum(Connection conn){
        int maxPurchaseNum = 0;
        try{
            Statement state     = conn.createStatement();
            ResultSet result    = state.executeQuery("SELECT count(*) from PURCHASE");
            while(result.next()){
                maxPurchaseNum = (result.getInt(1));
        }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return maxPurchaseNum += 10000;
    }

    public static int getContractId(Connection conn){
        int maxContractId = 0;
        try{
            Statement state     = conn.createStatement();
            ResultSet result    = state.executeQuery("SELECT count(*) from CONTRACT");
            while(result.next()){
                maxContractId = (result.getInt(1));
        }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return maxContractId += 1;
    }
}