package rosa_alawiyah.rest_full_api_belajar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rosa_alawiyah.rest_full_api_belajar.model.LoginUserRequest;
import rosa_alawiyah.rest_full_api_belajar.model.Response;
import rosa_alawiyah.rest_full_api_belajar.model.TokenResponse;
import rosa_alawiyah.rest_full_api_belajar.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(
            path = "/api/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<TokenResponse> login(@RequestBody LoginUserRequest request){
        TokenResponse tokenResponse =  authService.login(request);
        return Response.<TokenResponse>builder().data(tokenResponse).build();
    }

}
