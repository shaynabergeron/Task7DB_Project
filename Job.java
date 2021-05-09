import java.sql.*;
import java.util.*;

public class Job{
    private Connection conn;
    public Job(Connection con)
    {
        this.conn = con;
    }

    public void insertJob(int jskillcode, String jobName, String startDate,String endDate, int payLow, int payHigh, String payType, String jobDescription, int[] skills){
       int max = getMaxRow();
       try{
           
        String sql = "INSERT INTO position VALUES("+(max+1)+", \'"+jobName+"\', \'"+startDate+"\',\'"+endDate+"\', "+payLow+", "+payHigh+", \'"+payType+"\', \'"+jobDescription+"\')";
           PreparedStatement  state = conn.prepareStatement(sql);
           state.execute();
           state.close();

        sql = "INSERT INTO jobs VALUES("+(max+1)+","+(max+1)+","+jskillcode+","+67+",\'full time\')";
           state = conn.prepareStatement(sql);
           state.execute();
           state.close();
           
           for(int skill: skills){
           sql = "INSERT INTO requires VALUES("+(max+1)+", "+skill+")";
           state = conn.prepareStatement(sql);
           state.execute();
           state.close();}
       }
       catch(Exception e){
        System.out.println(e);
        }
    }

    public int getMaxRow(){
        int max = 0;
        try{
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT count(*) from jobs");
            while(result.next()){
            max = (result.getInt(1));
        }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return max;
    }

    public ArrayList requiresSkill(int jobid){
        ArrayList<String> rskills = new ArrayList<>();
        try{
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("select skillcode from requires where rsid = "+jobid);
            while(result.next()){
                    rskills.add(result.getString(1));
            }
        
        }
        catch(Exception e){
            System.out.println(e);
        }
        return rskills;
    }

    
}