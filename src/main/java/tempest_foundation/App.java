package tempest_foundation;

import java.util.ArrayList;
import tempest_foundation.ClassElements.AutoGrader;
import tempest_foundation.SubmissionElements.Submission;

/**
 * Hello Jura-Tempest Foundation!
 * App class for the project
 *
 */
public class App 
{ 
    public static void main( String[] args ) throws Exception
    {

        ArrayList<Submission> submissions = new ArrayList<>();
        FileReader f = new FileReader();
        f.readFiles(submissions);
        AutoGrader grader = new AutoGrader();

        grader.processAssignments(submissions);

    }
}