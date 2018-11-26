package ch.devsecops.isp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.AuthenticationException;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@SpringBootApplication
public class IdentityServiceProviderApplication {

    @Autowired
    IdentityService identityService;

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceProviderApplication.class);
    }

    @PostConstruct
    public void setup(){
        log.info("Identity Service Provider setup");

        List<String> names = identityService.getAllPersonNames();
        log.info("names: " + names);

        List<?> namesWithGroups = identityService.getAllPersonNamesWithGroups();
        log.info("names: " + namesWithGroups);

        try{

            log.info(identityService.getByUid(new AuthCommand("sce","secret")).toString());
        }catch(AuthenticationException ex){
            log.error(ex.getMessage());
        }


    }

}
