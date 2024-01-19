package fontys.sem3.school.configuration.security.token.impl;

import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.domain.Enum.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Long userid;
    private final String role;
    private final Double balance;
    private final String profilepicture;

    public AccessTokenImpl(String subject, Long userid, String role,Double balance,String profilepicture) {
        this.subject = subject;
        this.userid = userid;
        this.role = role;
        this.balance = balance;
        this.profilepicture = profilepicture;

    }


}
