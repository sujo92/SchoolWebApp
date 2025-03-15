package com.example.SchoolWebApp.service;

import com.example.SchoolWebApp.constants.EazySchoolConstants;
import com.example.SchoolWebApp.model.Person;
import com.example.SchoolWebApp.model.Roles;
import com.example.SchoolWebApp.repository.PersonRepository;
import com.example.SchoolWebApp.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean createNewUser(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(EazySchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if(person != null && person.getPersonId() > 0){
            isSaved = true;
        }
        return isSaved;
    }
}
