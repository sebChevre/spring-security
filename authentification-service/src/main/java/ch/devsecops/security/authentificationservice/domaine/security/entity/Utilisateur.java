package ch.devsecops.security.authentificationservice.domaine.security.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Utilisateur {

    private String nomUtilisateur;
    private String motDePasse;
}
