package tempest_foundation.ClassElements;
import java.util.ArrayList;
import java.util.Arrays;

import tempest_foundation.SubmissionElements.MarkSnippet;
public class Function extends MarkSnippet {
    private Visibility accessModifier;
    private String functionName;
    private ArrayList<Variable> parameters;
    private ArrayList<Variable> variables;
    private ArrayList<String> functionContent;
    private boolean constructor;
    private String returnType;
    private boolean isStatic;
    
    

    public Function(){
        super();
        this.functionContent = new ArrayList<>();
        this.variables = new ArrayList<>();
       this.parameters = new ArrayList<>();
       this.returnType ="";
    }
    
    public Function(String functionName, Visibility accessModifier){
        this.functionName = functionName;
        this.accessModifier = accessModifier;
        this.functionContent = new ArrayList<>();
        if(accessModifier.equals(Visibility.NONE))
            constructor=true;
        this.variables = new ArrayList<>();
       
    }

    
    // public Function(String functionName,ArrayList<Variable> variables ){
    //     this.functionName = functionName;
    //     this.variables = variables;
    // }
   

        public void processParameterString(String line) {
            String strBetweenBrackets = null;
            int startIndex = line.indexOf('(');
            int endIndex = line.indexOf(')');
            strBetweenBrackets = line.substring(startIndex + 1, endIndex);
            String[] tempStrArr = strBetweenBrackets.split("[\\s\\s*,\\s*]");
            int i = 0;
            while(i<tempStrArr.length){
               int bracketCount = bracketCounter(tempStrArr[i]);//This increments the angle counter per line (edited)
               String finalType = tempStrArr[i];
               String finalName;
               i++;
               int size = tempStrArr.length;
               if(i >= size)
                    break;
               
               while(bracketCount > 0){
                finalType += tempStrArr[i];
                bracketCount = bracketCounter(finalType);
                i++;
               }
               
               char ch = bracketLastIndex(finalType);
               if(ch != '*' && finalType.lastIndexOf(ch) != finalType.length()-1){
                    finalName = finalType.substring(finalType.lastIndexOf(ch) + 1, finalType.length());
               }
               else{
                    finalName = tempStrArr [ i ];
               }
               parameters.add(new Variable(finalName, finalType));
                i++; 
                                   
            }

        }


        private static int bracketCounter(String line){
            long lt = line.chars() 
            .filter(c -> c == '<') 
            .count();
            long mt = line.chars() 
            .filter(c -> c == '>') 
            .count();

            long lb = line.chars() 
            .filter(c -> c == '[') 
            .count();
            long mb = line.chars() 
            .filter(c -> c == ']') 
            .count();
            return ((int) lt + (int) lb ) - ((int) mt + (int) mb);
        }

        private static char bracketLastIndex(String str){
            if(str.lastIndexOf(">") > str.lastIndexOf("]"))
                return '>';
            else if(str.lastIndexOf(">") < str.lastIndexOf("]"))
                return ']';
            return '*';
        }

       



    public void setGrade(int grade){
        this.grade = grade;
    }
    public boolean isConstructor(){return constructor;}

   

    public ArrayList<Variable> getVariables(){return variables;}
    public String getFunctionName(){return functionName;}
    public Visibility getAccessModifier(){return accessModifier;}

    public void setFunctionName(String functionName){this.functionName=functionName;}
    public void setFunctionType(String returnType){this.returnType=returnType;}
    public void setAccessModifier(Visibility accessModifier){this.accessModifier=accessModifier;}


    public Visibility assignVisibility(String line){
        String[] tempStrArr = line.trim().split("[\\s\\s*,\\s*]"); 
        if(tempStrArr[0].equals("private"))
            return Visibility.PRIVATE;
        else if (tempStrArr[0].equals("protected"))
            return Visibility.PROTECTED;
        else if (tempStrArr[0].equals("public"))
            return Visibility.PUBLIC;
        return Visibility.NONE;
    }

    public static String assignName(String line){

        String[] tempStrArr = line.trim().split("[\\s\\s*,\\s*]"); 
       
        boolean[] tests = {false, false};
        int skipCount=0;
        if(Arrays.stream(tempStrArr).anyMatch("static"::equals))
            tests[0] = true;
        if(Arrays.stream(tempStrArr).anyMatch("final"::equals))
            tests[1] = true;
       
        for(boolean t: tests){
            if(t)
                skipCount++;
        }
        int i=1;
        String functionNameString =tempStrArr[i + skipCount];
        int bracketCount = bracketCounter(functionNameString);
        while(bracketCount>0){
            i++;
            functionNameString +=bracketCounter(tempStrArr[i + skipCount]);
            bracketCount = bracketCounter(functionNameString);
        }
        char ch = bracketLastIndex(functionNameString);

        if(ch != '*' && functionNameString.lastIndexOf(ch) != functionNameString.length()-1)
            functionNameString = functionNameString.substring(functionNameString.lastIndexOf(ch),functionNameString.length());
        else
            functionNameString = tempStrArr[i+ skipCount + 1];
        return functionNameString;
        
    }


