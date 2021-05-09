import java.sql.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Person{
    private Connection conn;
    public Person(Connection con)
    {
        this.conn = con;
    }
    public ArrayList getPerson(int P_PositionCode){
        ArrayList<String> list = new ArrayList();
        try{
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("select * from person where person.PersonID = "+P_PositionCode);
        int colcount = result.getMetaData().getColumnCount();
        
            while(result.next()){
                for(int i = 1; i != colcount+1;i++){
                    list.add(result.getString(i));
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return list;
    }
    public String skillidToSkillname(String RequiredSkill){
        String skillname = "";
        try{
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("select Title from skills where SkillCode = "+RequiredSkill);
        while(result.next()){
            skillname = result.getString(1);
        }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return skillname;
    }

    public ArrayList haskills(int P_PositionCode){
        ArrayList<String> skills = new ArrayList<>();
        try{
        
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("select * from Has_Skill where PersonID = "+P_PositionCode);
            while(result.next()){
                    skills.add(result.getString(2));
            }
        
        }
        catch(Exception e){
            System.out.println(e);
        }
        return skills;
    }
    
    public ArrayList skillFromTakenCourses(String className){
        ArrayList<String> skills = new ArrayList<>();
        try{
        
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("select T_SkillCode from teaches where T_CourseCode in(select CourseCode from course where Title = \'"+className+"\')");
            while(result.next()){
                    skills.add(result.getString(1));
            }
        
        }
        catch(Exception e){
            System.out.println(e);
        }
        return skills;
    }
    public void insertSkills(int PersonID,ArrayList<String> list){
        for(String skill:list){
            String sql = "INSERT INTO Has_Skill values("+PersonID+","+skill+")";
            try{
                PreparedStatement ps = this.conn.prepareStatement(sql);
                ps.execute();
                ps.close();
                }catch(Exception e){
                    System.out.println(e);
                }
        }
    }
    public int courseNum(String className){
        int corusenum = 0;
        try{
        
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("select CourseCode from course where Title = \'"+className+"\'");
            while(result.next()){
                corusenum = result.getInt(1);
            }
        
        }
        catch(Exception e){
            System.out.println(e);
        }
        return corusenum;
    }
    public int coruseNumFromSkill(String RequiredSkill){
        int corusenum = 0;
        try{
        
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("select T_CourseCode from teaches where T_SkillCode = \'"+RequiredSkill+"\'");
            while(result.next()){
                corusenum = result.getInt(1);
            }
        
        }
        catch(Exception e){
            System.out.println(e);
        }
        return corusenum;
    }
    public void insertintotakes(int P_PositionCode, int course, String findate){
        String sql = SQLformat(P_PositionCode, "takes", course, findate);
        try{
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.execute();
        ps.close();
        }catch(Exception e){
            System.out.println(e);
        }

    }
    public void insertIntoWorks(int P_PositionCode, String JobCode){
        String sql = SQLformat(P_PositionCode, "works", JobCode);
        try{
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        String monthYear = month+"/"+year;
        PreparedStatement ps= this.conn.prepareStatement(sql);
        ps.execute();
        ps.close();
        }catch(Exception e){
            System.out.println(e);
        }

    }
    public String SQLformat(int P_PositionCode, String table, int one, String two){
        String sql =  "INSERT INTO "+table+" VALUES("+P_PositionCode+", "+one+", \'"+two+"\')";
        return sql;
    }
    public String SQLformat(int P_PositionCode, String table, String one){
        String sql =  "INSERT INTO "+table+" VALUES("+P_PositionCode+", \'"+one+"\')";
        return sql;
    }

}