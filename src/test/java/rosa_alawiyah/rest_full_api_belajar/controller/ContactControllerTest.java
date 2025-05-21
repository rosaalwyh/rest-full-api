package rosa_alawiyah.rest_full_api_belajar.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rosa_alawiyah.rest_full_api_belajar.entity.Contact;
import rosa_alawiyah.rest_full_api_belajar.entity.User;
import rosa_alawiyah.rest_full_api_belajar.model.*;
import rosa_alawiyah.rest_full_api_belajar.repository.ContactRepository;
import rosa_alawiyah.rest_full_api_belajar.repository.UserRepository;
import rosa_alawiyah.rest_full_api_belajar.security.BCrypt;
import rosa_alawiyah.rest_full_api_belajar.security.TokenManager;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ContactControllerTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenManager tokenManager;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("rosa");
        user.setPassword(BCrypt.hashpw("rosa12345", BCrypt.gensalt()));
        user.setName("rosa");
        this.token = tokenManager.generateToken(user.getUsername());
        user.setToken(this.token);
        user.setTokenExpiredDate(System.currentTimeMillis() + 1000000);
        userRepository.save(user);
    }

    @Test
    void createContactBadRequest() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + this.token)

        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrorMessages());
        });

    }

    @Test
    void createContactSuccess() throws Exception{
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Rosa");
        request.setLastName("Alawiyah");
        request.setEmail("rosa@gmail.com");
        request.setPhone("6281212121");
        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + this.token)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<ContactResponse> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrorMessages());
            assertEquals("Rosa", response.getData().getFirstName());
            assertEquals("Alawiyah", response.getData().getLastName());
            assertEquals("rosa@gmail.com", response.getData().getEmail());
            assertEquals("6281212121", response.getData().getPhone());

            assertTrue(contactRepository.existsById(response.getData().getId()));
        });


    }

    @Test
    void getContactNotFound() throws Exception {
        mockMvc.perform(
                get("/api/contacts/23123123123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.token)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Response<String>>() {
            });
            assertNotNull(response.getErrorMessages());
        });
    }

    @Test
    void getContactSuccess() throws Exception {

        User user = new User();
        String token = tokenManager.generateToken(user.getUsername());
        user.setUsername("rosa");
        user.setName("rosa");
        user.setToken(token);
        user.setPassword(BCrypt.hashpw("test12345", BCrypt.gensalt()));
        user.setTokenExpiredDate(System.currentTimeMillis() + 1000000);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("rosa");
        contact.setLastName("alawiyah");
        contact.setEmail("rosa@gmail.com");
        contact.setPhone("628912716821");
        contactRepository.save(contact);

        mockMvc.perform(
                get("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.token)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrorMessages());

            assertEquals(contact.getId(), response.getData().getId());
            assertEquals(contact.getFirstName(), response.getData().getFirstName());
            assertEquals(contact.getLastName(), response.getData().getLastName());
            assertEquals(contact.getEmail(), response.getData().getEmail());
            assertEquals(contact.getPhone(), response.getData().getPhone());
        });
    }

    @Test
    void updateContactBadRequest() throws Exception {
        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                put("/api/contacts/1234")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + this.token)
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Response<String>>() {
            });
            assertNotNull(response.getErrorMessages());
        });
    }

    @Test
    void updateContactSuccess() throws Exception {
        User user = new User();
        user.setUsername("rosa");
        user.setName("rosa");
        user.setToken(token);
        user.setPassword(BCrypt.hashpw("test12345", BCrypt.gensalt()));
        user.setTokenExpiredDate(System.currentTimeMillis() + 1000000);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Rosa");
        contact.setLastName("Alawiyah");
        contact.setEmail("rosaa@gmail.com");
        contact.setPhone("9238423432");
        contactRepository.save(contact);

        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Budi");
        request.setLastName("Nugraha");
        request.setEmail("budi@example.com");
        request.setPhone("23123123");

        mockMvc.perform(
                put("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + this.token)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrorMessages());
            assertEquals(request.getFirstName(), response.getData().getFirstName());
            assertEquals(request.getLastName(), response.getData().getLastName());
            assertEquals(request.getEmail(), response.getData().getEmail());
            assertEquals(request.getPhone(), response.getData().getPhone());

            assertTrue(contactRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void deleteContactNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/contacts/23123123123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.token)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Response<String>>() {
            });
            assertNotNull(response.getErrorMessages());
        });
    }

    @Test
    void deleteContactSuccess() throws Exception {
        User user = new User();
        user.setUsername("rosa");
        user.setName("rosa");
        user.setToken(this.token);
        user.setPassword(BCrypt.hashpw("test12345", BCrypt.gensalt()));
        user.setTokenExpiredDate(System.currentTimeMillis() + 1000000);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Rosa");
        contact.setLastName("alawiyah");
        contact.setEmail("rosa@gmail.com");
        contact.setPhone("9238423432");
        contactRepository.save(contact);

        mockMvc.perform(
                delete("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.token)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrorMessages());
            assertEquals("OK", response.getData());
        });
    }
}
