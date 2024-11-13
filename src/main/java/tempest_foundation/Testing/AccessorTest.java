package tempest_foundation.Testing;

import java.util.ArrayList;
import java.util.Optional;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;


public class AccessorTest implements Test {

    private ClassDetails inTesting;
    private Function expectedFunction;

    public AccessorTest(Function expectedFunction){
        this.expectedFunction = expectedFunction;
    }

    public void setClassDetails(ClassDetails inTesting){
        this.inTesting=inTesting;
    }

    public void setExpectedFunction(Function expectedFunction){
        this.expectedFunction=expectedFunction;
    }

    @Override
    public void executeTest() {

        int numTests = expectedFunction.getContent().size();
        int currentTests = 0;

        for (Function currentFunction: inTesting.getFunctions()) {
            if(numTests == currentTests)
                break;

            Optional<Function> targetFunction = inTesting.getFunctions().stream()
                .filter(var -> var.getContent() == currentFunction.getContent())
                .findFirst();

            targetFunction.ifPresent(var -> {

                //Add mark if the accessor function prototype matches
                if (expectedFunction.equals(var))
                    currentFunction.addGrade(1.0);
                else
                    currentFunction.addComment(currentFunction.getFunctionName() + " did not contain the correct function prototype.");

                //Add mark if the return statement matches
                if (var.toString().contains(expectedFunction.toString()))
                    currentFunction.addGrade(1.0);
                else
                    currentFunction.addComment(currentFunction.getFunctionName() + " did not return the correct value.");
            });
        }

        System.out.println(inTesting.getGrade());
    }
}
