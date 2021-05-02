package swc3.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private final int statusCode;
    private final Date timestamp;
    private final String message;
    private final String description;
}
