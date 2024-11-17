package tempest_foundation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

import tempest_foundation.ClassElements.AutoGrader;
import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.ClassElements.Variable;
import tempest_foundation.ClassElements.Visibility;
import tempest_foundation.SubmissionElements.Submission;
import tempest_foundation.Testing.AccessorTest;
import tempest_foundation.Testing.CompliationCheck;
import tempest_foundation.Testing.VariableTest;
/**
 * Hello Jura-Tempest Federation!
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

        
        // System.out.println(s.getClass(0).getTotalGrade());

    }
}
