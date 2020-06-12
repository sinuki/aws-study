package parksw.app.config.auth.dto;

import lombok.Getter;
import parksw.app.domain.user.User;

import java.io.Serializable;

/**
 * SessionUser
 * author: sinuki
 * createdAt: 2020/05/31
 **/
@Getter
public class SessionUser implements Serializable {

    private final String name;
    private final String email;
    private final String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
