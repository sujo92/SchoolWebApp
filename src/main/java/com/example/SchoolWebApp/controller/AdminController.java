package com.example.SchoolWebApp.controller;

import com.example.SchoolWebApp.model.EClass;
import com.example.SchoolWebApp.model.Person;
import com.example.SchoolWebApp.repository.EClassRepository;
import com.example.SchoolWebApp.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    EClassRepository eClassRepository;

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        List<EClass> eClasses = eClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("eClass", new EClass());
        modelAndView.addObject("eClasses", eClasses);
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("eClass") EClass eClass){
        eClassRepository.save(eClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam Integer id){
        Optional<EClass> eClasses = eClassRepository.findById(id);
        for(Person person : eClasses.get().getPersons()){
            person.setEClass(null);
            personRepository.save(person);
        }
        eClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/displayStudents")
    public ModelAndView displayStudent(Model model, @RequestParam int classId, HttpSession session, @RequestParam(value="error", required = false) String error){
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("student.html");
        Optional<EClass> eClass = eClassRepository.findById(classId);
        modelAndView.addObject("eClass",eClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("eClass",eClass.get());
        if(error != null){
            errorMessage = "Invalid email entered!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(HttpSession session, Model model, @ModelAttribute("person") Person person){
        ModelAndView modelAndView = new ModelAndView();
        EClass eClass = (EClass) session.getAttribute("eClass");
        Person person1 = personRepository.readByEmail(person.getEmail());
        if(person1 == null || !(person1.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eClass.getClassId()+"&error=true");
            return modelAndView;
        }
        person1.setEClass(eClass);
        personRepository.save(person1);
        eClass.getPersons().add(person1);
        eClassRepository.save(eClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model,@RequestParam int personId, HttpSession session){
        EClass eClass = (EClass) session.getAttribute("eClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setEClass(null);
        eClass.getPersons().remove(person);
        EClass eClassSaved = eClassRepository.save(eClass);
        session.setAttribute("eClass", eClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+eClassSaved.getClassId());
        return modelAndView;
    }
}
