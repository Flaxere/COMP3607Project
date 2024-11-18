package tempest_foundation.Testing;

import java.util.ArrayList;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.ClassElements.Variable;
import tempest_foundation.ClassElements.Visibility;
import tempest_foundation.SubmissionElements.Submission;

public class TestSuite {
    
    private AccessorTest accessorTest;
    private ConstructorTest constructorTest;

    public TestSuite(){
        accessorTest = new AccessorTest();
        constructorTest = new ConstructorTest();
        accessorTest.setGrade(1.0);

        
    }
    public void executeTests(Submission s){
        ArrayList<Variable> variables = new ArrayList<>();
        variables.add(new Variable("chatBotName", "String", Visibility.PRIVATE));
        variables.add(new Variable("numResponsesGenerated", "int", Visibility.PRIVATE));
        variables.add(new Variable("messageLimit", "int" ,Visibility.PRIVATE));
        variables.add(new Variable("messageNumber", "int", Visibility.PRIVATE));

        ArrayList<Double> grades = new ArrayList<>();
        grades.add(1.0);
        grades.add(1.0);
        grades.add(3.0);
        grades.add(2.0);

        ClassDetails chatBot = s.getClass("public class ChatBot");
        ClassDetails chatBotGenerator = s.getClass("public class ChatBotGenerator");
        ClassDetails chatBotPlatform = s.getClass("public class ChatBotPlatform");
        ClassDetails chatBotSimulation = s.getClass("public class ChatBotSimulation");

        for(int i=0;i<variables.size();i++){
            VariableTest vTest = new VariableTest(variables.get(i), chatBot);
            s.getClass(0).setTotal(grades.get(i));
            vTest.setGrade(grades.get(i));
            vTest.executeTest();
        }
            
        ArrayList<String> chatBotTestStrings = new ArrayList<>();
        AccessorTestExecutor(chatBot,"public int getNumResponsesGenerated()", "return numResponsesGenerated;");
        AccessorTestExecutor(chatBot, "public int getNumResponsesGenerated()", "return numResponsesGenerated;");
        AccessorTestExecutor(chatBot,"public String getChatBotName()", "return chatBotName;");
        AccessorTestExecutor(chatBot,"public static int getTotalNumResponsesGenerated()", "return messageNumber;");
        AccessorTestExecutor(chatBot,"public static int getTotalNumMessagesRemaining()", "return messageLimit-messageNumber;");
        AccessorTestExecutor(chatBot,"public String getChatBotName()", "return chatBotName;");
        chatBotTestStrings.add("chatBotName = \"ChatGPT-3.5\"; ");

        ConstructorTestExecutor(chatBot,"public chatBot()",chatBotTestStrings,3);
        
        chatBotTestStrings.clear();
        chatBotTestStrings.add("if(messageNumber == messageLimit){");
        chatBotTestStrings.add("return true;");
        chatBotTestStrings.add("return false;");
        ConstructorTestExecutor(chatBot,"public static boolean limitReached()",chatBotTestStrings,3);

     
        chatBotTestStrings.clear();
        chatBotTestStrings.add("botS = new ArrayList<chatBot>();");
        ConstructorTestExecutor(chatBotPlatform,"public chatBotPlatform()", chatBotTestStrings, 2);

        chatBotTestStrings.clear();

        chatBotTestStrings.add("return true;");
        chatBotTestStrings.add("return false;");

        ConstructorTestExecutor(chatBotPlatform, "public boolean addChatBot(int LLMCode)", chatBotTestStrings, 5);
        chatBotTestStrings.clear();
        chatBotTestStrings.add("bots.get(botNumber)");
        chatBotTestStrings.add("bots.get(botNumber)");
        chatBotTestStrings.add("prompt(message)");

        ConstructorTestExecutor(chatBotPlatform, "public String interactWithBot(int botNumber, String message)", chatBotTestStrings, 5);
        chatBotTestStrings.clear();

        chatBotTestStrings.add("Your ChatBots");
        chatBotTestStrings.add("Bot number:");
        chatBotTestStrings.add("Your ChatBots");
        chatBotTestStrings.add("Total messages used:");
        chatBotTestStrings.add("Total messages remaining:");

        ConstructorTestExecutor(chatBotPlatform, "public String getChatBotList()", chatBotTestStrings, 6);


        chatBotTestStrings.clear();
        chatBotTestStrings.add("LLaMa");
        chatBotTestStrings.add("Mistral7B");
        chatBotTestStrings.add("Bard");
        chatBotTestStrings.add("Claude");
        chatBotTestStrings.add("Solar");
        chatBotTestStrings.add("ChatGPT-3.5");

        ConstructorTestExecutor(chatBotGenerator, "public static String generateChatBotLLM(int LLMCodeNumber)", chatBotTestStrings, 7);

        chatBotTestStrings.clear();
        chatBotTestStrings.add("Hello World!");
        chatBotTestStrings.add("new ChatBotPlatform();");
        chatBotTestStrings.add("while(");
        chatBotTestStrings.add("for(");
        chatBotTestStrings.add(".addChatBot(");
        chatBotTestStrings.add(".getChatBotList()");
        chatBotTestStrings.add(".getChatBotList()");

        ConstructorTestExecutor(chatBotSimulation, "public static void main(String[] args)", chatBotTestStrings, 12);





        
    }

    private void AccessorTestExecutor(ClassDetails classD,String testHeader, String testContent){
        if(classD==null)
            return;
        Function func = new Function();
        func.processFunctionDetails(testHeader);
        func.addContent(testContent);
        accessorTest.setClassDetails(classD);
        accessorTest.setExpectedFunction(func);
        accessorTest.executeTest();
    }

    private void ConstructorTestExecutor(ClassDetails classD,String testHeader, ArrayList<String> testContent,double grade){
        if(classD==null)
            return;
        Function func = new Function();
        func.processFunctionDetails(testHeader);
        
        for(String string:testContent)
            func.addContent(string);
        constructorTest.setClassDetails(classD);
        constructorTest.setExpectedFunction(func);
        constructorTest.setGrade(grade);
        constructorTest.executeTest();

        
        
    }

}
