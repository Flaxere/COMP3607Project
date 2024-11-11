package tempest_foundation.Testing;

import tempest_foundation.SubmissionElements.MarkSnippet;

import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;

public class ConstructorTest implements Test{

    private ClassDetails inTesting; 
    private ArrayList<Function> expectedConstructor;

    public ConstructorTest(ArrayList<Function> expectedConstructor, ClassDetails inTesting) {

        this.inTesting = inTesting; 
        this.expectedConstructor = expectedConstructor;
    }

    @Override
    public void executeTest() {

        ArrayList<Function> functions = inTesting.getFunctions();
        
        for (Function currentFunction:functions) {

            int expectedFunc = functions.indexOf(currentFunction);
            // Function expectedFunction = functions.get(expectedFunc);
            
            if (expectedFunc != 1){
                ArrayList<String> content = currentFunction.getContent();
                for (String data:content) {
                    if (data.contains("ChatGPT-3.5") && data.contains("chatBotName")) {
                        inTesting.addGrade(1.0);
                    } else {
                        inTesting.addGrade(0);

                        inTesting.addComment("The constructor was supposed to contain 'ChatGPT-3.5' as the default chatbotname.");
                    }
                }
            }
        } 
    }
}
