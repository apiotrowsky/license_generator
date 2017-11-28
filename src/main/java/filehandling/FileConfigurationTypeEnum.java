package filehandling;

import java.util.Arrays;

/**
 * Created by Adam on 2017-09-19.
 */
public enum FileConfigurationTypeEnum {
    MAVEN, GRADLE, SBT;

    public static FileConfigurationTypeEnum getInstanceIgnoringCase(String value) {
        return Arrays.stream(FileConfigurationTypeEnum.values())
                .filter(val->val.name().equalsIgnoreCase(value))
                .findAny()
                .orElse(null);
    }
 
}
