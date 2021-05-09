import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;

class PartB{
	public static void main(String args[]){
		String factory_t = "";
		String factory_r = "";
		String worker = "";
		String name = "";
		PreparedStatement ps;
		Scanner scan = new Scanner(System.in);

		//Connect to Databse
		ConnectToDBGV gv = new ConnectToDBGV();

		//Get DBs Connections
		Connection conn = gv.getConn();
		
		try{

			System.out.print("\nEnter Factory ID of Factory Transferring Worker: ");
			factory_t = scan.nextLine();
			System.out.print("\nEnter Factory ID of Factory Receiving Worker: ");
			factory_r = scan.nextLine();
			System.out.print("\nEnter Worker ID : ");
			worker = scan.nextLine();
						
			String sql = "SELECT Name_W FROM Worker WHERE FactoryID_W = ? AND WorkerID = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, factory_t);
			ps.setString(2, worker);
			
			//Step 6 Close the Database Connection
			ResultSet result = ps.executeQuery();
	
			
			if (result.next())
			{
				name = result.getString(1);
				
				sql = "SELECT * FROM FACTORY WHERE FactoryID = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, factory_r);
				result = ps.executeQuery();
				
				if(result.next()) 
				{
					sql = "DELETE FROM WORKER WHERE WorkerID = ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, worker);
					ps.executeQuery();
					
					sql = "INSERT INTO WORKER VALUES(?, ?, ?)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, worker);
					ps.setString(2, factory_r);
					ps.setString(3, name);
					ps.executeQuery();
					
					System.out.println("\n***Worker Transferred.");
				} else
					System.out.println("\n***No such factory exists to transfer this employee to.");
				
			} else
				System.out.println("\n***No such employee works at that factory.");
			//Step 6 Close the Database Connection
			gv.closeConnection();
		} 
		catch(Exception e){
			System.out.println(e);
		}
	}	
}