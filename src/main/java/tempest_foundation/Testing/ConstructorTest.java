package tempest_foundation.Testing;

import java.util.ArrayList;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.ClassElements.Variable;

public class ConstructorTest implements Test{

    private ClassDetails inTesting; 
    private ArrayList<Function> expectedConstructor;
    ArrayList<String> testStrings;

    public ConstructorTest(ArrayList<Function> expectedConstructor, ClassDetails inTesting, ArrayList<String> testStrings) {

        this.inTesting = inTesting; 
        this.expectedConstructor = expectedConstructor;
        this.testStrings=testStrings;
    }

    @Override
    public void executeTest() {

        ArrayList<Function> functions = inTesting.getFunctions();
        
        for (Function currentFunction:functions) {

            int expectedFunc = functions.indexOf(currentFunction);
            // Function expectedFunction = functions.get(expectedFunc);
            
            if (expectedFunc != 1){
                ArrayList<String> content = currentFunction.getContent();
                double grade = 1 / testStrings.size();
                for (String data:content) {
                    String currentString = data.replaceAll("\\s", "");
                    // "ChatGpt3.5-LLM"
                    // notChatBotName=ChatGpt3.5-LLM
                    int i=0;
                    while(i < testStrings.size()){
                        if((currentString.contains(testStrings.get(i)) || currentString.contains(testStrings.get(i).substring(testStrings.get(i).indexOf("="), testStrings.get(i).length()))) && currentFunction.hasVariable(new Variable("chatBotName","String"))){
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
