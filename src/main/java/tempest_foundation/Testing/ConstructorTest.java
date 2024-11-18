package tempest_foundation.Testing;

import java.util.ArrayList;
import java.util.Optional;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.ClassElements.Variable;

public class ConstructorTest implements Test{

    private ClassDetails inTesting; 
    private Function expectedFunction;
    private double grade=0;


    @Override
    public void setClassDetails(ClassDetails inTesting) {
        this.inTesting = inTesting;
    }

    public void setExpectedFunction(Function expectedFunction){
        this.expectedFunction=expectedFunction;
    }

    @Override
    public void setGrade(double grade) {
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
            
            int divisionRate = (expectedFunction.getContent().size()+1);
            f.addGrade(grade/divisionRate);
            

            //Add mark if the return statement matches
            int num=0;
            ArrayList<String> expectedContent;
            expectedContent = expectedFunction.getContent();
            for(String currentString:var.getContent()){
                String filteredString =currentString.replaceAll("\\s", "");
                filteredString = filteredString.replaceAll("[(){}]", "");
                for(int i=num;i < expectedContent.size();i++){
                    String expectedString = expectedContent.get(i).replaceAll("\\s", "");
                    expectedString = expectedString.replaceAll("[(){}]", "");
                    if(filteredString.contains(expectedString)){
                        f.addGrade(grade/divisionRate);
                        expectedContent.remove(i);
                        if(i>0)
                            i--; 
                        
                    }
    
                }
            }
            for(int i=0;i < expectedContent.size();i++){
                f.addComment("The inclusion of '" + expectedContent.get(i) + "' was missing from the function");
            }
            f.setTotal(grade);
          });
            
            
        }



    }

   
}
