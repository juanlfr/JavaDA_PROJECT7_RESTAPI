package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@Service
public final class UtilsService {
    /**
     * Static method that identifies the authenticated user whether authentication by login/password or OAuth2 Authentication
     * @return the user name of the authenticated user
     */
    public static String getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            if (authentication.isAuthenticated()) {
                User u = (User) authentication.getPrincipal();
                return u.getUsername();
            }
        } else if (authentication instanceof OAuth2AuthenticationToken) {
            if (authentication.isAuthenticated()) {
                OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) authentication);
                Map<String, Object> userAttributes = authToken.getPrincipal().getAttributes();
                return (String) userAttributes.get("name");
            }
        }
        return "Authenticated user";
    }

}



