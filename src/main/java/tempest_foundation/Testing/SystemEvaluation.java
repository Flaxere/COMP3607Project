package tempest_foundation.Testing;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.itextpdf.text.DocumentException;

import tempest_foundation.DocumentGenerator;

public class SystemEvaluation {

    private static ArrayList<Long> startTimes = new ArrayList<>();
    private static ArrayList<Long> endTimes = new ArrayList<>();
    private static ArrayList<String> studentIDs = new ArrayList<>();

    private static ArrayList<TestEnum> enums = new ArrayList<>();

    public static void setupTest() throws FileNotFoundException, DocumentException {DocumentGenerator.instantiateOverheadDocument();}
    public static void endTest() throws FileNotFoundException, DocumentException {DocumentGenerator.generateOverheadReport();}

    public static void addOverheadTime(long startTime, long endTime, String studentID, TestEnum testEnum) {

        startTimes.add(startTime);
        endTimes.add(endTime);
        studentIDs.add(studentID);
        enums.add(testEnum);
    }
    
    public static void runTest() throws FileNotFoundException, DocumentException {

        setupTest();

        for (int i = 0; i < startTimes.size(); i++)
            OverheadTest.calculateOverhead(startTimes.get(i), endTimes.get(i), studentIDs.get(i), enums.get(i));

        endTest();
    }
}