package system;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import system.data.Event;
import system.data.Lang;

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
                    new FileOutputStream(this.getFileLocation(event.getName())));
            return true;
        } catch (Exception ignored) {}
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
        renderer.setDocument(doc, null);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
        System.out.println("PDF OK");
    }

    public String generateTemplate(Map<String, Object> variables) throws Exception {
        // TODO: 9.05.2016 parse event into variables
        Configuration config = new Configuration(Configuration.VERSION_2_3_24);
        config.setDirectoryForTemplateLoading(new File("src\\system\\data"));
        config.setDefaultEncoding("UTF-8");
        Template tp = config.getTemplate("report2.ftl");
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
        variables.put("event", new DataObject(event));
        return variables;
    }


    public class DataObject {
        private String name;
        private String date;
        private String time;
        private List<String> tickettypes = new ArrayList<>();
        private List<String> ticketprices = new ArrayList<>();
        private List<String> ticketamounts = new ArrayList<>();

        public DataObject(Event event) {
            this.name = event.getName();
            this.date = event.getFormattedDate();
            this.time = event.getTime();
            for (String tickettype : event.getTickets().keySet()) {
                this.tickettypes.add(tickettype);
                this.ticketprices.add(String.format("%.2f", event.getTicketPrice(tickettype)) + " " +
                        Lang.getActiveLang().getCurrency());
                this.ticketamounts.add(String.valueOf(event.getTicketAmount(tickettype).intValue()));
            }
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public List<String> getTickettypes() {
            return tickettypes;
        }

        public List<String> getTicketprices() {
            return ticketprices;
        }

        public List<String> getTicketamounts() {
            return ticketamounts;
        }
    }
}