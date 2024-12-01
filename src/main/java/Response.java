import java.util.Objects;

public class Request {

    private String path;

    public Request() {
    }

    public Request(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(path, request.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

}
