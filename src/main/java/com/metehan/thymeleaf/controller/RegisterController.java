package com.metehan.thymeleaf.controller;
import com.metehan.thymeleaf.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.metehan.thymeleaf.repository.PersonRepository;

import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
public class RegisterController {
    @Value("${success.message}")
    private String successMessage;
    @Value("${error.message}")
    private String errorMessage;
    @Value("${login.message}")
    private String loginError;
	 @Autowired
	 private PersonRepository pRepo;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginPage(){
        return "RegisterAndLogin";
    }
    
    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute("person3")Person personForm ){
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        System.out.println(firstName);
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
        		
        		personForm.setMemberType("user");
        		if(personForm.getPassword().equals("admin123")) {
        			personForm.setMemberType("admin");
        		}
            pRepo.save(personForm); 
            model.addAttribute("successMessage", successMessage);
            return "RegisterAndLogin";
       	}
        model.addAttribute("errorMessage", errorMessage); 
    		return "RegisterAndLogin";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("person") Person person, Model model, HttpSession session){
    	    	if(pRepo.findByEmailAndPassword(person.getEmail(), person.getPassword())== null) {
    	            model.addAttribute("loginError",loginError);
    	            return "RegisterAndLogin";
    	    	}
    	    	Person sessionPerson = pRepo.findByEmail(person.getEmail());
    	    	session.setAttribute("kisi",sessionPerson);
        return "redirect:/personList";	
    }
    
   
}

