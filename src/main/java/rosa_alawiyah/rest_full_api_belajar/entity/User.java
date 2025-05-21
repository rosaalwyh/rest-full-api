package rosa_alawiyah.rest_full_api_belajar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

public class User {
    @Id
    private String username;
    private String password;
    private String name;
    private String token;

    @Column(name = "token_expired_date")
    private Long tokenExpiredDate;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

}
