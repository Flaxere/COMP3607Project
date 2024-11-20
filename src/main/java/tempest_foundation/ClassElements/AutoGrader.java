package tempest_foundation.ClassElements;

import java.util.ArrayList;

import tempest_foundation.DocumentGenerator;
import tempest_foundation.SubmissionElements.Submission;
import tempest_foundation.Testing.SystemEvaluation;
import tempest_foundation.Testing.TestEnum;
import tempest_foundation.Testing.TestSuite;


/**
 * The {@code AutoGrader} class implements a facade pattern to manage the 
 * automated grading process of student submissions. It coordinates the 
 * execution of tests on submissions and the generation of result documents.
 */
public class AutoGrader { 

     /**
     * Processes a list of student submissions by evaluating their work 
     * through test execution and document generation. It also records 
     * the overhead time for grading.
     *
     * @param submissions a list of {@link Submission} objects to be processed
     * @throws Exception if an error occurs during test execution or document generation
     */
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