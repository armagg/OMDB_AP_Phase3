package api;

public class Content {

    private final String content;
    private final int httpCode;


    public Content(String content, int httpCode) {
        this.content = content;
        this.httpCode = httpCode;
    }

    public String getContent() {
        return content;
    }

    public int getHttpCode() {
        return httpCode;
    }
}