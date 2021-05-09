import java.sql.*;
import java.util.*;
import java.io.*;
public class Part7a{
    
    public ConnectToDBLD ld  = new ConnectToDBLD();
    public  Connection ldcn = ld.getConn();
    public Person human = new Person(ldcn);
    public  void run(){
        try{
            //get ids
            System.out.println("Enter a pid for the new employee");
            Scanner input = new Scanner(System.in);
            int pid = input.nextInt();
            System.out.println("\n Enter a jid for the new employee");
            int jid = input.nextInt();
            System.out.println("Employee data:\n-------------------------\n"+dataAndSkills(pid)+"\n");
            //get transcript new employee 
            File file = new File("transcript"+pid+".txt");
            Scanner reader = new Scanner(file);
            String transcript = reader.nextLine();
            String[] courses = transcript.split(", ");
            ArrayList<String> skills = new ArrayList<String>();
            for(String course:courses){
                String [] details = course.split(": ");
                skillFromCourses(details[0],skills);
                human.insertintotakes(pid,human.courseNum(details[0]),details[1]);
            }
            ArrayList<String> selftaught = human.haskills(pid);
            int count = 1;
            System.out.println("skills not derived from taken classes\n--------------------");
            for (String skill: selftaught){
                if(!skills.contains(skill)){
                    System.out.println(count+"\t"+human.skillidToSkillname(skill));
                    count = count+1;
                }
            }
            System.out.println("adding skills database......\n--------------------------------");
            human.insertSkills(pid,skills);
            Job jobinques = new Job(ldcn);
            int[] pskill = {4,5,9,7,21,3,10};
            if(count >2){

                while(true){
                System.out.println("employee has too many self-claimed skill would you like to rescind the job offer?\n y for yes\n n for no");
                    char value = input.next().charAt(0);
                if(value == 'y'){
                    System.out.println("Job offer has been recinded");
                    break;
                }
                else if(value == 'n'){
                    System.out.println("You now have a new employee");
                    human.insertIntoWorks(pid,(""+jid));
                    break;
                }
                else{
                    System.out.println("Invalid choice");
                }
            }}
            ArrayList<String> allSkills = human.haskills(pid);
            ArrayList<String> requiredskills = jobinques.requiresSkill(jid);
            ArrayList<String> missingSkills = new ArrayList<String>();
            for (String skill: requiredskills){
                if (!allSkills.contains(skill)){
                    missingSkills.add(skill);
                }
            }
            System.out.println("Recomended courses are:\n---------------------------------------------------------\n");
            for(String skill: missingSkills){
            System.out.println(human.coruseNumFromSkill(skill)+" "+human.skillidToSkillname(skill));
            }            

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public ArrayList<String> dataAndSkills(int pid){
            ArrayList<String> join = human.getPerson(pid);
            return join;
        }
    public void skillFromCourses(String course,ArrayList join){
            join.addAll(human.skillFromTakenCourses(course));
        }
    
}