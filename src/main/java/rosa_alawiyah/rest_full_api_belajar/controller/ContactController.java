package rosa_alawiyah.rest_full_api_belajar.controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rosa_alawiyah.rest_full_api_belajar.entity.User;
import rosa_alawiyah.rest_full_api_belajar.model.ContactResponse;
import rosa_alawiyah.rest_full_api_belajar.model.CreateContactRequest;
import rosa_alawiyah.rest_full_api_belajar.model.Response;
import rosa_alawiyah.rest_full_api_belajar.service.ContactService;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(
            path = "/api/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ContactResponse> create (User user, @Valid @RequestBody CreateContactRequest request){
        ContactResponse contactResponse = contactService.create(user, request);
        return Response.<ContactResponse>builder().data(contactResponse).build();
    }


    @GetMapping(
            path = "/api/contacts/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ContactResponse> get(User user, @PathVariable("id") String id) {
        ContactResponse contactResponse = contactService.get(user, id);
        return Response.<ContactResponse>builder().data(contactResponse).build();
    }

}
