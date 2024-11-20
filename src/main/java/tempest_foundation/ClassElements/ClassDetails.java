package tempest_foundation.ClassElements;


import java.util.ArrayList;

import tempest_foundation.SubmissionElements.MarkSnippet;

/**
 * The {@code ClassDetails} class represents a detailed structure of a class in a program.
 * It extends {@link MarkSnippet} and contains information such as the class name, 
 * variables, functions, and class content. It also provides methods for manipulation 
 * and grading of class components.
 */
public class ClassDetails extends MarkSnippet{//TODO: This is the CLASS class

    private String className;
    private ArrayList<Variable> variables;
    private ArrayList<String> classContent;
    private ArrayList<Function> functions;

    /**
     * Constructs a {@code ClassDetails} object with the specified class name.
     *
     * @param className the name of the class
     */
    public ClassDetails(String className) {

        super();
        this.className = className;
        variables = new ArrayList<>();
        functions = new ArrayList<>();
    }

    /**
     * Constructs a {@code ClassDetails} object with the specified class name, 
     * variables, and functions.
     *
     * @param className the name of the class
     * @param variables a list of {@link Variable} objects representing class variables
     * @param functions a list of {@link Function} objects representing class functions
     */
    public ClassDetails(String className,ArrayList<Variable> variables,ArrayList<Function> functions ) {

        this.className = className;
        this.variables = variables;
        this.functions = functions;
    }

    /**
     * Adds a function to the class.
     *
     * @param f the {@link Function} object to add
     */
    public void addFunction(Function f){
        functions.add(f);
    }

     /**
     * Sets the content of the class.
     *
     * @param content a list of strings representing the class content
     */
    @Override
    public void setContent(ArrayList<String> content) {
       this.classContent = content;
    }
    
    /**
     * Adds a line of content to the class.
     *
     * @param content the string content to add
     */
    @Override
    public void addContent(String content){
        this.classContent.add(content);
    }
    /**
     * Gets the name of the class.
     *
     * @return the class name
     */
    public String getClassName(){return className;}
    /**
     * Gets the list of variables in the class.
     *
     * @return a list of {@link Variable} objects
     */
    public ArrayList<Variable> getVariables(){return variables;}

     /**
     * Gets the list of functions in the class.
     *
     * @return a list of {@link Function} objects
     */
    public ArrayList<Function> getFunctions(){return functions;}

    @Override
    public ArrayList<String> getContent() {
        return this.classContent;
    }

    /**
     * Adds a variable to the class by parsing a line of code that was read in, it adds the function name visibility.
     *  data can be held in a list of variables
     * @param line a string representing a variable declaration
     */
    @Override
    public void addVariable(String line){//FIX: THis is the function jared. Just put the variable in the bag bro
        String varName = Variable.assignName(line);
        if(varName.equals("0nonVariable"))
            return;
        Visibility vis = Variable.assignVisibility(line);
        if(vis==Visibility.NONE){
            variables.add(new Variable(varName, Variable.assignType(line)));
        }else
            variables.add(new Variable(Variable.assignName(line), Variable.assignType(line),vis)) ;
    }  

     /**
     * Extracts the class name from a given line of code.
     *
     * @param line a string containing the class declaration
     * @return the name of the class
     */
    public static String assignName(String line) {

        Boolean[] test = {false,false};
        int pos =line.lastIndexOf("{");

        if(line.contains("extends"))
            test[0]=true;
        if(line.contains("implements"))
            test[1]=true;

        if(test[1] && test[2]){
            pos = Math.min( line.indexOf("extends"),line.indexOf("implements"));
        }else if(test[1]){
            pos = line.indexOf("extends");
        }else if(test[2]){
            pos = line.indexOf("implements");
        }
        
        String newString = new String(line).substring(0,pos).trim();//FIX: Please make this not as stupidly implemented

        return newString.substring(newString.lastIndexOf(" "),newString.length());
    }

    /**
     * Calculates the total grade for the class, including grades for all functions.
     *
     * @return the total grade as a double
     */
    public double getTotalGrade() {

        double totalGrade = getGrade();

        for (Function f: functions)
            totalGrade += f.getGrade();

        return totalGrade;
    }

    
    public String toString() {

        String str = getClassName() + ":\n";

        for(Function f:functions)
            str +=f + "\n";
        return str;
    }
}
