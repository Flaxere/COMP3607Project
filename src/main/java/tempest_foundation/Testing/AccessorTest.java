package tempest_foundation.Testing;

import java.util.Optional;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;


public class AccessorTest implements Test {

    private ClassDetails inTesting;
    private Function expectedFunction;
    private double grade;

    public void setClassDetails(ClassDetails inTesting){
        this.inTesting=inTesting;
    }

    public void setExpectedFunction(Function expectedFunction){
        this.expectedFunction=expectedFunction;
    }

    public void setGrade(double grade){
        this.grade = grade;
    }

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
            });
        }
    }

    private String extractContent(String functionDetails){
        int startPos = functionDetails.indexOf("return");
        int endPos = functionDetails.indexOf(";");
        if(startPos == -1 || endPos ==-1)
            return "FAIL";
        return functionDetails.substring(functionDetails.indexOf("return"),functionDetails.lastIndexOf(";")+1);
    }
}