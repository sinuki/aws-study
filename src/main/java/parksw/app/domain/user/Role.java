package parksw.app.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Role
 * author: sinuki
 * createdAt: 2020/05/30
 **/
@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
