package tempest_foundation.SubmissionElements;

public abstract class MarkSnippet {
    protected int grade;
    protected String comment;

    public void setSnippet(int grade, String comment){
        this.grade = grade;
        this.comment = comment;
    }

    public int getGrade(){return grade;};
    public String getComment(){return comment;}
}
