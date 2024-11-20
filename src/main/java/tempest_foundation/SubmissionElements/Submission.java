package tempest_foundation.SubmissionElements;
import java.util.ArrayList;

import tempest_foundation.ClassElements.ClassDetails;

/**
 * Represents a submission made by a student, containing a collection of class details.
 * 
 * <p>This class manages the student's ID, the list of submitted classes, 
 * and provides methods to retrieve, add, and compute grades for the submission.</p>
 */
public class Submission {
    
    /**
     * The unique identifier for the student making the submission, to be read in from file name.
     */
    private String studentID;

     /**
     * A list of classes included in the student's submission.
     */
    private ArrayList<ClassDetails> classes;

    /**
     * Constructs a new submission for a student with an empty list of classes.
     *
     * @param studentID the unique ID of the student.
     */
    public Submission(String studentID){
        this.studentID = studentID;
        this.classes = new ArrayList<>();
    }

   
     /**
     * Constructs a new submission for a student with the specified classes.
     *
     * @param studentID the unique ID of the student.
     * @param classes   the list of class details included in the submission.
     */
    public Submission(String studentID, ArrayList<ClassDetails> classes){
        this.studentID = studentID;
        this.classes = classes;
    }   

    public void addClass(ClassDetails c){classes.add(c);}


     /**
     * Retrieves a class by its name from the submission.
     *
     * @param incomingClassName the name of the class to retrieve.
     * @return the matching class details if found, or {@code null} if not found.
     */
    public ClassDetails getClass(String incomingClassName){
        incomingClassName=incomingClassName.replaceAll("\\s", "");
        incomingClassName = incomingClassName.replaceAll("[(){}]", "");
        for(ClassDetails currentClass: classes){
            String className = currentClass.getClassName().replaceAll("\\s", "");
            className = className.replaceAll("[(){}]", "");
            if(incomingClassName.equalsIgnoreCase(className)){
                return currentClass;
            }
        }
        return null;
    }


    public ArrayList<ClassDetails> getClasses(){return classes;}

    /**
     * Retrieves a class by its index in the list of classes.
     *
     * @param num the index of the class.
     * @return the class details at the specified index.
     */
    public ClassDetails getClass(int num){
        return classes.get(num);
    }

    /**
     * Computes the total grade for all classes in the submission.
     *
     * @return the total grade.
     */
    public double getGrade(){
        double grade =0;
        for(ClassDetails c:classes)
            grade+=c.getTotalGrade();
        return grade;
    }


    public String getStudentId(){return studentID;}

    /**
     * Retrieves the number of classes in the submission.
     *
     * @return the number of classes.
     */
    public int getNumClasses(){return classes.size();}

    /**
     * Returns a string representation of the submission.
     *
     * @return a string describing the submission, including the student ID, 
     *         number of classes, and the list of classes.
     */
    public String toString(){return "Student " + studentID + " - " + getNumClasses() + " Classes\t=>" + getClasses();}
}
