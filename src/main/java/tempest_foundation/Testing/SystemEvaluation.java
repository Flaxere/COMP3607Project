package tempest_foundation.Testing;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.itextpdf.text.DocumentException;

import tempest_foundation.DocumentGenerator;


/**
 * A class that manages the evaluation of system performance for multiple tests.
 * <p>
 * This class collects the start and end times for tests, stores the associated student IDs and test types, and 
 * generates an overhead report for the tests once all the data has been collected.
 * </p>
 */
public class SystemEvaluation {

    private static ArrayList<Long> startTimes = new ArrayList<>();
    private static ArrayList<Long> endTimes = new ArrayList<>();
    private static ArrayList<String> studentIDs = new ArrayList<>();

    private static ArrayList<TestEnum> enums = new ArrayList<>();

      /**
     * Initializes the test setup by creating a document for recording overhead data.
     * <p>
     * This method is called at the start of the evaluation process to ensure the document is ready 
     * to store the performance metrics.
     * </p>
     * 
     * @throws FileNotFoundException if there is an issue accessing or creating the document.
     * @throws DocumentException if there is an issue generating the document.
     */
    public static void setupTest() throws FileNotFoundException, DocumentException {DocumentGenerator.instantiateOverheadDocument();}
    
     /**
     * Finalizes the test by generating an overhead report with the collected data.
     * <p>
     * This method is called after all tests have been evaluated to produce a final report of system performance.
     * </p>
     * 
     * @throws FileNotFoundException if there is an issue accessing or generating the document.
     * @throws DocumentException if there is an issue generating the document.
     */
    public static void endTest() throws FileNotFoundException, DocumentException {DocumentGenerator.generateOverheadReport();}

    /**
     * Adds the overhead data (start time, end time, student ID, and test type) to the lists.
     * <p>
     * This method is called to collect the performance metrics for a specific test.
     * </p>
     * 
     * @param startTime the start time of the test in nanoseconds.
     * @param endTime the end time of the test in nanoseconds.
     * @param studentID the ID of the student associated with the test.
     * @param testEnum the type of test that was executed.
     */
    public static void addOverheadTime(long startTime, long endTime, String studentID, TestEnum testEnum) {

        startTimes.add(startTime);
        endTimes.add(endTime);
        studentIDs.add(studentID);
        enums.add(testEnum);
    }
    

    /**
     * Runs the test evaluation for all collected data and generates an overhead report.
     * <p>
     * This method calls the setup method to initialize the report, processes each test's start and end times, 
     * and then generates the report once all tests have been processed.
     * </p>
     * 
     * @throws FileNotFoundException if there is an issue accessing or creating the document.
     * @throws DocumentException if there is an issue generating the document.
     */
    public static void runTest() throws FileNotFoundException, DocumentException {

        setupTest();

        for (int i = 0; i < startTimes.size(); i++)
            OverheadTest.calculateOverhead(startTimes.get(i), endTimes.get(i), studentIDs.get(i), enums.get(i));

        endTest();
    }
}