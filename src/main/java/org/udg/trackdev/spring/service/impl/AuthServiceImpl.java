package org.udg.trackdev.spring.service.impl;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.udg.trackdev.spring.configuration.AuthorizationConfiguration;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.service.AuthService;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthorizationConfiguration authorizationConfiguration;

    @Override
    public String getLoggedInUserId(Principal principal) {
        checkLoggedIn(principal);
        return principal.getName();
    }

    @Override
    public void checkLoggedIn(Principal principal) {
        if(principal == null)
            throw new ControllerException(ErrorConstants.USER_NOT_LOGGED_IN);
    }

    @Override
    public String generateJWT(User user) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        int durationInMinutes = authorizationConfiguration.getTokenLifetimeInMinutes();
        int durationInMilliseconds = durationInMinutes * 60 * 1000;

        String token = Jwts
                .builder()
                .setId(Constants.COOKIE_NAME)
                .setSubject(user.getId())
                .claim(Constants.S_AUTHORITIES,
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + durationInMilliseconds))
                .signWith(authorizationConfiguration.getKey())
                .compact();

        return Constants.BEARER + token;
    }
}
