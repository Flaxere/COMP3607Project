package tempest_foundation.Testing;

import java.util.List;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Variable;


public class VariableTest implements Test {
    
    private ClassDetails inTesting;
    private Variable expectedVariable;
    private double grade;

    public VariableTest(Variable expectedVariable,ClassDetails inTesting) {
        
        this.expectedVariable = expectedVariable;
        this.inTesting = inTesting;
       
    }

    public void setClassDetails(ClassDetails inTesting){
        this.inTesting=inTesting;
    }

    @Override
    public void executeTest() {
        
        // for(Variable currentVariable:inTesting.getVariables()){
        int expectedVal = inTesting.getVariables().indexOf(expectedVariable);
        // if(currentVariable != null){
        
        if(expectedVal!=-1){
            Variable foundVariable = inTesting.getVariables().get(expectedVal);
            if(foundVariable.getVisibility()==expectedVariable.getVisibility()){
                inTesting.addGrade(grade);
            }else{
                inTesting.addGrade(grade/2); 
                inTesting.addComment("The variable " + foundVariable.getName() + " was supposed to be "+expectedVariable.getVisibility());
            }

        }
            // }

        // }
        


    }

    @Override
    public void setGrade(double grade) {
        this.grade=grade;
    }


}
