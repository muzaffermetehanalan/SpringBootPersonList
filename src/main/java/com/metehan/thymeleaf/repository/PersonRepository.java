package com.metehan.thymeleaf.repository;

import com.metehan.thymeleaf.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface PersonRepository extends  JpaRepository<Person,Integer> {
    public Person findByEmail(String email);

    public Person findByEmailAndPassword (String email, String password);
	
}