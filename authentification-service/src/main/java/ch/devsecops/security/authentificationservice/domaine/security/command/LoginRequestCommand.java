package ch.devsecops.security.authentificationservice.domaine.security.command;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class LoginRequestCommand {

    private String nomUtilisateur;
    private String motDePasse;
}
