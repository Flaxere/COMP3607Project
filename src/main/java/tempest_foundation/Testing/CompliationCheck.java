package tempest_foundation.Testing;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class CompliationCheck {
    
    private String fileSubmissionPath;
    private String studentID;

    public CompliationCheck(String studentID) {
        this.fileSubmissionPath = "..//comp3607project//Submissions//" + studentID + "//";
        this.studentID = studentID;
    }

    public void RunCompliation() throws Exception {
        
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
                return;
            }

            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
            CompilationTask compilationTask = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

            boolean taskSuccess = compilationTask.call();
            if (taskSuccess)
                //Add comment directly to PDF Generator
                System.out.println(studentID + " code compilation was successful!\n");
            else {
                //Add comment directly to PDF Generator
                System.out.println(studentID + " code compilation failed. See errors:");
                diagnostics.getDiagnostics().forEach(diagnostic -> {
                    System.out.println("Error on line " + diagnostic.getLineNumber() + ": " + diagnostic.getMessage(null));
                });
                
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
