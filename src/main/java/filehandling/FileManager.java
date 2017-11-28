package filehandling;

import model.Configuration.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adam on 2017-09-28.
 */
@Component
public class FileManager {

    @Autowired
    private ConfigProperties properties;

    private String pathToTargetDir;

    private File targetFile;

    public File uploadFile(MultipartFile multipartFile) throws IOException {
        pathToTargetDir = properties.getPaths().getTargetDir();
        targetFile = saveFile(multipartFile);
        return targetFile;
    }

    private File createEmptyFileInTargetDir(String fileName) {
        String fullPath = pathToTargetDir + File.separator + fileName;
        File newFile = new File(fullPath);
        return newFile;
    }

    private File saveFile(MultipartFile multipartFile) throws IOException {
        File targetFile = createEmptyFileInTargetDir(multipartFile.getOriginalFilename());
        multipartFile.transferTo(targetFile);
        return targetFile;
    }
}
