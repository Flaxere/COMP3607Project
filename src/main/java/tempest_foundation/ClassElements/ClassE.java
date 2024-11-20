package tempest_foundation.ClassElements;

import java.util.ArrayList;
/**
 * The {@code ClassE} interface defines a blueprint for handling class-related operations 
 * such as managing variables and content. Classes implementing this interface are expected 
 * to provide methods for adding and retrieving variables and content.
 */
public interface ClassE {

    void addVariable(String line);
    void setContent(ArrayList<String> content);
    void addContent(String content);
    ArrayList<String> getContent();

    ArrayList<Variable> getVariables(); 
}


