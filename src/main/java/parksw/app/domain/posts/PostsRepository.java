package parksw.app.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PostsRepository
 * author: sinuki
 * createdAt: 2020/04/30
 **/
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
