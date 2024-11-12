package tempest_foundation.ClassElements;
import java.util.ArrayList;

import tempest_foundation.SubmissionElements.MarkSnippet;
public class Function extends MarkSnippet implements ClassE {
    private Visibility accessModifier;
    private String functionName;
    private ArrayList<Variable> parameters;
    private ArrayList<Variable> variables;
    private ArrayList<String> functionContent;
    private boolean constructor;
    private String returnType;
    
    

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
            int angleBracketCounter = 0;
            int startIndex = line.indexOf('(');
            int endIndex = line.indexOf(')');
            strBetweenBrackets = line.substring(startIndex + 1, endIndex);
            String[] tempStrArr = strBetweenBrackets.split("\"(?<=\\\\s)|(?=,)|(?<=,)|(?=\\\\s)\"");
            angleBracketCounter = bracketCounter(strBetweenBrackets);
            int i = 0;
            while(i<tempStrArr.length){
               int bracketCount = bracketCounter(tempStrArr[i]);//This increments the angle counter per line (edited)
               String finalType = tempStrArr[i];
               String finalName;
               i++;
               if(tempStrArr[i].indexOf("<")==0){
                finalType+=tempStrArr[i];
                bracketCount = bracketCounter(finalType);
               }
               while(bracketCount > 0){
                finalType += tempStrArr[i];
                bracketCount = bracketCounter(finalType);
                i++;
               }
               char ch = '*';
               if(finalType.lastIndexOf(">") > finalType.lastIndexOf("]"))
                    ch = '>';
               else{
                    ch = ']';
               }
               if(ch != '*' && finalType.lastIndexOf(ch) != finalType.length()-1){
                    finalName = finalType.substring(finalType.lastIndexOf(ch) + 1, finalType.length());
               }
               else{
                    i++;
                    finalName = tempStrArr [ i ];
               }
               parameters.add(new Variable(finalName, finalType));
                i++; 
                                   
            }

        }

        private int bracketCounter(String line){
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

       



    public void setGrade(int grade){
        this.grade = grade;
    }
    public boolean isConstructor(){return constructor;}

   

    public ArrayList<Variable> getVariables(){return variables;}
    public String getFunctionName(){return functionName;}
    public Visibility getAccessModifier(){return accessModifier;}


    public static Visibility assignVisibility(String line){
        if(line.contains("private"))
            return Visibility.PRIVATE;
        else if (line.contains("protected"))
            return Visibility.PROTECTED;
        else if (line.contains("public"))
            return Visibility.PUBLIC;
        return Visibility.NONE;
    }

    public static String assignName(String line){
        String tempString = new String(line);
        tempString= tempString.substring(0,tempString.indexOf("(")).trim();
        return tempString.substring(tempString.lastIndexOf(" "), tempString.length());
    }


    public static String assignreturnType(String line){
        String tempString = new String(line);
        tempString= tempString.substring(0,tempString.indexOf("(")).trim();
        return tempString.substring(tempString.indexOf(" "),tempString.lastIndexOf(" ")).trim();
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
        return functionName;
    }
}
