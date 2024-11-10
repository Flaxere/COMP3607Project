package tempest_foundation.ClassElements;


import java.util.ArrayList;
import tempest_foundation.SubmissionElements.MarkSnippet;


public class ClassDetails extends MarkSnippet implements ClassE{//TODO: THis is the CLASS class
    private String className;
    private ArrayList<Variable> variables;
    private ArrayList<String> classContent;
    private ArrayList<Function> functions;


    public ClassDetails(String className){
        this.className = className;
        variables = new ArrayList<>();
        functions = new ArrayList<>();
    }


    public void addFunction(Function f){
        functions.add(f);
    }

    @Override
    public void setContent(ArrayList<String> content) {
       this.classContent = content;
    }
    
   

    @Override
    public void addContent(String content){
        this.classContent.add(content);
    }

    public String getClassName(){return className;}
    public ArrayList<Variable> getVariables(){return variables;}
    public ArrayList<Function> getFunctions(){return functions;}

    @Override
    public ArrayList<String> getContent() {
        return this.classContent;
    }
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

    public static String assignName(String line){
        Boolean[] test = {false,false};
        int pos =line.lastIndexOf("{");
        if(line.contains("extends"))
            test[0]=true;
        if(line.contains("implements"))
            test[1]=true;

        if(test[1] && test[2]){
            pos = Math. min( line.indexOf("extends"),line.indexOf("implements"));
        }else if(test[1]){
            pos = line.indexOf("extends");
        }else if(test[2]){
            pos = line.indexOf("implements");
        }
        
        String newString = new String(line).substring(0,pos).trim();//FIX: Please make this not as stupidly implemented

        return newString.substring(newString.lastIndexOf(" "),newString.length());
    }
   


}
