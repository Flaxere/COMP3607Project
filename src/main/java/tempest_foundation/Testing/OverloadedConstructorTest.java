package tempest_foundation.Testing;

import java.util.ArrayList;
import java.lang.reflect.Parameter;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.ClassElements.Variable;

public class OverloadedConstructorTest implements Test{

    private ClassDetails inTesting; 
    private ArrayList<Function> expectedConstructor;
    private ArrayList<String> testStrings;

    public OverloadedConstructorTest(ArrayList<Function> expectedConstructor, ClassDetails inTesting, ArrayList<String> testStrings) {

        this.inTesting = inTesting; 
        this.expectedConstructor = expectedConstructor;
        this.testStrings=testStrings;
    }

    @Override
    public void setClassDetails(ClassDetails inTesting) {
        this.inTesting=inTesting;
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClassDetails'");
    }

    @Override
    public void executeTest() {

        ArrayList<Function> functions = inTesting.getFunctions();
        
        for (Function currentFunction:functions) {

            int expectedFunc = functions.indexOf(currentFunction);
            //Function expectedFunction = functions.get(expectedFunc);

            // if (expectedFunc != 1) {
            //     ArrayList<String> currentContent = currentFunction.getContent();
            //     ArrayList<String> expectedContent = expectedConstructor.get(expectedFunc).getContent();

            //     if (expectedContent != currentContent) {
            //         boolean isOverloaded = true;


            //     }
            // }
            if (expectedFunc != 1){
                ArrayList<String> content = currentFunction.getContent();
                double grade = 1 / testStrings.size();
                for (String data:content) {
                    String currentString = data.replaceAll("\\s", "");
                    // "ChatGpt3.5-LLM"
                    // notChatBotName=ChatGpt3.5-LLM
                    int i=0;
                    while(i < testStrings.size()){
                        String StringTest = testStrings.get(i).substring(testStrings.get(i).indexOf("="), testStrings.get(i).length()); // Retrieves the String needed for the current Constructor test.
                        if((currentString.contains(testStrings.get(i)) || currentString.contains(StringTest)) && currentFunction.hasVariable(new Variable("newBot","chatBotGenerator")) || currentFunction.hasVariable(new Variable("chatBotName", "String"))) {
                            inTesting.addGrade(grade);
                            testStrings.remove(i);
                            break;
                        }else{
                            inTesting.addGrade(0);
                            inTesting.addComment("The constructor was supposed to contain " + testStrings.get(i) + " as the default chatbotname.");
                        }
                    }
                    // for(String str:testStrings){
                        
                    //     if(currentString.contains(str.replaceAll("\\s", "" )) && data.contains("chatBotName") && currentFunction.hasVariable(new Variable("chatBotName","String"))){
                            
                    //     }
                    // }

                    // if (data.contains("ChatGPT-3.5") && data.contains("chatBotName") && currentFunction.hasVariable(new Variable("chatBotName","String"))) {
                    //     inTesting.addGrade(1.0);
                    // } else {
                    //     inTesting.addGrade(0);

                    //     inTesting.addComment("The constructor was supposed to contain 'ChatGPT-3.5' as the default chatbotname.");
                    // }
                }
            }
        } 
    }

   
}
