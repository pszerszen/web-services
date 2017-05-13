package com.osa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService implements UserDetailsService {

    @Value("${user.username}")
    private String username;

    @Autowired
    private AuthenticatedUser user;

    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        return Objects.equals(s, username) ? user : null;
    }
}