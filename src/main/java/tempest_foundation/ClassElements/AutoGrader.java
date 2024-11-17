package tempest_foundation.ClassElements;

import java.util.ArrayList;

import tempest_foundation.DocumentGenerator;
import tempest_foundation.FileReader;
import tempest_foundation.TestSuite;
import tempest_foundation.SubmissionElements.Submission;

public class AutoGrader {//Facade pattern
    public void processAssignments(ArrayList<Submission> submissions) throws Exception{
        DocumentGenerator d = new DocumentGenerator();
        TestSuite evaluator = new TestSuite();

        for(Submission s: submissions){
            evaluator.executeTests(s);
            d.createDocument(s);
        }
        
    }
}
