package rosa_alawiyah.rest_full_api_belajar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rosa_alawiyah.rest_full_api_belajar.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
}
