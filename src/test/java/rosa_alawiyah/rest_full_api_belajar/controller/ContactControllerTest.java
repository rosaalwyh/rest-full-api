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
import rosa_alawiyah.rest_full_api_belajar.entity.User;
import rosa_alawiyah.rest_full_api_belajar.model.ContactResponse;
import rosa_alawiyah.rest_full_api_belajar.model.CreateContactRequest;
import rosa_alawiyah.rest_full_api_belajar.model.RegisterUserRequest;
import rosa_alawiyah.rest_full_api_belajar.model.Response;
import rosa_alawiyah.rest_full_api_belajar.repository.ContactRepository;
import rosa_alawiyah.rest_full_api_belajar.repository.UserRepository;
import rosa_alawiyah.rest_full_api_belajar.security.BCrypt;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("rosa");
        user.setPassword(BCrypt.hashpw("rosa12345", BCrypt.gensalt()));
        user.setName("rosa");
        user.setToken("test");
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
                        .header("X-API-TOKEN", "test")
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
                        .header("X-API-TOKEN", "test")
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
}
