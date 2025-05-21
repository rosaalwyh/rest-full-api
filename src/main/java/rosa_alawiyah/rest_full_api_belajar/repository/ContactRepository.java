package rosa_alawiyah.rest_full_api_belajar.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rosa_alawiyah.rest_full_api_belajar.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

}
