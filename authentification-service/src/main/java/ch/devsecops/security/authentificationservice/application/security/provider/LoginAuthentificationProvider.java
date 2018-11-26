package ch.devsecops.security.authentificationservice.application.security.provider;

import ch.devsecops.security.authentificationservice.application.security.service.UtilisateurService;
import ch.devsecops.security.authentificationservice.domaine.security.command.LoginRequestCommand;
import ch.devsecops.security.authentificationservice.domaine.security.entity.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

@Component
public class LoginAuthentificationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;
    private final UtilisateurService utilisateurService;

    @Autowired
    public LoginAuthentificationProvider(final UtilisateurService utilisateurService, final BCryptPasswordEncoder encoder) {
        this.utilisateurService = utilisateurService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Optional<Utilisateur> utilisateur = utilisateurService.authentifie(new LoginRequestCommand(username,password));



       // if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

       /* List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);
*/
        return new UsernamePasswordAuthenticationToken(utilisateur, null, null);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
