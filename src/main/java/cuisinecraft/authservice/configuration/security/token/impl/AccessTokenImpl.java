package cuisinecraft.authservice.configuration.security.token.impl;

import cuisinecraft.authservice.configuration.security.token.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Long userid;
    private final String role;


    public AccessTokenImpl(String subject, Long userid, String role) {
        this.subject = subject;
        this.userid = userid;
        this.role = role;


    }


}
