package tempest_foundation.SubmissionElements;

import java.util.ArrayList;

import tempest_foundation.ClassElements.ClassE;

/**
 * An abstract class representing a snippet of code or content that is graded and commented on.
 * 
 * <p>This class serves as a base for elements that require grading, comments, 
 * and compliance with the {@link ClassE} interface. It provides shared functionalities 
 * such as managing grades, comments, and a total score.</p>
 */
public abstract class MarkSnippet implements ClassE {
    protected double grade=0.0;

    /**
     * A list of comments related to this snippet.
     */
    protected ArrayList<String> comment;
    
    protected double total=0;

    public  MarkSnippet(){
        this.comment = new ArrayList<>();
    }

     /**
     * Adds a grade to the current grade for this snippet.
     *
     * @param grade the grade to add.
     */
    public void addGrade(double grade){
        this.grade+=grade;

    }

    /**
     * Adds a comment to the list of comments for this snippet.
     *
     * @param comment the comment to add.
     */
    public void addComment(String comment){
        this.comment.add(comment);

    }

    public void setTotal(Double total){this.total = total;}
    public double getTotal(){return total;}

    public double getGrade(){return grade;}

    /**
     * Retrieves the list of comments for this snippet.
     *
     * @return the list of comments.
     */
    public ArrayList<String> getComment(){return comment;}
}
