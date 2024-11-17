package tempest_foundation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

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
        FileReader f = new FileReader();
       
        DocumentGenerator d = new DocumentGenerator();
        ArrayList<Submission> submissions = new ArrayList<>();
        f.readFiles(submissions);

        ArrayList<Variable> var = new ArrayList<>();
        var.add(new Variable("chatBotName", "String", Visibility.PRIVATE));
        var.add(new Variable("numResponsesGenerated", "int", Visibility.PRIVATE));
        var.add(new Variable("messageLimit", "int" ,Visibility.PRIVATE));
        var.add(new Variable("messageNumber", "int", Visibility.PRIVATE));

        Submission s =submissions.get(0);
        VariableTest vTest = new VariableTest(var, s.getClass(0));
        vTest.executeTest();

        Function func = new Function();
        func.processFunctionDetails("public String getChatBotName()");
        func.addContent("return chatBotName;");

        AccessorTest aTest = new AccessorTest(func);
        aTest.setClassDetails(s.getClass(0));
        aTest.setGrade(1.0);
        aTest.executeTest();

        func = new Function();
        func.processFunctionDetails("public int getNumResponsesGenerated()");
        func.addContent("return numResponsesGenerated;");
        aTest.setExpectedFunction(func);
        aTest.executeTest();

        func = new Function();
        func.processFunctionDetails("public int getTotalNumResponsesGenerated()");
        func.addContent("return messageNumber;");
        aTest.setExpectedFunction(func);
        aTest.executeTest();

        d.createDocument(submissions.get(0));
        // System.out.println(s.getClass(0).getTotalGrade());

    }
}
