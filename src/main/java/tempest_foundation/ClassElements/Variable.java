package tempest_foundation.ClassElements;

import java.util.Arrays;
public class Variable {
    private String type;
    private Visibility accessModifier;
    private String variableName;
    private boolean classVariable;
    private boolean isStatic;
    

    public Variable(String variableName, String type){
        this.variableName = variableName;
        this.type = type;
        accessModifier = Visibility.NONE;
        classVariable=false;
    }
    public Variable(String variableName, String type,Visibility accessModifier){
        this.variableName = variableName;
        this.type = type;
        this.accessModifier = accessModifier;
        classVariable=false;
    }


    public static String assignName(String line){//TODO: Account for multiple variables on the same line being separated by a ,
        String[] tempStrArr = line.trim().split("[\\s\\s*,\\s*]"); 
        if(nonVariable(line) || tempStrArr.length <=1 || tempStrArr[0].contains("=") || hasExpression(tempStrArr[1]) || isKeyword(tempStrArr[0]))
            return "0nonVariable";
        boolean[] tests = {false, false};
        int skipCount=0;
        // String tempString = new String(line.trim());
        if(Arrays.stream(tempStrArr).anyMatch("static"::equals))
            tests[0] = true;
        if(Arrays.stream(tempStrArr).anyMatch("final"::equals))
            tests[1] = true;
       
        for(boolean t: tests){
            if(t)
                skipCount++;
        }
        Visibility v = assignVisibility(line);
        if(v==Visibility.NONE){
            if(tempStrArr[1+skipCount].equals("="))
                return "0nonVariable";
            if(tempStrArr[1+skipCount].contains("="))
                return tempStrArr[1+skipCount].substring(0, tempStrArr[1+skipCount].indexOf("="));
            if(!tempStrArr[1+skipCount].contains(";") ||!tempStrArr[1+skipCount].contains("=") )
                return tempStrArr[1+skipCount];
            return tempStrArr[1+skipCount].substring(0, tempStrArr[1+skipCount].indexOf(";"));
        }else{
            if(tempStrArr[2+skipCount].contains("="))//Todo: This condition works well, but earlier checks are required to prevent += from entering 
                return tempStrArr[2+skipCount].substring(0, tempStrArr[2+skipCount].indexOf("="));
            if(!tempStrArr[2+skipCount].contains(";") ||!tempStrArr[2+skipCount].contains("=") )
                return tempStrArr[2+skipCount];
            return tempStrArr[2+skipCount].substring(0, tempStrArr[2+skipCount].indexOf(";"));
        }

        
           
       
        // if(tempString.contains("static"))
        //     tempString = tempString.substring(tempString.indexOf("static") + 6,tempString.length()).trim();
        // else{
        //     if(v==Visibility.NONE)
        //         return tempString.substring(0 , tempString.indexOf(" "));
        //     tempString = tempString.substring(assignVisibility(line).name().length(),tempString.length()).trim();
        // }
            
        // tempString = tempString.substring(tempString.indexOf(" "), tempString.length()).trim();

        // if(tempString.contains("=")){
            
        //     tempString =  tempString.substring(0, tempString.indexOf("=")).trim();
        //     if(tempString.equals(""))
        //         return "0nonVariable";
        //     return tempString;
        // }
        // return tempString.substring(0, tempString.indexOf(";")).trim();

    }

    private static boolean hasExpression(String line){
        if(line.equals(""))
            return false;
        if(line.charAt(0) == '=' || line.charAt(0) == '+' || line.charAt(0) == '-'|| line.charAt(0) == '/'||line.charAt(0) == '*' || line.charAt(0) == '%' )
            return true;
        return false;
        
    }
    
    private static boolean nonVariable(String line){
        String formattedLine = line.replace(" ","");
        
        if(formattedLine.contains(";")!=true || formattedLine.contains("return")==true || formattedLine.contains("if(") ||formattedLine.contains("do{") || formattedLine.contains("while{") || formattedLine.contains("System.out.println")  )
            return true;
        return false;
    }
    
    private static boolean isKeyword(String line){
        
        if(line.indexOf("do")==0||line.indexOf("while")==0||line.indexOf("for")==0||
        line.indexOf("catch")==0||line.indexOf("case")==0||line.indexOf("class")==0||
        line.indexOf("interface")==0||line.indexOf("throw")==0||line.indexOf("package")==0||
        line.indexOf("else")==0||line.indexOf("try")==0||line.indexOf("finally")==0||
        line.indexOf("break")==0|| line.indexOf("if")==0||line.indexOf("this")==0)
            return true;
        return false;
    }

    
    

    public static Visibility assignVisibility(String line){
        if(line.contains("private"))
            return Visibility.PRIVATE;
        else if (line.contains("protected"))
            return Visibility.PROTECTED;
        else if (line.contains("public"))
            return Visibility.PUBLIC;
        return Visibility.NONE;
    }

    public static String assignType(String line){
        if(line.contains(";")!=true || line.contains("return")==true)
            return "0nonVariable";
        


            String[] tempStrArr = line.trim().split("[,\\.\\s\\s*,\\s*]"); 
        boolean[] tests = {false, false, false};
        int skipCount=0;
        // String tempString = new String(line.trim());
        if(Arrays.stream(tempStrArr).anyMatch("static"::equals))
            tests[0] = true;
        if(Arrays.stream(tempStrArr).anyMatch("final"::equals))
            tests[1] = true;
        if(Arrays.stream(tempStrArr).anyMatch("transient"::equals))
            tests[2] = true;
        
        for(boolean t: tests){
            if(t)
                skipCount++;
        }
        Visibility v = assignVisibility(line);
        if(v==Visibility.NONE){
            if(tempStrArr[1].equals("="))
                return "0nonVariable";
            return tempStrArr[0+skipCount];
        }else{

            return tempStrArr[1+skipCount];
        }
        

        // String tempString = new String(line.trim());
        // if(tempString.contains("=")){
        //     tempString = tempString.substring(0, tempString.indexOf("=")).trim();
        //     if(tempString.contains(" ")!= true)
        //         return "0nonVariable";
        //     return tempString.substring(0,tempString.indexOf(" ")).trim();
        // }
        // if(tempString.contains("static")){
        //     tempString = tempString.substring(tempString.indexOf("static") + 6,tempString.length()).trim();
        //     return tempString.substring(0,tempString.indexOf(" ")).trim();
        // }   

        // if(tempString.contains(" ")!= true)
        //     return "0nonVariable";
        // if(assignVisibility(line) == Visibility.NONE)
        //     return tempString.substring(0,tempString.indexOf(" ")).trim();

        // tempString = tempString.substring(tempString.indexOf(" "), tempString.length()).trim();
        // tempString = tempString.substring(0,tempString.indexOf(" ")).trim();
        // return tempString;
    }


    public String getName(){return this.variableName;};
    public String getType(){return this.type;};
    public Visibility getVisibility(){return this.accessModifier;}

    public String toString(){
        return getVisibility() +" " + getType()+ " " + getName() ;
    }

    public boolean equals(Object obj) {
        if (obj instanceof  Variable) {
            Variable v = (Variable) obj;
            if(v.getName().equals(this.getName()) && v.getType().equals(this.getType()))
                   return true;
      }
        return false;
   }

}
