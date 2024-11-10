package tempest_foundation;

//Iteration imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.List;
import java.util.ArrayList;

//PDf imports


import java.util.Enumeration;
import java.util.HashMap;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.Map;
import tempest_foundation.ClassElements.*;
import tempest_foundation.SubmissionElements.*;
//import org.apache.pdfbox.pdmodel.PDPage;
 

/**
    Class focused on unzipping and reading files and its content.
*/
public class FileReader {

    private String filePath = "..\\comp3607project\\sample.zip";
    private String unzippedFilePath = "..\\comp3607project\\UnzippedFolder\\";
    private List<Path> submissionPaths;
    //private int hasSubmissions;
    private Map<String,ArrayList<String>> classMethods;
    private Map<String,Map<String,ArrayList<String>>> submissionListing;
    private Submission currSub;
    private boolean sLineFunc;

    public FileReader(Map<String,Map<String,ArrayList<String>>> submissionListing){

        classMethods = new HashMap<>();
        this.submissionListing = submissionListing;
    }
    
    /**
        Unzips and reads the files within the given directory specified globally in the FileReader class
        @see FileReader 
    */

    public void readFiles(ArrayList<Submission> studentSubmissions) throws IOException {
        unzip(filePath, unzippedFilePath);
        Map<String,ArrayList<String>> map;
        submissionPaths = listFiles(Paths.get(unzippedFilePath));
        String studentID="temp";
        for(Path p: submissionPaths){
            
            if(p.toString().lastIndexOf("816") != -1)
                studentID = p.toString().substring(p.toString().lastIndexOf("816"),p.toString().lastIndexOf("816") + 9);
            currSub = new Submission(studentID);
            try (ZipFile submission = new ZipFile(p.toString())) {
                Enumeration<? extends ZipEntry> assignments = submission.entries(); 
                if (!assignments.hasMoreElements())
                    System.out.println(getPlainName(submission.getName()) + " contains no files");
                else while (assignments.hasMoreElements()) {
                    ZipEntry submissionZip = assignments.nextElement();
                    try (InputStream inputStream = submission.getInputStream(submissionZip)) {
                        Scanner scanner = new Scanner(inputStream); {
                            if(submissionZip.getName().contains(".java"))
                                readAssigment(scanner);     
                        }
                    } 
                }
           
                classMethods.clear();
                studentID = "temp";
            }catch(ZipException zExc){
                System.out.println("ZipException => " + zExc.getMessage());
            }
        }

        // System.out.println(submissionListing.get("816035980"));    
    }

    private void readAssigment(Scanner scanner){{
        ArrayList<String> fileContents = new ArrayList<>(); 
        while (scanner.hasNextLine()) {
            String line = nextNonEmptyLine(scanner);
            String classNameString;
            //The code snippet below still neeeds to account for single line functions
            // ClassDetails tempClass = null;
            // Function tempFunction = null;
             if(line.lastIndexOf("{")!=-1){//TODO: Run through all of the code, replacing the old functionality with the more abstract version
                classNameString = line.substring(0, line.lastIndexOf("{")+1).trim();
                if(classNameString.substring(0, 1).equals("{"))
                    classNameString =fileContents.get(fileContents.size()-1);
            
                ClassDetails tempClass = new ClassDetails(classNameString);
                Function tempFunction = null;
                int classCurlyBrackets=1;
                while(classCurlyBrackets!=0 && scanner.hasNextLine()){  
                    tempFunction = readFunction(classCurlyBrackets, scanner, line,tempClass) ; 
                    // while(classCurlyBrackets>1){
                    //     if (tempFunction==null){
                    //         tempFunction = new Function(Function.assignName(line),Function.assignVisibility(line));
                    //         tempFunction.addContent(line);
                    //     }
                    //     else{
                    //         tempFunction.addContent(line);
                    //         line = nextNonEmptyLine(scanner);
                    //         classCurlyBrackets += bracketCounter(line,tempClass,tempFunction) ;
                    //     }
                    // }
                    // tempFunction.addContent(line);]

                    //Adds the function into the designated class
                    if(tempFunction!=null){
                        tempFunction.addContent(line);
                        tempClass.addFunction(tempFunction);
                        
                    }
                    line = nextNonEmptyLine(scanner);
                    if(line.contains("{") || line.contains("}"))
                        classCurlyBrackets += bracketCounter(line, tempClass,tempFunction);
                    if(!sLineFunc && classCurlyBrackets < 2)
                        tempClass.addVariable(line);
                    if(classCurlyBrackets==0)
                        break;
                    sLineFunc = false;
                    tempFunction = null;
                }
                
                if (tempClass!=null){
                    System.out.println(tempClass.getClassName());
                    System.out.println(tempClass.getVariables());
                    
                }  
            }
            fileContents.add(line);
        } 
        
        scanner.close();


    }

    }
     /**
        Strips the preceding filePath from the fileName
        @param fname The original string that contains the filepath
        @return The updated string without the filepath
    */
    private String getPlainName(String fname){
        return fname.substring(fname.lastIndexOf("\\")+1,fname.lastIndexOf("."));
    }

