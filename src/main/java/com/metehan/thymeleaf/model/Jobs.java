
package com.metehan.thymeleaf.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="jobs" ,schema ="myapp")
public class Jobs {
	@ManyToMany
	private List<Person> people;
	
	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="JOBID")
	private int jobId;
	@Column(name="JOBNAME")
	private String jobName;
	
	
	
	    public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public void addPerson(Person person)
	{
		if(this.people==null)
		this.people = new ArrayList<Person>();
		this.people.add(person);
		
	}
	
	 
    public void removePerson(Person person) {
        people.remove(person);
        person.getJobs().remove(this);
    }



}
