package tempest_foundation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.SubmissionElements.Submission;
import tempest_foundation.Testing.TestEnum;


/**
 * The DocumentGenerator class is responsible for generating PDF reports for student submissions.
 * These reports include details such as the student's grade, the grade for individual methods,
 * comments, and other relevant information about the student's submission.
 * <p>
 * This class includes methods for generating documents for successful submissions, failed submissions,
 * or cases where no submission was made. It also supports creating overhead reports and appending data to them.
 * </p>
 */
public class DocumentGenerator {

    private static Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    private static Document overheadDocument = new Document();

    /**
        Creates the directory in which the PDF Reports will be generated
        * @throws DocumentException 
        * @throws FileNotFoundException 
    */
    private static void SetupDirectory(Document document, String pdfName) throws FileNotFoundException, DocumentException {

        new File("..//comp3607project//Submissions//PDF Reports").mkdirs();
        PdfWriter.getInstance(document, new FileOutputStream( "..//comp3607project//Submissions//PDF Reports//" + pdfName + ".pdf"));
    }

    /**
        Creates a new PDF document of type PDF and adds content by appending chunks
    */
    public void createDocument(Submission s) throws FileNotFoundException, DocumentException {

        Document document = new Document();
        SetupDirectory(document, s.getStudentId());

        document.open();
        font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(s.getStudentId(), font);

        chunk.append(" " + s.getGrade() + "%");
        document.add(chunk);

        
        // Add cells to the table
       
        ArrayList <ClassDetails> classes = s.getClasses();

            
        PdfPTable currentRow = new PdfPTable(4);
        // currentRow.setKeepTogether(true);
        currentRow.setWidths(new float[]{ 50F, 15F,85F,20F});

       for(int i = 0; i < classes.size(); i++) { // Loop through classes
        ClassDetails currentClass=classes.get(i);
        // Add rows for each class

            PdfPTable headerRow = new PdfPTable(1); // 3 columns
            headerRow.setKeepTogether(true);

            Paragraph p = new Paragraph(currentClass.getClassName());
            p.setAlignment(Element.PTABLE);

            PdfPCell cell = new PdfPCell();
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerRow.addCell(cell);
           
            // cell.setRowspan(currentClass.getFunctions().size());
            document.add(headerRow);

            currentRow.deleteBodyRows();
            // Add cells to the table
            currentRow.addCell("Method");
            currentRow.addCell("Method's Score");
            currentRow.addCell("Comment");
            currentRow.addCell("Total");
            for (Function f:currentClass.getFunctions()) { // Loop through methods for the current class
                // Class Name (only shown in the first column for the first method of each class)
                
                currentRow.addCell(f.toString());
                String grade = String.format("%.2f",f.getGrade());
                currentRow.addCell(grade);
                String comments="";
                for(String comment:f.getComment())
                    comments+=comment +"\n";
                comments=comments.strip();
                currentRow.addCell(comments);
                currentRow.addCell("" +(int) f.getTotal());
                // classCell.setRowspan(currentClass.getFunctions().size()); // Span across all methods for this class
                // classCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                // classCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            }

            document.add(currentRow);
            document.newPage();
        }

        document.close();
    }

    public static synchronized void generateFailDocument(String studentID) throws FileNotFoundException, DocumentException {

        Document doc = new Document();
        SetupDirectory(doc, studentID);

        PdfWriter.getInstance(doc, new FileOutputStream( "..//comp3607project//Submissions//PDF Reports//" + studentID + ".pdf"));
        doc.open();

        Paragraph p = new Paragraph(studentID + "'s project contained zero (0) java files!\n", font);

        doc.add(p);
        doc.close();
    }

    public static synchronized void generateNoSumbmissionDocument(String directoryPath) throws DocumentException, FileNotFoundException {
        
        Document doc = new Document();
        SetupDirectory(doc, "Report");

        doc.open();

        Paragraph p = new Paragraph("No submissions were found!\n", font);
        p.add("Empty zip submissions folder found at " + directoryPath);

        doc.add(p);
        doc.close();
    }

    public static synchronized void instantiateOverheadDocument() throws FileNotFoundException, DocumentException {
        
        SetupDirectory(overheadDocument, "Overhead Report");
        overheadDocument.open();
    }

    public static synchronized void addOverheadData(String formattedSeconds, String formattedMillis, String studentID, TestEnum testEnum)
        throws FileNotFoundException, DocumentException {

        Paragraph p = new Paragraph("(" + testEnum + " TEST)" + "Overhead time for " + studentID + ": " + formattedSeconds + " s " + formattedMillis + "ms\n", font);
        overheadDocument.add(p);
    }

    public static synchronized void addOverheadComment(String comment)
        throws FileNotFoundException, DocumentException {

        Paragraph p = new Paragraph(comment + "\n\n", font);
        overheadDocument.add(p);
    }

    public static synchronized void generateOverheadReport() {overheadDocument.close();}
}