     /**
        This function is used to keep track of if the supplied line is in a paticular method/ class by returning 
        @param line The line that will be scanned for a bracket
        @return 
    */
    private int bracketCounter(String line,ClassDetails classref,Function funcRef){

        if(line.contains("{") &&line.contains("}") ){
            // stringRef[0]+=line  + "\n";
            if(funcRef==null)
                funcRef = new Function(Function.assignName(line), Function.assignVisibility(line));
            sLineFunc = true;
            //Todo: Make this account for a long program on one line, splitting up into separate lines
            funcRef.addContent(line);
            return 0;
        }  
        else if(line.contains("{"))
            return 1;
    else if(line.contains("}"))
            return -1;
        return 0;
    }


    private String nextNonEmptyLine(Scanner scanner){
        String nEmptyString;
        if(scanner.hasNextLine()){
            nEmptyString = scanner.nextLine();
        
            while(nEmptyString.trim().equals("")){
                nEmptyString = scanner.nextLine();
            }
            return nEmptyString;
        } 
        return "";
    }

    private Function readFunction(int bracketCount, Scanner scanner, String line, ClassDetails tempClass){
        Function tempFunction = null;
        while(bracketCount>1){
            if (tempFunction==null){
                tempFunction = new Function(Function.assignName(line),Function.assignVisibility(line));
            }
            else{
                tempFunction.addVariable(line);
                System.out.println(tempFunction.getVariables());
                tempFunction.addContent(line);  
                line = nextNonEmptyLine(scanner);
                if(line.contains("{") || line.contains("}"))
                    bracketCount += bracketCounter(line,tempClass,tempFunction) ;
            }
        }
        if(tempFunction!=null)
            tempFunction.addContent(line);
        return tempFunction;
    }

    /**
        Gets the paths of all of the files listed within a zip file 
        @param path The line that will be scanned for a bracket
        @return A list of the filtered paths
    */
    public List<Path> listFiles(Path path) throws IOException {
        ArrayList<Integer> removeList = new ArrayList<>();
        List<Path> result;
       
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile )
                    .collect(Collectors.toList());
        }
        // for (int i=result.size()-1;i >=0;i--) {
        //     System.out.println(result.get(i).toString());
        //     Path r = result.get(i);
        //     String name = getPlainName(result.get(i).toString());
        //     if(getPlainName(result.get(i).toString()).matches("Jared_DeFour_816[0-9]{6}_A1") == false)
        //         result.remove(i);
        // }
        if(result.size()==0)
            System.out.print("No submissions exist in this directory");
        return result;
    }

    /**
        Unzips the supplied file and stores its contents in the appropriate folder
        @param zipFile the file that will be unzipped
        @param destFolder the location that the extracted file will be stored in
    */
    public static void unzip(String zipFile, String destFolder) throws IOException {

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destFolder + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }
}
