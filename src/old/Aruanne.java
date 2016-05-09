/*
package old;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Aruanne {
    //http://tutorials.jenkov.com/java-itext/getting-started.html
    private String text;

    public Aruanne(String text) {
        this.text = text;
    }

    public void looAruanne() {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("UusFailiNimi.pdf"));
            doc.open();
            doc.add(new Paragraph("Algab siit!"));
            doc.add(new Paragraph("Enda lisatud tekst: " + text));
            doc.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public void saadaEmail() {
        //http://stackoverflow.com/questions/3649014/send-email-using-java
        //otse läbi gmaili?
        //kohalikku meiliserverit ajutiselt luua ei ole mõtet
    }
}
*/
