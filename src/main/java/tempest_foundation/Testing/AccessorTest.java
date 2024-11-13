package tempest_foundation.Testing;

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
        int currTests=0;
         for(Function currentFunction:inTesting.getFunctions()){
            if(numTests==currTests)
                break;
            if(expectedFunction.equals(currentFunction)){
                currentFunction.addGrade(1.0);
                for(String expectedString: expectedFunction.getContent()){
                    for(String actualString:currentFunction.getContent() ){
                        if(actualString.contains(expectedString.substring(0,expectedString.indexOf(";")))){
                            currentFunction.addGrade(1.0);
                            currTests++;
                            break;
                        }
                    }  
                }
            }
         }
    }   
    
}
