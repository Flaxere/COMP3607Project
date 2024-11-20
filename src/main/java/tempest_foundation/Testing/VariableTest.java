package tempest_foundation.Testing;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Variable;


/**
 * The VariableTest class is responsible for testing the visibility, type, and name etc of a specific variable 
 * within a class to ensure it matches the expected visibility specified in the test case.
 * <p>
 *  
 * 
 * </p>
 */
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

    /**
     * Executes the test to check to see if variable matches with expected variable within the class.
     * <p>
     * The method compares the visibility of the variable found in the class to the expected visibility. 
     * If the visibility matches, the full grade is awarded. If it doesn't match, half the grade is awarded, 
     * and a comment is added indicating the expected visibility.
     * </p>
     */
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

     /**
     * Sets the grade for this variable test.
     * 
     * @param grade the grade to assign to the test.
     */
    @Override
    public void setGrade(double grade) {
        this.grade=grade;
    }
}
