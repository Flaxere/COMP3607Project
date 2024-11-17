package tempest_foundation.SubmissionElements;
import java.util.ArrayList;

import tempest_foundation.ClassElements.*;
public class Submission {
    private String studentID;
    private ArrayList<ClassDetails> classes;

    public Submission(String studentID){
        this.studentID = studentID;
        this.classes = new ArrayList<>();
    }
    public Submission(String studentID, ArrayList<ClassDetails> classes){
        this.studentID = studentID;
        this.classes = classes;
    }   

    public void addClass(ClassDetails c){classes.add(c);}

    public ArrayList<ClassDetails> getClasses(){return classes;}

    public ClassDetails getClass(int num){
        return classes.get(num);
    }

    public double getGrade(){
        double grade =0;
        for(ClassDetails c:classes)
            grade+=c.getTotalGrade();
        return grade;
    }

    public String getStudentId(){return studentID;}

    public int getNumClasses(){return classes.size();}

    public String toString(){
        return "Student " + studentID + " - " + getNumClasses() + " Classes\t=>" + getClasses() ;
    }
    
}
