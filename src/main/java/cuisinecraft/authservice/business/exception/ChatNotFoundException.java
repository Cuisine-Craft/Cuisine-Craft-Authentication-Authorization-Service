package cuisinecraft.authservice.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ChatNotFoundException extends ResponseStatusException {
    public ChatNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "CHAT_DOESN'T_EXIST");
    }
}
