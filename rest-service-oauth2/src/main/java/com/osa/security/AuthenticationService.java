package com.osa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService implements UserDetailsService, AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final AuthenticatedUser user;
    private final TokenStore tokenStore;

    @Autowired
    public AuthenticationService(final AuthenticatedUser user, final TokenStore tokenStore) {
        this.user = user;
        this.tokenStore = tokenStore;
    }

    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        return Objects.equals(s, user.getUsername()) ? user : null;
    }

    @Override
    public UserDetails loadUserDetails(final PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication((String) token.getPrincipal());
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();

        return authenticationToken.isAuthenticated() ? (UserDetails) authenticationToken.getPrincipal() : null;
    }
}
