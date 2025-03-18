package com.example.SchoolWebApp.security;

import com.example.SchoolWebApp.model.Person;
import com.example.SchoolWebApp.model.Roles;
import com.example.SchoolWebApp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolUsernamePwdAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Person person = personRepository.readByEmail(email);
        if(person != null && person.getPersonId()>0 && passwordEncoder.matches(pwd,person.getPwd())){
            return new UsernamePasswordAuthenticationToken(email, null, getGrantedAuthorities(person.getRoles()));
        }else{
            throw new  BadCredentialsException("Invalid credentials!");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));
        return grantedAuthorityList;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
