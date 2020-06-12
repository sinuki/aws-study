package parksw.app.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository
 * author: sinuki
 * createdAt: 2020/05/30
 **/
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
