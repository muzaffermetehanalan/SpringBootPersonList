
package com.metehan.thymeleaf.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="jobs")
public class Jobs {
	@ManyToMany
    	@JoinTable(
        name = "Job_Person", 
        joinColumns = { @JoinColumn(name = "jobId") }, 
        inverseJoinColumns = { @JoinColumn(name = "person_id") }
    	)
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
