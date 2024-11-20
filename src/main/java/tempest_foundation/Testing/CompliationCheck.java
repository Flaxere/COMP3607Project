package tempest_foundation.Testing;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import tempest_foundation.DocumentGenerator;

/**
 * A class for checking to see if a Java file compiles.
 * <p>
 * This class searches for all Java files in the specified submission directory and attempts to compile them.
 * If any compilation errors occur, the errors are printed, and a failure document is generated for the student.
 * </p>
 */
public class CompliationCheck {
    
    private String fileSubmissionPath;
    private String studentID;

     /**
     * Constructs a new {@link CompliationCheck} object for the given student ID.
     * 
     * @param studentID the student ID for whom the compilation check is to be performed.
     */
    public CompliationCheck(String studentID) {
        this.fileSubmissionPath = "..//comp3607project//Submissions//" + studentID + "//";
        this.studentID = studentID;
    }

    /**
     * Runs the compilation check on the student's Java files in the specified submission directory.
     * <p>
     * This method attempts to compile all the Java files found in the submission directory. If no Java files
     * are found or if compilation fails, it prints relevant error messages and generates a failure document for the student.
     * </p>
     *
     * @return true if the compilation is successful, false if there are any errors.
     * @throws Exception if an error occurs during the compilation process.
     */
    public boolean RunCompliation() throws Exception {
        
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        try {
            if (compiler == null)
                System.out.println("Java compiler is not available. Make sure you're using a JDK, not just a JRE.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try(StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {

            ArrayList<File> javaFiles = new ArrayList<>();

            try (Stream<Path> paths = Files.walk(Paths.get(fileSubmissionPath))) {
                paths
                    .filter(Files::isRegularFile) 
                    .filter(path -> path.toString().endsWith(".java")) 
                    .forEach(path -> {
                        try {
                            javaFiles.add(new File(path.toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (javaFiles.size() == 0 || javaFiles == null) {

                System.out.println("Empty directory found, cancelling compliation!");
                DocumentGenerator.generateFailDocument(studentID);
                return false;
            }

            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
            CompilationTask compilationTask = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

            boolean taskSuccess = compilationTask.call();
            if (taskSuccess)
                return true;
            else {
                System.out.println(studentID + " code compilation failed. See errors:");
                diagnostics.getDiagnostics().forEach(diagnostic -> {
                    System.out.println("Error on line " + diagnostic.getLineNumber() + ": " + diagnostic.getMessage(null));
                });
                
                System.out.println();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
