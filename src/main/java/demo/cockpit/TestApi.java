package demo.cockpit;

import io.vanillabp.cockpit.commons.exceptions.BcUnauthorizedException;
import io.vanillabp.cockpit.commons.security.usercontext.UserContext;
import io.vanillabp.cockpit.commons.security.usercontext.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/api")
public class TestApi {

    @Autowired(required = false)
    private UserContext userContext;

    @GetMapping("/user-info")
    public ResponseEntity<UserDetails> getUserInfo() {
        if(userContext == null){
            return ResponseEntity.notFound().build();
        }

        try {
            return ResponseEntity.ok(userContext
                    .getUserLoggedInDetails());
        } catch (BcUnauthorizedException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
