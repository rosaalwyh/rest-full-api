package rosa_alawiyah.rest_full_api_belajar.controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rosa_alawiyah.rest_full_api_belajar.entity.User;
import rosa_alawiyah.rest_full_api_belajar.model.ContactResponse;
import rosa_alawiyah.rest_full_api_belajar.model.CreateContactRequest;
import rosa_alawiyah.rest_full_api_belajar.model.Response;
import rosa_alawiyah.rest_full_api_belajar.model.UpdateContactRequest;
import rosa_alawiyah.rest_full_api_belajar.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ContactResponse>> list(User user) {

        List<ContactResponse> contactResponses = contactService.list(user);

        return Response.<List<ContactResponse>>builder()
                .data(contactResponses)
                .message("Successfully get contacts")
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ContactResponse> create (User user, @Valid @RequestBody CreateContactRequest request){
        ContactResponse contactResponse = contactService.create(user, request);
        return Response.<ContactResponse>builder()
                .data(contactResponse)
                .message("Successfully create contact")
                .build();
    }


    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ContactResponse> get(User user, @PathVariable("id") String id) {
        ContactResponse contactResponse = contactService.get(user, id);
        return Response.<ContactResponse>builder().data(contactResponse).build();
    }


    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<ContactResponse> update(User user,
                                               @RequestBody UpdateContactRequest request,
                                               @PathVariable("id") String id) {

        request.setId(id);

        ContactResponse contactResponse = contactService.update(user, request);
        return Response.<ContactResponse>builder().data(contactResponse).build();
    }


    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> delete(User user, @PathVariable("id") String id) {
        contactService.delete(user, id);
        return Response.<String>builder().data("OK").build();
    }

}
