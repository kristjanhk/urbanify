package system;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.lowagie.text.pdf.BaseFont;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import system.data.Event;
import system.data.Lang;
import system.data.Word;

/**
 * Aruande genereerija
 *
 * //http://freemarker.org/docs/dgui.html
 * //http://freemarker.org/docs/pgui_quickstart.html
 */
public class ReportHandler {

    public boolean generatePdf(Event event) {
        try {
            this.generatePdf(generateTemplate(this.getFormattedData(event)),
                    new FileOutputStream(this.getFileLocation(event.getName() + "_" +
                            event.getFormattedDate().replaceAll("/", ".") + "_" +
                            event.getTime().replaceAll(":", "."))));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private File getFileLocation(String name) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save pdf file");
        fileChooser.setInitialDirectory(new File(MainHandler.getFileHandler().getPath()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialFileName(name);
        return fileChooser.showSaveDialog(MainHandler.getStageHandler().getStage());
    }

    public void generatePdf(String htmlStr, OutputStream out) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes()));
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont(System.getProperty("user.dir") +
                "\\src\\system\\graphics\\common\\ticketfont2.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocument(doc, null);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
        System.out.println("PDF OK");
    }

    public String generateTemplate(Map<String, Object> variables) throws Exception {
        Configuration config = new Configuration(Configuration.VERSION_2_3_24);
        config.setDirectoryForTemplateLoading(new File("src\\system\\data"));
        config.setDefaultEncoding("UTF-8");
        Template tp = config.getTemplate("report.ftl");
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        tp.process(variables, writer);
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        stringWriter.close();
        return htmlStr;
    }

    private Map<String, Object> getFormattedData(Event event) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("tickettype", Word.TICKETYPE.toString());
        variables.put("ticketprice", Word.PRICE.toString());
        variables.put("ticketamount", Word.QUANTITY.toString());
        variables.put("tickettotal", Word.TOTAL.toString());
        DataObject dataobject = new DataObject(event);
        variables.put("event", dataobject);
        variables.put("totalprice", String.format("%.2f", dataobject.getTotalprice()) +
                " " + Lang.getActiveLang().getCurrency());
        variables.put("totalamount", dataobject.getTotalamount());
        return variables;
    }

    public class DataObject {
        private String name;
        private String date;
        private String time;
        private List<String> tickettypes = new ArrayList<>();
        private List<String> ticketprices = new ArrayList<>();
        private List<String> ticketamounts = new ArrayList<>();
        private List<String> tickettotals = new ArrayList<>();
        private double totalprice;
        private int totalamount;

        public DataObject(Event event) {
            this.name = event.getName();
            this.date = event.getFormattedDate();
            this.time = event.getTime();
            for (String tickettype : event.getTickets().keySet()) {
                this.tickettypes.add(tickettype);
                double ticketprice = event.getTicketPrice(tickettype);
                int ticketamount = event.getTicketAmount(tickettype).intValue();
                this.ticketprices.add(String.format("%.2f", ticketprice) + " " + Lang.getActiveLang().getCurrency());
                this.ticketamounts.add(String.valueOf(ticketamount));
                this.tickettotals.add(String.format("%.2f", ticketprice * ticketamount) + " " +
                        Lang.getActiveLang().getCurrency());
                this.totalprice += ticketprice * ticketamount;
                this.totalamount += ticketamount;
            }
        }

        public String getName() {
            return this.name;
        }

        public String getDate() {
            return this.date;
        }

        public String getTime() {
            return this.time;
        }

        public List<String> getTickettypes() {
            return this.tickettypes;
        }

        public List<String> getTicketprices() {
            return this.ticketprices;
        }

        public List<String> getTicketamounts() {
            return this.ticketamounts;
        }

        public List<String> getTickettotals() {
            return this.tickettotals;
        }

        public double getTotalprice() {
            return this.totalprice;
        }

        public double getTotalamount() {
            return this.totalamount;
        }
    }
}