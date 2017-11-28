package businessLogic;

import license.jacksonTemplate.LicenseSummary;

import java.io.File;

public interface ConfigurationFileService {
    File generateFiles(File file);
    LicenseSummary getJSON();
    File getGeneratedFiles();
}
