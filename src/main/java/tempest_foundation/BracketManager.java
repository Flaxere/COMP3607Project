package tempest_foundation;


/**
 * The BracketManager class provides utility methods for managing and analyzing bracket-like characters 
 * (e.g., angle brackets '<>', square brackets '[]') in a given string.
 * <p>
 * This class includes methods to count bracket occurrences for the purpose of being able pare out variables.
 * </p>
 */
public class BracketManager {


    /**
     * Counts the balance of bracket-like characters in a given string based on their specified types.
     * <p>
     * This method iterates over the characters specified in the 'chars' array, incrementing or decrementing
     * the count based on the type of bracker. If the bracket is a left bracket the count is increased. 
     * If the type is a right bracket the count is decreased
     * </p>
     * 
     * @param line the string in which bracket-like characters will be counted.
     * @param chars a line, potentially wth brackets to be counted.
     * @return the final count of brakets, if the final count is 0 the brackets are balanced and correct.
     *         
     */
    public static int bracketCounter(String line, char[] chars){
        int count =0;
        for(int i=0;i < chars.length;i++){
            if(i%2==0)
                count+=characterCounter(line,chars[i]);
            else
                count-=characterCounter(line,chars[i]);
        }    
        return count;
    }

    /**
     * Counts the occurrences of a specific character in a string, this will be used to check brackets.
     * <p>
     * This helper method filters the string to count how many times the specified character appears.
     * </p>
     * 
     * @param line the string in which the character will be counted.
     * @param ch the character to count in the string.
     * @return the number of times the specified character appears in the string.
     */
    private static int characterCounter(String line,char ch){
        return (int) line.chars() 
        .filter(c -> c == ch) 
        .count();
    }



    /**
     * Identifies the last bracket-like character in a given string based on the types of angle brackets ('>') 
     * and square brackets (']').
     * <p>
     * This method compares the last indices of '>' and ']' in the string and returns the character that occurs last.
     * If both characters appear at the same position, it returns '*' to indicate no clear last character.
     * </p>
     * 
     * @param str the string in which the last bracket-like character is to be identified.
     * @return the last occurring bracket-like character, either '>' or ']', or '*' if neither is dominant.
     */
    public static char bracketLastIndex(String str){
        if(str.lastIndexOf(">") > str.lastIndexOf("]"))
            return '>';
        else if(str.lastIndexOf(">") < str.lastIndexOf("]"))
            return ']';
        return '*';
    }

   
    
}
