package filehandling;

import lombok.Getter;
import lombok.Setter;
import model.Configuration.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by Adam on 2017-09-19.
 */

public class ConfigurationFile {
    private File file;
    FileConfigurationTypeEnum fileTypeEnum;
    private @Getter File generatedTxtFileLicenseNameList;
    private @Getter File generatedZipLicensesFile;

    protected ConfigurationFile(File file, FileConfigurationTypeEnum fileTypeEnum) {
        this.file = file;
        this.fileTypeEnum = fileTypeEnum;
    }
}
