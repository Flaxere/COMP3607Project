package tempest_foundation.Testing;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import com.itextpdf.text.DocumentException;

import tempest_foundation.DocumentGenerator;

/**
 * A class that handles the calculation of code execution overhead for a given test.
 * <p>
 * This class calculates the time taken for a specific test to execute and formats the result for reporting.
 * It generates an overhead report with the calculated execution times in both seconds and milliseconds.
 * </p>
 */
public class OverheadTest {


    /**
     * Calculates the overhead (execution time) for the code in a test and adds the results to a document.
     * <p>
     * The method computes the time difference between the start and end timestamps (in nanoseconds),
     * then converts it into seconds and milliseconds. The results are formatted and passed to a document generator
     * to record the test execution overhead for the given student.
     * </p>
     * 
     * @param startTime the start time of the code execution in nanoseconds.
     * @param endTime the end time of the code execution in nanoseconds.
     * @param studentID the student ID associated with the test.
     * @param testEnum the type of test that was executed (used for reporting).
     * @throws FileNotFoundException if there is an issue with file handling during report generation.
     * @throws DocumentException if there is an error generating the report document.
     */
    public static synchronized void calculateOverhead(long startTime, long endTime, String studentID, TestEnum testEnum) throws FileNotFoundException, DocumentException {

        long codeExecutionTime = endTime - startTime;

        double durationInMillis = codeExecutionTime / 1_000_000.0;
        double durationInSeconds = codeExecutionTime / 1_000_000_000.0;

        DocumentGenerator.addOverheadData(
            getFormattedString(durationInSeconds, "#"), getFormattedString(durationInMillis, "#.###"), studentID, testEnum);
    }

     /**
     * Formats a decimal value to a string based on the provided format pattern.
     * <p>
     * This method uses `DecimalFormat` to format the decimal number according to the specified pattern
     * (e.g., rounding to a specific number of decimal places).
     * </p>
     * 
     * @param decimalString the decimal number to format.
     * @param decimalPoint the pattern to format the decimal number (e.g., "#.###").
     * @return the formatted string representation of the decimal number.
     */
    private static synchronized String getFormattedString(Double decimalString, String decimalPoint) {

        DecimalFormat decimal = new DecimalFormat(decimalPoint);
        String formattedString = decimal.format(decimalString);

        return formattedString;
    }
}