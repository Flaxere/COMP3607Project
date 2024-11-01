package tempest_foundation;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.DocumentException;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, DocumentException
    {
        // System.out.println( "Hello World!2" );
        FileReader f = new FileReader();

        DocumentGenerator d = new DocumentGenerator();
        d.createDocument();

        f.readFiles();
    }
}
