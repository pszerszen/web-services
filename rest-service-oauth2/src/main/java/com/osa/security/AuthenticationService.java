package com.osa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
        return Objects.equals(s, user.getUsername()) ? user : new InvalidUser();
    }

    @Override
    public UserDetails loadUserDetails(final PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        return (UserDetails) Optional.ofNullable(token)
                .map(PreAuthenticatedAuthenticationToken::getPrincipal)
                .map(Object::toString)
                .map(tokenStore::readAuthentication)
                .map(OAuth2Authentication::getUserAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .orElseGet(InvalidUser::new);
    }
}
