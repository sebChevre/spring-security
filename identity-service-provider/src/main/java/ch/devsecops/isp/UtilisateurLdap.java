package ch.devsecops.isp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "password" })
public class UtilisateurLdap {

    private String uid;
    private String password;
    private String nomComplet;
    private List<?> groupes;
}
