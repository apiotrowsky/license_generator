package filehandling;

import lombok.Data;

@Data
public class UploadFileControllerResponse {

    private byte[] fileByteArray;
    private String json;

    public UploadFileControllerResponse(byte[] fileByteArray, String json) {
        this.fileByteArray = fileByteArray;
        this.json = json;
    }
}
