package rosa_alawiyah.rest_full_api_belajar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rosa_alawiyah.rest_full_api_belajar.model.RegisterUserRequest;
import rosa_alawiyah.rest_full_api_belajar.model.Response;
import rosa_alawiyah.rest_full_api_belajar.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> register(@RequestBody RegisterUserRequest user) {
        userService.register(user);
        return Response.<String>builder().message("Successfully register user").build();
    }
}
