package tempest_foundation;

//Iteration imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.SubmissionElements.Submission;
import tempest_foundation.Testing.CompliationCheck;
import tempest_foundation.Testing.SystemEvaluation;
import tempest_foundation.Testing.TestEnum;
 

/**
    Class focused on unzipping and reading files and its content.
*/
public class FileReader {

    private String filePath = "..\\comp3607project\\sample.zip";
    private String unzippedFilePath = "..\\comp3607project\\UnzippedFolder\\";
    private List<Path> submissionPaths;
    private boolean sLineFunc;

    /**
        Unzips and reads the files within the given directory specified globally in the FileReader class
        * @throws Exception 
        @see FileReader 
    */
    public void readFiles(ArrayList<Submission> studentSubmissions) throws Exception {

        unzip(filePath, unzippedFilePath);
        submissionPaths = listFiles(Paths.get(unzippedFilePath));

        if (submissionPaths.size() == 0) {
            DocumentGenerator.generateNoSumbmissionDocument(filePath);
            return;
        }

        int correctNames = 0;

        for (Path p: submissionPaths) {
            if(p.toString().lastIndexOf("816") != -1)
                correctNames++;
        }

        ExecutorService executor = Executors.newFixedThreadPool(correctNames);

        for(Path p: submissionPaths) {

            if(p.toString().lastIndexOf("816") == -1)
                continue;
            
            long startThreadTime = System.nanoTime();
            executor.submit(() -> {

            String studentID = "no_id";
            ClassDetails tempClass = null;

            String currentFilePath = p.toString();
            String fileSubmissionPath = "";

            if(p.toString().lastIndexOf("816") != -1) {
                studentID = p.toString().substring(p.toString().lastIndexOf("816"),p.toString().lastIndexOf("816") + 9);
                fileSubmissionPath = "..\\comp3607project\\Submissions\\" + studentID + "\\";
                
                try {
                    unzip(currentFilePath, fileSubmissionPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                
            long startTime = System.nanoTime();
            CompliationCheck compliationTester= new CompliationCheck(studentID);
            long endTime = System.nanoTime();

            SystemEvaluation.addOverheadTime(startTime, endTime, studentID, TestEnum.COMPILATION);

            try {
                if(studentID.equals("no_id") ||  compliationTester.RunCompliation()==false)
                    System.out.println("bad_id/code compilation failed\n");
                else if(fileSubmissionPath!=""){
                
                    Submission currSub = new Submission(studentID);
                    try (Stream<Path> paths = Files.walk(Paths.get(fileSubmissionPath))) {
                        paths
                            .filter(Files::isRegularFile) 
                            .filter(path -> path.toString().endsWith(".java")) 
                            .forEach(path -> {
                                try {
                                    Scanner scanner = new Scanner(path);
                                    ClassDetails newClass = readAssigment(scanner, tempClass);
                                    currSub.addClass(new ClassDetails(newClass.getClassName(),newClass.getVariables(),newClass.getFunctions()));

                                } catch (IOException e) {System.out.println("Error reading file: " + path + " - " + e.getMessage());}
                            });

                        } catch (IOException e) {System.out.println("IOException: " + e.getMessage());}
                        studentSubmissions.add(currSub);
                    }
                } catch (Exception e) {e.printStackTrace();}

                long endThreadTime = System.nanoTime();

                SystemEvaluation.addOverheadTime(startThreadTime, endThreadTime, studentID, TestEnum.THREAD);
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File oldZipFolder = new File(unzippedFilePath);
        FileUtils.deleteDirectory(oldZipFolder);
    }


    /**
        Reads through the assignment, maintaining awareness of class and function content
        @param scanner the scanner that keeps track of the current reading
    */
    private synchronized ClassDetails readAssigment(Scanner scanner, ClassDetails tempClass) {

        ArrayList<String> fileContents = new ArrayList<>(); 
        tempClass = null;
        while (scanner.hasNextLine()) {
            String line = nextNonEmptyLine(scanner);
            String classNameString;
            //The code snippet below still neeeds to account for single line functions
           
            Function tempFunction = null;
            if(line.lastIndexOf("{")!=-1){//TODO: Run through all of the code, replacing the old functionality with the more abstract version
                classNameString = line.substring(0, line.lastIndexOf("{")+1).trim();
                if(classNameString.substring(0, 1).equals("{"))
                    classNameString =fileContents.get(fileContents.size()-1);
            
                tempClass = new ClassDetails(classNameString);
                tempFunction = null;
                int[]classCurlyBrackets={1};
                while(classCurlyBrackets[0]!=0 && scanner.hasNextLine()){  
                    tempFunction = readFunction(fileContents,classCurlyBrackets, scanner, line,tempClass) ; 

                    //Adds the function into the designated class
                    if(tempFunction!=null){
                        tempFunction.addContent(line);
                        tempClass.addFunction(tempFunction);
                    }
                    
                    fileContents.add(line);
                    line = nextNonEmptyLine(scanner);
                  
                    if((line.contains("{") || line.contains("}")) && !isComment(line)){
                        tempFunction=null;
                        classCurlyBrackets[0] += bracketCounter(line, tempClass,tempFunction);
                    }
                    if(!sLineFunc && classCurlyBrackets[0] < 2)
                        tempClass.addVariable(line);
                    if(classCurlyBrackets[0]==0)
                        break;
                    sLineFunc = false;
                    tempFunction = null;
                }
            }

            fileContents.add(line);
        }

        scanner.close();
        return tempClass;
    }

    private boolean isComment(String line) {

        line = line.trim();
        if(line.indexOf("/")==0)
            return true;
        return false;
    }


    /**
        Reads the function details within a class
        @param fileContents code within the file
        @param bracketCount used to keep track of the current open/closed bracket
        @param scanner the scanner used to iterate through the code
        @param line current line being read
        @param tempClass the class containing the function
        @return the function containing it's content
    */
    private synchronized Function readFunction(ArrayList<String> fileContents,int[] bracketCount, Scanner scanner, String line, ClassDetails tempClass) {

        Function tempFunction = null;
        String funcName =line;

        line=line.trim();
        while(bracketCount[0]>1){
            if (tempFunction==null){
                if(line.indexOf("{")==0)
                    funcName = fileContents.get(fileContents.size()-1) + line;
                tempFunction = new Function();
                tempFunction.processFunctionDetails(funcName);
            }
            else{
                tempFunction.addVariable(line);
                tempFunction.addContent(line);  
                line = nextNonEmptyLine(scanner);
                if(line.contains("{") || line.contains("}"))
                    bracketCount[0] += bracketCounter(line,tempClass,tempFunction) ;
            }
        }

        if(tempFunction!=null)
            tempFunction.addContent(line);

        return tempFunction;
    }


    /**
        This function is used to keep track of if the supplied line is in a paticular method/ class by returning 
        @param line The line that will be scanned for a bracket
        @return 
    */
    private synchronized int bracketCounter(String line,ClassDetails classref,Function funcRef) {

        if(line.contains("{") &&line.contains("}") ){
            // stringRef[0]+=line  + "\n";
            if(funcRef==null){
                funcRef = new Function();
                funcRef.processFunctionDetails(line);
            }
            sLineFunc = true;
            //Todo: Make this account for a long program on one line, splitting up into separate lines
            funcRef.addContent(line);
            classref.addFunction(funcRef);
            return 0;
        }  
        else if(line.contains("{"))
            return 1;
    else if(line.contains("}"))
            return -1;
        return 0;
    }


    /**
        Gets the next non empty line whilst reading
        @param scanner The line that will be scanned for a bracket
        @return The non empty string if found
    */
    private String nextNonEmptyLine(Scanner scanner) {

        String nEmptyString;
        if(!scanner.hasNextLine()) {
            return "";
        }

        nEmptyString = scanner.nextLine();
        while(scanner.hasNextLine() && nEmptyString.trim().equals("")) {
            nEmptyString = scanner.nextLine();
        }

        return nEmptyString;
    }


    /**
        Gets the paths of all of the files listed within a zip file 
        @param path The line that will be scanned for a bracket
        @return A list of the filtered paths
    */
    public List<Path> listFiles(Path path) throws IOException {

        List<Path> result;
       
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile )
                    .collect(Collectors.toList());
        }
        
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