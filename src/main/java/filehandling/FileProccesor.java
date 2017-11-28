package filehandling;

import businessLogic.impl.Maven.MavenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import license.jacksonTemplate.LicenseSummary;
import model.Configuration.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileProccesor {
    @Autowired
    ConfigProperties properties;

    @Autowired
    MavenService mvnService;

    public byte[] process(File file, FileConfigurationTypeEnum fileTypeEnum) {
        String json = null;
        byte[] byteArray = null;
        File zipFile = null;
                if(fileTypeEnum == FileConfigurationTypeEnum.MAVEN) {
            mvnService.generateFiles(file);
            LicenseSummary jacksonObj = mvnService.getJSON();
                    try {
                        zipFile = mvnService.getGeneratedFiles();
                        json = convertJacksonToStringJson(jacksonObj);
                        byteArray = Files.readAllBytes(zipFile.toPath());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//            zip = mvnService.getGeneratedFiles();
        }
    return byteArray;
    }
    private String convertJacksonToStringJson(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}
