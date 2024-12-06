package http.server.dtos.request;

public class RequestBody {

    private String content;

    public RequestBody() {
    }

    public RequestBody(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