    public static String assignreturnType(String line){
        String[] tempStrArr = line.trim().split("[\\s\\s*,\\s*]"); 
       
        boolean[] tests = {false, false};
        int skipCount=0;
        if(Arrays.stream(tempStrArr).anyMatch("static"::equals))
            tests[0] = true;
        if(Arrays.stream(tempStrArr).anyMatch("final"::equals))
            tests[1] = true;
       
        for(boolean t: tests){
            if(t)
                skipCount++;
        }
        int i=1;
        String returnTypeString =tempStrArr[i + skipCount];
        int bracketCount = bracketCounter(returnTypeString);
        while(bracketCount>0){
            i++;
            returnTypeString +=bracketCounter(tempStrArr[i + skipCount]);
            bracketCount = bracketCounter(returnTypeString);
        }
        char ch = bracketLastIndex(returnTypeString);
        if(ch != '*' && returnTypeString.lastIndexOf(ch) != returnTypeString.length()-1)
            returnTypeString = returnTypeString.substring(0,returnTypeString.lastIndexOf(ch));
        return returnTypeString;

    }

    public void processFunctionDetails(String line){//FIX: FIgure out why there are duplicate functions appearing when you press run
        processParameterString(line);
        String cutLine = line.substring(0,line.indexOf("("));
        this.accessModifier = assignVisibility(cutLine);
        String[] tempStrArr = cutLine.trim().split("[\\s+\\s*,{\\s*]"); 
       
        if(tempStrArr.length<3){
            String functionName =tempStrArr[1];
            if(functionName.contains("("))
                functionName = functionName.substring(0, functionName.indexOf("("));
            this.functionName=functionName;
            return;
        }
        boolean[] tests = {false, false};
        int skipCount=0;
        if(Arrays.stream(tempStrArr).anyMatch("static"::equals)){
            tests[0] = true;
            isStatic = true;
        }
        if(Arrays.stream(tempStrArr).anyMatch("final"::equals))
            tests[1] = true;
       
        for(boolean t: tests){
            if(t)
                skipCount++;
        }
        int i=1;
        String returnTypeString =tempStrArr[i + skipCount];
        int bracketCount = bracketCounter(returnTypeString);
        while(bracketCount>0){
            i++;
            returnTypeString +=bracketCounter(tempStrArr[i + skipCount]);
            bracketCount = bracketCounter(returnTypeString);
        }
        char ch = bracketLastIndex(returnTypeString);

        if(ch != '*' && returnTypeString.lastIndexOf(ch) != returnTypeString.length()-1){
            this.returnType=returnTypeString.substring(0,returnTypeString.lastIndexOf(ch));
            String functionName =returnTypeString.substring(returnTypeString.lastIndexOf(ch),returnTypeString.length());
            if(functionName.contains("("))
                functionName=functionName.substring(0, functionName.indexOf("("));
            this.functionName=functionName;
        }
        else{
            this.returnType=returnTypeString;
            String functionName =tempStrArr[i+ skipCount + 1];
            if(functionName.contains("("))
                functionName=functionName.substring(0, functionName.indexOf("("));
            this.functionName=functionName;
        }
            
    }

    @Override
    public void addContent(String content){this.functionContent.add(content.trim());}

    @Override
    public void setContent(ArrayList<String> content) {
       this.functionContent = content;
    }
    @Override
    public void addVariable(String line){
        String varName = Variable.assignName(line);
        if(varName.equals("0nonVariable"))
            return;
        Visibility vis = Variable.assignVisibility(line);
        if(vis==Visibility.NONE){
            variables.add(new Variable(varName, Variable.assignType(line)));
        }
    }

    @Override
    public ArrayList<String> getContent() {
        return this.functionContent;
    }

    public String toString(){
        String str=accessModifier + " " +  returnType + " " + functionName + "(";
        for(Variable p:parameters)
            str+=p.getType() + " " + p.getName() + ",";
        str+=")";
        return str;
    }
}
