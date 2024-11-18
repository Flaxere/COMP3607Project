package tempest_foundation.ClassElements;

import java.util.ArrayList;

import tempest_foundation.DocumentGenerator;
import tempest_foundation.Testing.TestSuite;
import tempest_foundation.SubmissionElements.Submission;
import tempest_foundation.Testing.SystemEvaluation;
import tempest_foundation.Testing.TestEnum;

public class AutoGrader { //Facade pattern

    public synchronized void processAssignments(ArrayList<Submission> submissions) throws Exception{
        DocumentGenerator d = new DocumentGenerator();
        TestSuite evaluator = new TestSuite();

        for(Submission s: submissions) {

            long startTime = System.nanoTime();

            evaluator.executeTests(s);
            d.createDocument(s);

            long endTime = System.nanoTime();

            SystemEvaluation.addOverheadTime(startTime, endTime, s.getStudentId(), TestEnum.GRADING);
        }
    }
}