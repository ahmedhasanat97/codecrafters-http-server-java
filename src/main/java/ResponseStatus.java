import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    OK(200, "OK"),
    NOT_FOUND(404, "NOT_FOUND");

    private final int code;
    private final String text;

}
