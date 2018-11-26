package ch.devsecops.security.authentificationservice.application.security.filter;

import ch.devsecops.security.authentificationservice.application.security.exception.AuthentificationMethodNotSupportedException;
import ch.devsecops.security.authentificationservice.application.security.handler.FailLoginHandler;
import ch.devsecops.security.authentificationservice.application.security.handler.SuccessLoginHandler;
import ch.devsecops.security.authentificationservice.domaine.security.command.LoginRequestCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {


    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failHandler;

    public LoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
                                 AuthenticationFailureHandler failHandler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failHandler = failHandler;
        this.objectMapper = mapper;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request,response,authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        failHandler.onAuthenticationFailure(request,response,authException);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //authentification via post
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            log.warn("Authentication method not supported. Request method: " + request.getMethod());
            throw new AuthentificationMethodNotSupportedException("Authentication method not supported");
        }

        LoginRequestCommand loginRequestCommand = objectMapper.readValue(request.getReader(), LoginRequestCommand.class);

        if (StringUtils.isBlank(loginRequestCommand.getNomUtilisateur()) || StringUtils.isBlank(loginRequestCommand.getMotDePasse())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequestCommand.getNomUtilisateur(),
                loginRequestCommand.getMotDePasse());

        return this.getAuthenticationManager().authenticate(token);
    }
}
