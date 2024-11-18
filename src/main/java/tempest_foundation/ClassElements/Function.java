package tempest_foundation.ClassElements;
import java.util.ArrayList;
import java.util.Arrays;

import tempest_foundation.BracketManager;
import tempest_foundation.SubmissionElements.MarkSnippet;
public class Function extends MarkSnippet {
    private Visibility accessModifier;
    private String functionName;
    private ArrayList<Variable> parameters;
    private ArrayList<Variable> variables;
    private ArrayList<String> functionContent;
    private boolean constructor;
    private String returnType;
    private boolean isStatic=false;
    // private boolean isStatic;
    
    

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

    public void processParameterString(String line, char[] characterFilter ) {
        String strBetweenBrackets = null;
        strBetweenBrackets = line.substring( line.indexOf('(') + 1, line.indexOf(')'));
        String[] tempStrArr = strBetweenBrackets.split("[\\s\\s*,\\s*]");
        // char[] characterFilter = {'<','>','[',']'};
        int i = 0;

        while(i<tempStrArr.length){
            int bracketCount =  BracketManager.bracketCounter(tempStrArr[i],characterFilter);
            String finalName, finalType = tempStrArr[i];
            i++;
            if(i >= tempStrArr.length)
                break; 

            while(bracketCount > 0){
                finalType += tempStrArr[i];
                bracketCount = BracketManager.bracketCounter(finalType,characterFilter);
                i++;
            }
            
            char ch = BracketManager.bracketLastIndex(finalType);
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

    public void setGrade(int grade){
        this.grade = grade;
    }
    public boolean isConstructor(){return constructor;}

   

    public ArrayList<Variable> getVariables(){return variables;}
    public String getFunctionName(){return functionName;}
    public Visibility getAccessModifier(){return accessModifier;}
    public String getReturnType(){return returnType;}
    public boolean isStatic(){return isStatic;}
    public ArrayList<Variable> getParameters(){return parameters;}


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


    public void processFunctionDetails(String line){
        char[] characterFilter = {'<','>','[',']'};
        processParameterString(line,characterFilter);
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
            isStatic=true;
            tests[0] = true;
        }
        if(Arrays.stream(tempStrArr).anyMatch("final"::equals))
            tests[1] = true;
       
        for(boolean t: tests){
            if(t)
                skipCount++;
        }
        int spaceCount =0;;
        while(tempStrArr[spaceCount+1].equals(""))
            spaceCount++;
                        
        int i=1 + spaceCount;
        String returnTypeString =tempStrArr[i + skipCount];
        int bracketCount = BracketManager.bracketCounter(returnTypeString,characterFilter);
        while(bracketCount>0 && returnTypeString.equals("")){
            i++;
            returnTypeString += BracketManager.bracketCounter(tempStrArr[i + skipCount],characterFilter);
            bracketCount = BracketManager.bracketCounter(returnTypeString,characterFilter);
        }
        char ch = BracketManager.bracketLastIndex(returnTypeString);

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
    public void addVariable(String line) {

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

    public Boolean hasVariable(Variable v) {
        for(Variable var:variables){
            if(var.equals(v)){
                return true;
            }
                
        }
        return false;
    }

    public String toString() {

        String str=accessModifier + " ";
        if(isStatic)
            str+= "static ";
        str+= returnType + " " + functionName + "(";
        for(Variable p:parameters)
            str+=p.getType() + " " + p.getName() + ",";
        str = str.substring(0, str.length()-1);
        if(parameters.size()!=0)
            str+=")";
        else
            str+="()";
        return str;
    }

    public boolean equals(Object obj) {

        if (obj instanceof  Function) {
            Function f = (Function) obj;
            if(f.getParameters().toString().equalsIgnoreCase(this.getParameters().toString()) && f.getFunctionName().equalsIgnoreCase(this.getFunctionName()) && f.getReturnType().equalsIgnoreCase(this.getReturnType()) 
            && f.getAccessModifier().equals(this.getAccessModifier()) && f.isStatic() ==this.isStatic())
                   return true;
      }
        return false;
   }
}