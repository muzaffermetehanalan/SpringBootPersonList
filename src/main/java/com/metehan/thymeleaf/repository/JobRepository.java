package com.metehan.thymeleaf.repository;

import com.metehan.thymeleaf.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface JobRepository extends  JpaRepository<Jobs,Integer> {

	
}