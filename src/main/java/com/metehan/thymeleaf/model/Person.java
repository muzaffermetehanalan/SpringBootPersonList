package com.metehan.thymeleaf.model;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="person" , schema = "personlistspring")
public class Person implements Serializable {
	
	@ManyToMany(mappedBy="people")
	private List<Jobs> jobs;
	
	public List<Jobs> getJobs() {
		return jobs;
	}



	public void setJobs(List<Jobs> jobs) {
		this.jobs = jobs;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="person_id")
    private int person_id;  
	@Column(name="firstname")
    private String firstName;
	@Column(name="lastname")
    private String lastName;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="memberType")
	private String memberType;
	public String getEmail() {
		return email;
	}
	
	

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

 
    public Person() {
 
    }
 
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
        
}
 
