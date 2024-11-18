package tempest_foundation.Testing;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import com.itextpdf.text.DocumentException;
import tempest_foundation.DocumentGenerator;

public class OverheadTest {
    
    public static synchronized void calculateOverhead(long startTime, long endTime, String studentID, TestEnum testEnum) throws FileNotFoundException, DocumentException {

        long codeExecutionTime = endTime - startTime;

        double durationInMillis = codeExecutionTime / 1_000_000.0;
        double durationInSeconds = codeExecutionTime / 1_000_000_000.0;

        DocumentGenerator.addOverheadData(
            getFormattedString(durationInSeconds, "#"), getFormattedString(durationInMillis, "#.###"), studentID, testEnum);
    }

    private static synchronized String getFormattedString(Double decimalString, String decimalPoint) {

        DecimalFormat decimal = new DecimalFormat(decimalPoint);
        String formattedString = decimal.format(decimalString);

        return formattedString;
    }
}