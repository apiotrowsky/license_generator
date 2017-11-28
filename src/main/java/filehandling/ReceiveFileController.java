package filehandling;

import license.jacksonTemplate.LicenseSummary;
import model.Configuration.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class ReceiveFileController {
    private FileConfigurationTypeEnum fileTypeEnum;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private FileProccesor fileProccesor;

    @Autowired
    private ConfigProperties prop;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json")
    public byte[] uploadFileHandler(@RequestParam("file") MultipartFile multipartFile, @RequestParam(value = "fileType") String fileType) {
        if (!multipartFile.isEmpty()) {
            fileTypeEnum = FileConfigurationTypeEnum.getInstanceIgnoringCase(fileType);
            try {
                File uploadedFile = fileManager.uploadFile(multipartFile);
                return fileProccesor.process(uploadedFile, fileTypeEnum);
            } catch (IOException e) {
                e.printStackTrace();
                return null;

            }
        } else {
            return null;
        }
    }
}
