package tempest_foundation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.DocumentException;

import tempest_foundation.SubmissionElements.Submission;
/**
 * Hello Jura-Tempest Federation!
 *
 */
public class App 
{ 
    public static void main( String[] args ) throws IOException, DocumentException//TOdo: Account for abstract classes when reading the file
    {
        
        Map<String,Map<String,ArrayList<String>>> submissionListing= new HashMap<>();
        FileReader f = new FileReader(submissionListing);
       
        DocumentGenerator d = new DocumentGenerator();
        d.createDocument();
        ArrayList<Submission> submissions = new ArrayList<>();
        f.readFiles(submissions);
        System.out.println(submissions.get(1));
        // System.out.println(submissionListing.get("816035980").get("public class ChatBot{"));
    }
}
