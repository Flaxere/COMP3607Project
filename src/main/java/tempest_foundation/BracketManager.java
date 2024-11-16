package tempest_foundation;


public class BracketManager {


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

    private static int characterCounter(String line,char ch){
        return (int) line.chars() 
        .filter(c -> c == ch) 
        .count();
    }


    public static char bracketLastIndex(String str){
        if(str.lastIndexOf(">") > str.lastIndexOf("]"))
            return '>';
        else if(str.lastIndexOf(">") < str.lastIndexOf("]"))
            return ']';
        return '*';
    }

   
    
}
