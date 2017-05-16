package com.osa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class CombinedAuthenticationProvider implements AuthenticationProvider {

    private final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    public CombinedAuthenticationProvider(AuthenticationService userDetailsService, PasswordEncoder passwordEncoder) {
        preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(userDetailsService);

        daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> authenticationClass = authentication.getClass();

        if (preAuthenticatedAuthenticationProvider.supports(authenticationClass)) {
            return preAuthenticatedAuthenticationProvider.authenticate(authentication);
        } else if (daoAuthenticationProvider.supports(authenticationClass)) {
            return daoAuthenticationProvider.authenticate(authentication);
        }
        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return preAuthenticatedAuthenticationProvider.supports(authentication) ||
                daoAuthenticationProvider.supports(authentication);
    }
}
