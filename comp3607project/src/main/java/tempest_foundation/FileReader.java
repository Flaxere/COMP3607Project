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
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPage;
 


public class FileReader{
    private String filePath = "C:\\Users\\Default.LAPTOP-10891MC3\\Documents\\COMP3607Project\\comp3607project\\Sample.zip\\";
    private String unzippedFilePath = "C:\\Users\\Default.LAPTOP-10891MC3\\Documents\\COMP3607Project\\comp3607project\\UnzippedFolder\\";
    private List<Path> submissionPaths;
    private int hasSubmissions;
    private Map<String,ArrayList<String>> classMethods;

    public FileReader(){
        classMethods = new HashMap<>();
    }
    
    public void readFiles() throws IOException{
        unzip(filePath, unzippedFilePath);
        submissionPaths = listFiles(Paths.get(unzippedFilePath));
        if(submissionPaths.size()== 0)
            System.out.print("No submissions exist in this directory");
        else for(Path p: submissionPaths){
            try (ZipFile submission = new ZipFile(p.toString())) {
                Enumeration<? extends ZipEntry> assignments = submission.entries(); 
                if (!assignments.hasMoreElements())
                    System.out.println(getPlainName(submission.getName()) + " contains no files");
                else while (assignments.hasMoreElements()) {
                    ZipEntry submissionZip = assignments.nextElement();
                    try (InputStream inputStream = submission.getInputStream(submissionZip)) {
                        Scanner scanner = new Scanner(inputStream); {
                            String methodContentString="";   
                            while (scanner.hasNextLine()) {
                                String line = scanner.nextLine();
                                String classNameString;
                                //The code snippet below still neeeds to account for single line functions
                                if(line.lastIndexOf("{")!=-1){
                                    classNameString = line.stripTrailing().substring(0, line.lastIndexOf("{"));
                                    classMethods.put(classNameString ,new ArrayList<>());
                                    String classContentString ="";
                                    methodContentString +=line;
                                    int classCurlyBrackets=1;
                                    while(classCurlyBrackets!=0 && scanner.hasNextLine()){
                                        classContentString ="";
                                        while(classCurlyBrackets>1){
                                            classContentString+=line + "\n";
                                            line = scanner.nextLine();
                                            classCurlyBrackets += bracketCounter(line) ;
                                        }
                                        //Adds the function into the designated arrayList
                                        if(classContentString!=""){
                                            classContentString+=line ;
                                            classMethods.get(classNameString).add(classContentString);
                                        }

                                        line = scanner.nextLine();
                                        classCurlyBrackets += bracketCounter(line);
                                        if(classCurlyBrackets==0)
                                            break;
                                        methodContentString +="\n" + line;
                                    }
                                    
                                }
                                if(methodContentString!="")
                                    System.out.println("Class CAPTURED BELOW\n"+methodContentString);
                                System.out.println(line); 
                            }  
                            scanner.close();
                            
                        }
                    } 
                }
            }
        }  
        System.out.println(classMethods);    
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
    private int bracketCounter(String line){
        if(line.lastIndexOf("{")!=-1 &&line.lastIndexOf("}")!=-1 )
            return 0;
        if(line.lastIndexOf("{")!=-1)
            return 1;
        else if(line.lastIndexOf("}")!=-1)
            return -1;
        return 0;
    }

    // private boolean functionSnippet(int bracketCount){
    //     if(bracketCount ==0)
    //         return false;

    //     return true;
    // }

    public static List<Path> listFiles(Path path) throws IOException {

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
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
