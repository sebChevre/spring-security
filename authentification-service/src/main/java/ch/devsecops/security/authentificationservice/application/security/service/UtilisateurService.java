package ch.devsecops.security.authentificationservice.application.security.service;

import ch.devsecops.security.authentificationservice.domaine.security.command.LoginRequestCommand;
import ch.devsecops.security.authentificationservice.domaine.security.entity.Utilisateur;

import java.util.Optional;

public interface UtilisateurService {
    Optional<Utilisateur> authentifie(LoginRequestCommand loginRequestCommand);
}
