package rosa_alawiyah.rest_full_api_belajar.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rosa_alawiyah.rest_full_api_belajar.entity.Contact;
import rosa_alawiyah.rest_full_api_belajar.entity.User;

import java.util.List;
import java.util.Optional;

@Repository

public interface ContactRepository extends JpaRepository<Contact, String> {
    Optional<Contact> findFirstByUserAndId(User user, String id);

    List<Contact> findAllByUser(User user);
}
