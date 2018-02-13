package com.metehan.thymeleaf.controller;
import com.metehan.thymeleaf.model.Jobs;
import com.metehan.thymeleaf.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metehan.thymeleaf.repository.JobRepository;
import com.metehan.thymeleaf.repository.PersonRepository;
import com.metehan.thymeleaf.service.MailService;


import java.util.List;


import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

 
@Controller
public class MainController {
	@Autowired
     	private MailService mailService;
	//private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	 @Autowired
	 private PersonRepository pRepo;
	 @Autowired
	 private JobRepository jRepo;
    @Value("${welcome.message}")
    private String message;
 
    @Value("${error.message}")
    private String errorMessage;
    
    @Value("${job.message}")
    private String jobMessage;
    @Value("${jobS.message}")
    private String jobSMessage;
    /*
    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index(Model model,HttpSession session) {
    		Person isAdmin = (Person) session.getAttribute("kisi");
    		if(isAdmin == null)
    			return "RegisterAndLogin";
    		
    		model.addAttribute("person",isAdmin);
        return "index";
    } */
 
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model,HttpSession session) {
    
    		Person isAdmin = (Person) session.getAttribute("kisi");
		model.addAttribute("p",isAdmin);
		model.addAttribute("persons", pRepo.findAll());
    		if(isAdmin == null)
    			return "RegisterAndLogin";
    		
    		if (isAdmin.getMemberType().equalsIgnoreCase("admin")) {
    	            return "personList";
    	     }

        return "userPersonList";
    }
    @RequestMapping(value = { "/allJobsList" }, method = RequestMethod.GET)
    public String allJobsList(Model model,HttpSession session) {
    
    		Person isAdmin = (Person) session.getAttribute("kisi");
    		if(isAdmin == null)
    			return "RegisterAndLogin";
    		model.addAttribute("jobs",jRepo.findAll());

    		return "allJobsList";
    }
    
    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model,HttpSession session) {
    		Person isAdmin = (Person) session.getAttribute("kisi");
    		if(isAdmin == null) {
    			return "RegisterAndLogin";
    		}
    		
        Person personForm = new Person();
        model.addAttribute("person", personForm);
 
        return "addPerson";
    } 
 	
    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, //
        @ModelAttribute("person") Person personForm) {
 
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
        		//personForm.setMemberType("user");
            pRepo.save(personForm);  
            return "redirect:/personList";
       	}
        model.addAttribute("errorMessage", errorMessage);
        return "addPerson"; 
    }
    
    @RequestMapping(value = { "/deletePerson" }, method = RequestMethod.GET)
    public String deletePerson(Model model, //
    		@RequestParam(name="personId")String personId) {
    		
    			Person per = pRepo.findOne(Integer.valueOf(personId));
    			List <Jobs> jobList = per.getJobs();
    			while ( jobList.size() != 0) {
    				jobList.get(0).removePerson(per);
    			}
            pRepo.delete((Integer.valueOf(personId)));  
            return "redirect:/personList";        
    }
    
    
    @RequestMapping(value = {"update_person" } , method = RequestMethod.GET)
    public ModelAndView update_person(@RequestParam(name="per")String id , HttpSession session) {
		Person isAdmin = (Person) session.getAttribute("kisi");
		ModelAndView modelAndView = new ModelAndView("updatePerson");
		if(isAdmin == null) {
			
			modelAndView.setViewName("RegisterAndLogin");
			return modelAndView;
		}
    		int integerId = Integer.valueOf(id);

    		modelAndView.addObject("per",pRepo.findOne(integerId));
    		modelAndView.addObject("jobs",jRepo.findAll());
    		return modelAndView;
    } 
     
     @RequestMapping(value = {"updatePerson"}, method = RequestMethod.GET)
    public String updatePerson(@ModelAttribute Person per,@RequestParam(required=false)  List<String> listJobs,Model model){
		Jobs tempJob = new Jobs();
    		Person newP = pRepo.findOne(Integer.valueOf(per.getPerson_id()));
    		if(listJobs != null) {
    			for(int i = 0 ; i<listJobs.size() ; i++) {
    				tempJob  = jRepo.findOne(Integer.valueOf(listJobs.get(i)));
    				if(! tempJob.getPeople().contains(newP)) {
    					tempJob.addPerson(per);
    					jRepo.save(tempJob);
    				}	
    			}
    		}

		newP = pRepo.findOne(Integer.valueOf(per.getPerson_id()));
        try {
			mailService.sendEmail(newP);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	pRepo.save(per);
        
        return "redirect:/personList";
    }
    
    @RequestMapping(value = {"job_person" } , method = RequestMethod.GET)
    public ModelAndView job_person(@RequestParam(name="per")String id , HttpSession session) {
		Person isAdmin = (Person) session.getAttribute("kisi");
		ModelAndView modelAndView = new ModelAndView("jobPerson");
		if(isAdmin == null) {
			modelAndView.setViewName("RegisterAndLogin");
			return modelAndView;
		} 
		modelAndView.addObject("jobs",pRepo.findOne(Integer.valueOf(id)).getJobs());
		return modelAndView;
		
    	
    }
    
    @RequestMapping(value = { "/addJob" }, method = RequestMethod.GET)
    public String showAddJobPage(Model model,HttpSession session) {
    		Person isAdmin = (Person) session.getAttribute("kisi");
    		if(isAdmin == null) {
    			return "RegisterAndLogin";
    		}
    		
        Person personForm = new Person();
        model.addAttribute("person", personForm);
 
        return "addJob";
    } 
    
 	
    @RequestMapping(value = { "/addJob" }, method = RequestMethod.POST)
    public String saveJob(Model model, //
        @ModelAttribute("job") Jobs job) {
 
    		String jobName = job.getJobName();
        if (jobName != null &&jobName.length() > 0) {
        		//personForm.setMemberType("user");
            jRepo.save(job);  
            model.addAttribute("jobSMessage", jobSMessage);
            return "addJob";
       	}
        model.addAttribute("jobMessage", jobMessage);
        return "addJob"; 
    }
    
   
   
 
}
