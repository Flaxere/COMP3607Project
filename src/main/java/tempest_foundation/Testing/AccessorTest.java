package tempest_foundation.Testing;

import java.util.Optional;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;


/**
 * A test implementation for evaluating accessor methods in a given class.
 * <p>
 * This class verifies whether a specific accessor function in the tested class 
 * matches the expected function and its implementation details.
 * </p>
 */
public class AccessorTest implements Test {

    /**
     * The class details being tested.
     */
    private ClassDetails inTesting;
    private Function expectedFunction;
    private double grade;

    /**
     * Sets the class details to be tested.
     *
     * @param inTesting the {@link ClassDetails} object representing the class under test.
     */
    public void setClassDetails(ClassDetails inTesting){
        this.inTesting=inTesting;
    }

    public void setExpectedFunction(Function expectedFunction){
        this.expectedFunction=expectedFunction;
    }

    public void setGrade(double grade){
        this.grade = grade;
    }

    /**
     * Executes the test on the given class details, validating the accessor function.
     * <p>
     * The test checks if the expected function exists within the class, and if so, 
     * verifies whether the implementation of the function matches the expected details. 
     * Grades and comments are updated accordingly.
     * </p>
     */
    @Override
    public void executeTest() {
        Function function = null;
        for (Function currentFunction: inTesting.getFunctions()) {
            if(currentFunction.equals(expectedFunction)){
                function=currentFunction;
                break;
            }
        }
        if(function!=null){
            Function f = function;
            Optional<Function> targetFunction = inTesting.getFunctions().stream()
                .filter(var -> var == f)
                .findFirst();

            targetFunction.ifPresent(var -> {
            f.addGrade(grade/2);

            String expectedDetails =extractContent(expectedFunction.getContent().toString());
            String actualDetails = var.getContent().toString().replaceAll("\\s", "");
            
            actualDetails = extractContent(actualDetails);
            actualDetails = actualDetails.replaceAll("[()]", "");
            expectedDetails =expectedDetails.replaceAll("\\s", "");
          
            if (actualDetails.contains(expectedDetails))
                f.addGrade(grade/2);
            else
                f.addComment(f.getFunctionName() + " did not return the correct value.");

           
            f.setTotal(grade);
            
            });

        }
    }

    /**
     * Extracts the content of a function by isolating its return statement.
     *
     * @param functionDetails the string representation of the function's content.
     * @return the extracted return statement, or "FAIL" if extraction fails.
     */
    private String extractContent(String functionDetails){
        int startPos = functionDetails.indexOf("return");
        int endPos = functionDetails.indexOf(";");
        if(startPos == -1 || endPos ==-1)
            return "FAIL";
        return functionDetails.substring(functionDetails.indexOf("return"),functionDetails.lastIndexOf(";")+1);
    }
}