package ch.devsecops.isp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/auth")
@RestController
public class IdentityServiceController {


    @Autowired
    IdentityService identityService;

    @PostMapping
    public ResponseEntity authentifie(@RequestBody AuthCommand authCommand){

        if(identityService.getByUid(authCommand).isPresent()){
            return ResponseEntity.ok(identityService.getByUid(authCommand));
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
