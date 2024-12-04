package cuisinecraft.authservice.configuration.security.token;

public interface AccessToken {
    String getSubject();

    Long getUserid();

    String getRole();



}
