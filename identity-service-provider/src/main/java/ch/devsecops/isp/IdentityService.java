package ch.devsecops.isp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Slf4j
@Service
public class IdentityService {

    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    LdapContextSource contextSource;


    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
     */
    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get() );
    }

    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
     */
    public List<ArrayList<?>> getAllPersonNamesWithGroups() {

        //Get the attribute of user's "memberOf"
        List<ArrayList<?>> membersOf = ldapTemplate.search(
                query().where("objectclass").is("person"),
                (AttributesMapper<ArrayList<?>>) attrs -> {
                    return Collections.list(attrs.get("memberOf").getAll());
                }
        );

        return membersOf;
    }

    private boolean authentifie (AuthCommand authCommand) {

        log.info("AuthCommand:{}", authCommand);

        try{
            contextSource.getContext("uid=" + authCommand.getUid() + ",ou=personne,dc=devsecops,dc=ch" ,
                    authCommand.getPass());
            return Boolean.TRUE;
        }catch (AuthenticationException ex){
            return Boolean.FALSE;
        }

    }

    private Optional<UtilisateurLdap> chargePersonne (AuthCommand authCommand) {

        log.info("AuthCommand:{}", authCommand);

        List<UtilisateurLdap> utilisateurLdaps = ldapTemplate
                .search(
                        "ou=personne,dc=devsecops,dc=ch",
                        "uid=" + authCommand.getUid(),
                        (AttributesMapper<UtilisateurLdap>) attrs -> {

                            List<?> l = Collections.list(attrs.get("memberOf").getAll());

                            byte[] a = (byte[]) attrs.get("userPassword").get();

                            return new UtilisateurLdap((String) attrs.get("uid").get(),
                                    new String(a),
                                    (String) attrs.get("cn").get(),
                                    l);
                        });

        if(utilisateurLdaps.isEmpty()){
            return Optional.empty();
        }else{
            log.info("Utilisateur find :{}", utilisateurLdaps.get(0));
            return Optional.of(utilisateurLdaps.get(0));
        }

    }

    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
     */
    public Optional<UtilisateurLdap> getByUid(AuthCommand authCommand) {

        if(authentifie(authCommand)){
            return chargePersonne(authCommand);
        }else{
            return Optional.empty();
        }

    }




}
