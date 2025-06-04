package rosa_alawiyah.rest_full_api_belajar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import rosa_alawiyah.rest_full_api_belajar.entity.Contact;
import rosa_alawiyah.rest_full_api_belajar.entity.User;
import rosa_alawiyah.rest_full_api_belajar.model.ContactResponse;
import rosa_alawiyah.rest_full_api_belajar.model.CreateContactRequest;
import rosa_alawiyah.rest_full_api_belajar.model.UpdateContactRequest;
import rosa_alawiyah.rest_full_api_belajar.repository.ContactRepository;
import rosa_alawiyah.rest_full_api_belajar.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<ContactResponse> list(){
        return contactRepository.findAll().stream()
                .map(
                contact -> ContactResponse.builder()
                        .id(contact.getId())
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public ContactResponse create(User user, CreateContactRequest request) {
        validationService.validate(request);

        User existingUser = userRepository.findById(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user"));
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(existingUser);

        contactRepository.save(contact);

        return toContactResponse(contact);
    }

    private ContactResponse toContactResponse(Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(User user, String id) {

        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        return toContactResponse(contact);

    }

    @Transactional(readOnly = true)
    public ContactResponse get(User user, String id) {
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        return toContactResponse(contact);
    }

    @Transactional
    public ContactResponse update(User user, UpdateContactRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contactRepository.save(contact);

        return toContactResponse(contact);
    }


    @Transactional
    public void delete(User user, String id) {
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contactRepository.delete(contact);
    }


}
