package tempest_foundation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.DocumentException;

import tempest_foundation.ClassElements.Variable;
import tempest_foundation.ClassElements.Visibility;
import tempest_foundation.SubmissionElements.Submission;
import tempest_foundation.Testing.VariableTest;
/**
 * Hello Jura-Tempest Federation!
 *
 */
public class App 
{ 
    public static void main( String[] args ) throws IOException, DocumentException//TOdo: Account for abstract classes when reading the file
    {
        FileReader f = new FileReader();
       
        DocumentGenerator d = new DocumentGenerator();
        d.createDocument();
        ArrayList<Submission> submissions = new ArrayList<>();
        f.readFiles(submissions);
   
        ArrayList<Variable> var = new ArrayList<>();
        var.add(new Variable("chatBotName", "String", Visibility.PROTECTED));
        var.add(new Variable("numResponsesGenerated", "int", Visibility.PRIVATE));
        var.add(new Variable("messageLimit", "int" ,Visibility.PRIVATE));
        var.add(new Variable("messageNumber", "int", Visibility.PRIVATE));

        Submission s =submissions.get(2);
        VariableTest vTest = new VariableTest(var, s.getClass(0));
        vTest.executeTest();
     
       
    }
}
