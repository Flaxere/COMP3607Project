package tempest_foundation.SubmissionElements;

import java.util.ArrayList;

import tempest_foundation.ClassElements.ClassE;

public abstract class MarkSnippet implements ClassE {
    protected double grade=0;
    protected ArrayList<String> comment;

    public void addGrade(double grade){
        this.grade+=grade;

    }
    public void addComment(String comment){
        this.comment.add(comment);

    }

    public double getGrade(){return grade;}
    public ArrayList<String> getComment(){return comment;}
}
