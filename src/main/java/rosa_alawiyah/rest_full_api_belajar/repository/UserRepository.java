package rosa_alawiyah.rest_full_api_belajar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rosa_alawiyah.rest_full_api_belajar.entity.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findFirstByToken(String token);
}
