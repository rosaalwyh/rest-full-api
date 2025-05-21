package rosa_alawiyah.rest_full_api_belajar.service;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import rosa_alawiyah.rest_full_api_belajar.entity.User;
import rosa_alawiyah.rest_full_api_belajar.model.LoginUserRequest;
import rosa_alawiyah.rest_full_api_belajar.model.TokenResponse;
import rosa_alawiyah.rest_full_api_belajar.repository.UserRepository;
import rosa_alawiyah.rest_full_api_belajar.security.BCrypt;
import rosa_alawiyah.rest_full_api_belajar.security.TokenManager;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  ValidationService validationService;

    @Autowired
    private TokenManager tokenManager;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())) {

            String token = tokenManager.generateToken(request.getUsername());
            user.setToken(token);
            user.setTokenExpiredDate(next30Days());
            userRepository.save(user);
            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredDate(user.getTokenExpiredDate())
                    .build();
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }
    }

    private Long next30Days(){
        return System.currentTimeMillis() + (16*24*30*1000);
    }
}
