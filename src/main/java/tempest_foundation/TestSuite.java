package tempest_foundation;

import java.util.ArrayList;

import tempest_foundation.ClassElements.Function;
import tempest_foundation.ClassElements.Variable;
import tempest_foundation.ClassElements.Visibility;
import tempest_foundation.SubmissionElements.Submission;
import tempest_foundation.Testing.AccessorTest;
import tempest_foundation.Testing.VariableTest;

public class TestSuite {
    
    public void executeTests(Submission s){
        ArrayList<Variable> variables = new ArrayList<>();
        variables.add(new Variable("chatBotName", "String", Visibility.PRIVATE));
        variables.add(new Variable("numResponsesGenerated", "int", Visibility.PRIVATE));
        variables.add(new Variable("messageLimit", "int" ,Visibility.PRIVATE));
        variables.add(new Variable("messageNumber", "int", Visibility.PRIVATE));

        ArrayList<Double> grades = new ArrayList<>();
        grades.add(1.0);
        grades.add(1.0);
        grades.add(3.0);
        grades.add(2.0);

        for(int i=0;i<variables.size();i++){
            VariableTest vTest = new VariableTest(variables.get(i), s.getClass(0));
            vTest.setGrade(grades.get(i));
            vTest.executeTest();
        }
            
        

        Function func = new Function();
        func.processFunctionDetails("public String getChatBotName()");
        func.addContent("return chatBotName;");

        AccessorTest aTest = new AccessorTest();
        aTest.setClassDetails(s.getClass(0));
        aTest.setGrade(1.0);
        aTest.executeTest();

        func = new Function();
        func.processFunctionDetails("public int getNumResponsesGenerated()");
        func.addContent("return numResponsesGenerated;");
        aTest.setExpectedFunction(func);
        aTest.executeTest();

        func = new Function();
        func.processFunctionDetails("public static int getTotalNumResponsesGenerated()");
        func.addContent("return messageNumber;");
        aTest.setExpectedFunction(func);
        aTest.executeTest();

        func = new Function();
        func.processFunctionDetails("public static int getTotalNumMessagesRemaining()");
        func.addContent("return messageLimit-messageNumber;");
        aTest.setExpectedFunction(func);
        aTest.executeTest();
    }
}
