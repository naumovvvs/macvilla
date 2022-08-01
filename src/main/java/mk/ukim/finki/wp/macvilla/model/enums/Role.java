package mk.ukim.finki.wp.macvilla.model.enums;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT, ROLE_HOTELIER;


    @Override
    public String getAuthority() {
        return name();
    }
}
