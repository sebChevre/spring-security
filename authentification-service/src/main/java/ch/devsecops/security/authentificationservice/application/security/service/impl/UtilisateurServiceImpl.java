package ch.devsecops.security.authentificationservice.application.security.service.impl;

import ch.devsecops.security.authentificationservice.application.security.service.UtilisateurService;
import ch.devsecops.security.authentificationservice.domaine.security.command.LoginRequestCommand;
import ch.devsecops.security.authentificationservice.domaine.security.entity.Utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private  RestTemplate restTemplate;

    @Override
    public Optional<Utilisateur> authentifie(LoginRequestCommand loginRequestCommand) {

        ResponseEntity resp = restTemplate.postForEntity("http://locahost:9999/auth",
                new LoginRequestCommand(loginRequestCommand.getNomUtilisateur(),loginRequestCommand.getMotDePasse()), Utilisateur.class);

        log.info(resp.toString());

        return Optional.of((Utilisateur) resp.getBody());

    }
}
