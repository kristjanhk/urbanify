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

/**
 * Aruande genereerija
 */
public class ReportHandler {
    //https://github.com/xiang-lee/pdf-generator
    //http://freemarker.org/docs/dgui.html
    //http://freemarker.org/docs/pgui_quickstart.html
    private Map<String, Object> variables = new HashMap<>();
    private String templates_location = "src\\system\\data";
    private String report_template = "report.ftl";

    public ReportHandler() throws Exception {
        variables.put("users", createUserList());
    }

    public void generatePdf(Event event) {
        try {
            generatePdf(generateTemplate(event), new FileOutputStream(getFileLocation(event.getName())));
        } catch (Exception e) {
            // TODO: 9.05.2016 ignore filenotfoundexception, fixme else 
            e.printStackTrace();
        }
    }

    private File getFileLocation(String name) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save pdf file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
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

    public String generateTemplate(Event event) throws Exception {
        // TODO: 9.05.2016 parse event into variables
        Configuration config = new Configuration(Configuration.VERSION_2_3_24);
        config.setDirectoryForTemplateLoading(new File(this.templates_location));
        config.setDefaultEncoding("UTF-8");
        Template tp = config.getTemplate(this.report_template);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        tp.process(variables, writer);
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        stringWriter.close();
        return htmlStr;
    }


    private List<User> createUserList() {
        User user1 = createUser(1, "Luffy", 12);
        User user2 = createUser(2, "Jonh", 34);
        User user3 = createUser(3, "Tom", 26);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

    private User createUser(long id, String username, int age) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setAge(age);
        return user;
    }

    public class User {
        private long id;
        private String username;
        private int age;

        public int getAge() {
            return age;
        }

        public long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}