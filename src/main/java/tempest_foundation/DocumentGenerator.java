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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tempest_foundation.ClassElements.ClassDetails;
import tempest_foundation.ClassElements.Function;
import tempest_foundation.SubmissionElements.Submission;

public class DocumentGenerator {
    /**
        Creates a new PDF document of type PDF and adds content by appending chunks
    */
    public void createDocument(Submission s) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        new File("..//comp3607project//Submissions//PDF Reports").mkdirs();
        PdfWriter.getInstance(document, new FileOutputStream( "..//comp3607project//Submissions//PDF Reports//" + s.getStudentId() + ".pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(s.getStudentId(), font);
        Paragraph paragraph = new Paragraph("This is a test to see how a pdf would look with a paragraph and a chunk",font);
        chunk.append(" " + s.getGrade() + "%");
        document.add(chunk);
        document.add(paragraph);
        //trying to create a table


        // Add cells to the table
        PdfPCell cell1 = new PdfPCell(new Paragraph("Class", font));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Method", font));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Method's Score", font));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Comment", font));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Total", font));
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
            for (Function f:currentClass.getFunctions()) { // Loop through methods for the current class
                // Class Name (only shown in the first column for the first method of each class)
                
                currentRow.addCell(f.toString());
                currentRow.addCell(Double.toString(f.getGrade()));
                String comments="";
                for(String comment:f.getComment())
                    comments+=comment +"\n";
                comments=comments.strip();
                currentRow.addCell(comments);
                currentRow.addCell("100?");
                // classCell.setRowspan(currentClass.getFunctions().size()); // Span across all methods for this class
                // classCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                // classCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                
                

            }
            document.add(currentRow);
            document.newPage();
        }

        // Add the table to the document
        


        document.close();
    }
}
