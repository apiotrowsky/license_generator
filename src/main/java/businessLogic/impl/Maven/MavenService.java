package businessLogic.impl.Maven;

import businessLogic.ConfigurationFileService;
import businessLogic.impl.Maven.JSON.LicenseJackson;
import license.jacksonTemplate.LicenseSummary;
import misc.ZipDirectory;
import model.Configuration.ConfigProperties;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class MavenService implements ConfigurationFileService {

    @Autowired
    private ConfigProperties properties;

    private File genereatedFiles;

    private static final String pathToOutputLicensesDir = "target" + File.separator + "generated-resources" + File.separator + "licenses";
    private static final String pathToOutputLicensesTxtFile = File.separator + "target" + File.separator + "generated-sources" +
            File.separator + "license" + File.separator + "THIRD-PARTY.txt";


    private static final String mavenCommandGeneratingLicenseList = "license:add-third-party ";
    private static final String mavenCommandGeneretingLicenseFiles = "license:download-licenses "+
            "-DlicensesOutputDirectory=\"output\" " + "-DlicensesOutputFile=\"output" + File.separator + "licenses.xml\"";

    @Override
    public File generateFiles(File file) {
        File result = null;
        String mavenExePath = properties.getPaths().getMaven();
        String pathToTargetDir = properties.getPaths().getTargetDir();
        generateFileThirdPartDependeciesList(pathToTargetDir,mavenExePath);
        generateZipLicenseFiles(pathToTargetDir, mavenExePath);
        try {
            result = ZipDirectory.createZippedDirectory(properties.getPaths().getTargetDir() + File.separator + "output");
        } catch (IOException e) {
            e.printStackTrace();
        }
        genereatedFiles = result;
        return result;
    }

    @Override
    public LicenseSummary getJSON() {
        String pathToLicenseListXml = properties.getPaths().getTargetDir() + File.separator + "output" + File.separator + "licenses.xml";
        String jsonString = xmlFileToJsonString(pathToLicenseListXml);
        return jsonStringToJacksonObj(jsonString);
    }

    @Override
    public File getGeneratedFiles() {
        return genereatedFiles;
    }

    public void generateFileThirdPartDependeciesList(String pathToTargetDir, String mavenExePath) {
        String log = "";
        Process p = null;
        File dir = new File(pathToTargetDir);
        String fullCommand = mavenExePath + " " + mavenCommandGeneretingLicenseFiles;

        try {
            p = this.executeCommand(fullCommand, null, dir);
            log = this.getLogFromProcess(p);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(log);

    }

    public void generateZipLicenseFiles(String pathToTargetDir, String mavenExePath) {
        String log = "";
        Process p = null;
        File dir = new File(pathToTargetDir);
        String fullCommand = mavenExePath + " " + mavenCommandGeneretingLicenseFiles;
        try {
            p = this.executeCommand(fullCommand, null, dir);
            log = this.getLogFromProcess(p);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(log);

    }

    private String getLogFromProcess(Process p) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    private Process executeCommand(String command, String[] envp, File dir) throws IOException, InterruptedException {
        Process p = null;
        p = Runtime.getRuntime().exec(command, null, dir);
        p.waitFor();
        return p;
    }
    private String xmlFileToJsonString(String xmlFilePath){
        String output = "";
        try (InputStream inputStream = new FileInputStream(new File(
                xmlFilePath))) {
            String xml = IOUtils.toString(inputStream);
            JSONObject jObject = XML.toJSONObject(xml);
            System.out.println(jObject.toString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            output = mapper.writeValueAsString(json);
            return output;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  output;
    }
    private LicenseSummary jsonStringToJacksonObj(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        LicenseJackson jsonObj= null;
        try {
            jsonObj = mapper.readValue(jsonString, LicenseJackson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObj.getLicenseSummary();
    }
}
