package system;

import java.io.*;
import java.util.*;

import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.pdfboxout.PdfBoxRenderer;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.TemplateExceptionHandler;
import javafx.stage.FileChooser;
import org.jsoup.Jsoup;

import freemarker.template.Configuration;
import freemarker.template.Template;
import system.data.Event;
import system.data.Lang;
import system.data.Word;

/**
 * Aruande genereerija
 * http://freemarker.org/
 * https://github.com/danfickle/openhtmltopdf
 */
public class ReportHandler {
    private Configuration cfg;

    public ReportHandler() {
        this.cfg = new Configuration(Configuration.VERSION_2_3_24);
        this.cfg.setClassForTemplateLoading(ReportHandler.class, "data");
        this.cfg.setDefaultEncoding("UTF-8");
        this.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        this.cfg.setLogTemplateExceptions(false);
    }

    public boolean generatePdf(Event event, Map<String, Object> variables) {
        try {
            this.generatePdf(generateTemplate(this.getFormattedData(event, variables)),
                    new FileOutputStream(this.getFileLocation(event.getName() + "_" +
                            event.getFormattedDate().replaceAll("/", ".") + "_" +
                            event.getTime().replaceAll(":", "."))));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private File getFileLocation(String name) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Word.PDF.toString());
        fileChooser.setInitialDirectory(new File(MainHandler.getFileHandler().getPath()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialFileName(name);
        return fileChooser.showSaveDialog(MainHandler.getSecondaryStageHandler().getStage());
    }

    private void generatePdf(String htmlString, OutputStream out) throws Exception {
        PdfRendererBuilder builder = new PdfRendererBuilder();
        PdfBoxRenderer renderer = builder.buildPdfRenderer();
        renderer.getFontResolver().addFont(
                this.getClass().getResourceAsStream("graphics/common/TicketFont.ttf"), "TicketFont");
        renderer.setDocument(DOMBuilder.jsoup2DOM(Jsoup.parse(htmlString)), null);
        renderer.layout();
        renderer.createPDF(out);
        renderer.finishPDF();
        out.close();
    }

    private String generateTemplate(Map<String, Object> variables) throws Exception {
        Template tp = this.cfg.getTemplate("report.ftl", "UTF-8");
        StringWriter stringWriter = new StringWriter();
        tp.setOutputEncoding("UTF-8");
        tp.process(variables, stringWriter);
        stringWriter.flush();
        String htmlStr = stringWriter.toString();
        stringWriter.close();
        System.out.println("6");
        return htmlStr;
    }

    private Map<String, Object> getFormattedData(Event event, Map<String, Object> formattedExtras) {
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
        for (String key : formattedExtras.keySet()) {
            variables.put(key, formattedExtras.get(key));
        }
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
                int ticketamount = event.getTicketAmount(tickettype);
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