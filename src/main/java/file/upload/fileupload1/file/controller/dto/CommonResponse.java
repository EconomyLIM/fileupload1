package file.upload.fileupload1.file.controller.dto;

/**
 * date           : 2025-01-21
 * created by     : 임경재
 * description    :
 */
public class CommonResponse {
    private String code;
    private String message;

    public CommonResponse() {
        this.code = "OK";
        this.message = null;
    }

    public CommonResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResponse of(final String code, final String message) {
        return new CommonResponse(code, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
