package tempest_foundation.Testing;

import java.util.ArrayList;
import java.util.Optional;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;


public class AccessorTest implements Test {

    private ClassDetails inTesting;
    private Function expectedFunction;
    private double grade;

    public AccessorTest(Function expectedFunction){
        this.expectedFunction = expectedFunction;
    }

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

        // int numTests = expectedFunction.getContent().size();
        // int currentTests = 0;
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
                .filter(var -> var.getContent() == f.getContent())
                .findFirst();

            targetFunction.ifPresent(var -> {
                
                //Add mark if the accessor function prototype matches
                if (expectedFunction.equals(var))
                    f.addGrade(grade/2);
                else
                    f.addComment(f.getFunctionName() + " did not contain the correct function prototype.");

                //Add mark if the return statement matches
                if (var.getContent().contains(expectedFunction.getContent().toString()))
                    f.addGrade(grade/2);
                else
                    f.addComment(f.getFunctionName() + " did not return the correct value.");
                System.out.println(var.getContent().toString());
                System.out.println(expectedFunction.getContent().toString());

            });
        }
        
       

        System.out.println(inTesting.getGrade());
    }
}
