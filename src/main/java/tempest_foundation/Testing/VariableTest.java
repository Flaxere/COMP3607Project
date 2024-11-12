package tempest_foundation.Testing;

import java.util.ArrayList;
import java.util.List;

import tempest_foundation.ClassElements.Variable;
import tempest_foundation.SubmissionElements.MarkSnippet;

public class VariableTest implements Test {
    
    private MarkSnippet inTesting;
    private List<Variable> expectedVariables;

    public VariableTest(List<Variable> expectedVariables,MarkSnippet inTesting) {
        
        this.expectedVariables = expectedVariables;
        this.inTesting = inTesting;
       
    }

    @Override
    public void executeTest() {
        ArrayList<Variable> variables = inTesting.getVariables();
        
        for(Variable currentVariable:inTesting.getVariables()){
           
            if(currentVariable != null){
                int expectedVal = expectedVariables.indexOf(currentVariable);
                if(expectedVal!=-1){
                    Variable expected = expectedVariables.get(expectedVal);
                    if(expected.getVisibility()==currentVariable.getVisibility()){
                        inTesting.addGrade(1.0);
                        System.out.println(inTesting.getGrade());
                    }else{
                        inTesting.addGrade(0.5); 
                        inTesting.addComment("The variable " + currentVariable.getName() + " was supposed to be "+expected.getVisibility());
                        System.out.println(inTesting.getGrade());
                        System.out.println(inTesting.getComment());
                    }

                }
            }

        }
        


    }


}
